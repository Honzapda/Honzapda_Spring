package Honzapda.Honzapda_server.userHelpInfo.data.dto;

import Honzapda.Honzapda_server.userHelpInfo.data.entity.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class UserHelpInfoRequestDto {

    @Getter
    public static class CreateDto {
        @Schema(example = "yyyy-MM-ddThh:mm:ss")
        private String visitDateTime;

        // 혼잡도: 10%, 20%, 30%, 40%, 50%, 60%, 70%, 80%, 90%, 100%
        @Schema(description = "available inputs : 10, 20,... , 90")
        @NotNull(message = "형식에 맞지 않는 값입니다.")
        private Congestion congestion;

        // 앉았던 책상의 넓이: 넓었어요, 적당했어요, 좁았어요, 기억나지 않아요
        @Schema(description = "available inputs : small, medium, large, none")
        @NotNull(message = "형식에 맞지 않는 값입니다.")
        private DeskSize deskSize;

        // 콘센트 개수: 넉넉했어요, 적당했어요, 부족했어요, 기억나지 않아요
        @Schema(description = "available inputs : enough, adequate, lack, none")
        @NotNull(message = "형식에 맞지 않는 값입니다.")
        private OutletCount outletCount;

        // 카페 조명은 어떻게 느껴졌나요?: 밝았어요, 적당했어요, 어두웠어요, 기억나지 않아요
        @Schema(description = "available inputs : bright, adequate, dark, none")
        @NotNull(message = "형식에 맞지 않는 값입니다.")
        private Light light;

        // 콘센트는 주로 어디에 있었나요?: 직접 입력, 기억나지 않아요
        private String outletLocation;

        // 화장실은 어디에 있었나요?: 직접 입력, 기억나지 않아요
        private String restroomLocation;

        // 카페에서 어떤 종류의 노래가 많이 나왔나요?: 직접 입력, 기억나지 않아요
        private String musicGenre;

        // 카페의 전체적인 분위기는 어떻게 느껴졌나요?: 직접 입력, 기억나지 않아요
        private String atmosphere;

    }
}
