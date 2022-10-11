package com.wnsales.util.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
        return true;
    }
}
