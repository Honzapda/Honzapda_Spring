package Honzapda.Honzapda_server.review.data.dto;

import Honzapda.Honzapda_server.common.dto.ComResDto;
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
        // 필수 정보
        private ComResDto.UserProfileDto user;
        private Double score;
        private List<ReviewImageResponseDto.ImageDto> images;
        private String body;
        private LocalDateTime visitedAt;
        // 기타 정보
        private Long reviewId;
        private Long shopId;
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
