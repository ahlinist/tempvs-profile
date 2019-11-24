package club.tempvs.profile.service;

import club.tempvs.profile.domain.Profile;

public interface ProfileService {

    Profile createUserProfile(Profile profile);

    Profile createClubProfile(Profile profile);

    Profile get(Long id);

    Profile getUserProfile();
}
