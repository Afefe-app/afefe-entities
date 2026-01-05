package com.ocean.afefe.entities.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Message;
import com.ocean.afefe.entities.proto.GrpcStringResponse;
import com.ocean.afefe.entities.proto.PaginationMetaGrpc;
import com.tensorpoint.toolkit.pagination.PaginationMeta;
import com.tensorpoint.toolkit.tpointcore.commons.*;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Supplier;

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

    public PaginationMetaGrpc map(PaginationMeta paginationMeta){
        if(Objects.isNull(paginationMeta)){
            return PaginationMetaGrpc.newBuilder().build();
        }
        return PaginationMetaGrpc.newBuilder()
                .setPageNumber((int) paginationMeta.getPageNumber())
                .setPageSize((int) paginationMeta.getPageSize())
                .setPageCount((int) paginationMeta.getPageCount())
                .setNumberOfPages((int) paginationMeta.getNumberOfPages())
                .setTotalCount((int) paginationMeta.getTotalCount())
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

    public GrpcStringResponse mapError(Exception e) {
        if (e instanceof OmnixApiException omnixApiException) {
            return mapError(omnixApiException);
        }else {
            return GrpcStringResponse.newBuilder()
                    .setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR)
                    .setResponseMessage(e.getMessage())
                    .addAllErrors(Collections.singleton("Something went wrong"))
                    .build();
        }
    }

    @SneakyThrows
    public <T extends Message> void propagateError(
            Exception e,
            StreamObserver<T> streamObserver,
            Supplier<? extends Message.Builder> builderSupplier) {
        GrpcStringResponse grpcStringResponse = mapError(e);

        List<String> errors = new ArrayList<>();
        for (int i = 0; i < grpcStringResponse.getErrorsCount(); i++) {
            errors.add(grpcStringResponse.getErrors(i));
        }

        ApiBaseResponse apiBaseResponse =
                ApiBaseResponse.getInstance()
                        .withResponseCode(grpcStringResponse.getResponseCode())
                        .withResponseMessage(grpcStringResponse.getResponseMessage())
                        .withErrors(errors);

        String apiBaseResponseJson = objectMapper.writeValueAsString(apiBaseResponse);

        T serviceResponse = GrpcJsonMapper.fromJson(apiBaseResponseJson, builderSupplier);

        streamObserver.onNext(serviceResponse);
        streamObserver.onCompleted();
    }
}
