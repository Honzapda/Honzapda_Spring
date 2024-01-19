package Honzapda.Honzapda_server.shop.repository.mysql;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopUserBookmark;
import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopUserBookmarkRepository extends JpaRepository<ShopUserBookmark, Long> {
    Optional<ShopUserBookmark> findByUserAndShop(User user, Shop shop);
}
