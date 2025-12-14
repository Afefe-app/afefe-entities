package com.ocean.afefe.entities.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "security.paths")
public record SecurityPathsProps(List<String> exclusions) {}
