package club.tempvs.profile.token;

import club.tempvs.profile.dto.TempvsPrincipal;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

import java.util.Collection;

public class AuthToken extends PreAuthenticatedAuthenticationToken {

    @Getter
    private TempvsPrincipal principal;

    public AuthToken(TempvsPrincipal principal, Collection<SimpleGrantedAuthority> authorities) {
        super(principal.getEmail(), null, authorities);
        this.principal = principal;
    }
}
