package com.dga.springboot.m13garciadavid.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = TelefonRegexValidador.class)
@Retention(RUNTIME)
@Target({FIELD, METHOD})
public @interface TelefonRegex {
    String message() default "Telèfon incorrecte, ha de contenir nou números i començar per 6, 7 o 9";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
