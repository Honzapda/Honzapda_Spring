package Honzapda.Honzapda_server.shop.data.dto;

import lombok.Getter;

public class ShopRequestDto {

    @Getter
    public static class registerDto {
        String name;
        String description;
        String otherDetails;
        String phoneNumber;
        String address;
        String address_spec;
        Long userId;
    }
}
