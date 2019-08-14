package club.tempvs.profile.service.impl;

import club.tempvs.profile.component.UserHolder;
import club.tempvs.profile.dao.ProfileRepository;
import club.tempvs.profile.domain.Profile;
import club.tempvs.profile.model.User;
import club.tempvs.profile.service.ProfileService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;
    private final UserHolder userHolder;

    @Override
    public Profile create(Profile profile) {
        User user = userHolder.getUser();
        profile.setId(null);
        profile.setUserId(user.getId());
        profile.setIsActive(Boolean.TRUE);
        //TODO: validate single user profile

        return save(profile);
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE")
    })
    private Profile save(Profile profile) {
        return profileRepository.save(profile);
    }
}
