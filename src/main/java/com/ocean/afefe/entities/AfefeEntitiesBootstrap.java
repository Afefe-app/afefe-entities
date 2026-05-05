package com.ocean.afefe.entities;


import com.ocean.afefe.entities.common.GrpcCommons;
import com.ocean.afefe.entities.common.SecurityPathsProps;
import com.ocean.afefe.entities.core.CoreModule;
import com.ocean.afefe.entities.core.security.SecurityModule;
import com.ocean.afefe.entities.domain.DomainModule;
import com.ocean.afefe.entities.modules.calendar.CalendarModule;
import com.ocean.afefe.entities.modules.contents.ContentModule;
import com.ocean.afefe.entities.modules.enrollments.EnrollmentModule;
import com.ocean.afefe.entities.modules.payment.PaymentModule;
import com.ocean.afefe.entities.modules.trainings.TrainingsModule;
import com.ocean.afefe.entities.modules.taxonomy.TaxonomyModule;
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
        ContentModule.class,
        CalendarModule.class,
        EnrollmentModule.class,
        PaymentModule.class,
        TaxonomyModule.class,
        TrainingsModule.class,
        GrpcCommons.class,
})
// Exclude com.ocean.afefe.entities.proto: protobuf-generated classes live there and are not
// repositories; scanning them breaks startup (Spring ASM can fail on large generated classes).
@EnableJpaRepositories(basePackages = {
        "com.ocean.afefe.entities.modules",
        "com.ocean.afefe.entities.core.localstore"
})
@EntityScan(basePackages = {
        "com.ocean.afefe.entities.modules",
        "com.ocean.afefe.entities.core.localstore"
})
@EnableConfigurationProperties({
        SecurityPathsProps.class
})
public class AfefeEntitiesBootstrap {
}
