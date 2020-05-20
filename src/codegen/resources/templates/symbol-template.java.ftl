package net.mlcoder.unimath.category;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ${className} implements UniCode {
    public static final List<UniCode> symbols = new ArrayList<>();

    public final String desc;
    public final int codePoint;
    public final String chars;
    public final String generalCategory;
    public final int combiningClass;
    public final String[] tokenized;

    @Nullable
    public final String latex;

    @Nullable
    public final String ulatex;

    @Nullable
    public final String mathCategory;

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

    @Override @Nullable
    public String ulatex() {
        return ulatex;
    }

    @Override @Nullable
    public String mathCategory() {
        return mathCategory;
    }

    @Override @Nullable
    public String[] tokenized() {
        return tokenized;
    }

    ${className}(int codePoint, String desc, String chars, String generalCategory, int combiningClass) {
        this(codePoint, desc, chars, generalCategory, combiningClass, null, null, null);
    }

    ${className}(int codePoint, String desc, String chars, String generalCategory, int combiningClass,
                 @Nullable String latex, @Nullable String ulatex, @Nullable String mathCategory) {
        this.codePoint = codePoint;
        this.desc = desc;
        this.generalCategory = generalCategory;
        this.combiningClass = combiningClass;
        this.chars = chars;
        this.latex = latex;
        this.ulatex = ulatex;
        this.mathCategory = mathCategory;
        this.tokenized = Arrays.stream(desc.split(" "))
            .filter(s -> s.length() != 0).toArray(String[]::new);
    }

    @Override
    public String toString() {
        return "[(${className})]{" +
            "codePoint=" + codePoint +
            ", desc='" + desc + '\'' +
            ", chars='" + chars + '\'' +
            ", generalCategory='" + generalCategory + '\'' +
            ", combiningClass=" + combiningClass +
            ", latex='" + latex + '\'' +
            ", ulatex='" + ulatex + '\'' +
        '}';
    }

    static {
    <#list symbols as s>
        <#if s.tex?has_content>
        symbols.add(new ${className}(${s.unicode.hexCodePoint}, "${s.unicode.desc}", "${s.unicode.chars}", "${s.unicode.generalCategory.abbr}", ${s.unicode.combiningClass.value}, <#if s.tex.latex?has_content>"\${s.tex.latex}"<#else>null</#if>, <#if s.tex.ulatex?has_content>"\${s.tex.ulatex}"<#else>null</#if>, <#if s.tex.category?has_content>"${s.tex.category.desc}"<#else>null</#if>));
        <#else>
        symbols.add(new ${className}(${s.unicode.hexCodePoint}, "${s.unicode.desc}", "${s.unicode.chars}", "${s.unicode.generalCategory.abbr}", ${s.unicode.combiningClass.value}));
        </#if>
    </#list>
    }
}