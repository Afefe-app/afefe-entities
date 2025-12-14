package com.ocean.afefe.entities.domain.usecase;


import com.ocean.afefe.entities.common.RequestContextMeta;
import com.ocean.afefe.entities.domain.aggregate.Aggregate;
import com.ocean.afefe.entities.domain.aggregate.CommandResult;

public interface DomainUseCase<T, R extends Aggregate> {

    CommandResult<R> execute(T command, RequestContextMeta requestContextMeta);
}
