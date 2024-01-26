package Honzapda.Honzapda_server.shop.data.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

public class MapRequestDto {

    @Getter
    public static class LocationDto {

        @Min(-90)
        @Max(90)
        @NotBlank
        Double latitude;

        @Min(-180)
        @Max(180)
        @NotBlank
        Double longitude;

        @Positive
        @Max(500)
        Double distance = 2.0;
    }
}
