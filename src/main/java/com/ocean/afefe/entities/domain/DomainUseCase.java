package com.ocean.afefe.entities.domain;


import com.ocean.afefe.entities.common.RequestContextMeta;

public interface DomainUseCase<T, R extends Aggregate> {

    CommandResult<R> execute(T command, RequestContextMeta requestContextMeta);
}
