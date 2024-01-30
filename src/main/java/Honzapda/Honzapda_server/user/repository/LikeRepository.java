package Honzapda.Honzapda_server.user.repository;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.LikeData;
import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeData, Long> {
    Optional<LikeData> findByShopAndUser(Shop shop, User user);

    Optional<List<LikeData>> findAllByUser(User user);

    void deleteAllByUser(User user);
}
