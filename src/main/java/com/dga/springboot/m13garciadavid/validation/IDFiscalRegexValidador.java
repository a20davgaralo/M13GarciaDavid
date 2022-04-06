package com.dga.springboot.m13garciadavid.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IDFiscalRegexValidador implements ConstraintValidator<IDFiscalRegex, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.matches("[0-9]{8}[A-Z&&[^IOUÑ]]")) {
            return true;
        }
        return false;
    }
}
