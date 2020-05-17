package net.mlcoder.unicode.category;

import com.google.common.collect.Maps;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MathSymbol {
    public static final Map<String, MathSymbol> titleSymbols = Maps.newHashMap();
    public static final Map<String, MathSymbol> latexSymbols = Maps.newHashMap();
    public static final List<MathSymbol> symbols = new ArrayList<>();

    public final String title;
    public final int codePoint;
    public final String chars;
    public final String generalCategory;
    public final int combiningClass;

    @Nullable
    public final String latex;

    MathSymbol(String title, int codePoint, String chars, String generalCategory, int combiningClass) {
        this(title, codePoint, chars, generalCategory, combiningClass, null);
    }

    MathSymbol(String title, int codePoint, String chars, String generalCategory, int combiningClass, String latex) {
        this.title = title;
        this.codePoint = codePoint;
        this.generalCategory = generalCategory;
        this.combiningClass = combiningClass;
        this.chars = chars;
        this.latex = latex;
    }

    @Override
    public String toString() {
        return "MathSymbol{" +
            "title='" + title + '\'' +
            ", codePoint=" + codePoint +
            ", chars='" + chars + '\'' +
            ", generalCategory='" + generalCategory + '\'' +
            ", combiningClass=" + combiningClass +
            ", latex='" + latex + '\'' +
            '}';
    }

    static {
[# th:each="item : ${items}"]
        symbols.add(new MathSymbol("[(${item.title})]", [(${item.codePoint})], "[(${item.chars})]", "[(${item.generalCategory.abbr})]", [(${item.combiningClass.value})][# th:if="${item.latex} != null"], "\[(${item.latex})]"[/]));
[/]
        for (MathSymbol i : symbols) {
            titleSymbols.put(i.title, i);
            if (i.latex != null)
                latexSymbols.put(i.latex, i);
        }
    }
}