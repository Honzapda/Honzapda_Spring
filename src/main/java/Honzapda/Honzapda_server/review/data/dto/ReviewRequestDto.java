package Honzapda.Honzapda_server.review.data.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

public class ReviewRequestDto {

    @Getter
    public static class ReviewRegisterDto {

        @DecimalMin(value = "0.0", inclusive = true, message = "별점은 0~5 사이여야 합니다.")
        @DecimalMax(value = "5.0", inclusive = true, message = "별점은 0~5 사이여야 합니다.")
        private Double score;

        @Size(max = 200, message = "본문은 200자까지만  허용됩니다.") // 약 3줄
        private String body;

        private List<String> reviewUrls;
    }
}
