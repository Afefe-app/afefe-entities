package com.ocean.afefe.entities.core.localstore;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Objects;

@Repository
public interface LocalParamStorage extends JpaRepository<AppGenericParam, Long> {

    AppGenericParam findFirstByParamKey(String key);

    default String getParamValueOrDefault(Object key, String defaultValue){
        AppGenericParam param = findFirstByParamKey(String.valueOf(key));
        if(Objects.isNull(param)){
            param = new AppGenericParam();
            param.setParamKey(String.valueOf(key));
            param.setParamValue(defaultValue);
            param.setCreatedAt(Instant.now());
            param = saveAndFlush(param);
        }
        return param.getParamValue();
    }
}
