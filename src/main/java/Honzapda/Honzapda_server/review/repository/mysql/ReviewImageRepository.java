package Honzapda.Honzapda_server.review.repository.mysql;

import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.review.data.entity.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewImageRepository extends JpaRepository<ReviewImage,Long> {
    Optional<List<ReviewImage>> findAllByReview(Review review);

}
