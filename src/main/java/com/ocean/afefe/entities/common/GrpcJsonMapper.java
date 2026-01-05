package com.ocean.afefe.entities.common;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import java.util.function.Supplier;

public final class GrpcJsonMapper {

    private GrpcJsonMapper() {}

    public static <T extends Message> T fromJson(
            String json, Supplier<? extends Message.Builder> builderSupplier) {
        try {
            Message.Builder builder = builderSupplier.get();
            JsonFormat.parser().ignoringUnknownFields().merge(json, builder);
            @SuppressWarnings("unchecked")
            T msg = (T) builder.build();
            return msg;
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse protobuf json", e);
        }
    }
}
