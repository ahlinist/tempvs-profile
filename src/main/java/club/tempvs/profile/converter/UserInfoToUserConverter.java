package club.tempvs.profile.converter;

import club.tempvs.profile.dto.UserInfoDto;
import club.tempvs.profile.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserInfoToUserConverter implements Converter<UserInfoDto, User> {

    @Override
    public User convert(UserInfoDto source) {
        User target = new User();
        target.setId(source.getUserId());
        return target;
    }
}
