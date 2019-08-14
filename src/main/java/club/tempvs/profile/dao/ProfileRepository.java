package club.tempvs.profile.dao;

import club.tempvs.profile.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    @Query("SELECT p FROM profile p where s.type = 'USER' AND s.userId = :userId")
    Optional<Profile> findUserProfileByUserId(Long userId);
}
