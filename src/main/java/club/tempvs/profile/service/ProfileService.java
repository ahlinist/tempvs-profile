package club.tempvs.profile.service;

import club.tempvs.profile.domain.Profile;

public interface ProfileService {

    Profile create(Profile profile);

    Profile get(Long id);
}
