package Honzapda.Honzapda_server.user.data.dto;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.LikeData;
import Honzapda.Honzapda_server.user.data.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LikeResDto {

    private Long shopId;

    private Long userId;

    private String shopName;

    private String userName;

    public static LikeResDto toDTO(LikeData likeData){

        return LikeResDto.builder()
                .shopId(likeData.getShop().getId())
                .userId(likeData.getUser().getId())
                .shopName(likeData.getShop().getShopName())
                .userName(likeData.getUser().getName())
                .build();
    }
}
