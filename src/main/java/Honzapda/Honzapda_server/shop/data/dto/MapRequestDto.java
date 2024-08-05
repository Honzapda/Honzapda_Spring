package Honzapda.Honzapda_server.shop.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;

public class MapRequestDto {

    @Getter
    public static class LocationDto {

        @Min(-90)
        @Max(90)
        @NotNull
        @Schema(example ="37.5204279064529")
        Double latitude;

        @Min(-180)
        @Max(180)
        @NotNull
        @Schema(example ="126.887847771379")
        Double longitude;

        @Positive
        @Max(500)
        @Schema(example ="2")
        Double distance = 2.0;

        public String getCacheKey() {
            return latitude + ":" + longitude + ":" + distance;
        }
    }
}
