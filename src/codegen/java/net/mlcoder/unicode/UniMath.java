package net.mlcoder.unicode;

import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UniMath {
    public static final Map<Integer, String> codePointToLatex = Maps.newHashMap();

    static List<String> skips = Arrays.asList(
        "\\mathbb",
        "\\mathbf",
        "\\mathbfit",
        "\\mathcal",
        "\\mathfrak",
        "\\mathsf",
        "\\mathsfbf",
        "\\mathsfbfit",
        "\\mathtt",
        "\\mbf",
        "\\mbffrak",
        "\\mbfit",
        "\\mbfsans",
        "\\mbfscr",
        "\\mfrak",
        "\\mit",
        "\\msans",
        "\\mscr",
        "\\mtt",
        "\\Bbb"
    );

    public static void init() throws IOException {
        List<String> lines = Resources.readLines(Resources.getResource("unicode-math.tsv"), Charset.defaultCharset());
        for (String l : lines) {
            if (StringUtils.startsWith(l, "#"))
                continue;
            String[] columns = StringUtils.splitByWholeSeparator(l, "\t");

            int codePoint = Integer.parseInt(columns[0], 16);
            String latex = columns[1];

            if (!StringUtils.startsWith(latex, "\\"))
                continue;

            if (skips.stream().anyMatch(s -> StringUtils.startsWith(latex, s)))
                continue;


            codePointToLatex.put(codePoint, latex);
        }
    }

    public static void main(String[] args) throws IOException {

        try {
            init();

            codePointToLatex.forEach((integer, s) -> {
                System.out.println(integer + ":" + s);
            });
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}