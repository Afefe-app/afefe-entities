package com.ocean.afefe.entities.common;

import java.util.function.BiConsumer;

public final class GrpcSetters {

    private GrpcSetters() {}

    public static <B, V> void setIfPresent(BiConsumer<B, V> setter, B builder, V value) {
        if (value != null) {
            setter.accept(builder, value);
        }
    }
}
