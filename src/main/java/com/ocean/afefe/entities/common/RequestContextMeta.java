package com.ocean.afefe.entities.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestContextMeta {

    private String requestUrl;
    private Map<String, String> requestHeaders = new HashMap<>();
    private Map<String, String> requestParams = new HashMap<>();
}
