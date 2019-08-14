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

        profile.setId(null);
        profile.setUserId(userId);
        profile.setIsActive(Boolean.TRUE);

        return save(profile);
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
        return profileRepository.findUserProfileByUserId(userId);
    }
}
