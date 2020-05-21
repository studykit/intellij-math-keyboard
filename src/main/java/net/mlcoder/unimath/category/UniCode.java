package net.mlcoder.unimath.category;

import javax.annotation.Nullable;

public interface UniCode {
    String desc();
    int codePoint();
    String codeStr();
    String chars();
    String generalCategory();
    int combiningClass();
    @Nullable String latex();
    // Unicde-Math Latex
    @Nullable String ulatex();
    @Nullable String mathCategory();
    String[] tokenized();
}
