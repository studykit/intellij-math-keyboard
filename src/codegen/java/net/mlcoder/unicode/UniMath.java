package net.mlcoder.unicode;

import com.google.common.collect.Maps;
import com.google.common.io.Resources;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public class UniMath {
    public static final Map<Integer, String> codePointToLatex = Maps.newHashMap();

    public static void init() throws IOException {
        List<String> lines = Resources.readLines(Resources.getResource("unicode-math.tsv"), Charset.defaultCharset());
        for (String l : lines) {
            String[] columns = l.split("\t");
            int codePoint = Integer.parseInt(columns[0], 16);
            String latex = columns[1];
            codePointToLatex.put(codePoint, latex);
        }
    }
}