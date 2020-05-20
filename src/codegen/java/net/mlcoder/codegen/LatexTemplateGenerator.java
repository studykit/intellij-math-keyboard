package net.mlcoder.codegen;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.*;
import net.mlcoder.unimath.Tex;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.mlcoder.codegen.Snippet.extractVariable;
import static net.mlcoder.codegen.Snippet.liveTemplateValueFrom;


class Snippet {
    static final String NEWLINE = "&#10;";
    static Pattern variablePattern = Pattern.compile("\\$([0-9]+)|\\$\\{([0-9]+)}|\\$\\{[0-9]+:<??(\\p{Alnum}+)>??}");
    static final Predicate<String> hasVariable = variablePattern.asPredicate();

    static List<String> extractVariable(String snippet) {
        if (StringUtils.isEmpty(snippet))
            return Collections.emptyList();

        List<String> vars = variablePattern.matcher(snippet)
            .results()
            .map(mr -> {
                String result = RegExUtils.replaceAll(mr.group(), "\\$([0-9]+)", "$1,\\$VAR$1\\$");
                result = RegExUtils.replaceAll(result, "\\$\\{([0-9]+)\\}", "$1,\\$VAR$1\\$");
                result = RegExUtils.replaceAll(result, "\\$\\{([0-9]+):<??(\\p{Alnum}+)>??\\}", "$1,$2\\$");
                return StringUtils.remove(result, "$");
            }).collect(Collectors.toList());
        vars.sort(String::compareTo);
        return vars.stream()
            .map(s -> RegExUtils.removePattern(s, "^\\d+,"))
            .collect(Collectors.toList());
    }

    static String liveTemplateValueFrom(String commandname, String snippet) {
        if (StringUtils.isEmpty(snippet)) {
            return "\\" + commandname + " $END$";
        }

        String result = StringUtils.replace(snippet, "\n", NEWLINE);
        result = StringUtils.replace(result, "\t", "  ");
        result = RegExUtils.replaceAll(result, "\\$([0-9]+)", "\\$VAR$1\\$");
        result = RegExUtils.replaceAll(result, "\\$\\{([0-9]+)\\}", "\\$VAR$1\\$");
        result = RegExUtils.replaceAll(result, "\\$\\{[0-9]+:<??(\\p{Alnum}+)>??\\}", "\\$$1\\$");
        return "\\" + result + (StringUtils.contains(snippet, "\\end") ? NEWLINE + "$END$" : " $END$");
    }
}


public class LatexTemplateGenerator {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
        .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.PUBLIC_ONLY)
        .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private static TypeReference<Map<String, Command>> type = new TypeReference<>() {};

    public static Collection<Command> commands() throws Exception {
        Map<String, Tex> texes = Maps.newHashMap();

        for (Tex tex : Tex.load().values()) {
            if (tex.latex != null)
                texes.put(tex.latex.substring(1), tex);

            if (tex.ulatex != null)
                texes.put(tex.ulatex.substring(1), tex);
        }

        Collection<Command> cmds = OBJECT_MAPPER.readValue(Util.readAll("latex/commands.json"), type).values();

        for (Command c : cmds) {
            Tex tex = texes.get(c.command);

            c.vars = extractVariable(c.snippet);

            if (tex != null && c.vars.size() == 0) {
                c.label = tex.chars;
                c.snippet = "\\" + c.command +" $END$";
                continue;
            }

            c.snippet = liveTemplateValueFrom(c.command, c.snippet);

            if (StringUtils.isEmpty(c.label) && StringUtils.isNotEmpty(c.detail)) {
                c.label = c.detail;
                continue;
            }

            c.label = c.command;
        }

        return cmds;
    }

    public static List<Environment> environments() throws Exception {
        List<String> lines = Util.readlLines("latex/environments.txt");
        List<Environment> envs = Lists.newArrayList();
        for (String l : lines) {
            if (StringUtils.isEmpty(l))
                continue;
            envs.add(new Environment(l, String.format("\\begin{%s}&#10;  $EXPR$&#10;\\end{%s}&#10;&#10;$SELECTION$&#10;", l, l)));
        }
        return envs;
    }


    @RequiredArgsConstructor
    @ToString @Getter
    public static class Environment {
        public final String command;
        public final String snippet;
    }

    @Getter @ToString
    @AllArgsConstructor @NoArgsConstructor
    public static class Command {
        @Nullable public String command;
        @Nullable public String detail;
        @Nullable public String label;
        @Nullable public String snippet;
        @JsonIgnore public List<String> vars;
    }

    public static void main(String[] args) throws Exception {
        Configuration configuration = Util.freeMarkerConfiguration();
        Template template = configuration.getTemplate("live-template.xml.ftl");

        Map<String, Object> root = Maps.newHashMap();
        root.put("envs", environments());
        root.put("cmds", commands());
        root.put("name", "Latex");
        template.process(root, new FileWriter("Latex.xml"));
    }
}
