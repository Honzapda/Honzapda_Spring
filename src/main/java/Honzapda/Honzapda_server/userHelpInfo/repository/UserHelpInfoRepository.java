package Honzapda.Honzapda_server.userHelpInfo.repository;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserHelpInfoRepository extends JpaRepository<UserHelpInfo, Long> {
    Optional<UserHelpInfo> findByUserAndShop(User user, Shop shop);

    List<UserHelpInfo> findAllByUser(User user);

    void deleteAllByUser(User user);
}
