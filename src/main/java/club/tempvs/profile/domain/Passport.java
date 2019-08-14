package club.tempvs.profile.domain;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Passport {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    private String description;
    @Size(max = 20)
    @OrderColumn
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Long> items = new ArrayList<>();
    @CreatedDate
    private Instant createdDate;
}
