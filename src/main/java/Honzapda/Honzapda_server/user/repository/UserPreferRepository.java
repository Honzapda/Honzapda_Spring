package Honzapda.Honzapda_server.user.repository;

import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.data.entity.UserPrefer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPreferRepository extends JpaRepository<UserPrefer, Long> {

}
