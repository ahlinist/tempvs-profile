package club.tempvs.profile.component;

import club.tempvs.profile.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class UserHolder {

    @Getter
    @Setter
    private User user;
}
