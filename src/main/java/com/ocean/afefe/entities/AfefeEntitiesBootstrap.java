package com.ocean.afefe.entities;


import com.ocean.afefe.entities.core.CoreModule;
import com.ocean.afefe.entities.domain.DomainModule;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@Import({
        DomainModule.class,
        CoreModule.class
})
@EntityScan(basePackages = { "com.ocean.afefe" })
public class AfefeEntitiesBootstrap {
}
