package club.tempvs.profile.controller;

import club.tempvs.profile.domain.Profile;
import club.tempvs.profile.dto.ProfileDto;
import club.tempvs.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("/api/profile")
public class ProfileController {

    private final ConversionService mvcConversionService;
    private final ProfileService profileService;

    @PostMapping
    public ProfileDto create(@RequestBody ProfileDto profileDto) {
        Profile profile = mvcConversionService.convert(profileDto, Profile.class);
        Profile persistentProfile = profileService.create(profile);
        return mvcConversionService.convert(persistentProfile, ProfileDto.class);
    }
}
