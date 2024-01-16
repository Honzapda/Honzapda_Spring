package Honzapda.Honzapda_server.user.validation.annotation;

import Honzapda.Honzapda_server.user.validation.validator.UniqueNickNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueNickNameValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface UnqiueNickName {
    String message() default "이미 등록된 닉네임입니다.";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
