package club.tempvs.profile.dto;

import club.tempvs.profile.domain.Profile.Type;
import club.tempvs.profile.domain.Profile.Period;
import club.tempvs.profile.dto.validation.Scope;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.Instant;
import java.util.List;

@Data
public class ProfileDto {

    @Null(groups = Scope.Create.UserProfile.class)
    private Long id;
    @Null(groups = Scope.Create.UserProfile.class)
    private Long userId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @Null(groups = Scope.Create.UserProfile.class)
    private String nickName;
    @Null(groups = Scope.Create.UserProfile.class)
    private String profileEmail;
    @Null(groups = Scope.Create.UserProfile.class)
    private String location;
    @Null(groups = Scope.Create.UserProfile.class)
    private String alias;
    @Null(groups = Scope.Create.UserProfile.class)
    private Boolean isActive;
    @Null(groups = Scope.Create.UserProfile.class)
    private Period period;
    @Null(groups = Scope.Create.UserProfile.class)
    private List<PassportDto> passports;
    @Null(groups = Scope.Create.UserProfile.class)
    private Type type;
    @Null(groups = Scope.Create.UserProfile.class)
    private Instant createdDate;
}
