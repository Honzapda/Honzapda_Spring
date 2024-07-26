package Honzapda.Honzapda_server.shop.data.dto;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopAverageCongestion;
import Honzapda.Honzapda_server.shop.data.entity.ShopDayCongestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ShopCongestionDto {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DayCongestionDTO {
        String dayOfWeek;

        ShopDayCongestion.CongestionLevel congestionLevel;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AverageCongestionDTO {
        boolean weekend;

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
                .weekend(averageCongestionDTO.weekend)
                .startTime(averageCongestionDTO.startTime)
                .endTime(averageCongestionDTO.endTime)
                .shop(shop)
                .build();
    }
    public static AverageCongestionDTO toDTO(ShopAverageCongestion shopAverageCongestion){
        return AverageCongestionDTO.builder()
                .weekend(shopAverageCongestion.isWeekend())
                .startTime(shopAverageCongestion.getStartTime())
                .endTime(shopAverageCongestion.getEndTime())
                .build();

    }




}
