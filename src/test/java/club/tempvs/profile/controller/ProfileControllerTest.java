package club.tempvs.profile.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import club.tempvs.profile.domain.Profile;
import club.tempvs.profile.dto.ProfileDto;
import club.tempvs.profile.service.ProfileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.convert.ConversionService;

@RunWith(MockitoJUnitRunner.class)
public class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private ProfileService profileService;
    @Mock
    private ConversionService conversionService;

    @Mock
    private Profile profile;
    @Mock
    private ProfileDto profileDto;

    @Test
    public void testCreate() {
        when(conversionService.convert(profileDto, Profile.class)).thenReturn(profile);
        when(profileService.create(profile)).thenReturn(profile);
        when(conversionService.convert(profile, ProfileDto.class)).thenReturn(profileDto);

        ProfileDto result = profileController.create(profileDto);

        verify(conversionService).convert(profileDto, Profile.class);
        verify(profileService).create(profile);
        verify(conversionService).convert(profile, ProfileDto.class);
        verifyNoMoreInteractions(conversionService, profileService);

        assertEquals("ProfileDto is returned", profileDto, result);
    }

    @Test
    public void testGet() {
        Long id = 1L;

        when(profileService.get(id)).thenReturn(profile);
        when(conversionService.convert(profile, ProfileDto.class)).thenReturn(profileDto);

        ProfileDto result = profileController.get(id);

        verify(profileService).get(id);
        verify(conversionService).convert(profile, ProfileDto.class);
        verifyNoMoreInteractions(conversionService, profileService);

        assertEquals("ProfileDto is returned", profileDto, result);
    }

    @Test
    public void testGetUserProfile() {
        Long userId = 1L;

        when(profileService.getUserProfile()).thenReturn(profile);
        when(conversionService.convert(profile, ProfileDto.class)).thenReturn(profileDto);

        ProfileDto result = profileController.getUserProfile();

        verify(profileService).getUserProfile();
        verify(conversionService).convert(profile, ProfileDto.class);
        verifyNoMoreInteractions(conversionService, profileService);

        assertEquals("ProfileDto is returned", profileDto, result);
    }
}
