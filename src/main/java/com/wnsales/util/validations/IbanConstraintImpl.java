package com.wnsales.util.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IbanConstraintImpl implements ConstraintValidator<IbanConstraint, String> {

    @Override
    public void initialize(IbanConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String iban, ConstraintValidatorContext constraintValidatorContext) {
        if(iban == null || iban.trim().isEmpty() || iban.contains(" ")){
            return false;
        }
        String reg = "^PT[0-9]{2}[0-9]{21}$";
        return Pattern.matches(reg, iban);
    }
}
