package net.mlcoder.unimath;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.Objects;

@Getter
@RequiredArgsConstructor
public enum GeneralCategory {
    LETTER_UPPERCASE("Lu"),
    LETTER_LOWERCASE("Ll"),
    LETTER_TITLECASE("Lt"),
    LETTER_MODIFIED("Lm"),
    LETTER_OTHER("Lo"),
    LETTER_CASED("LC") {
        @Override
        public boolean isKindOf(String abbr) {
            return LETTER_LOWERCASE.isKindOf(abbr) ||
                LETTER_UPPERCASE.isKindOf(abbr) ||
                LETTER_TITLECASE.isKindOf(abbr) ||
                Objects.equals(this.abbr, abbr);
        }
    },
    LETTER("L"),

    MARK("M"),
    MARK_NONSPACING("Mn"),
    MARK_SPACING("Mc"),
    MARK_ENCLOSING("Me"),
    NUMBER("N"),
    NUMBER_DECIMAL("Nd"),
    NUMBER_LETTER("Nl"),
    NUMBER_OTHER("No"),

    PUNCTUATION("P"),
    PUNCTUATION_CONNECTOR("Pc"),
    PUNCTUATION_DASH("Pd"),
    PUNCTUATION_OPEN("Ps"),
    PUNCTUATION_CLOSE("Pe"),
    PUNCTUATION_INITIAL("Pi"),
    PUNCTUATION_FINAL("Pf"),
    PUNCTUATION_OTHER("Po"),

    SYMBOL("S"),
    SYMBOL_MATH("Sm"),
    SYMBOL_CURRENCY("Sc"),
    SYMBOL_MODIFIER("Sk"),
    SYMBOL_OTHER("So"),

    SEPARATOR("Z"),
    SEPARATOR_SPACE("Zs"),
    SEPARATOR_LINE("Zl"),
    SEPARATOR_PARAGRAPH("Zp"),

    OTHER("C"),
    OTHER_CONTROL("Cc"),
    OTHER_FORMAT("Cf"),
    OTHER_SURROGATE("Cs"),
    OTHER_PRIVATE("Co"),
    OTHER_UNASSIGNED("Cn"),
    ;

    private static final Map<String, GeneralCategory> container = Maps.newHashMap();
    static {
        for (GeneralCategory gc : GeneralCategory.values()) {
            container.put(gc.abbr, gc);
        }
    }

    @Nonnull public static GeneralCategory of(String abbr) {
        return container.get(abbr);
    }
    @Nonnull public final String abbr;

    public boolean isKindOf(String abbr) {
        return this.abbr.startsWith(abbr);
    }

    public boolean isKindOf(GeneralCategory generalCategory) {
        return this.isKindOf(generalCategory.abbr);
    }
}
