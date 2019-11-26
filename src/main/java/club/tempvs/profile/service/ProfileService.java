package club.tempvs.profile.service;

import club.tempvs.profile.domain.Profile;

import java.util.List;

public interface ProfileService {

    Profile createUserProfile(Profile profile);

    Profile createClubProfile(Profile profile);

    Profile get(Long id);

    Profile getUserProfile();

    List<Profile> getClubProfiles(Long userId);
}
