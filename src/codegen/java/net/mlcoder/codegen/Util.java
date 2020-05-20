package net.mlcoder.codegen;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class Util {
    public static final String NEWLINE = "&#10;";
    public static final ObjectMapper objectMapper = new ObjectMapper()
        .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.PUBLIC_ONLY)
        .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
        .enable(JsonGenerator.Feature.IGNORE_UNKNOWN);

    public static TemplateEngine templateEngine(TemplateMode mode) {
        return templateEngine(mode, null, null);
    }

    public static TemplateEngine templateEngine(TemplateMode mode, String prefix, String suffix) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(mode);

        if (StringUtils.isNotEmpty(prefix))
            templateResolver.setPrefix("/templates/");

        if (StringUtils.isNotEmpty(suffix))
            templateResolver.setSuffix(".xml");


        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        return templateEngine;
    }

    public static Configuration freeMarkerConfiguration() {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_20);
        cfg.setClassForTemplateLoading(Configuration.class, "/templates");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        return cfg;
    }


    public static String transformSnippet(String text) {
        String result = StringUtils.replace(text, "\n", NEWLINE);
        result = StringUtils.replace(result, "\t", "  ");
        result = RegExUtils.replaceAll(result, "\\$([1-9]+)", "\\$VAR$1\\$");
        result = RegExUtils.replaceAll(result, "\\$\\{([1-9]+)\\}", "\\$VAR$1\\$");
        result = RegExUtils.replaceAll(result, "\\$\\{[0-9]+:(\\p{Alnum}+)\\}", "\\$$1\\$");

        return "\\" + result + "&#10;$END$";
    }

    public static String readAll(String resourceName) throws Exception {
        return Resources.toString(Resources.getResource(resourceName), StandardCharsets.UTF_8);
    }

    public static List<String> readlLines(String file) throws Exception {
        return Resources.readLines(Resources.getResource(file), StandardCharsets.UTF_8);
    }

    public static <T> T readModel(String resourceName, TypeReference<T> tTypeReference) throws Exception {
        String content = readAll(resourceName);
        return objectMapper.readValue(content, tTypeReference);
    }

    public static void main(String[] args) {
        System.out.println(transformSnippet("Bigl(${1}\\Bigr)"));

    }
}
