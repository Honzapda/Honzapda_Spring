package Honzapda.Honzapda_server.userHelpInfo.repository;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.UserHelpInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserHelpInfoRepository extends JpaRepository<UserHelpInfo, Long> {

    void deleteAllByUser(User user);
    Optional<UserHelpInfo>findFirstByUserAndShopOrderByIdDesc(User user, Shop shop);
    Page<UserHelpInfo> findAllByShop(Shop shop, Pageable pageable);
    Optional<List<UserHelpInfo>> findAllByShop(Shop shop);

}
