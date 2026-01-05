package com.ocean.afefe.entities.modules.taxonomy.service;

import com.ocean.afefe.entities.modules.taxonomy.models.Industry;

import java.util.List;
import java.util.UUID;

public interface TaxonomyDomainService {
    Industry validateIndustryExistenceById(UUID industryId);

    List<Industry> validateIndustryExistence(List<UUID> industryIds);
}
