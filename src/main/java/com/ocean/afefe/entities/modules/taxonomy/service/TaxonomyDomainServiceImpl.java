package com.ocean.afefe.entities.modules.taxonomy.service;

import com.ocean.afefe.entities.modules.taxonomy.models.Industry;
import com.ocean.afefe.entities.modules.taxonomy.repository.IndustryRepository;
import com.tensorpoint.toolkit.tpointcore.commons.HttpUtil;
import com.tensorpoint.toolkit.tpointcore.commons.MessageUtil;
import com.tensorpoint.toolkit.tpointcore.commons.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaxonomyDomainServiceImpl implements TaxonomyDomainService{

    private final MessageUtil messageUtil;
    private final IndustryRepository industryRepository;

    @Override
    public Industry validateIndustryExistenceById(UUID industryId){
        return industryRepository.findById(industryId)
                .orElseThrow(() -> HttpUtil.getResolvedException(ResponseCode.RECORD_NOT_FOUND, messageUtil.getMessage("industry.record.not.found")));
    }

    @Override
    public List<Industry> validateIndustryExistence(List<UUID> industryIds){
        List<Industry> industries = industryRepository.findAllByIdIn(industryIds);
        if(industries.size() != industryIds.size()){
            List<UUID> unknownIds;
            List<UUID> databaseIds = new ArrayList<>(industries.stream().map(Industry::getId).toList());
            if(industries.size() > industryIds.size()){
                unknownIds = databaseIds;
                unknownIds.removeAll(industryIds);
            }else{
                unknownIds = new ArrayList<>(industryIds);
                unknownIds.removeAll(databaseIds);
            }
            if(!unknownIds.isEmpty()){
                throw HttpUtil.getResolvedException(ResponseCode.RECORD_NOT_FOUND, messageUtil.getMessage("industry.record.not.found.for.ids.in", String.valueOf(unknownIds)));
            }
        }
        return industries;
    }
}
