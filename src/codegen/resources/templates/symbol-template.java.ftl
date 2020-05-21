package net.mlcoder.unimath.category;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ToString @Getter @Accessors(fluent = true)
public class ${className} implements UniCode {
    public static final List<UniCode> symbols = new ArrayList<>();

    public final String desc;
    public final int codePoint;
    public final String codeStr;
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

    ${className}(int codePoint, String desc, String chars, String generalCategory, int combiningClass) {
        this(codePoint, desc, chars, generalCategory, combiningClass, null, null, null);
    }

    ${className}(int codePoint, String desc, String chars, String generalCategory, int combiningClass,
                 @Nullable String latex, @Nullable String ulatex, @Nullable String mathCategory) {
        this.codePoint = codePoint;
        this.codeStr = String.format("U+%04X", codePoint);
        this.desc = desc;
        this.generalCategory = generalCategory;
        this.combiningClass = combiningClass;
        this.chars = chars;
        this.latex = latex;
        this.ulatex = ulatex;
        this.mathCategory = mathCategory;

        String _latex = latex != null ? " " + latex : "";
        String _ulatex = ulatex != null ? " " + ulatex : "";
        this.tokenized = Arrays.stream((desc + _latex + _ulatex + " " + codeStr).split(" "))
            .filter(s -> s.length() != 0).toArray(String[]::new);
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