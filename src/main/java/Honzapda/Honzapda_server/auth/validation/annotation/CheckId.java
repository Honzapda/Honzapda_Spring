package Honzapda.Honzapda_server.auth.validation.annotation;

import Honzapda.Honzapda_server.auth.validation.validator.IdCheckValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IdCheckValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CheckId {
    String message() default "이미 존재하는 이메일입니다.";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}