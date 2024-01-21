package Honzapda.Honzapda_server.review.data;

import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.review.data.entity.ReviewImage;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.stream.Collectors;


public class ReviewConverter {

    public static ReviewResponseDto.ReviewDto toReviewDto(
            Review review, List<ReviewImage> reviewImages) {
        List<ReviewResponseDto.ImageDto> imageDtos = reviewImages.stream()
                .map(ReviewResponseDto::toImageDto)
                .collect(Collectors.toList());

        return ReviewResponseDto.ReviewDto.builder()
                .images(imageDtos)
                .reviewId(review.getId())
                .shopId(review.getShop().getId())
                .userId(review.getUser().getId())
                .score(review.getScore())
                .body(review.getBody())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResponseDto.ReviewListDto toReviewListDto(
            Page<Review> reviews, List<ReviewResponseDto.ReviewDto>reviewDtos){

        return ReviewResponseDto.ReviewListDto.builder()
                .reviews(reviewDtos)
                .listSize(reviews.getSize())
                .totalPage(reviews.getTotalPages())
                .totalElements(reviews.getTotalElements())
                .isFirst(reviews.isFirst())
                .isLast(reviews.isLast())
                .build();
    }
}
