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

    public GrpcStringResponse mapError(OmnixApiException omnixApiException) {
        return GrpcStringResponse.newBuilder()
                .setResponseCode(CommonUtil.returnOrDefault(omnixApiException.getCode(), ResponseCode.INTERNAL_SERVER_ERROR))
                .setResponseMessage(CommonUtil.returnOrDefault(omnixApiException.getMessage(), "Something went wrong"))
                .addAllErrors(CommonUtil.returnOrDefault(omnixApiException.getErrors(), new ArrayList<>()))
                .build();
    }

    public GrpcStringResponse mapError(Exception e) {
        if (e instanceof OmnixApiException omnixApiException) {
            return mapError(omnixApiException);
        }else {
            return GrpcStringResponse.newBuilder()
                    .setResponseCode(ResponseCode.INTERNAL_SERVER_ERROR)
                    .setResponseMessage(CommonUtil.returnOrDefault(e.getMessage(), "Something went wrong"))
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
        System.out.println("Code: ======>" + grpcStringResponse.getResponseCode());
        System.out.println("Message: =======>" + grpcStringResponse.getResponseMessage());

        List<String> errors = new ArrayList<>();
        for (int i = 0; i < grpcStringResponse.getErrorsCount(); i++) {
            errors.add(grpcStringResponse.getErrors(i));
        }

        System.out.println("Errors: =========>" + errors);
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
