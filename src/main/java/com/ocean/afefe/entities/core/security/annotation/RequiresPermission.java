package com.ocean.afefe.entities.core.security.annotation;

import com.ocean.afefe.entities.modules.auth.models.GovernmentPermission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {

    GovernmentPermission[] value();

    Mode mode() default Mode.ALL;

    enum Mode {
        ALL,
        ANY
    }
}
