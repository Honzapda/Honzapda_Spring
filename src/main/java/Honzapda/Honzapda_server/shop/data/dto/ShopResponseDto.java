package Honzapda.Honzapda_server.shop.data.dto;

import Honzapda.Honzapda_server.review.data.dto.ReviewResponseDto;
import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.shop.data.entity.ShopBusinessHour;
import Honzapda.Honzapda_server.shop.data.entity.ShopCoordinates;
import Honzapda.Honzapda_server.userHelpInfo.data.dto.UserHelpInfoResponseDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.querydsl.core.annotations.QueryProjection;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ShopResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OwnerInfoDto{
        private Long id;
        private String name;
        private String loginId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchDto {
        Long shopId;
        String shopName;
        String description;
        String shopPhoneNumber;
        String mainImage;
        Long reviewCount;
        Double rating;
        String address;
        String address_spec;
        String stationDistance;
        boolean openNow;
        boolean userLike;
        LocalDateTime inactiveDate;
        List<UserHelpInfoResponseDto.UserHelpInfoDto> userHelpInfoDtoList;
        List<ReviewResponseDto.ReviewDto> reviewList;
        List<BusinessHoursResDTO> businessHours;

        // 위도 경도
        Double latitude;
        Double longitude;

        List<ShopCongestionDto.AverageCongestionDTO> averageCongestions;
        List<ShopCongestionDto.DayCongestionDTO> dayCongestions;

        Long totalSeatCount;
        int cameraCount;
        int wifiCount;
        public void setRating(double rating){
            this.rating = rating;
        }

        public void addCoordinates(ShopCoordinates shopCoordinates) {
            this.latitude = shopCoordinates.getLocation().getY();
            this.longitude = shopCoordinates.getLocation().getX();
        }

        public void setOpenNow(boolean openNow) {
            this.openNow = openNow;
        }

        public void setUserLike(boolean userLike) {
            this.userLike = userLike;
        }

        public void setPhotoUrls(String photoUrl){this.mainImage = photoUrl;}

        public void setBusinessHours(List<BusinessHoursResDTO> businessHours){
            this.businessHours = businessHours;
        }

        public void setReviewCount(Long reviewCount){
            this.reviewCount = reviewCount;
        }

        public void setReviewList(List<ReviewResponseDto.ReviewDto> reviewList){
            this.reviewList = reviewList;
        }
        public void setUserHelpInfoDtoList(List<UserHelpInfoResponseDto.UserHelpInfoDto> userHelpInfoDtoList){
            this.userHelpInfoDtoList = userHelpInfoDtoList;
        }

        public void setCongestion(List<ShopCongestionDto.AverageCongestionDTO> averageCongestions,
                                  List<ShopCongestionDto.DayCongestionDTO> dayCongestions,
                                  int cameraCount,
                                  int wifiCount){
            this.averageCongestions = averageCongestions;
            this.dayCongestions = dayCongestions;
            this.cameraCount = cameraCount;
            this.wifiCount = wifiCount;
        }

    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BusinessHoursResDTO {
        private String dayOfWeek;
        @JsonProperty("isOpen")
        private boolean isOpen;
        private String openHours;
        private String closeHours;

        @JsonIgnore
        private boolean open;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchByNameDto {
        private Long shopId;
        private String shopName;
        private String address;
        private String address_spec;
        private boolean openNow;
        private String photoUrl;

        @JsonIgnore
        private ShopBusinessHour shopBusinessHour;

        @QueryProjection
        public SearchByNameDto(Shop shop, ShopBusinessHour shopBusinessHour) {
            this.shopId = shop.getId();
            this.shopName = shop.getShopName();
            this.address = shop.getAddress();
            this.address_spec = shop.getAddress_spec();
            this.openNow = false;
            this.photoUrl = shop.getShopMainImage();
            this.shopBusinessHour = shopBusinessHour;
        }

        public void setOpenNow(boolean openNow) {
            this.openNow = openNow;
        }
    }

    @Builder
    @Getter
    public static class SimpleSearchDto{
        Long shopId;
        String shopName;
        String shopMainImage;
    }

}
