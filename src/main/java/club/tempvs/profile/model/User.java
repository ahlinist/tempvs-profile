package club.tempvs.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private Long id;
    private Long profileId;
    private String lang;
}
