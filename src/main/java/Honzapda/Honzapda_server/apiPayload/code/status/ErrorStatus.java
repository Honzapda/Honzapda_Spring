package Honzapda.Honzapda_server.apiPayload.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import Honzapda.Honzapda_server.apiPayload.code.BaseErrorCode;
import Honzapda.Honzapda_server.apiPayload.code.ErrorReasonDto;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"COMMON500","서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    // 회원가입 응답
    EMAIL_NOT_UNIQUE(HttpStatus.CONFLICT,"JOIN"+HttpStatus.CONFLICT.value(),"이미 존재하는 계정입니다."),
    NICKNAME_NOT_UNIQUE(HttpStatus.CONFLICT,"JOIN"+HttpStatus.CONFLICT.value(),"이미 존재하는 닉네임입니다."),
    // 로그인 응답
    ID_NOT_EXIST(HttpStatus.NOT_FOUND,"LOGIN"+HttpStatus.NOT_FOUND.value(),"ID를 잘못 입력하셨습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.NOT_FOUND,"LOGIN"+HttpStatus.NOT_FOUND.value(),"닉네임을 잘못 입력하셨습니다."),
    PW_NOT_MATCH(HttpStatus.FORBIDDEN,"LOGIN"+HttpStatus.FORBIDDEN.value(),"PW를 잘못 입력하셨습니다."),

    // 유저 응답
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4000", "해당 id의 유저를 찾을 수 없습니다."),

    // 가게 위치 응답
    SHOP_COORDINATES_NOT_FOUND(HttpStatus.NO_CONTENT, "SHOP_COORDINATES2040" , "가게 위치를 찾을 수 없습니다."),

    // 가게 응답
    SHOP_NOT_FOUND(HttpStatus.BAD_REQUEST, "SHOP4000", "해당 id의 가게를 찾을 수 없습니다."),

    // <유저,샵> 북마크 응답
    BOOKMARK_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "BOOKMARK4000", "이미 존재하는 북마크입니다."),
    BOOKMARK_NOT_FOUND(HttpStatus.BAD_REQUEST, "BOOKMARK4001", "해당 북마크를 찾을 수 없습니다."),

    // 리뷰 응답
    REVIEW_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "REVIEW4000", "리뷰는 가게당 하나입니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,"REVIEW4001","리뷰가 없습니다."),

    // 유저 도움 정보 응답
    INVALID_CONGESTION_EMPTY(HttpStatus.BAD_REQUEST, "USERHELPINFO4000", "혼잡도를 입력해주세요."),
    INVALID_CONGESTION(HttpStatus.BAD_REQUEST, "USERHELPINFO4001", "혼잡도는 10, 20, 30, ... , 100으로 입력해주세요."),
    INVALID_DESK_SIZE_EMPTY(HttpStatus.BAD_REQUEST, "USERHELPINFO4002", "책상의 넓이를 입력해주세요."),
    INVALID_DESK_SIZE(HttpStatus.BAD_REQUEST, "USERHELPINFO4003", "책상의 넓이는 small, medium, large, none으로 입력해주세요."),
    INVALID_LIGHT_EMPTY(HttpStatus.BAD_REQUEST, "USERHELPINFO4004", "조명을 입력해주세요."),
    INVALID_LIGHT(HttpStatus.BAD_REQUEST, "USERHELPINFO4005", "조명은 bright, adequate, dark, none으로 입력해주세요."),
    INVALID_OUTLET_COUNT_EMPTY(HttpStatus.BAD_REQUEST, "USERHELPINFO4006", "콘센트 개수를 입력해주세요."),
    INVALID_OUTLET_COUNT(HttpStatus.BAD_REQUEST, "USERHELPINFO4007", "콘센트 개수는 enough, adequate, lack, none으로 입력해주세요."),
    USER_HELP_INFO_NOT_FOUND(HttpStatus.NOT_FOUND,"USERHELPINFO4008","이미지가 속한 유저 도움 정보가 없습니다."),
    // 유저 도움 정보 좋아요
    LIKE_ALREADY_LIKED(HttpStatus.CONFLICT,"LIKE4000","이미 좋아요를 누르셨습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    @Override
    public ErrorReasonDto getReason(){
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }
    @Override
    public ErrorReasonDto getReasonHttpStatus() {
        return ErrorReasonDto.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
