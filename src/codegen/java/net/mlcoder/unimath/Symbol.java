package net.mlcoder.unimath;

import com.google.common.collect.Maps;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Map;

public class Symbol {
    @Getter
    public final Unicode unicode;

    @Nullable @Getter
    public final Tex tex;

    private Symbol(Unicode unicode, @Nullable Tex tex) {
        this.unicode = unicode;
        this.tex = tex;
    }

    public static Map<Integer, Symbol> load() throws Exception {
        Map<Integer, Symbol> result = Maps.newTreeMap();

        Map<Integer, Unicode> unicodes = Unicode.loadUnicodeData();
        Map<Integer, Tex> texs = Tex.load();

        for (Unicode code : unicodes.values()) {
            result.put(code.codePoint, new Symbol(code, texs.get(code.codePoint)));
        }
        return result;
    }
}
