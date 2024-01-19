package Honzapda.Honzapda_server.shop.data.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

public class ShopRequestDto {

    @Getter
    public static class RegisterDto {
        String shopName;
        String adminName;
        String description;
        String otherDetails;
        String shopPhoneNumber;
        String adminPhoneNumber;
        String address;
        String address_spec;
        String businessNumber;

        // 위도 경도
        Double latitude;
        Double longitude;
    }

    @Getter
    public static class SearchDto {

        String keyword;

        SortColumn sortColumn;

        @Min(-90)
        @Max(90)
        Double latitude;

        @Min(-180)
        @Max(180)
        Double longitude;

        @Positive
        @Max(500)
        Double distance;
    }
}
