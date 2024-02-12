package Honzapda.Honzapda_server.shop.data.dto;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopAverageCongestion;
import Honzapda.Honzapda_server.shop.data.entity.ShopDayCongestion;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.Congestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ShopCongestionDto {
    @Getter
    @Builder
    public static class DayCongestionDTO {
        String dayOfWeek;

        ShopDayCongestion.CongestionLevel congestionLevel;

    }

    @Getter
    @Builder
    public static class AverageCongestionDTO {
        boolean isWeekend;

        String startTime;

        String endTime;
    }

    public static ShopDayCongestion toEntity(DayCongestionDTO dayCongestionDTO, Shop shop){
        return ShopDayCongestion.builder()
                .dayOfWeek(dayCongestionDTO.getDayOfWeek())
                .congestionLevel(dayCongestionDTO.getCongestionLevel())
                .shop(shop)
                .build();
    }
    public static DayCongestionDTO toDTO(ShopDayCongestion shopDayCongestion){

        return DayCongestionDTO.builder()
                .dayOfWeek(shopDayCongestion.getDayOfWeek())
                .congestionLevel(shopDayCongestion.getCongestionLevel())
                .build();
    }
    public static ShopAverageCongestion toEntity(AverageCongestionDTO averageCongestionDTO,Shop shop){
        return ShopAverageCongestion.builder()
                .isWeekend(averageCongestionDTO.isWeekend)
                .startTime(averageCongestionDTO.startTime)
                .endTime(averageCongestionDTO.endTime)
                .shop(shop)
                .build();
    }
    public static AverageCongestionDTO toDTO(ShopAverageCongestion shopAverageCongestion){
        return AverageCongestionDTO.builder()
                .isWeekend(shopAverageCongestion.isWeekend())
                .startTime(shopAverageCongestion.getStartTime())
                .endTime(shopAverageCongestion.getEndTime())
                .build();

    }




}
