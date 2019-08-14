package club.tempvs.profile.converter;

import club.tempvs.profile.domain.Profile;
import club.tempvs.profile.dto.ProfileDto;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToProfileConverter implements Converter<ProfileDto, Profile> {

    @Override
    public Profile convert(ProfileDto source) {
        Profile target = new Profile();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
