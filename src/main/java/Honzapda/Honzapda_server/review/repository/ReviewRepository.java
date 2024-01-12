package Honzapda.Honzapda_server.review.repository;

import Honzapda.Honzapda_server.review.data.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByShopId(Long shopId);
}
