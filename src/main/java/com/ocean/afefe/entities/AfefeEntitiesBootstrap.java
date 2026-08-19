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
// NSE lives in schema afefe_nse with parallel entity/repository types. It is wired via
// NseEntitiesConfig in afefe-nse-backend only. Scanning it here collides on bean names
// (e.g. TrainerRepository) with the shared modules below.
@EnableJpaRepositories(basePackages = {
        "com.ocean.afefe.entities.modules.admin",
        "com.ocean.afefe.entities.modules.analytics",
        "com.ocean.afefe.entities.modules.appuser",
        "com.ocean.afefe.entities.modules.assessment",
        "com.ocean.afefe.entities.modules.auth",
        "com.ocean.afefe.entities.modules.calendar",
        "com.ocean.afefe.entities.modules.certification",
        "com.ocean.afefe.entities.modules.contents",
        "com.ocean.afefe.entities.modules.enrollments",
        "com.ocean.afefe.entities.modules.helpcenter",
        "com.ocean.afefe.entities.modules.notifications",
        "com.ocean.afefe.entities.modules.payment",
        "com.ocean.afefe.entities.modules.talentpool",
        "com.ocean.afefe.entities.modules.taxonomy",
        "com.ocean.afefe.entities.modules.trainings",
        "com.ocean.afefe.entities.core.localstore"
})
@EntityScan(basePackages = {
        "com.ocean.afefe.entities.modules.admin",
        "com.ocean.afefe.entities.modules.analytics",
        "com.ocean.afefe.entities.modules.appuser",
        "com.ocean.afefe.entities.modules.assessment",
        "com.ocean.afefe.entities.modules.auth",
        "com.ocean.afefe.entities.modules.calendar",
        "com.ocean.afefe.entities.modules.certification",
        "com.ocean.afefe.entities.modules.contents",
        "com.ocean.afefe.entities.modules.enrollments",
        "com.ocean.afefe.entities.modules.helpcenter",
        "com.ocean.afefe.entities.modules.notifications",
        "com.ocean.afefe.entities.modules.payment",
        "com.ocean.afefe.entities.modules.talentpool",
        "com.ocean.afefe.entities.modules.taxonomy",
        "com.ocean.afefe.entities.modules.trainings",
        "com.ocean.afefe.entities.core.localstore"
})
@EnableConfigurationProperties({
        SecurityPathsProps.class
})
public class AfefeEntitiesBootstrap {
}
