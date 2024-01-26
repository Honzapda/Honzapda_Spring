package Honzapda.Honzapda_server.shop.data.dto;

import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopBusinessHour;
import Honzapda.Honzapda_server.shop.data.entity.ShopCoordinates;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryProjection;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        private Long shopId;
        private String shopName;
        private String adminName;
        private String description;
        private String otherDetails;
        private String shopPhoneNumber;
        private String adminPhoneNumber;
        private String address;
        private String address_spec;
        private LocalDateTime inactiveDate;
        private boolean openNow;
        private List<String> photoUrls;
        private Double rating;
        private Long reviewCount;

        // 위도 경도
        private Double latitude;
        private Double longitude;

        @JsonIgnore
        private ShopBusinessHour shopBusinessHour;

        @QueryProjection
        public HomeDto(Shop shop, Double reviewAvg, Long reviewCnt) {
            this.shopId = shop.getId();
            this.shopName = shop.getShopName();
            this.adminName = shop.getAdminName();
            this.description = shop.getDescription();
            this.otherDetails = shop.getOtherDetails();
            this.shopPhoneNumber = shop.getShopPhoneNumber();
            this.adminPhoneNumber = shop.getAdminPhoneNumber();
            this.address = shop.getAddress();
            this.address_spec = shop.getAddress_spec();
            this.inactiveDate = shop.getInactiveDate();
            this.openNow = false;
            this.photoUrls = new ArrayList<>();
            this.rating = reviewAvg;
            this.reviewCount = reviewCnt;
        }

        public void addCoordinates(ShopCoordinates shopCoordinates) {
            this.latitude = shopCoordinates.getLocation().getY();
            this.longitude = shopCoordinates.getLocation().getX();
        }

        public void setOpenNow(boolean openNow) {
            this.openNow = openNow;
        }

        public void setPhotoUrls(List<String> photoUrls){this.photoUrls = photoUrls;}

        public void setShopBusinessHour(ShopBusinessHour shopBusinessHour) {
            this.shopBusinessHour = shopBusinessHour;
        }
    }
}
