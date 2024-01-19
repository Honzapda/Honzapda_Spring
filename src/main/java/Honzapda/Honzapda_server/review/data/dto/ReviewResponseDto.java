package Honzapda.Honzapda_server.review.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReviewResponseDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewDto {
        private Long reviewId;
        private Long shopId;
        private Long userId;
        private Double score;
        private String body;
    }
}
