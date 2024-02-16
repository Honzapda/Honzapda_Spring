package Honzapda.Honzapda_server.shop.data.dto;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopBusinessHour;
import Honzapda.Honzapda_server.shop.data.entity.ShopCoordinates;
import Honzapda.Honzapda_server.shop.data.entity.ShopDayCongestion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BookmarkResponseDto {

        private Long userId;
        private Long shopId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HomeDto {
        private Long id;
        private String place_name;
        private String adminName;
        private String description;
        private String otherDetails;
        private String phone;
        private String adminPhoneNumber;
        private String address;
        private String address_spec;
        private LocalDateTime inactiveDate;
        private boolean openNow;
        private String photoUrl;
        private Double rating;
        private Long reviewCount;

        private String posFromStation;
        private List<Integer> densityOfDays;

        // 위도 경도
        private Double x;
        private Double y;

        @JsonIgnore
        private ShopBusinessHour shopBusinessHour;

        @QueryProjection
        public HomeDto(Shop shop, Double reviewAvg, Long reviewCnt) {
            this.id = shop.getId();
            this.place_name = shop.getShopName();
            this.adminName = shop.getAdminName();
            this.description = shop.getDescription();
            this.phone = shop.getShopPhoneNumber();
            this.adminPhoneNumber = shop.getAdminPhoneNumber();
            this.address = shop.getAddress();
            this.address_spec = shop.getAddress_spec();
            this.inactiveDate = shop.getInactiveDate();
            this.openNow = false;
            this.photoUrl = shop.getShopMainImage();
            this.rating = reviewAvg;
            this.reviewCount = reviewCnt;
            this.posFromStation = shop.getStationDistance();
            this.densityOfDays = new ArrayList<>();
        }

        public void addCoordinates(ShopCoordinates shopCoordinates) {
            this.y = shopCoordinates.getLocation().getY();
            this.x = shopCoordinates.getLocation().getX();
        }

        public void setOpenNow(boolean openNow) {
            this.openNow = openNow;
        }

        public void setShopBusinessHour(ShopBusinessHour shopBusinessHour) {
            this.shopBusinessHour = shopBusinessHour;
        }

        //TODO: 만약 월화수 순서가 아니라면 수정 필요
        public void setShopDayCongestions(List<ShopDayCongestion> shopDayCongestions) {
            shopDayCongestions.stream()
                    .map(ShopDayCongestion::getCongestionLevel)
                    .map(Enum::ordinal)
                    .map(i -> i + 1)
                    .forEach(densityOfDays::add);
        }
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class UserBookmarkShopResponseDto {
        private Long id;
        private String place_name;
        private String address;
        private String address_spec;
        private LocalDateTime inactiveDate;
        private String photoUrl;

        // 위도 경도
        private Double x;
        private Double y;

        @QueryProjection
        public UserBookmarkShopResponseDto(Shop shop) {
            this.id = shop.getId();
            this.place_name = shop.getShopName();
            this.address = shop.getAddress();
            this.address_spec = shop.getAddress_spec();
            this.inactiveDate = shop.getInactiveDate();
            this.photoUrl = shop.getShopMainImage();
        }

        public static UserBookmarkShopResponseDto createFrom(Shop shop) {
            return UserBookmarkShopResponseDto.builder()
                    .id(shop.getId())
                    .place_name(shop.getShopName())
                    .address(shop.getAddress())
                    .address_spec(shop.getAddress_spec())
                    .inactiveDate(shop.getInactiveDate())
                    .photoUrl(Objects.requireNonNullElse(shop.getShopMainImage(), "null"))
                    .build();
        }

        public void addCoordinates(ShopCoordinates shopCoordinates) {
            this.y = shopCoordinates.getLocation().getY();
            this.x = shopCoordinates.getLocation().getX();
        }

    }
}
