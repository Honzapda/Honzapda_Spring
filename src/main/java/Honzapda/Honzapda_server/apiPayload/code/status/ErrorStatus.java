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
    _TOO_MANY_REQUEST(HttpStatus.TOO_MANY_REQUESTS,"COMMON429","3시간 이후에 작성해주세요."),
    // 회원가입 응답
    EMAIL_NOT_UNIQUE(HttpStatus.CONFLICT,"JOIN"+HttpStatus.CONFLICT.value(),"이미 존재하는 계정입니다."),
    NICKNAME_NOT_UNIQUE(HttpStatus.CONFLICT,"JOIN"+HttpStatus.CONFLICT.value(),"이미 존재하는 닉네임입니다."),
    // 로그인 응답
    SESSION_EXPIRED(HttpStatus.UNAUTHORIZED,"LOGIN"+HttpStatus.UNAUTHORIZED.value(),"세션이 만료되었습니다."),
    ID_NOT_EXIST(HttpStatus.NOT_FOUND,"LOGIN"+HttpStatus.NOT_FOUND.value(),"ID를 잘못 입력하셨습니다."),
    PW_NOT_MATCH(HttpStatus.FORBIDDEN,"LOGIN"+HttpStatus.FORBIDDEN.value(),"입력하신 정보가 회원정보와 일치하지 않습니다."),

    // 유저 응답
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER4000", "해당 id의 유저를 찾을 수 없습니다."),

    // 가게 위치 응답
    SHOP_COORDINATES_NOT_FOUND(HttpStatus.NO_CONTENT, "SHOP_COORDINATES2040" , "가게 위치를 찾을 수 없습니다."),

    // 가게 응답
    SHOP_EXIST_MYSQL(HttpStatus.CONFLICT,"SHOP409","MYSQL에 이미 등록된 가게입니다."),
    SHOP_NOT_FOUND(HttpStatus.NOT_FOUND, "SHOP404", "해당 id의 가게를 찾을 수 없습니다."),
    SHOP_EXIST_MONGO(HttpStatus.BAD_REQUEST,"SHOP400","MONGODB에 존재하는 가게입니다."),

    // <유저,샵> 북마크 응답
    BOOKMARK_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "BOOKMARK4000", "이미 존재하는 북마크입니다."),
    BOOKMARK_NOT_FOUND(HttpStatus.NOT_FOUND, "BOOKMARK404", "해당 북마크를 찾을 수 없습니다."),

    // 리뷰 응답
    REVIEW_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "REVIEW4000", "리뷰는 가게당 하나입니다."),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND,"REVIEW4001","리뷰가 없습니다."),
    INVALID_REVIEW(HttpStatus.FORBIDDEN,"REVIEW4002","해당 리뷰에 접근 권한이 없습니다."),
    REVIEW_IMAGE_NOT_FOUND(HttpStatus.NOT_FOUND,"REVIEW4003","해당 리뷰사진이 없습니다."),

    // 유저 도움 정보 응답
    INVALID_CONGESTION_EMPTY(HttpStatus.BAD_REQUEST, "USERHELPINFO4000", "혼잡도를 입력해주세요."),
    INVALID_CONGESTION(HttpStatus.BAD_REQUEST, "USERHELPINFO4001", "혼잡도는 10, 20, 30, ... , 100으로 입력해주세요."),
    INVALID_DESK_SIZE_EMPTY(HttpStatus.BAD_REQUEST, "USERHELPINFO4002", "책상의 넓이를 입력해주세요."),
    INVALID_DESK_SIZE(HttpStatus.BAD_REQUEST, "USERHELPINFO4003", "책상의 넓이는 small, medium, large, none으로 입력해주세요."),
    INVALID_LIGHT_EMPTY(HttpStatus.BAD_REQUEST, "USERHELPINFO4004", "조명을 입력해주세요."),
    INVALID_LIGHT(HttpStatus.BAD_REQUEST, "USERHELPINFO4005", "조명은 bright, adequate, dark, none으로 입력해주세요."),
    INVALID_OUTLET_COUNT_EMPTY(HttpStatus.BAD_REQUEST, "USERHELPINFO4006", "콘센트 개수를 입력해주세요."),
    INVALID_OUTLET_COUNT(HttpStatus.BAD_REQUEST, "USERHELPINFO4007", "콘센트 개수는 enough, adequate, lack, none으로 입력해주세요."),

    USER_HELP_INFO_NOT_FOUND(HttpStatus.NOT_FOUND,"USERHELPINFO4008","해당하는 유저 도움 정보가 없습니다."),
    INVALID_USER_HELP_INFO(HttpStatus.FORBIDDEN,"USERHELPINFO4009","해당 유저 도움 정보에 접근 권한이 없습니다."),

    // 유저 도움 정보 좋아요
    LIKE_ALREADY_LIKED(HttpStatus.CONFLICT,"LIKE4000","이미 좋아요를 누르셨습니다."),
    LIKE_NOT_FOUND(HttpStatus.NOT_FOUND,"LKE4001","좋아요를 안누른 유저 도움 정보입니다."),

    // FILE 응답
    INVALID_URL(HttpStatus.BAD_REQUEST,"FILE4000","URL형식이 올바르지 않습니다."),
  
    // 유저 가게 찜하기 응답
    LIKE_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "LIKE4000", "이미 찜을 눌렀습니다."),

    LIKE_NOT_EXIST(HttpStatus.BAD_REQUEST, "REVIEW4001", "찜을 누른 적이 없습니다.");


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
