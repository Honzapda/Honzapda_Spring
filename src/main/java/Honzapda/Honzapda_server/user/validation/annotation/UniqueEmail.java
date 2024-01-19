package Honzapda.Honzapda_server.user.validation.annotation;

import Honzapda.Honzapda_server.user.validation.validator.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {
    String message() default "이미 존재하는 계정입니다.";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}