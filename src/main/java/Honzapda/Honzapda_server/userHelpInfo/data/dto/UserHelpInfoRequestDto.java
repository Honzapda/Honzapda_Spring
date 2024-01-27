package Honzapda.Honzapda_server.userHelpInfo.data.dto;

import Honzapda.Honzapda_server.shop.data.entity.Shop;
import Honzapda.Honzapda_server.user.data.entity.User;
import Honzapda.Honzapda_server.userHelpInfo.data.entity.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserHelpInfoRequestDto {

    @Getter
    public static class UserHelpInfoCreateRequest {

        private LocalDateTime visitDate;

        // 혼잡도: 10%, 20%, 30%, 40%, 50%, 60%, 70%, 80%, 90%, 100%
        private Congestion congestion;

        // 앉았던 책상의 넓이: 넓었어요, 적당했어요, 좁았어요, 기억나지 않아요
        private DeskSize deskSize;

        // 콘센트 개수: 넉넉했어요, 적당했어요, 부족했어요, 기억나지 않아요
        private OutletCount outletCount;

        // 카페 조명은 어떻게 느껴졌나요?: 밝았어요, 적당했어요, 어두웠어요, 기억나지 않아요
        private Light light;

        // 콘센트는 주로 어디에 있었나요?: 직접 입력, 기억나지 않아요
        @NotBlank
        private String outletLocation;

        // 화장실은 어디에 있었나요?: 직접 입력, 기억나지 않아요
        @NotBlank
        private String restroomLocation;

        // 카페에서 어떤 종류의 노래가 많이 나왔나요?: 직접 입력, 기억나지 않아요
        @NotBlank
        private String musicGenre;

        // 카페의 전체적인 분위기는 어떻게 느껴졌나요?: 직접 입력, 기억나지 않아요
        @NotBlank
        private String atmosphere;

        private List<String> imageUrls = new ArrayList<>();

        public UserHelpInfo toEntity(User user, Shop shop) {
            return UserHelpInfo.builder()
                    .visitDate(visitDate)
                    .congestion(congestion)
                    .deskSize(deskSize)
                    .outletCount(outletCount)
                    .light(light)
                    .outletLocation(outletLocation)
                    .restroomLocation(restroomLocation)
                    .musicGenre(musicGenre)
                    .atmosphere(atmosphere)
                    .user(user)
                    .shop(shop)
                    .build();
        }

        public List<UserHelpInfoImage> toImageEntity(UserHelpInfo savedUserHelpInfo) {
            return imageUrls.stream()
                    .map(imageUrl -> UserHelpInfoImage.createImage(imageUrl, savedUserHelpInfo))
                    .toList();
        }

    }
}
