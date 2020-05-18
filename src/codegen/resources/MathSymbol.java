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

    public final String desc;
    public final int codePoint;
    public final String chars;
    public final String generalCategory;
    public final int combiningClass;

    @Nullable
    public final String latex;

    MathSymbol(String desc, int codePoint, String chars, String generalCategory, int combiningClass) {
        this(desc, codePoint, chars, generalCategory, combiningClass, null);
    }

    MathSymbol(String desc, int codePoint, String chars, String generalCategory, int combiningClass, String latex) {
        this.desc = desc;
        this.codePoint = codePoint;
        this.generalCategory = generalCategory;
        this.combiningClass = combiningClass;
        this.chars = chars;
        this.latex = latex;
    }

    @Override
    public String toString() {
        return "MathSymbol{" +
            "desc='" + desc + '\'' +
            ", codePoint=" + codePoint +
            ", chars='" + chars + '\'' +
            ", generalCategory='" + generalCategory + '\'' +
            ", combiningClass=" + combiningClass +
            ", latex='" + latex + '\'' +
            '}';
    }

    static {
[# th:each="item : ${items}"]
        symbols.add(new MathSymbol("[(${item.desc})]", [(${item.codePoint})], "[(${item.chars})]", "[(${item.generalCategory.abbr})]", [(${item.combiningClass.value})][# th:if="${item.latex} != null"], "\[(${item.latex})]"[/]));
[/]
        for (MathSymbol i : symbols) {
            titleSymbols.put(i.desc, i);
            if (i.latex != null)
                latexSymbols.put(i.latex, i);
        }
    }
}