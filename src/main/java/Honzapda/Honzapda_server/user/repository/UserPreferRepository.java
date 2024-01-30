package Honzapda.Honzapda_server.user.repository;

import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.user.data.entity.UserPrefer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferRepository extends JpaRepository<UserPrefer, Long> {

    void deleteAllByUser(User user);
}
