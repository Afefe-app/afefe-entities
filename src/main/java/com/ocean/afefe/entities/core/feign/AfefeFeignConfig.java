package com.ocean.afefe.entities.core.feign;

import com.tensorpoint.toolkit.logger.OmnixFeignLogger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;

public class AfefeFeignConfig extends SpringDecoder implements RequestInterceptor {

    private final OmnixFeignLogger omnixFeignLogger;

    public AfefeFeignConfig(ObjectFactory<HttpMessageConverters> messageConverters, OmnixFeignLogger omnixFeignLogger) {
        super(messageConverters);
        this.omnixFeignLogger = omnixFeignLogger;
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        try{
            byte[] rawRequestBodyBytes = requestTemplate.body();
            String rawRequestBodyJson = new String(rawRequestBodyBytes);
            omnixFeignLogger.logHttpFeignRequest(requestTemplate, rawRequestBodyJson, rawRequestBodyJson);
        }catch (Exception ignored){}
    }
}
