package com.ocean.afefe.entities.domain.usecase;


import com.ocean.afefe.entities.common.RequestContextMeta;
import com.ocean.afefe.entities.domain.aggregate.Aggregate;
import com.ocean.afefe.entities.domain.aggregate.CommandResult;
import com.ocean.afefe.entities.modules.auth.models.Organization;
import com.ocean.afefe.entities.modules.contents.models.Instructor;
import com.tensorpoint.toolkit.tpointcore.commons.OmnixApiException;
import org.springframework.transaction.annotation.Transactional;

public interface DomainUseCase<T, R extends Aggregate> {

    CommandResult<R> execute(T command, RequestContextMeta requestContextMeta);
}
