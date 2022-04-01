package com.dga.springboot.m13garciadavid.validation;

import javax.validation.Payload;

public @interface TelefonRegex {
    String message() default "Telèfon incorrecte, ha de contenir nou números i començar per 6, 7 o 9";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
