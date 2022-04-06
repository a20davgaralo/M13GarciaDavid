package com.dga.springboot.m13garciadavid.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IDFiscalRegexValidador implements ConstraintValidator<IDFiscalRegex, String> {

    private final String REGEX_CIF = "([ABCDEFGHJKLMNPQRSUVW])(\\d{7})([0-9A-J])";
    private final String REGEX_DNI = "[0-9]{8}[A-Z&&[^IOUÑ]]";


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.matches(REGEX_DNI) || s.matches(REGEX_CIF) ) {
            return true;
        }
        return false;
    }
}
