package com.ocean.afefe.entities.common;


import com.tensorpoint.toolkit.tpointcore.commons.PageParameters;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtils {

    public static Pageable from(PageParameters pageParameters, String... sortProperties) {
        return PageRequest.of(
                pageParameters.getPageNumber(),
                pageParameters.getPageSize(),
                Sort.Direction.valueOf(pageParameters.getSort()),
                sortProperties);
    }
}
