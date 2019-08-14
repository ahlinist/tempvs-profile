package club.tempvs.profile.interceptor;

import club.tempvs.profile.component.UserHolder;
import club.tempvs.profile.converter.UserInfoToUserConverter;
import club.tempvs.profile.dto.UserInfoDto;
import club.tempvs.profile.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class UserInfoInterceptor implements HandlerInterceptor {

    private static final String USER_INFO_HEADER = "User-Info";

    private final ObjectMapper objectMapper;
    private final UserHolder userHolder;
    private final UserInfoToUserConverter userInfoToUserConverter;

    @Override
    @SneakyThrows
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userInfoHeaderValue = request.getHeader(USER_INFO_HEADER);
        response.setHeader(USER_INFO_HEADER, userInfoHeaderValue);

        UserInfoDto userInfoDto = objectMapper.readValue(userInfoHeaderValue, UserInfoDto.class);
        User user = userInfoToUserConverter.convert(userInfoDto);
        userHolder.setUser(user);

        return true;
    }
}
