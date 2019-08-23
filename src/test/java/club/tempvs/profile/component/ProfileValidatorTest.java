package club.tempvs.profile.component;

import club.tempvs.profile.domain.Profile;
import club.tempvs.profile.domain.Profile.Type;
import club.tempvs.profile.domain.Profile.Period;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

@RunWith(MockitoJUnitRunner.class)
public class ProfileValidatorTest {

    @InjectMocks
    private ProfileValidator profileValidator;

    @Mock
    private MessageSource messageSource;
    @Mock
    private ObjectMapper objectMapper;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testValidateUserProfile() {
        Profile profile = new Profile();
        profile.setFirstName("first name");
        profile.setLastName("last name");
        profile.setPeriod(Period.ANTIQUITY);
        profile.setType(Type.USER);

        profileValidator.validateUserProfile(profile);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateUserProfileForMissingFirstName() {
        Profile profile = new Profile();
        profile.setLastName("last name");
        profile.setPeriod(Period.ANTIQUITY);
        profile.setType(Type.USER);

        profileValidator.validateUserProfile(profile);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testValidateUserProfileForMissingLastName() {
        Profile profile = new Profile();
        profile.setFirstName("first name");
        profile.setPeriod(Period.ANTIQUITY);
        profile.setType(Type.USER);

        profileValidator.validateUserProfile(profile);
    }
}
