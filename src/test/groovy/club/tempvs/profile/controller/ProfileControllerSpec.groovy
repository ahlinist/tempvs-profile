package club.tempvs.profile.controller

import club.tempvs.profile.domain.Profile
import club.tempvs.profile.dto.ProfileDto
import club.tempvs.profile.service.ProfileService
import org.springframework.core.convert.ConversionService
import spock.lang.Specification
import spock.lang.Subject

class ProfileControllerSpec extends Specification {

    ConversionService mvcConversionService = Mock ConversionService
    ProfileService profileService = Mock ProfileService

    @Subject
    ProfileController profileController = new ProfileController(mvcConversionService, profileService)

    Profile profile = Mock Profile
    ProfileDto profileDto = Mock ProfileDto

    def "create user profile"() {
        when:
        ProfileDto result = profileController.createUserProfile(profileDto)

        then:
        1 * mvcConversionService.convert(profileDto, Profile.class) >> profile
        1 * profileService.createUserProfile(profile) >> profile
        1 * mvcConversionService.convert(profile, ProfileDto.class) >> profileDto
        0 * _

        and:
        result == profileDto
    }

    def "create club profile"() {
        when:
        ProfileDto result = profileController.createClubProfile(profileDto)

        then:
        1 * mvcConversionService.convert(profileDto, Profile.class) >> profile
        1 * profileService.createClubProfile(profile) >> profile
        1 * mvcConversionService.convert(profile, ProfileDto.class) >> profileDto
        0 * _

        and:
        result == profileDto
    }

    def "get profile"() {
        given:
        Long id = 1

        when:
        ProfileDto result = profileController.get(id)

        then:
        1 * profileService.get(id) >> profile
        1 * mvcConversionService.convert(profile, ProfileDto.class) >> profileDto
        0 * _

        and:
        result == profileDto
    }

    def "get user profile"() {
        when:
        ProfileDto result = profileController.userProfile

        then:
        1 * profileService.userProfile >> profile
        1 * mvcConversionService.convert(profile, ProfileDto.class) >> profileDto
        0 * _

        and:
        result == profileDto
    }

    def "get club profiles"() {
        given:
        Long userId = 1L
        List<ProfileDto> profileDtos = [profileDto]

        when:
        List<ProfileDto> result = profileController.getClubProfiles(userId)

        then:
        1 * profileService.getClubProfiles(userId) >> [profile]
        1 * mvcConversionService.convert(profile, ProfileDto) >> profileDto
        0 * _

        and:
        result == profileDtos
    }
}