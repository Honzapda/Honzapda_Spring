package Honzapda.Honzapda_server.shop.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

public class ShopRequestDto {

    @Getter
    public static class LoginDto {
        //@Email TODO: 데모데이 이후 활성화
        @Schema(example = "allthatmind")
        private String loginId;
        @Schema(example = "07280")
        private String password;
    }

    @Getter
    public static class RegisterDto{
        @Schema(example = "올댓마인드")
        String shopName;
        @Schema(example = "adminName")
        String adminName;
        @Schema(example = "실200평 대형공간대여 워크샵, 세미나, 파티, 학회, 레크리에이션, 플리마켓")
        String description;
        @Schema(example = "02-6014-0361")
        String shopPhoneNumber;
        @Schema(example = "987-654-3210")
        String adminPhoneNumber;
        @Schema(example = "서울 영등포구")
        String address;
        @Schema(example = "문래로 55 203호")
        String address_spec;
        @Schema(example = "문래역에서 걸어서 8분")
        String stationDistance;
        @Schema(example = "123456789")
        String businessNumber;
        //@Email TODO: 데모데이 이후 활성화
        @NotBlank
        @Schema(example = "allthatmind")
        String loginId;
        @NotBlank
        @Schema(example = "07280")
        String password;
        // 위도 경도
        @Schema(example = "37.5204279064529")
        Double latitude;
        @Schema(example = "126.887847771379")
        Double longitude;

        @Schema(example = "https://storage.googleapis.com/honzapda-bucket/84a1b150-acfb-4278-8941-e4ab9b73e6ad")
        String shopMainImage;

        @Schema(example = "[\n" +
                "        { \"dayOfWeek\": \"MONDAY\", \"isOpen\": true, \"openHours\": \"09:00\", \"closeHours\": \"21:30\" },\n" +
                "        { \"dayOfWeek\": \"TUESDAY\", \"isOpen\": true, \"openHours\": \"09:00\", \"closeHours\": \"21:30\" },\n" +
                "        { \"dayOfWeek\": \"WEDNESDAY\", \"isOpen\": true, \"openHours\": \"09:00\", \"closeHours\": \"21:30\" },\n" +
                "        { \"dayOfWeek\": \"THURSDAY\", \"isOpen\": true, \"openHours\": \"08:00\", \"closeHours\": \"21:00\" },\n" +
                "        { \"dayOfWeek\": \"FRIDAY\", \"isOpen\": true, \"openHours\": \"08:00\", \"closeHours\": \"21:00\" },\n" +
                "        { \"dayOfWeek\": \"SATURDAY\", \"isOpen\": false, \"openHours\": \"\", \"closeHours\": \"\" },\n" +
                "        { \"dayOfWeek\": \"SUNDAY\", \"isOpen\": false, \"openHours\": \"\", \"closeHours\": \"\" }\n" +
                "    ]")
        List<BusinessHoursReqDTO> businessHours;

        @Schema(example = "[\n" +
                "        { \"dayOfWeek\": \"MONDAY\", \"congestionLevel\" :  \"COMFORTABLE\"},\n" +
                "        { \"dayOfWeek\": \"TUESDAY\", \"congestionLevel\" :  \"BUSY\" },\n" +
                "        { \"dayOfWeek\": \"WEDNESDAY\", \"congestionLevel\" :  \"COMFORTABLE\" },\n" +
                "        { \"dayOfWeek\": \"THURSDAY\", \"congestionLevel\" :  \"NORMAL\" },\n" +
                "        { \"dayOfWeek\": \"FRIDAY\", \"congestionLevel\" :  \"COMFORTABLE\" },\n" +
                "        { \"dayOfWeek\": \"SATURDAY\", \"congestionLevel\" :  \"BUSY\" },\n" +
                "        { \"dayOfWeek\": \"SUNDAY\", \"congestionLevel\" :  \"BUSY\"}\n" +
                "    ]")
        List<ShopCongestionDto.DayCongestionDTO> dayCongestions;

        @Schema(example = "[\n" +
                "        { \"isWeekend\" :  false, \"startTime\" : \"18:00\", \"endTime\": \"20:00\" },\n" +
                "        { \"isWeekend\": true,  \"startTime\" : \"14:00\", \"endTime\": \"16:00\"}\n" +
                "    ]")
        List<ShopCongestionDto.AverageCongestionDTO> averageCongestions;

        @Schema(example = "80")
        Long totalSeatCount;
    }

    @Getter
    public static class SearchDto {

        @NotBlank
        @Schema(example = "올댓마인드")
        String keyword;

        @Schema(example = "distance")
        SortColumn sortColumn;


        @Min(-90)
        @Max(90)
        @Schema(example = "37.5204279064529")
        Double latitude;

        @Min(-180)
        @Max(180)
        @Schema(example = "126.887847771379")
        Double longitude;

        @Positive
        @Max(500)
        @Schema(example = "500")
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
