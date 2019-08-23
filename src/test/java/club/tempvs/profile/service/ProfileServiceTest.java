package club.tempvs.profile.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import club.tempvs.profile.component.ProfileValidator;
import club.tempvs.profile.domain.Profile.Type;
import club.tempvs.profile.component.UserHolder;
import club.tempvs.profile.dao.ProfileRepository;
import club.tempvs.profile.domain.Profile;
import club.tempvs.profile.model.User;
import club.tempvs.profile.service.impl.ProfileServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.NoSuchElementException;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private UserHolder userHolder;
    @Mock
    private ProfileValidator profileValidator;

    @Mock
    private Profile profile;
    @Mock
    private User user;

    @Test
    public void testCreateClubProfile() {
        Long userId = 1L;

        when(userHolder.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(userId);
        when(profile.getType()).thenReturn(Type.CLUB);
        when(profileRepository.countByTypeAndUserId(Type.CLUB, userId)).thenReturn(9);
        when(profileRepository.save(profile)).thenReturn(profile);

        Profile result = profileService.create(profile);

        verify(userHolder).getUser();
        verify(profileRepository).countByTypeAndUserId(Type.CLUB, userId);
        verify(profileRepository).save(profile);
        verifyNoMoreInteractions(profileRepository, userHolder);

        assertEquals("Profile object is returned", profile, result);
    }

    @Test
    public void testCreateUserProfile() {
        Long userId = 1L;

        when(userHolder.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(userId);
        when(profile.getType()).thenReturn(Type.USER);
        when(profileRepository.findByTypeAndUserId(Type.USER, userId)).thenReturn(Optional.empty());
        when(profileRepository.save(profile)).thenReturn(profile);

        Profile result = profileService.create(profile);

        verify(userHolder).getUser();
        verify(profileRepository).findByTypeAndUserId(Type.USER, userId);
        verify(profileRepository).save(profile);
        verify(profileValidator).validateUserProfile(profile);
        verifyNoMoreInteractions(profileRepository, userHolder, profileValidator);

        assertEquals("Profile object is returned", profile, result);
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateUserProfileWhenTheOneAlreadyExists() {
        Long userId = 1L;

        when(userHolder.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(userId);
        when(profile.getType()).thenReturn(Type.USER);
        when(profileRepository.findByTypeAndUserId(Type.USER, userId)).thenReturn(Optional.of(profile));

        profileService.create(profile);
    }

    @Test(expected = IllegalStateException.class)
    public void testCreateClubProfileOutOfLimits() {
        Long userId = 1L;

        when(userHolder.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(userId);
        when(profile.getType()).thenReturn(Type.CLUB);
        when(profileRepository.countByTypeAndUserId(Type.CLUB, userId)).thenReturn(10);

        profileService.create(profile);
    }

    @Test
    public void testGet() {
        Long id = 1L;

        when(profileRepository.findById(id)).thenReturn(Optional.of(profile));

        Profile result = profileService.get(id);

        verify(profileRepository).findById(id);
        verifyNoMoreInteractions(profileRepository);

        assertEquals("Profile object is returned", profile, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetForMissingProfile() {
        Long id = 1L;

        when(profileRepository.findById(id)).thenReturn(Optional.empty());

        profileService.get(id);
    }

    @Test
    public void testGetUserProfile() {
        Long userId = 1L;

        when(userHolder.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(userId);
        when(profileRepository.findByTypeAndUserId(Type.USER, userId)).thenReturn(Optional.of(profile));

        Profile result = profileService.getUserProfile();

        verify(profileRepository).findByTypeAndUserId(Type.USER, userId);
        verifyNoMoreInteractions(profileRepository);

        assertEquals("Profile object is returned", profile, result);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetUserProfileForNoResult() {
        Long userId = 1L;

        when(userHolder.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(userId);
        when(profileRepository.findByTypeAndUserId(Type.USER, userId)).thenReturn(Optional.empty());

        profileService.getUserProfile();
    }
}
