package Honzapda.Honzapda_server.userHelpInfo.data.entity;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * 앉았던 책상의 넓이: 넓었어요, 적당했어요, 좁았어요, 기억나지 않아요
 */
@RequiredArgsConstructor
@Getter
public enum DeskSize {

    SMALL("small", "좁은"),
    MEDIUM("medium", "적당한"),
    LARGE("large", "넓은"),
    NONE("none", "none");

    private final String requestDescription;
    private final String responseDescription;

    @JsonCreator
    public static DeskSize fromString(String source) {

        if (!StringUtils.hasText(source)) {
            throw new GeneralException(ErrorStatus.INVALID_DESK_SIZE_EMPTY);
        }

        for (DeskSize deskSize : DeskSize.values()) {
            if (deskSize.getRequestDescription().equals(source)) {
                return deskSize;
            }
        }

        throw new GeneralException(ErrorStatus.INVALID_DESK_SIZE);
    }
}
