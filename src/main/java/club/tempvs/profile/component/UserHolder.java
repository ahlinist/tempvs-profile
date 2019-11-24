package club.tempvs.profile.component;

import club.tempvs.profile.dto.TempvsPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserHolder {

    public Long getUserId() {
        TempvsPrincipal principal = (TempvsPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return principal.getUserId();
    }
}
