package com.ocean.afefe.entities.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ocean.afefe.entities.proto.GrpcStringResponse;
import com.tensorpoint.toolkit.tpointcore.commons.ApiBaseResponse;
import com.tensorpoint.toolkit.tpointcore.commons.OmnixApiException;
import com.tensorpoint.toolkit.tpointcore.commons.OmnixApiResponse;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class GrpcCommons {

    private final ObjectMapper objectMapper;

    public GrpcStringResponse map(OmnixApiResponse<String> response) {
        return GrpcStringResponse.newBuilder()
                .setResponseCode(response.getResponseCode())
                .setResponseMessage(response.getResponseMessage())
                .setResponseData(response.getResponseData())
                .build();
    }

    public <T> void completeError(StreamObserver<T> streamObserver, Class<T> tClass, Exception e) {
        streamObserver.onNext(applyError(e, tClass));
        streamObserver.onCompleted();
    }

    @SneakyThrows
    public <T> T applyError(Exception e, Class<T> tClass) {
        ApiBaseResponse apiBaseResponse;
        if (e instanceof OmnixApiException omnixApiException) {
            apiBaseResponse = applyError(omnixApiException);
        } else {
            apiBaseResponse =
                    ApiBaseResponse.getInstance()
                            .withResponseCode(ResponseCode.INTERNAL_SERVER_ERROR)
                            .withResponseMessage(e.getMessage())
                            .withError(e.getMessage())
                            .withErrors(new HashSet<>());
        }
        String json = objectMapper.writeValueAsString(apiBaseResponse);
        return objectMapper.readValue(json, tClass);
    }

    public ApiBaseResponse applyError(OmnixApiException omnixApiException) {
        return ApiBaseResponse.getInstance()
                .withResponseCode(omnixApiException.getCode())
                .withResponseMessage(omnixApiException.getMessage())
                .withError(omnixApiException.getException().getMessage())
                .withErrors(omnixApiException.getErrors());
    }
}
