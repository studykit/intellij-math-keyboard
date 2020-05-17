package net.mlcoder.unicode;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class UnicodeData {
    public static int CODE_POINT = 0;
    public static int NAME = 1;
    public static int GENERAL_CATEGORY = 2;
    public static int CANONICAL_COMBINING_CLASS = 3;

    public final int codePoint;
    public final String hexCodePoint;
    public final String chars;
    public final String title;
    public final GeneralCategory generalCategory;
    public final CombiningClass combiningClass;

    @Nullable
    public final String latex;

    public static UnicodeData of(@Nonnull String line) {
        String[] columns = line.split(";");

        int combingClass = Integer.parseInt(columns[CANONICAL_COMBINING_CLASS]);

        return new UnicodeData(
            columns[CODE_POINT],
            columns[NAME],
            GeneralCategory.of(columns[GENERAL_CATEGORY]),
            CombiningClass.of(combingClass)
        );
    }

    public UnicodeData(String hexCodePoint,
                       String title,
                       GeneralCategory generalCategory,
                       CombiningClass combiningClass) {
        this.codePoint = Integer.parseInt(hexCodePoint, 16);
        this.hexCodePoint = hexCodePoint;
        this.chars = Character.toString(codePoint);
        this.title = shorten(title);
        this.generalCategory = generalCategory;
        this.combiningClass = combiningClass;
        this.latex = UniMath.codePointToLatex.get(codePoint);
    }

    @Override
    public String toString() {
        return "UnicodeData{" +
            "chars='" + chars + '\'' +
            ", codePoint=" + Integer.toHexString(codePoint) +
            ", name='" + title + '\'' +
            ", generalCategory=" + generalCategory +
            ", combiningClass=" + combiningClass +
            '}';
    }

    static String[] replacements = {
        "small capital ",
        "capital ",
    };

    public static String shorten(String title) {
        String t = StringUtils.lowerCase(title);

        t = t.replace("letter ", "");
        t = t.replace("greek ", "");
        t = t.replace("digit ", "");

        if (t.contains("superscript ")) {
            t = "^" + t.replace("superscript ", "");
        }
        if (t.contains("subscript ")) {
            t = "_" + t.replace("subscript ", "");
        }

        for (String r : replacements) {
            if (t.contains(r)) {
                int i = t.lastIndexOf(r);
                String prefix = t.substring(0, i);
                return prefix + StringUtils.capitalize(t.substring(i).replace(r, ""));
            }
        }

        return t.replace("small ", "");
    }
}
