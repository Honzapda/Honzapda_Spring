package Honzapda.Honzapda_server.review.data;

import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.review.data.entity.Review;

public class ReviewConverter {

    public static ReviewResponseDto.ReviewDto toReviewDto(Review review) {
        return ReviewResponseDto.ReviewDto.builder()
                .reviewId(review.getId())
                .shopId(review.getShop().getId())
                .userId(review.getUser().getId())
                .score(review.getScore())
                .body(review.getBody())
                .build();
    }
}
