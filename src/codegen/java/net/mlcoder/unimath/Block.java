package net.mlcoder.unimath;

import org.apache.commons.lang3.Range;

public class Block {
    public final Range<Integer> range;
    public final String desc;

    public Block(Range<Integer> range, String desc) {
        this.range = range;
        this.desc = desc;
    }

    public boolean has(int codePoint) {
        return range.contains(codePoint);
    }

    public static Block of(String line) {
        String[] columns = line.split(";", 2);
        String[] fromTo = columns[0].split("\\.\\.", 2);
        int from = Integer.parseInt(fromTo[0], 16);
        int to = Integer.parseInt(fromTo[1], 16);

        return new Block(Range.between(from, to), columns[1]);
    }
}
