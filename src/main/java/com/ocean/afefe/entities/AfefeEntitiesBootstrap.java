package com.ocean.afefe.entities;


import com.ocean.afefe.entities.common.SecurityPathsProps;
import com.ocean.afefe.entities.core.CoreModule;
import com.ocean.afefe.entities.core.security.SecurityModule;
import com.ocean.afefe.entities.domain.DomainModule;
import com.ocean.afefe.entities.modules.contents.ContentModule;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@Import({
        DomainModule.class,
        CoreModule.class,
        SecurityModule.class,
        ContentModule.class
})
@EnableJpaRepositories(value = {"com.ocean.afefe.entities"})
@EntityScan(basePackages = { "com.ocean.afefe.entities" })
@EnableConfigurationProperties({
        SecurityPathsProps.class
})
public class AfefeEntitiesBootstrap {
}
