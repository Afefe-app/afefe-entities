package com.ocean.afefe.entities.modules.taxonomy;

import com.ocean.afefe.entities.modules.taxonomy.service.TaxonomyDomainServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        TaxonomyDomainServiceImpl.class
})
public class TaxonomyModule {
}
