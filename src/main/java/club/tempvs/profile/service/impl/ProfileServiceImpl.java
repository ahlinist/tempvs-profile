package club.tempvs.profile.service.impl;

import club.tempvs.profile.component.UserHolder;
import club.tempvs.profile.dao.ProfileRepository;
import club.tempvs.profile.domain.Profile;
import club.tempvs.profile.domain.Profile.Type;
import club.tempvs.profile.model.User;
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
    private final UserHolder userHolder;

    @Override
    public Profile create(Profile profile) {
        User user = userHolder.getUser();
        Long userId = user.getId();

        if (profile.getType() == Type.USER) {
            if(findUserProfileByUserId(userId).isPresent()) {
                throw new IllegalStateException(String.format("User with id %d already has user profile", userId));
            }
        }

        if (profile.getType() == Type.CLUB) {
            if(countClubProfilesByUserId(userId) >= MAX_CLUB_PROFILE_COUNT) {
                throw new IllegalStateException(String.format("User with id %d has too many club profiles", userId));
            }
        }

        profile.setId(null);
        profile.setUserId(userId);
        profile.setIsActive(Boolean.TRUE);

        return save(profile);
    }

    @Override
    public Profile get(Long id) {
        return fetchProfile(id);
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
