package com.ocean.afefe.entities.common;


import com.tensorpoint.toolkit.tpointcore.commons.PageParameters;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public class PageUtils {

    public static Pageable from(PageParameters pageParameters, String... sortProperties) {
        String[] sortProps = Objects.isNull(sortProperties) || sortProperties.length == 0 ? new String[]{ "createdAt" } : sortProperties;
        return PageRequest.of(
                pageParameters.getPageNumber(),
                pageParameters.getPageSize(),
                Sort.Direction.valueOf(pageParameters.getSort()),
                sortProps);
    }
}
