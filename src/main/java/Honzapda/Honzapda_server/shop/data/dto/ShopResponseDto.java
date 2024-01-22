package Honzapda.Honzapda_server.shop.data.dto;

import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.shop.data.entity.ShopCoordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

public class ShopResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SearchDto {
        Long shopId;
        String shopName;
        String adminName;
        String description;
        String otherDetails;
        String shopPhoneNumber;
        String adminPhoneNumber;
        Double rating;
        String address;
        String address_spec;
        String businessNumber;
        boolean openNow;
        LocalDateTime inactiveDate;
        List<Review> reviewList;
        List<String> photoUrls;
        List<BusinessHoursResDTO> businessHours;

        // 위도 경도
        Double latitude;
        Double longitude;

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

        public void setPhotoUrls(List<String> photoUrls){this.photoUrls = photoUrls;}

        public void setBusinessHours(List<BusinessHoursResDTO> businessHours){
            this.businessHours = businessHours;
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
}
