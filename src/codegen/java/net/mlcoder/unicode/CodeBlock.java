package net.mlcoder.unicode;

import org.apache.commons.lang3.Range;

import javax.annotation.Nullable;

public class CodeBlock {
    public final Range<Integer> range;

    @Nullable
    public final Character.UnicodeBlock block;

    public CodeBlock(@Nullable Character.UnicodeBlock block, int fromInclusive, int toInclusive) {
        this.block = block;
        this.range = Range.between(fromInclusive, toInclusive);
    }

    public boolean has(int codePoint) {
        return range.contains(codePoint);
    }

    public static CodeBlock of(Character.UnicodeBlock block, String line) {
        String[] columns = line.split(";", 2);
        String[] fromTo = columns[0].split("\\.\\.", 2);
        int from = Integer.parseInt(fromTo[0], 16);
        int to = Integer.parseInt(fromTo[1], 16);

        return new CodeBlock(block, from, to);
    }

    public static CodeBlock of(String line) {
        return CodeBlock.of(null, line);
    }
}
