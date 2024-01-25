package Honzapda.Honzapda_server.review.repository.mysql;

import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByShopId(Long shopId);

    Optional<Review> findByUserAndShop(User user, Shop shop);

    void deleteAllByUser(User user);

    Page<Review> findAllByShopOrderByCreatedAtDesc(Shop shop, Pageable pageable);
    List<Review> findTop3ByShopOrderByCreatedAtDesc(Shop shop);

}
