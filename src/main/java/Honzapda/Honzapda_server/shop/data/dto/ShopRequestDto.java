package Honzapda.Honzapda_server.shop.data.dto;

import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public class ShopRequestDto {

    @Getter
    public static class LoginDto {
        //@Email TODO: 데모데이 이후 활성화
        private String loginId;
        private String password;
    }

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
        String stationDistance;
        String businessNumber;
        //@Email TODO: 데모데이 이후 활성화
        @NotBlank
        String loginId;
        @NotBlank
        String password;
        // 위도 경도
        Double latitude;
        Double longitude;

        String shopMainImage;
        List<BusinessHoursReqDTO> businessHours;

        List<ShopCongestionDto.DayCongestionDTO> dayCongestions;

        List<ShopCongestionDto.AverageCongestionDTO> averageCongestions;

        Long totalSeatCount;
    }

    @Getter
    public static class SearchDto {

        @NotBlank
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
        Double distance = 2.0;

    }

    @Getter
    public static class BusinessHoursReqDTO {
        String dayOfWeek;

        @JsonProperty("isOpen")
        boolean isOpen;

        String openHours;

        String closeHours;
    }

}
