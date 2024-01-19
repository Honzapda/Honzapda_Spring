package Honzapda.Honzapda_server.user.validation.validator;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.user.service.UserService;
import Honzapda.Honzapda_server.user.validation.annotation.UniqueEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        boolean valid = !userService.isEMail(value);
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.EMAIL_NOT_UNIQUE.getMessage()).addConstraintViolation();
        }
        return valid;
    }
}
