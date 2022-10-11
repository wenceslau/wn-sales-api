package com.wnsales.util.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IbanConstraintImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface IbanConstraint {
    String message() default "Invalid format IBAN";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
