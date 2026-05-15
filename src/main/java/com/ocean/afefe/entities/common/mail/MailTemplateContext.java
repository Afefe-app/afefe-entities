package com.ocean.afefe.entities.common.mail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Template file key plus placeholder map for org-wide mail rendering.
 * Uses the same {@code {key}} placeholders as {@code FileUtilities.formatHtmlLinkWithSimpleContextBinder}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MailTemplateContext {

    private String templateFile;

    @Builder.Default
    private Map<String, Object> contextData = new HashMap<>();
}
