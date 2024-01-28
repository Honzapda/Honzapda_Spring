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
        private List<UserHelpInfoImageResponseDto.ImageDto> imageDtoList;
        private LocalDateTime createdAt;
    }
//    @Builder
//    @Getter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class ReviewListDtozzzzzzz {
//
//    }

}
