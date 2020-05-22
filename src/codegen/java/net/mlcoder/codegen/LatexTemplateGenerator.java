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
import java.util.function.Predicate;
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
    private static final Pattern varNumNamePattern = Pattern.compile("\\$\\{([0-9]+):([a-zA-Z0-9_<>=,%& -]+)}");
    private static final Pattern variablePattern = Pattern.compile(varNumPattern.pattern() + "|" + varNumCurlyPattern.pattern() + "|" + varNumNamePattern.pattern());
    // <original abbr>, < intelij-template>
    private static final Map<String, String> CUSTOM_TEMPLATE = Maps.newHashMap();

    private static final List<Predicate<String>> SKIP_ABBRS_PATTERN = Arrays.asList(
        (s) -> StringUtils.equals("llbracket", s),
        (s) -> StringUtils.equals("onlyInPDF<PDF text>", s),
        (s) -> StringUtils.equals("onlyInPS<PS text>", s),
        (s) -> StringUtils.equals("extrarowsep =_%<dimen%>", s),
        (s) -> StringUtils.equals("extrarowsep =_%<dimen%>^%<dimen%>", s),
        (s) -> StringUtils.equals("tabulinesep = %<dimen%>", s),
        (s) -> StringUtils.equals("company{}", s),
        (s) -> StringUtils.equals("widehat{}", s),
        (s) -> StringUtils.equals("widetilde{}", s),
        (s) -> StringUtils.equals("mathbb{}", s),
        (s) -> StringUtils.equals("mathfrak{}", s),
        (s) -> StringUtils.equals("mathbf{}", s),
        (s) -> StringUtils.equals("begin", s),
        (s) -> StringUtils.equals("textsterling", s),
        (s) -> StringUtils.equals("textsuperscript", s),
        (s) -> StringUtils.contains(s, "extrarowsep"),
        StringUtils::isEmpty
    );

    private static final Map<String, String> CUSTOM_ABBR = Maps.newHashMap();

    static {
        CUSTOM_ABBR.put("latexdisplaymath", "[");
        CUSTOM_ABBR.put("latexinlinemath", "(");
        CUSTOM_ABBR.put("mathscr", "mathnscr{}");
        CUSTOM_ABBR.put("text", "text{}");

//        CUSTOM_TEMPLATE.put("latexdisplaymath", "\\[&#10; {$SELECTION$} &#10;\\] $END$");
//        CUSTOM_TEMPLATE.put("latexinlinemath", "\\( {$SELECTION$} \\) $END$");
//        CUSTOM_TEMPLATE.put("Bbb{}", "\\Bbb{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathbb{}", "\\mathbb{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathbf{}", "\\mathbf{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathcal{}", "\\mathcal{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathds{}", "\\mathds{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathfrak{}", "\\mathfrak{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathit{}", "\\mathit{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathnormal{}", "\\mathnormal{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathrm{}", "\\mathrm{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathscr", "\\mathscr{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathsfbf{}", "\\mathsfbf{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathsf{}", "\\mathsf{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathsterling", "\\mathsterling{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mathtt{}", "\\mathtt{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mbfit{}", "\\mbfit{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mbfscr{}", "\\mbfscr{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mfrak{}", "\\mfrak{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("mtt{}", "\\mtt{$SELECTION$} $END$");
        CUSTOM_TEMPLATE.put("text", "\\text{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textara{}", "\\textara{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textarc{}", "\\textarc{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textarl{}", "\\textarl{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textarm{}", "\\textarm{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textarn{}", "\\textarn{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textart{}", "\\textart{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textbf{}", "\\textbf{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textcu{}", "\\textcu{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("texthi{}", "\\texthi{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textit{}", "\\textit{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textlf{}", "\\textlf{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textlo{}", "\\textlo{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textmd{}", "\\textmd{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textnormal{}", "\\textnormal{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textrm{}", "\\textrm{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textro{}", "\\textro{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textsc{}", "\\textsc{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textsection", "\\textsection{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textsf{}", "\\textsf{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textsl{}", "\\textsl{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textsterling", "\\textsterling{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textst{}", "\\textst{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textsuperscript{}", "\\textsuperscript{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("texttt{}", "\\texttt{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textup{}", "\\textup{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textwil{}", "\\textwil{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("textwol{}", "\\textwol{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("widehat{}", "\\widehat{$SELECTION$} $END$");
//        CUSTOM_TEMPLATE.put("widetilde{}", "\\widetilde{$SELECTION$} $END$");
    }


    private static Pair<String, List<String>> toLiveTemplate(String snippet) {
        if (StringUtils.isEmpty(snippet))
            return Pair.of(snippet, Collections.emptyList());

        Matcher matcher = variablePattern.matcher(snippet);

        String result = snippet;
        result = RegExUtils.replaceAll(result, "\\n", "&#10;");
        result = RegExUtils.replaceAll(result, "\\t", "  ");

        if (!matcher.find()) {
            return Pair.of(result, Collections.emptyList());
        }

        List<String> vars = Lists.newArrayList();

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

        result = StringUtils.replace(result, "\\n", "&#10;");
        result = StringUtils.replace(result, "\\t", "  ");
        result = StringEscapeUtils.escapeXml11(result);

        vars.sort(String::compareTo);
        List<String> r = vars.stream()
            .map(s -> RegExUtils.removePattern(s, "^\\d+:"))
            .collect(Collectors.toList());
        return Pair.of(result, r);
    }

    @ToString @Getter
    public static class Environment {
        public static TypeReference<Map<String, Environment>> type = new TypeReference<>() {};

        @Nullable @JsonProperty("package") public String packageName;
        @Nullable public String snippet;
        public String name;

        @JsonIgnore public String abbreation;
        @JsonIgnore public List<String> vars = Collections.emptyList();

        public static List<Environment> of(Path envFile) throws Exception {
            List<Environment> envs = Lists.newArrayList();

            OBJECT_MAPPER.readValue(Files.readString(envFile), type).forEach((abbr, e) -> {
                if (SKIP_ABBRS_PATTERN.stream().anyMatch(predicate -> predicate.test(abbr)))
                    return;

                String sanitized = RegExUtils.removePattern(abbr, "\\s+");
                e.init(sanitized);
                envs.add(e);
            });

            return envs;
        }

        public void init(String abbr) {
            Pair<String, List<String>> result = toLiveTemplate(snippet);
            vars = result.getRight();
            snippet = String.format("\\begin{%s}%s&#10;  $EXPR$&#10;\\end{%s}&#10;&#10;$END$&#10;", name, StringUtils.isNoneEmpty(result.getLeft()) ? result.getLeft() : "", name);
            abbreation = StringEscapeUtils.escapeXml11(CUSTOM_ABBR.getOrDefault(abbr, abbr));
        }
    }

    @Getter @ToString
    public static class Command {
        public static TypeReference<Map<String, Command>> mapType = new TypeReference<>() {};
        public static Map<String, Tex> texes = Maps.newHashMap();

        @JsonIgnore public String abbreation;
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
                if (SKIP_ABBRS_PATTERN.stream().anyMatch(p -> p.test(abbr)))
                    return;

                String sanitized = RegExUtils.removePattern(abbr, "\\s+");
                c.init(sanitized);
                cmds.add(c);
            });

            return cmds;
        }

        private void init(String abbr) {
            try {

                Tex tex = texes.get(command);

                if (CUSTOM_TEMPLATE.containsKey(abbr)) {
                    snippet = CUSTOM_TEMPLATE.get(abbr);
                    vars = Collections.emptyList();
                    return;
                }

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

            } finally {
                if (StringUtils.isEmpty(label) && StringUtils.isNotEmpty(detail)) {
                    label = detail;
                } else {
                    label = command;
                    label = StringEscapeUtils.escapeXml11(CUSTOM_ABBR.containsKey(abbr) ? CUSTOM_ABBR.get(abbr) : label);
                }

                abbreation = StringEscapeUtils.escapeXml11(CUSTOM_ABBR.getOrDefault(abbr, abbr));
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
