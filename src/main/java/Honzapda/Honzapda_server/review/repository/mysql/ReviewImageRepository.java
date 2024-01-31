package Honzapda.Honzapda_server.review.repository.mysql;

import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.review.data.entity.ReviewImage;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,Long> {
    Optional<List<ReviewImage>> findAllByReview(Review review);
    Slice<ReviewImage> findAllByShopOrderByCreatedAtDesc(Shop shop, Pageable pageable);

    void deleteAllByReview(Review review);
}
