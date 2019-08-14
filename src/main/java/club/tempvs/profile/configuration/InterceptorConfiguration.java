package club.tempvs.profile.configuration;

import club.tempvs.profile.interceptor.AuthInterceptor;
import club.tempvs.profile.interceptor.UserInfoInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
@RequiredArgsConstructor
public class InterceptorConfiguration implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;
    private final UserInfoInterceptor userInfoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor);
        registry.addInterceptor(userInfoInterceptor);
    }
}
