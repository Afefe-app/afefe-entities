package com.ocean.afefe.entities.core.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresRole {

    String[] value();

    Mode mode() default Mode.ANY;

    enum Mode {
        ALL,
        ANY
    }
}
