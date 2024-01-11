package Honzapda.Honzapda_server.shop.data.dto;

import Honzapda.Honzapda_server.review.data.entity.Review;
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
    public static class searchDto {
        Long shopId;
        String name;
        String description;
        String otherDetails;
        String phoneNumber;
        Double rating;
        String address;
        String address_spec;
        LocalDateTime inactiveDate;
        List<Review> reviewList;
    }
}
