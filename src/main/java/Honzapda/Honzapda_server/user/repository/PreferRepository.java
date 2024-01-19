package Honzapda.Honzapda_server.user.repository;

import Honzapda.Honzapda_server.user.data.entity.Prefer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PreferRepository extends JpaRepository<Prefer, Long> {

    Optional<Prefer> findByPreferName(String preferName);
}
