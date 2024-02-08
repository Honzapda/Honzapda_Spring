package Honzapda.Honzapda_server.userHelpInfo.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

public class UserHelpInfoResponseDto {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserHelpInfoDto {
        private LocalDateTime visitDateTime;
        // 혼잡도: 10%, 20%, 30%, 40%, 50%, 60%, 70%, 80%, 90%, 100%
        private String congestion;
        // 앉았던 책상의 넓이: 넓었어요, 적당했어요, 좁았어요, 기억나지 않아요
        private String deskSize;
        // 콘센트 개수: 넉넉했어요, 적당했어요, 부족했어요, 기억나지 않아요
        private String outletCount;
        // 카페 조명은 어떻게 느껴졌나요?: 밝았어요, 적당했어요, 어두웠어요, 기억나지 않아요
        private String light;
        // 콘센트는 주로 어디에 있었나요?: 직접 입력, 기억나지 않아요
        private String outletLocation;
        // 화장실은 어디에 있었나요?: 직접 입력, 기억나지 않아요
        private String restroomLocation;
        // 카페에서 어떤 종류의 노래가 많이 나왔나요?: 직접 입력, 기억나지 않아요
        private String musicGenre;
        // 카페의 전체적인 분위기는 어떻게 느껴졌나요?: 직접 입력, 기억나지 않아요
        private String atmosphere;
        private Long likeCount;
        private boolean userLike;
        private Long userHelpInfId;
        private LocalDateTime createdAt;
    }
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserHelpInfoListDto {
        private List<UserHelpInfoResponseDto.UserHelpInfoDto> userHelpInfoDtoList;
        private Integer listSize;
        private Integer totalPage;
        private Long totalElements;
        private Boolean isFirst;
        private Boolean isLast;
    }

}
