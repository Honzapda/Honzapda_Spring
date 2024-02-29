package Honzapda.Honzapda_server.review.data;

import Honzapda.Honzapda_server.review.data.dto.ReviewImageResponseDto;
import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.review.data.entity.ReviewImage;
import Honzapda.Honzapda_server.user.data.UserConverter;
import Honzapda.Honzapda_server.user.data.dto.UserResDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class ReviewConverter {

    public static ReviewResponseDto.ReviewDto toReviewDto(Review review) {

        UserResDto.InfoDto user = UserConverter.toUserInfo(review.getUser());

        return ReviewResponseDto.ReviewDto.builder()
                .images(null)
                .reviewId(review.getId())
                .shopId(review.getShop().getId())
                .user(user)
                .score(review.getScore())
                .body(review.getBody())
                .visitedAt(review.getVisitedAt())
                .createdAt(review.getCreatedAt())
                .build();
    }
    public static ReviewResponseDto.ReviewDto toReviewDto(
            Review review, List<ReviewImage> reviewImages) {

        List<ReviewImageResponseDto.ImageDto> imageDtos = reviewImages.stream()
                .map(ReviewImageConverter::toImageDto)
                .collect(Collectors.toList());

        UserResDto.InfoDto user = UserConverter.toUserInfo(review.getUser());

        return ReviewResponseDto.ReviewDto.builder()
                .images(imageDtos)
                .reviewId(review.getId())
                .shopId(review.getShop().getId())
                .user(user)
                .score(review.getScore())
                .body(review.getBody())
                .visitedAt(review.getVisitedAt())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResponseDto.ReviewListDto toReviewListDto(
            Page<Review> reviews, List<ReviewResponseDto.ReviewDto>reviewDtos, Integer currentPage){

        return ReviewResponseDto.ReviewListDto.builder()
                .reviews(reviewDtos)
                .listSize(reviews.getSize())
                .totalPage(reviews.getTotalPages())
                .totalElements(reviews.getTotalElements())
                .currentPage(currentPage)
                .isFirst(reviews.isFirst())
                .isLast(reviews.isLast())
                .build();
    }
}
