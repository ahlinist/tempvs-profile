package club.tempvs.profile.converter;

import static java.util.stream.Collectors.toList;

import club.tempvs.profile.domain.Passport;
import club.tempvs.profile.domain.Profile;
import club.tempvs.profile.dto.PassportDto;
import club.tempvs.profile.dto.ProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileToDtoConverter implements Converter<Profile, ProfileDto> {

    private final PassportToDtoConverter passportToDtoConverter;

    @Override
    public ProfileDto convert(Profile source) {
        ProfileDto target = new ProfileDto();
        BeanUtils.copyProperties(source, target);
        List<Passport> passports = source.getPassports();

        if (passports != null) {
            List<PassportDto> passportDtos = passports.stream()
                    .map(passportToDtoConverter::convert)
                    .collect(toList());
            target.setPassports(passportDtos);
        }

        return target;
    }
}
