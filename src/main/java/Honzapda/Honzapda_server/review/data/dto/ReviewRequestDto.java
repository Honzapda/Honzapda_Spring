package Honzapda.Honzapda_server.review.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewRequestDto {

    @Getter
    public static class ReviewRegisterDto {

        @DecimalMin(value = "0.0", inclusive = true, message = "별점은 0~5 사이여야 합니다.")
        @DecimalMax(value = "5.0", inclusive = true, message = "별점은 0~5 사이여야 합니다.")
        @Schema(example = "4.0")
        private Double score;

        @Size(max = 200, message = "본문은 200자까지만  허용됩니다.") // 약 3줄
        @Schema(example = "요즘 읽는 책인데, 졸릴 때마다 여기 카페를 오고 있어요. 책 읽기 좋은 카페입니다~")
        private String body;

        @Schema(example = "2024-02-14T19:19:08")
        private LocalDateTime visitedAt;

        @Schema(example = "[\n" +
                "    \"https://storage.googleapis.com/honzapda-bucket/c7ceb056-68ae-43b3-b4e5-faa46b220421\",\n" +
                "    \"https://storage.googleapis.com/honzapda-bucket/e923c594-1f0d-40bc-a248-bfdd4d2b19d9\",\n" +
                "    \"https://storage.googleapis.com/honzapda-bucket/85e57324-5951-49b3-9455-d8ab1a986299\"\n" +
                "  ]")
        private List<String> reviewUrls;
    }
}
