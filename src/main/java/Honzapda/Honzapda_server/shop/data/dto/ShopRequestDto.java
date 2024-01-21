package Honzapda.Honzapda_server.shop.data.dto;

import lombok.Getter;

import java.util.List;

public class ShopRequestDto {

    @Getter
    public static class registerDto {
        String shopName;
        String adminName;
        String description;
        String otherDetails;
        String shopPhoneNumber;
        String adminPhoneNumber;
        String address;
        String address_spec;
        String businessNumber;
        List<String> photoUrls;
    }
}
