package Honzapda.Honzapda_server.auth.repository;

import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByName(String name);
    Optional<User> findByEmail(String email);
}
