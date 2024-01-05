package Honzapda.Honzapda_server.user.repository;

import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
