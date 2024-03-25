package Honzapda.Honzapda_server.user.data.dto;

import Honzapda.Honzapda_server.shop.data.dto.ShopResponseDto;
import Honzapda.Honzapda_server.user.data.entity.SignUpType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;


public class UserResDto {
    @Data
    @Builder
    public static class InfoDto{
        @Schema(example = "1")
        private Long id;
        @Schema(example = "김관주")
        private String name;
        @Schema(example = "https://storage.googleapis.com/honzapda-bucket/image_my_profile.png")
        private String profileImage;
        @Schema(example = "kkj6235@ajou.ac.kr")
        private String email;
        @Schema(example = "LOCAL")
        private SignUpType signUpType;
    }
    @Data
    @Builder
    public static class ProfileDto{
        @Schema(example = "1")
        private Long id;
        @Schema(example = "김관주")
        private String name;
        @Schema(example = "https://storage.googleapis.com/honzapda-bucket/image_my_profile.png")
        private String profileImage;
        private List<String> preferNameList;
        private List<ShopResponseDto.SimpleSearchDto> likeShops;
    }

}
