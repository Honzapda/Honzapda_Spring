package Honzapda.Honzapda_server.userHelpInfo.data.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Arrays;

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
            //throw new GeneralException(ErrorStatus.INVALID_DESK_SIZE_EMPTY);
            return null;
        }

        return Arrays.stream(values())
                .filter(deskSize -> deskSize.getRequestDescription().equals(source))
                .findFirst()
                //.orElseThrow(() -> new GeneralException(ErrorStatus.INVALID_DESK_SIZE));
                .orElse(null);
    }

}
