package club.tempvs.profile.converter;

import club.tempvs.profile.domain.Passport;
import club.tempvs.profile.dto.PassportDto;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PassportToDtoConverter implements Converter<Passport, PassportDto> {
    
    @Override
    public PassportDto convert(Passport source) {
        PassportDto target = new PassportDto();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
