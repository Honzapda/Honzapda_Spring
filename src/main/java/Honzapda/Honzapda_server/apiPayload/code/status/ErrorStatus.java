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
    _DATA_INTEGRITY_VIOLATED(HttpStatus.CONFLICT,"COMMON409","이미 존제하는 데이터입니다."),
    // 회원가입 응답
    EMAIL_NOT_UNIQUE(HttpStatus.CONFLICT,"JOIN"+HttpStatus.CONFLICT.value(),"이미 존재하는 계정입니다."),
    NICKNAME_NOT_UNIQUE(HttpStatus.CONFLICT,"JOIN"+HttpStatus.CONFLICT.value(),"이미 존재하는 닉네임입니다."),
    // 로그인 응답
    ID_NOT_EXIST(HttpStatus.NOT_FOUND,"LOGIN"+HttpStatus.NOT_FOUND.value(),"ID를 잘못 입력하셨습니다."),
    NICKNAME_NOT_EXIST(HttpStatus.NOT_FOUND,"LOGIN"+HttpStatus.NOT_FOUND.value(),"닉네임을 잘못 입력하셨습니다."),
    PW_NOT_MATCH(HttpStatus.FORBIDDEN,"LOGIN"+HttpStatus.FORBIDDEN.value(),"PW를 잘못 입력하셨습니다."),
    // 유저 응답
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"USER"+HttpStatus.NOT_FOUND.value(),"존재하지 않는 유저입니다.");

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
