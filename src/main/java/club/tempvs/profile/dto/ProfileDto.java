package club.tempvs.profile.dto;

import club.tempvs.profile.domain.Profile.Type;
import club.tempvs.profile.domain.Profile.Period;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

@Data
public class ProfileDto {

    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String nickName;
    private String profileEmail;
    private String location;
    private String alias;
    private Boolean isActive;
    private String clubName;
    private Period period;
    private List<PassportDto> passports;
    @NotNull
    private Type type;
    private Instant createdDate;
}
