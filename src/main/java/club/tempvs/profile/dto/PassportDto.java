package club.tempvs.profile.dto;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

public class PassportDto {

    private Long id;
    @NotBlank
    private String name;
    private String description;
    private List<Long> items;
    private Instant createdDate;
}
