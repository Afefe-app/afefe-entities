package com.ocean.afefe.entities.modules.contents;

import com.ocean.afefe.entities.modules.contents.service.CourseDomainServiceImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
        CourseDomainServiceImpl.class
})
public class ContentModule {
}
