package club.tempvs.profile.component;

import club.tempvs.profile.domain.Profile;
import club.tempvs.profile.dto.ErrorsDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class ProfileValidator {

    private static final String FIRST_NAME_FIELD = "firstName";
    private static final String LAST_NAME_FIELD = "lastName";
    private static final String FIRST_NAME_BLANK = "profile.firstName.blank.error";
    private static final String LAST_NAME_BLANK = "profile.lastName.blank.error";

    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void validateUserProfile(Profile profile) {
        ErrorsDto errorsDto = new ErrorsDto();

        if (StringUtils.isBlank(profile.getFirstName())) {
            String errorMessage = translateError(FIRST_NAME_BLANK);
            errorsDto.addError(FIRST_NAME_FIELD, errorMessage);
        }

        if (StringUtils.isBlank(profile.getLastName())) {
            String errorMessage = translateError(LAST_NAME_BLANK);
            errorsDto.addError(LAST_NAME_FIELD, errorMessage);
        }

        if (!errorsDto.getErrors().isEmpty()) {
            throw new IllegalArgumentException(objectMapper.writeValueAsString(errorsDto));
        }
    }

    private String translateError(String messageKey) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(messageKey, null, messageKey, locale);
    }
}
