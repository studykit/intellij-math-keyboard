package net.mlcoder.unicode.category;

import javax.annotation.Nullable;


public class [(${className})] implements UniCode {
    public static final List<UniCode> symbols = new ArrayList<>();

    public final String desc;
    public final int codePoint;
    public final String chars;
    public final String generalCategory;
    public final int combiningClass;

    @Nullable
    public final String latex;

    @Override
    public String desc() {
        return desc;
    }

    @Override
    public int codePoint() {
        return codePoint;
    }

    @Override
    public String chars() {
        return chars;
    }

    @Override
    public String generalCategory() {
        return generalCategory;
    }

    @Override
    public int combiningClass() {
        return combiningClass;
    }

    @Override @Nullable
    public String latex() {
        return latex;
    }

    [(${className})](String desc, int codePoint, String chars, String generalCategory, int combiningClass) {
        this(desc, codePoint, chars, generalCategory, combiningClass, null);
    }

    [(${className})](String desc, int codePoint, String chars, String generalCategory, int combiningClass, String latex) {
        this.desc = desc;
        this.codePoint = codePoint;
        this.generalCategory = generalCategory;
        this.combiningClass = combiningClass;
        this.chars = chars;
        this.latex = latex;
    }

    @Override
    public String toString() {
        return "[(${className})]{" +
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
        symbols.add(new [(${className})]("[(${item.desc})]", 0x[(${@java.lang.Integer@toHexString(item.codePoint)})], "[(${item.chars})]", "[(${item.generalCategory.abbr})]", [(${item.combiningClass.value})][# th:if="${item.latex} != null"], "\[(${item.latex})]"[/]));
[/]    }
}