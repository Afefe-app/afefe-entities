package com.ocean.afefe.entities.common;

import com.google.protobuf.Any;
import com.google.protobuf.BoolValue;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import com.google.protobuf.InvalidProtocolBufferException;

import java.util.HashMap;
import java.util.Map;

public class AnyMapper {

    public static Map<String, Object> toJavaMap(Map<String, Any> anyMap) {
        Map<String, Object> result = new HashMap<>();

        for (Map.Entry<String, Any> entry : anyMap.entrySet()) {
            String key = entry.getKey();
            Any any = entry.getValue();

            try {
                result.put(key, unpackToJavaObject(any));
            } catch (InvalidProtocolBufferException e) {
                result.put(key, any);
            }
        }

        return result;
    }

    private static Object unpackToJavaObject(Any any) throws InvalidProtocolBufferException {
        if (any.is(StringValue.class)) {
            return any.unpack(StringValue.class).getValue();
        }
        if (any.is(Int32Value.class)) {
            return any.unpack(Int32Value.class).getValue();
        }
        if (any.is(Int64Value.class)) {
            return any.unpack(Int64Value.class).getValue();
        }
        if (any.is(BoolValue.class)) {
            return any.unpack(BoolValue.class).getValue();
        }
        if (any.is(DoubleValue.class)) {
            return any.unpack(DoubleValue.class).getValue();
        }
        return any;
    }
}
