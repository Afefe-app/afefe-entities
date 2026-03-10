package com.ocean.afefe.entities.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestContextMeta {

    private String requestUrl;
    private Map<String, String> requestHeaders = new HashMap<>();
    private Map<String, String> requestParams = new HashMap<>();


    public static RequestContextMeta newDefault(){
        return new RequestContextMeta(StringValues.EMPTY_STRING, new HashMap<>(), new HashMap<>());
    }
}
