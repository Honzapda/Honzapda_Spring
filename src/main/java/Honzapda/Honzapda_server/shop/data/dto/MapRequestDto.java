package Honzapda.Honzapda_server.shop.data.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;

public class MapRequestDto {

    @Getter
    public static class LocationDto {

        @Min(-90)
        @Max(90)
        @NotNull
        Double latitude;

        @Min(-180)
        @Max(180)
        @NotNull
        Double longitude;

        @Positive
        @Max(500)
        Double distance = 2.0;
    }
}
