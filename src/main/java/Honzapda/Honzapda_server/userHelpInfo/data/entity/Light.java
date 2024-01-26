package Honzapda.Honzapda_server.userHelpInfo.data.entity;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * 카페 조명은 어떻게 느껴졌나요?: 밝았어요, 적당했어요, 어두웠어요, 기억나지 않아요
 */
@RequiredArgsConstructor
@Getter
public enum Light {

    BRIGHT("bright", "밝은"),
    ADEQUATE("adequate", "적당한"),
    DARK("dark", "어두운"),
    NONE("none", "none");

    private final String requestDescription;
    private final String responseDescription;

    @JsonCreator
    public static Light fromString(String source) {

        if (!StringUtils.hasText(source)) {
            throw new GeneralException(ErrorStatus.INVALID_LIGHT_EMPTY);
        }

        for (Light light : Light.values()) {
            if (light.getRequestDescription().equals(source)) {
                return light;
            }
        }

        throw new GeneralException(ErrorStatus.INVALID_LIGHT);
    }
}
