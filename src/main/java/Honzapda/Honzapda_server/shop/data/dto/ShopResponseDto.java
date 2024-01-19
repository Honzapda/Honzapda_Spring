package Honzapda.Honzapda_server.shop.data.dto;

import Honzapda.Honzapda_server.review.data.entity.Review;
import Honzapda.Honzapda_server.shop.data.entity.ShopCoordinates;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
        LocalDateTime inactiveDate;
        List<Review> reviewList;

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
    }
}
