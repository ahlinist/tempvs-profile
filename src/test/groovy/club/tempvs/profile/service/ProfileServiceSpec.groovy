package club.tempvs.profile.service

import club.tempvs.profile.component.ProfileValidator
import club.tempvs.profile.component.UserHolder
import club.tempvs.profile.dao.ProfileRepository
import club.tempvs.profile.domain.Profile
import club.tempvs.profile.domain.Profile.Type
import club.tempvs.profile.service.impl.ProfileServiceImpl
import spock.lang.Specification
import spock.lang.Subject

class ProfileServiceSpec extends Specification {

    UserHolder userHolder = Mock UserHolder
    ProfileValidator profileValidator = Mock ProfileValidator
    ProfileRepository profileRepository = Mock ProfileRepository

    @Subject
    ProfileService profileService = new ProfileServiceImpl(userHolder, profileValidator, profileRepository)

    Profile profile = Mock Profile

    def "create club profile"() {
        given:
        Long userId = 1L

        when:
        Profile result = profileService.createClubProfile(profile)

        then:
        1 * userHolder.userId >> userId
        1 * profileRepository.countByTypeAndUserId(Type.CLUB, userId) >> 9
        1 * profile.setType(Type.CLUB)
        1 * profile.setUserId(userId)
        1 * profile.setIsActive(Boolean.TRUE)
        1 * profileRepository.save(profile) >> profile
        0 * _

        and:
        result == profile
    }

    def "create user profile"() {
        given:
        Long userId = 1L

        when:
        Profile result = profileService.createUserProfile(profile)

        then:
        1 * profileValidator.validateUserProfile(profile)
        1 * userHolder.userId >> userId
        1 * profileRepository.findByTypeAndUserId(Type.USER, userId) >> Optional.empty()
        1 * profile.setType(Type.USER)
        1 * profile.setUserId(userId)
        1 * profile.setIsActive(Boolean.TRUE)
        1 * profileRepository.save(profile) >> profile
        0 * _

        and:
        result == profile
    }

    def "create a user profile duplicate"() {
        given:
        Long userId = 1L

        when:
        profileService.createUserProfile(profile)

        then:
        1 * profileValidator.validateUserProfile(profile)
        1 * userHolder.userId >> userId
        1 * profileRepository.findByTypeAndUserId(Type.USER, userId) >> Optional.of(profile)
        0 * _

        and:
        Exception exception = thrown IllegalStateException
        exception.message == 'User with id 1 already has user profile'
    }

    def "create 11th club profile"() {
        given:
        Long userId = 1L

        when:
        profileService.createClubProfile(profile)

        then:
        1 * userHolder.userId >> userId
        1 * profileRepository.countByTypeAndUserId(Type.CLUB, userId) >> 10
        0 * _

        and:
        Exception exception = thrown IllegalStateException
        exception.message == 'User with id 1 has too many club profiles'
    }

    def "get profile"() {
        given:
        Long id = 1L

        when:
        Profile result = profileService.get(id)

        then:
        1 * profileRepository.findById(id) >> Optional.of(profile)
        0 * _

        and:
        result == profile
    }

    def "get missing profile"() {
        given:
        Long id = 1L

        when:
        profileService.get(id)

        then:
        1 * profileRepository.findById(id) >> Optional.empty()
        0 * _

        and:
        Exception exception = thrown NoSuchElementException
        exception.message == 'No value present'
    }

    def "get user profile"() {
        given:
        Long userId = 1L

        when:
        Profile result = profileService.userProfile

        then:
        1 * userHolder.userId >> userId
        1 * profileRepository.findByTypeAndUserId(Type.USER, userId) >> Optional.of(profile)
        0 * _

        and:
        result == profile
    }

    def "get missing user profile"() {
        given:
        Long userId = 1L

        when:
        Profile result = profileService.userProfile

        then:
        1 * userHolder.userId >> userId
        1 * profileRepository.findByTypeAndUserId(Type.USER, userId) >> Optional.empty()
        0 * _

        and:
        Exception exception = thrown NoSuchElementException
        exception.message == 'No value present'
    }
}
