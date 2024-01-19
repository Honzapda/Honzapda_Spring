package Honzapda.Honzapda_server.shop.service.shop_coordinates.dto;

import Honzapda.Honzapda_server.shop.data.entity.ShopCoordinates;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShopCoordinatesDto {

    private Long mysqlId;
    private String shopName;
    private Double latitude;
    private Double longitude;

    @Builder
    private ShopCoordinatesDto(Long mysqlId, String shopName, Double latitude, Double longitude) {
        this.mysqlId = mysqlId;
        this.shopName = shopName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static ShopCoordinatesDto of(Long mysqlId, String shopName, Double latitude, Double longitude) {
        return ShopCoordinatesDto.builder()
                .mysqlId(mysqlId)
                .shopName(shopName)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public ShopCoordinates toEntity() {
        Point location = new Point(longitude, latitude);
        return ShopCoordinates.builder()
                .mysqlId(mysqlId)
                .shopName(shopName)
                .location(location)
                .build();
    }
}
