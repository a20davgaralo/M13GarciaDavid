package com.dga.springboot.m13garciadavid.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Author: David García Alonso
 * Versió: 1.0
 * Classe de tipus annotation per definir el missatge mostrat per validar el camp identificacionFiscal de la classe Cliente
 */
@Component
@Constraint(validatedBy = IDFiscalRegexValidador.class)
@Retention(RUNTIME)
@Target({FIELD, METHOD})
public @interface IDFiscalRegex {

    String message() default "Format identificació fiscal incorrecte";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
