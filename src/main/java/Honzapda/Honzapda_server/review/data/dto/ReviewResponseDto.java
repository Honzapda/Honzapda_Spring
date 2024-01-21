package Honzapda.Honzapda_server.review.data.dto;

import Honzapda.Honzapda_server.review.data.entity.ReviewImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageDto{
        private Long imageId;
        private String url;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewDto {
        private List<ImageDto> images;
        private Long reviewId;
        private Long shopId;
        private Long userId;
        private Double score;
        private String body;
        private LocalDateTime createdAt;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewListDto {
        private List<ReviewDto> reviews;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }
    public static ReviewResponseDto.ImageDto toImageDto(ReviewImage image){
        return ReviewResponseDto.ImageDto.builder()
                .imageId(image.getId())
                .url(image.getUrl())
                .build();
    }

}
