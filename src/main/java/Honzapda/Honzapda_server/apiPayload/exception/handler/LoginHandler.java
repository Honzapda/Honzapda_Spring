package Honzapda.Honzapda_server.apiPayload.exception.handler;

import Honzapda.Honzapda_server.apiPayload.code.BaseErrorCode;
import Honzapda.Honzapda_server.apiPayload.exception.GeneralException;

public class LoginHandler extends GeneralException {
    public LoginHandler(BaseErrorCode code) {
        super(code);
    }
}
