package net.mlcoder.unicode.category;

public interface UniCode {
    String desc();
    int codePoint();
    String chars();
    String generalCategory();
    int combiningClass();
    String latex();
}
