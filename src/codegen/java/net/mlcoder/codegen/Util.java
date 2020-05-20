package net.mlcoder.codegen;

import com.google.common.io.Resources;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Util {
    public static Configuration freeMarkerConfiguration() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_20);
        cfg.setClassForTemplateLoading(Configuration.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        return cfg;
    }

    public static String readAll(String resourceName) throws Exception {
        return Resources.toString(Resources.getResource(resourceName), StandardCharsets.UTF_8);
    }

    public static List<String> readlLines(String file) throws Exception {
        return Resources.readLines(Resources.getResource(file), StandardCharsets.UTF_8);
    }
}
