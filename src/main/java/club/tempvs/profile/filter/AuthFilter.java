package club.tempvs.profile.filter;


import club.tempvs.profile.component.UserHolder;
import club.tempvs.profile.dto.TempvsPrincipal;
import club.tempvs.profile.model.User;
import club.tempvs.profile.token.AuthToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor
public class AuthFilter extends GenericFilterBean {

    private static final String USER_INFO_HEADER = "User-Info";

    private final UserHolder userHolder;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String userInfoHeaderValue = httpRequest.getHeader(USER_INFO_HEADER);

        if (!StringUtils.isEmpty(userInfoHeaderValue)) {
            httpResponse.setHeader(USER_INFO_HEADER, userInfoHeaderValue);

            TempvsPrincipal principal = objectMapper.readValue(userInfoHeaderValue, TempvsPrincipal.class);
            Set<String> roles = principal.getRoles();
            Set<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(toSet());
            AuthToken authToken = new AuthToken(principal, authorities);
            SecurityContextHolder.getContext()
                    .setAuthentication(authToken);

            User user = new User(principal.getUserId(), principal.getCurrentProfileId(), principal.getLang());
            userHolder.setUser(user);

            String lang = principal.getLang();
            LocaleContextHolder.setLocale(new Locale(lang));
        }

        chain.doFilter(httpRequest, httpResponse);
    }
}
