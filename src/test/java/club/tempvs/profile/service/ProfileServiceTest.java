package club.tempvs.profile.service;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

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

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private UserHolder userHolder;

    @Mock
    private Profile profile;
    @Mock
    private User user;

    @Test
    public void testCreate() {
        Long userId = 1L;

        when(userHolder.getUser()).thenReturn(user);
        when(user.getId()).thenReturn(userId);
        when(profileRepository.save(profile)).thenReturn(profile);

        Profile result = profileService.create(profile);

        verify(profileRepository).save(profile);
        verify(userHolder).getUser();
        verifyNoMoreInteractions(profileRepository, userHolder);

        assertEquals("Profile object is returned", profile, result);
    }
}
