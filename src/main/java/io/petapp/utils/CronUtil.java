/*
 * Copyright (c) 2021. Pitagon., JSC. All rights reserved.
 */

package io.petapp.utils;

import com.cronutils.descriptor.CronDescriptor;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinition;
import com.cronutils.model.definition.CronDefinitionBuilder;
import com.cronutils.parser.CronParser;

import java.util.Locale;

/**
 * @author truongtran
 */
public class CronUtil {

    private static final CronDefinition CRON_DEFINITION = CronDefinitionBuilder.instanceDefinitionFor(CronType.QUARTZ);
    private static final CronParser CRON_PARSER = new CronParser(CRON_DEFINITION);

    public static String describe(String exp, Locale locale) {
        CronDescriptor descriptor = CronDescriptor.instance(locale);
        return descriptor.describe(CRON_PARSER.parse(exp));
    }

}
