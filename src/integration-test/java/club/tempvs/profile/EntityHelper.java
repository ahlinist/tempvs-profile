package club.tempvs.profile;

import club.tempvs.profile.dao.ProfileRepository;
import club.tempvs.profile.domain.Profile;
import club.tempvs.profile.domain.Profile.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityHelper {

    @Autowired
    private ProfileRepository profileRepository;

    public Profile createProfile(Long userId, String firstName, String lastName, Type type) {
        Profile profile = new Profile();
        profile.setUserId(userId);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setType(type);
        return profileRepository.save(profile);
    }
}
