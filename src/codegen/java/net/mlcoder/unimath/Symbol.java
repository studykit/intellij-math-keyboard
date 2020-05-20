package net.mlcoder.unimath;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Symbol {
    public final Unicode unicode;
    public final Tex tex;

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
