package Honzapda.Honzapda_server.user.validation.validator;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.user.service.UserService;
import Honzapda.Honzapda_server.user.validation.annotation.UnqiueNickName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueNickNameValidator implements ConstraintValidator<UnqiueNickName,String> {

    private final UserService userService;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean valid = !userService.isNickName(value);
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.NICKNAME_NOT_UNIQUE.getMessage()).addConstraintViolation();
        }
        return valid;
    }
}
