package com.ocean.afefe.entities.core;

import com.ocean.afefe.entities.core.localstore.FileResourceCacheConfig;
import com.ocean.afefe.entities.core.security.SecurityModule;
import com.ocean.afefe.entities.core.service.impl.OrganizationServiceImpl;
import com.ocean.afefe.entities.core.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
        OrganizationServiceImpl.class,
        UserServiceImpl.class,
        SecurityModule.class,
        FileResourceCacheConfig.class
})
@Configuration
public class CoreModule {
}
