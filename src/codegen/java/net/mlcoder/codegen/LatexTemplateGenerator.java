package net.mlcoder.codegen;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Getter;
import lombok.ToString;
import net.mlcoder.unimath.Tex;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.StringEscapeUtils;

import javax.annotation.Nullable;
import java.io.FileWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class LatexTemplateGenerator {
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
        .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.PUBLIC_ONLY)
        .setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    private static final String NEWLINE = "&#10;";
    private static final Pattern varNumPattern = Pattern.compile("\\$([0-9]+)");
    private static final Pattern varNumCurlyPattern = Pattern.compile("\\$\\{([0-9]+)}");
//    private static final Pattern varNumNamePattern = Pattern.compile("\\$\\{([0-9]+):([a-zA-Z0-9_<>=,%& -]+)}");
    private static final Pattern varNumNamePattern = Pattern.compile("\\$\\{([0-9]+):((\\p{Alnum}|[0-9_<>=,%& -])+)}");
    private static final Pattern variablePattern = Pattern.compile(varNumPattern.pattern() + "|" + varNumCurlyPattern.pattern() + "|" + varNumNamePattern.pattern());

    private static Pair<String, List<String>> toLiveTemplate(String snippet) {
        if (StringUtils.isEmpty(snippet))
            return Pair.of(snippet, Collections.emptyList());

        Matcher matcher = variablePattern.matcher(snippet);

        if (!matcher.find())
            return Pair.of(snippet, Collections.emptyList());

        List<String> vars = Lists.newArrayList();

        String result = snippet;

        Matcher vmr = varNumPattern.matcher(result);
        result = vmr.replaceAll(mr -> {
            vars.add(mr.group(1) + ":VAR" + mr.group(1));
            return "\\$VAR" + mr.group(1) + "\\$";
        });

        vmr = varNumCurlyPattern.matcher(result);
        result = vmr.replaceAll(mr -> {
            vars.add(mr.group(1) + ":VAR" + mr.group(1));
            return "\\$VAR" + mr.group(1) + "\\$";
        });

        vmr = varNumNamePattern.matcher(result);
        result = vmr.replaceAll(mr -> {
            String sanitized = RegExUtils.replacePattern(mr.group(2), "\\s+|-", "_");
            sanitized = RegExUtils.removePattern(sanitized, "[%<>=&,]+").toUpperCase();
            vars.add(mr.group(1) + ":" + sanitized);
            return "\\$" + sanitized + "\\$";
        });

        result = StringEscapeUtils.escapeXml10(result);

        vars.sort(String::compareTo);
        List<String> r = vars.stream()
            .map(s -> RegExUtils.removePattern(s, "^\\d+:"))
            .collect(Collectors.toList());
        return Pair.of(result, r);
    }

    @ToString @Getter
    public static class Environment {
        private static final List<String> skipCommands = Arrays.asList("begin", "/");
        public static TypeReference<Map<String, Environment>> type = new TypeReference<>() {};

        @Nullable @JsonProperty("package") public String packageName;
        @Nullable public String snippet;
        public String name;

        @JsonIgnore public String abbr;
        @JsonIgnore public List<String> vars = Collections.emptyList();

        public static List<Environment> of(Path envFile) throws Exception {
            List<Environment> envs = Lists.newArrayList();

            OBJECT_MAPPER.readValue(Files.readString(envFile), type).forEach((abbr, e) -> {
                if (skipCommands.contains(e.name))
                    return;

                if (StringUtils.isEmpty(e.name))
                    return;

                e.init(abbr);
                envs.add(e);
            });

            return envs;
        }

        public void init(String abbr) {
            this.abbr = StringEscapeUtils.escapeXml11(abbr);

            Pair<String, List<String>> result = toLiveTemplate(snippet);
            vars = result.getRight();
            snippet = String.format("\\begin{%s}%s&#10;  $EXPR$&#10;\\end{%s}&#10;&#10;$END$&#10;", name, StringUtils.isNoneEmpty(result.getLeft()) ? result.getLeft() : "", name);
        }
    }

    @Getter @ToString
    public static class Command {
        private static final List<String> skipCommands = Arrays.asList("llbracket",
            "onlyInPDF<PDF text>",
            "onlyInPS<PS text>",
            "extrarowsep =_%<dimen%>",
            "extrarowsep =_%<dimen%>^%<dimen%>",
            "tabulinesep = %<dimen%>",
            "company{}"
        );
        public static TypeReference<Map<String, Command>> mapType = new TypeReference<>() {};
        public static Map<String, Tex> texes = Maps.newHashMap();

        @JsonIgnore public String abbr;
        @Nullable public String command;
        @Nullable public String detail;
        @Nullable public String label;
        @Nullable public String snippet;
        @Nullable public String packageName;

        @JsonIgnore
        public List<String> vars = Collections.emptyList();

        static {
            for (Tex tex : Tex.load().values()) {
                if (tex.latex != null)
                    texes.put(tex.latex.substring(1), tex);

                if (tex.ulatex != null)
                    texes.put(tex.ulatex.substring(1), tex);
            }
        }

        public static List<Command> of(Path cmdFile) throws Exception {
            List<Command> cmds = Lists.newArrayList();
            OBJECT_MAPPER.readValue(Files.readString(cmdFile), mapType).forEach((abbr, c) -> {
                if (skipCommands.contains(c.command))
                    return;

                if (StringUtils.contains(c.command, "extrarowsep"))
                    return;

                c.init(abbr);
                cmds.add(c);
            });

            return cmds;
        }

        private void init(String abbr) {
            try {
                this.abbr = StringEscapeUtils.escapeXml11(abbr);

                Tex tex = texes.get(command);

                Pair<String, List<String>> templ = toLiveTemplate(snippet);

                this.vars = templ.getRight();
                if (tex != null && vars.size() == 0) {
                    label = tex.chars;
                    snippet = "\\" + command +" $END$";
                    return;
                }

                String result = StringUtils.replace(templ.getLeft(), "\n", NEWLINE);
                result = StringUtils.replace(result, "\t", "  ");

                if (StringUtils.isEmpty(templ.getLeft()) || vars.size() == 0) {
                    snippet = "\\" + command + " $END$";
                } else {
                    snippet = "\\" + result + (StringUtils.contains(snippet, "\\end") ? NEWLINE + "$END$" : " $END$");
                }

                if (StringUtils.isEmpty(label) && StringUtils.isNotEmpty(detail)) {
                    label = detail;
                    return;
                }

                label = command;
            } finally {
                label = StringEscapeUtils.escapeXml11(label);
                if (StringUtils.equals(abbr, "latexdisplaymath")) {
                    this.abbr = "[";
                }

                if (StringUtils.equals(abbr, "latexinlinemath")) {
                    this.abbr = "(";
                }

            }
        }
    }

    public static void main(String[] args) throws Exception {
        String resourceDir = Objects.requireNonNull(args[0]);

        Configuration configuration = Util.freeMarkerConfiguration();
        Template template = configuration.getTemplate("live-template.xml.ftl");

        Map<String, Object> root = Maps.newHashMap();
        root.put("envs", Environment.of(Path.of(resourceDir, "environments.json")));
        root.put("cmds", Command.of(Path.of(resourceDir, "commands.json")));
        root.put("name", "Latex");
        template.process(root, new FileWriter("Latex.xml"));

        DirectoryStream<Path> packages = Files.newDirectoryStream(Path.of(resourceDir, "packages"));
        Map<String, List<Path>> pkgs = Maps.newTreeMap();
        packages.forEach(latexPkg -> {
            Path fileName = latexPkg.getFileName();
            String pkgname = StringUtils.remove(fileName.toString(), "_cmd.json");
            pkgname = StringUtils.remove(pkgname, "_env.json");

            List<Path> paths = MapUtils.getObject(pkgs, pkgname, Lists.newArrayList());
            paths.add(latexPkg);
            pkgs.put(pkgname, paths);
        });

        for (Map.Entry<String, List<Path>>entry : pkgs.entrySet()) {
            String pkgname = entry.getKey();

            if (Objects.equals(pkgname, "yathesis"))
                continue;

            Map<String, Object> model = Maps.newHashMap();
            model.put("envs", Collections.emptyList());
            model.put("cmds", Collections.emptyList());
            model.put("name", "Latex (" + pkgname + ")");
            for (Path p : entry.getValue()) {
                if (p.toString().endsWith("_env.json")) {
                    model.put("envs", Environment.of(p));
                }
                if (p.toString().endsWith("_cmd.json")) {
                    model.put("cmds", Command.of(p));
                }
            }
            template.process(model, new FileWriter(String.format("Latex(%s).xml", pkgname)));
        }
    }
}
