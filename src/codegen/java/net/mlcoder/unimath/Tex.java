package net.mlcoder.unimath;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.ToString;
import net.mlcoder.codegen.Util;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
public class Tex {
    @Getter
    public final int codePoint;

    @Nullable @Getter
    public String latex;

    @Nullable @Getter
    public String ulatex;

    @Nullable
    public UniMathCategory category;

    private Tex(int codePoint, @Nullable String latex, @Nullable String ulatex, UniMathCategory category) {
        this.latex = latex;
        this.ulatex = ulatex;
        this.codePoint = codePoint;
        this.category = category;
    }

    private static final Predicate<String> skipPattern = Pattern
        .compile("^\\\\Bbb.+|^\\\\mfrak.+|^\\\\mbfscr.+|^\\\\mscr.+|^\\\\mbfit.+|^\\\\mit|^\\\\mbf.+|^\\\\mtt.+|^\\\\msans.+|^\\\\mathtt.+|\\\\mit.+|^\\\\mathfrak.+|^\\\\mathsfbf.+|^\\\\mathbf.+|^\\\\mathbb.+")
        .asPredicate();

    private static final List<String> skipCommands = Arrays.asList("\\#", "\\$", "\\%", "\\&", "\\mathexclam");

    private static final int C_CODE_PONIT = 0;
    private static final int C_CHARS = 1;
    private static final int C_LATEX = 2;
    private static final int C_ULATEX = 3;
    private static final int C_CLS = 4;
    private static final int C_CATEGORY = 5;

    private static Map<Integer, Tex> loadUniMathSymbols() throws Exception {
        //code^chr^LaTeX^unicode-math^cls^category^requirements^comments
        List<String> lines = Util.readlLines("unimath/unimathsymbols.txt");

        Map<Integer, Tex> result = Maps.newLinkedHashMap();
        for (String ln : lines) {

            ln = RegExUtils.replaceAll(ln, "\\^", " ^");
            String[] cols = StringUtils.splitByWholeSeparator(ln, " ", 8);
            if (cols.length != 8)
                continue;

            cols = Arrays.stream(cols)
                .map(s -> RegExUtils.removePattern(s, "\\^")).toArray(String[]::new);


            if (!StringUtils.startsWith(cols[C_LATEX], "\\") && !StringUtils.startsWith(cols[C_ULATEX], "\\"))
                continue;

            String latex = null;
            if (StringUtils.startsWith(cols[C_LATEX], "\\"))
                latex = cols[C_LATEX];

            String ulatex = null;
            if (StringUtils.startsWith(cols[C_ULATEX], "\\"))
                ulatex = cols[C_ULATEX];

            if (StringUtils.equals(latex, ulatex))
                ulatex = null;

            int codePoint = Integer.parseInt(cols[C_CODE_PONIT], 16);
            Tex texSymbol = new Tex(codePoint, latex, ulatex, UniMathCategory.of(cols[C_CATEGORY]));
            result.put(codePoint, texSymbol);
        }

        return result;
    }

    private static final Pattern uniMathTable = Pattern.compile("^\\\\UnicodeMathSymbol\\{\"(\\w+)\\}\\{(\\\\\\w+)\\s*\\}\\{\\\\(\\w+)\\}.*");
    private static Map<Integer, Tex> loadUniMathTable() throws Exception {
        List<String> lines = Util.readlLines("unimath/unicode-math-table.tex");
        Map<Integer, Tex> result = Maps.newLinkedHashMap();

        for (String line : lines) {
            Matcher matcher = uniMathTable.matcher(line);
            if (!matcher.matches())
                continue;

            Tex mathSymbol = new Tex(Integer.parseInt(matcher.group(1), 16),
                null,
                matcher.group(2),
                UniMathCategory.of(matcher.group(3))
            );

            result.put(mathSymbol.codePoint, mathSymbol);
        }

        return result;
    }

    public static Map<Integer, Tex> load() throws Exception {
        Map<Integer, Tex> mathSymbols = loadUniMathSymbols();
        Map<Integer, Tex> mathTable = loadUniMathTable();

        Map<Integer, Tex> result = Maps.newTreeMap();
        for (Tex symbol : mathSymbols.values()) {
            if (!Character.isValidCodePoint(symbol.codePoint))
                continue;

            Tex tableSym = mathTable.get(symbol.codePoint);
            if (tableSym == null) {
                result.put(symbol.codePoint, symbol);
                continue;
            }

            List<String> texes = Arrays.asList(symbol.latex, symbol.ulatex, tableSym.ulatex);
            Optional<String> any = texes
                .stream()
                .filter(Objects::nonNull)
                .filter(skipPattern)
                .findAny();

            if (any.isPresent())
                continue;

            any = texes
                .stream()
                .filter(Objects::nonNull)
                .filter(skipCommands::contains)
                .findAny();

            if (any.isPresent())
                continue;

            symbol.category = tableSym.category;
            symbol.ulatex = !StringUtils.equals(symbol.latex, tableSym.ulatex) ? tableSym.ulatex : null;

            result.put(symbol.codePoint, symbol);
        }

        Sets.SetView<Integer> onlyMathTable = Sets.difference(mathTable.keySet(), mathSymbols.keySet());
        for (Integer codePoint : onlyMathTable) {
            result.put(codePoint, mathTable.get(codePoint));
        }

        return result;
    }

    @ToString
    public enum UniMathCategory {
        MATH_OPEN("mathopen", "opening"),
        MATH_CLOSE("mathclose", "closing"),
        MATH_FENCE("mathfence", "punctuation"),
        MATH_OVER("mathover", "over"),
        MATH_UNDER("mathunder", "under"),
        MATH_ACCENT("mathaccent", "accent"),
        MAHT_PUNCT("mathpunct", "puncation"),
        MATH_BOTMACCENT("mathbotmaccent", "bottom accents"),
        MATH_BIGOP("mathop", "big operators"),
        MATH_BIN("mathbin", "binary relation"),
        MATH_ORD("mathord", "ordinary"),
        MATH_REL("mathrel", "relation"),
        MATH_ALPHA("mathalpha", "alphabetical"),
        ;

        private static final Map<String, UniMathCategory> tables;

        static {
            tables = Maps.newLinkedHashMap();
            for (UniMathCategory category : values()) {
                tables.put(category.categoryName, category);
            }
        }


        @Getter public final String categoryName;
        @Getter public final String desc;

        UniMathCategory(String categoryName, String desc) {
            this.categoryName = categoryName;
            this.desc = desc;
        }

        public static UniMathCategory of(String clzName) {
            return tables.get(clzName);
        }
    }

    public static void main(String[] args) throws Exception {
        Map<Integer, Tex> symbols = load();
        symbols.values().forEach(System.out::println);
    }

}
