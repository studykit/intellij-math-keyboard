package net.mlcoder.unimath;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.ToString;
import net.mlcoder.codegen.Util;
import org.apache.commons.lang3.RegExUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
@Getter
public class Unicode {
    public static int CODE_POINT = 0;
    public static int DESC = 1;
    public static int GENERAL_CATEGORY = 2;
    public static int CANONICAL_COMBINING_CLASS = 3;
    private static final List<Predicate<Unicode>> SKIP_PREDICATE = Arrays.asList(
        (Unicode uc) -> uc.generalCategory == GeneralCategory.OTHER_CONTROL
    );

    public final int codePoint;
    public final String desc;
    public final String hexCodePoint;
    public final String chars;
    public final GeneralCategory generalCategory;
    public final CombiningClass combiningClass;

    private Unicode(String hexCodePoint,
                    String desc,
                    GeneralCategory generalCategory,
                    CombiningClass combiningClass) {
        this.codePoint = Integer.parseInt(hexCodePoint, 16);
        this.hexCodePoint = "0x"+ StringUtils.upperCase(Integer.toHexString(codePoint));
        this.chars = Character.toString(codePoint);
        this.desc = shorten(desc, generalCategory);
        this.generalCategory = generalCategory;
        this.combiningClass = combiningClass;
    }

    public static Map<Integer, Unicode> loadUnicodeData() throws Exception {
        List<String> lines = Util.readlLines("data/UnicodeData.txt");
        Map<Integer, Unicode> result = Maps.newLinkedHashMap();

        for (String line : lines) {
            Unicode data = from(line);
            if (SKIP_PREDICATE.stream().anyMatch(p -> p.test(data)))
                continue;

            result.put(data.codePoint, data);
        }

        return result;
    }

    public static Unicode from(@Nonnull String line) {
        String[] columns = line.split(";");

        int combingClass = Integer.parseInt(columns[CANONICAL_COMBINING_CLASS]);

        return new Unicode(
            columns[CODE_POINT],
            columns[DESC],
            GeneralCategory.of(columns[GENERAL_CATEGORY]),
            CombiningClass.of(combingClass)
        );
    }

    private static final Pattern superscript = Pattern.compile("^\\s*superscript\\s+(\\w|\\(|\\)|=|-|\\+)\\s*$");
    private static final Pattern subscript = Pattern.compile("^\\s*subscript\\s+(\\w|\\(|\\)|=|-|\\+)\\s*$");
    private static final Pattern capital = Pattern.compile("(?:letter|small)?+\\s*\\bcapital\\b\\s+(?:letter)?(.+)", Pattern.CASE_INSENSITIVE);
    private static final String[] numbers = {
        "\\bzero$",
        "\\bone$",
        "\\btwo$",
        "\\bthree$",
        "\\bfour$",
        "\\bfive$",
        "\\bsix$",
        "\\bseven$",
        "\\beight$",
        "\\bnine$",
    };

    private static String shorten(String desc, GeneralCategory category) {
        String t = StringUtils.lowerCase(desc);

        t = RegExUtils.removePattern(t, "\\bletter\\b");
        t = RegExUtils.removePattern(t,"\\bgreek\\b");
        t = RegExUtils.removePattern(t, "\\bsymbol\\b");
        t = RegExUtils.removePattern(t, "\\blatin\\b");
        t = RegExUtils.replacePattern(t, "\\bsmall(?: letter\\b)??\\s+([a-z])\\b", "$1");
        t = RegExUtils.replacePattern(t, "\\bdigit\\s+(\\w+)\\b", "$1");


        Matcher matcher = capital.matcher(t);
        if (matcher.find()) {
            t = matcher.replaceAll(mr -> " " + StringUtils.capitalize(mr.group(1).trim()));
        }

        t = RegExUtils.replacePattern(t, "\\s+-(\\d+)", "-$1");
//        t = RegExUtils.replacePattern(t, "\\blatin ([a-zA-Z])\\b", "$1");
        if (category == GeneralCategory.NUMBER_DECIMAL || category == GeneralCategory.NUMBER_OTHER) {
            for (int i = 0; i < 10; i++ ) {
                t = RegExUtils.replaceAll(t, numbers[i], "" + i);
            }
        }

        t = RegExUtils.replacePattern(t, "\\bplus(?: sign)??$", "+");
        t = RegExUtils.replacePattern(t, "\\bminus(?: sign)??$", "-");
        t = RegExUtils.replacePattern(t, "\\bequals(?: sign)??$", "=");
        t = RegExUtils.replacePattern(t, "\\bleft parenthesis$", "(");
        t = RegExUtils.replacePattern(t, "\\bright parenthesis$", ")");

        if (superscript.asPredicate().test(t)) {
            t = "^" + RegExUtils.replacePattern(t, superscript.pattern(), "$1").trim();
        }

        if (subscript.asPredicate().test(t)) {
            t = "_" + RegExUtils.replacePattern(t, subscript.pattern(), "$1").trim();
        }

        return StringUtils.normalizeSpace(t);
    }
}
