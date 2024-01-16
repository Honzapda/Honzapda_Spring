package Honzapda.Honzapda_server.auth.validation.validator;

import Honzapda.Honzapda_server.apiPayload.code.status.ErrorStatus;
import Honzapda.Honzapda_server.auth.service.AuthService;
import Honzapda.Honzapda_server.auth.validation.annotation.CheckId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdCheckValidator implements ConstraintValidator<CheckId, String> {

    private final AuthService authService;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean valid = !authService.isEMail(value);
        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.EMAIL_EXIST.toString()).addConstraintViolation();
        }
        return valid;
    }
}
