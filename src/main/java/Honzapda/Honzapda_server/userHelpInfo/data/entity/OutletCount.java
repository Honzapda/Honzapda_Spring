package Honzapda.Honzapda_server.userHelpInfo.data.entity;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

/**
 * 콘센트 개수: 넉넉했어요, 적당했어요, 부족했어요, 기억나지 않아요
 */
@RequiredArgsConstructor
@Getter
public enum OutletCount {

    ENOUGH("enough", "넉넉한"),
    ADEQUATE("adequate", "적당한"),
    LACK("lack", "부족한"),
    NONE("none", "none");

    private final String requestDescription;
    private final String responseDescription;

    @JsonCreator
    public static OutletCount fromString(String source) {

        if (!StringUtils.hasText(source)) {
            throw new GeneralException(ErrorStatus.INVALID_OUTLET_COUNT_EMPTY);
        }

        for (OutletCount outletCount : OutletCount.values()) {
            if (outletCount.getRequestDescription().equals(source)) {
                return outletCount;
            }
        }

        throw new GeneralException(ErrorStatus.INVALID_OUTLET_COUNT);
    }
}
