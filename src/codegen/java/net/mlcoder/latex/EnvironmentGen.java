package net.mlcoder.latex;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import net.mlcoder.codegen.Util;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;

public class EnvironmentGen {
    public static List<LatexEnvironment> latexEnvironments() throws Exception{
        List<String> lines = Util.readlLines("latex/environments.txt");
        List<LatexEnvironment> envs = Lists.newArrayList();
        for (String l : lines) {
            if (StringUtils.isEmpty(l))
                continue;
            envs.add(new LatexEnvironment(l, String.format("\\begin{%s}&#10;  $EXPR$&#10;\\end{%s}&#10;&#10;$SELECTION$&#10;", l, l)));
        }
        return envs;
    }

    public static void main(String[] args) throws Exception {

        try {
            Configuration configuration = Util.freeMarkerConfiguration();
            Template template = configuration.getTemplate("live-template.xml.ftl");

            HashMap<String, Object> root = Maps.newHashMap();
            root.put("envs", latexEnvironments());

            template.process(root, new OutputStreamWriter(System.out));

        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }

    public static class LatexEnvironment {
        public String command;
        public String snippet;

        public LatexEnvironment(String command, String snippet) {
            this.command = command;
            this.snippet = snippet;
        }

        public String getCommand() {
            return command;
        }

        public String getSnippet() {
            return snippet;
        }
    }
}
