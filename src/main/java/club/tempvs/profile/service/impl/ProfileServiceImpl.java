package club.tempvs.profile.service.impl;

import club.tempvs.profile.component.ProfileValidator;
import club.tempvs.profile.component.UserHolder;
import club.tempvs.profile.dao.ProfileRepository;
import club.tempvs.profile.domain.Profile;
import club.tempvs.profile.domain.Profile.Type;
import club.tempvs.profile.service.ProfileService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private static final int MAX_CLUB_PROFILE_COUNT = 10;

    private final ProfileRepository profileRepository;
    private final ProfileValidator profileValidator;
    private final UserHolder userHolder;

    @Override
    public Profile createUserProfile(Profile profile) {
        profileValidator.validateUserProfile(profile);
        Long userId = userHolder.getUserId();

        if(findUserProfileByUserId(userId).isPresent()) {
            throw new IllegalStateException(String.format("User with id %d already has user profile", userId));
        }

        profile.setType(Type.USER);
        profile.setUserId(userId);
        profile.setIsActive(Boolean.TRUE);

        return save(profile);
    }

    @Override
    public Profile createClubProfile(Profile profile) {
        Long userId = userHolder.getUserId();

        if(countClubProfilesByUserId(userId) >= MAX_CLUB_PROFILE_COUNT) {
            throw new IllegalStateException(String.format("User with id %d has too many club profiles", userId));
        }

        profile.setType(Type.CLUB);
        profile.setUserId(userId);
        profile.setIsActive(Boolean.TRUE);

        return save(profile);
    }

    @Override
    public Profile get(Long id) {
        return fetchProfile(id);
    }

    @Override
    public Profile getUserProfile() {
        Long userId = userHolder.getUserId();
        return findUserProfileByUserId(userId)
                .get();
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
    private Profile fetchProfile(Long id) {
        return profileRepository.findById(id)
                .get();
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
    private Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
    private Optional<Profile> findUserProfileByUserId(Long userId) {
        return profileRepository.findByTypeAndUserId(Type.USER, userId);
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
    private int countClubProfilesByUserId(Long userId) {
        return profileRepository.countByTypeAndUserId(Type.CLUB, userId);
    }
}
