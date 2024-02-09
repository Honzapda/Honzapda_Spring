package Honzapda.Honzapda_server.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class ComResDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserProfileDto{
        private Long userId;
        private String name;
        private String profileImage;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LikeDto{
        private Long likeCount;
        private boolean userLike;
    }


}
