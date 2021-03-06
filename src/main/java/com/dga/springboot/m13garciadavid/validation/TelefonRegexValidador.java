package com.dga.springboot.m13garciadavid.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Classe que defineix els paràmetres per validar el telefono
 */
public class TelefonRegexValidador implements ConstraintValidator<TelefonRegex, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s.matches("(6|7|9)[ -]*([0-9][ -]*){8}")) {
            return true;
        }
        return false;
    }
}
