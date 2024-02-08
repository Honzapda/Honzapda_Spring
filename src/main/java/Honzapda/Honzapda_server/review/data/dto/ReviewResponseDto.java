package Honzapda.Honzapda_server.review.data.dto;

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
    public static class ReviewDto {
        private List<ReviewImageResponseDto.ImageDto> images;
        private Long reviewId;
        private Long shopId;
        private Long userId;
        private String profileImage;
        private String name;
        private Double score;
        private String body;
        private LocalDateTime visitedAt;
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
        private Integer currentPage;
        private Boolean isFirst;
        private Boolean isLast;
    }



}
