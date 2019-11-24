package club.tempvs.profile.controller;

import club.tempvs.profile.domain.Profile;
import club.tempvs.profile.dto.ProfileDto;
import club.tempvs.profile.dto.validation.Scope;
import club.tempvs.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final ConversionService mvcConversionService;
    private final ProfileService profileService;

    @PostMapping("/user-profile")
    public ProfileDto createUserProfile(@Validated(Scope.Create.UserProfile.class) @RequestBody ProfileDto profileDto) {
        Profile profile = mvcConversionService.convert(profileDto, Profile.class);
        Profile persistentProfile = profileService.createUserProfile(profile);
        return mvcConversionService.convert(persistentProfile, ProfileDto.class);
    }

    @PostMapping("/club-profile")
    public ProfileDto createClubProfile(@Validated(Scope.Create.ClubProfile.class) @RequestBody ProfileDto profileDto) {
        Profile profile = mvcConversionService.convert(profileDto, Profile.class);
        Profile persistentProfile = profileService.createClubProfile(profile);
        return mvcConversionService.convert(persistentProfile, ProfileDto.class);
    }

    @GetMapping("/profile/{id}")
    public ProfileDto get(@PathVariable Long id) {
        Profile profile = profileService.get(id);
        return mvcConversionService.convert(profile, ProfileDto.class);
    }

    @GetMapping("/profile")
    public ProfileDto getUserProfile() {
        Profile profile = profileService.getUserProfile();
        return mvcConversionService.convert(profile, ProfileDto.class);
    }
}
