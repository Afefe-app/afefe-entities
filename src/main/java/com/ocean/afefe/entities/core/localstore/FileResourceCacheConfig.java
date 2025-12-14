package com.ocean.afefe.entities.core.localstore;

import com.tensorpoint.toolkit.tpointcore.commons.GlobalContextStore;
import com.tensorpoint.toolkit.tpointcore.commons.StringValues;
import com.tensorpoint.toolkit.tpointcore.file.FileUtilities;
import com.tensorpoint.toolkit.tpointcore.graphic.ColorWrapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class FileResourceCacheConfig {

    private static final String MAIL_FOLDER = "/mail/";
    private static final String MAIL_RESOURCES_PATH_MATCHING = "classpath*:/static/mail/**";

    @PostConstruct()
    public void mailResourceResourceCacheApplicationRunner() {
        log.info("Starting to list mail resources");
        List<Resource> mailFileResources =
                FileUtilities.listFiles(MAIL_RESOURCES_PATH_MATCHING);
        mailFileResources.forEach(
                resource -> {
                    try {
                        String completePath = resource.getURL().getPath();
                        String relativeFilePath =
                                completePath
                                        .substring(completePath.indexOf(MAIL_FOLDER))
                                        .replace(MAIL_FOLDER, StringValues.EMPTY_STRING);
                        GlobalContextStore.save(
                                relativeFilePath,
                                resource.getContentAsString(StandardCharsets.UTF_8));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        log.info(
                ColorWrapper.wrapGreen(
                        "Completely saved mail resources in the global context store"));
    }
}
