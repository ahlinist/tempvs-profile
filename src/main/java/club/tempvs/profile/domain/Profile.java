package club.tempvs.profile.domain;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.EAGER;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.IDENTITY;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Profile {

    @Id
    @GeneratedValue(strategy= IDENTITY)
    private Long id;
    @NotNull
    private Long userId;
    @NotBlank
    private String firstName;
    private String lastName;
    private String nickName;
    private String profileEmail;
    private String location;
    private String alias;
    private Boolean isActive;
    private String clubName;
    @Enumerated(STRING)
    private Period period;
    @Size(max = 20)
    @OrderColumn
    @OneToMany(cascade = ALL, fetch = EAGER)
    private List<Passport> passports = new ArrayList<>();
    @NotNull
    @Enumerated(STRING)
    private Type type;
    @CreatedDate
    private Instant createdDate;

    public enum Period {

        ANCIENT,
        ANTIQUITY,
        EARLY_MIDDLE_AGES,
        HIGH_MIDDLE_AGES,
        LATE_MIDDLE_AGES,
        RENAISSANCE,
        MODERN,
        WWI,
        WWII,
        CONTEMPORARY,
        OTHER
    }

    public enum Type {

        USER,
        CLUB
    }
}
