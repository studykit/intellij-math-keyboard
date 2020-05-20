package net.mlcoder.unimath;

import lombok.Getter;

import javax.annotation.Nullable;

public enum CombiningClass {
    NOT_REORDERED(0),
    OVERLAY(1),
    HAN_READING(6),
    NUKTA(7),
    KANA_VOICING(8),
    VIRAMA(9),
    CCC_10(10),
    ATTACHED_BELOW_LEFT(200),
    ATTACHED_BELOW(202),
    ATTACHED_BOTTOM_RIGHT(204),
    ATTACHED_LEFT(208),
    ATTACHED_RIGHT(210),
    ATTACHED_TOP_LEFT(212),
    ATTACHED_ABOVE(214),
    ATTACHED_ABOVE_RIGHT(216),
    BELOW_LEFT(218),
    BELOW(220),
    BELOW_RIGHT(222),
    LEFT(224),
    RIGHT(226),
    ABOVE_LEFT(228),
    ABOVE(230),
    ABOVE_RIGHT(232),
    DOUBLE_BELOW(233),
    DOUBLE_ABOVE(234),
    IOTA_SUBSCRIPT(240),
    ;

    @Getter
    public final int value;

    CombiningClass(int value) {
        this.value = value;
    }

    @Nullable
    public static CombiningClass of(int value) {
        if (CCC_10.value <= value && value < ATTACHED_BELOW_LEFT.value) {
            return CCC_10;
        }

        for (CombiningClass cc : CombiningClass.values()) {
            if (cc.value == value)
                return cc;
        }

        return null;
    }
}
