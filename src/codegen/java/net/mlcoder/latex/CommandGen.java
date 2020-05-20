package net.mlcoder.latex;

import com.fasterxml.jackson.core.type.TypeReference;
import net.mlcoder.codegen.Util;

import java.lang.reflect.Type;
import java.util.Map;

public class CommandGen {
    private static TypeReference<Map<String, Entry >> typeReference = new TypeReference<>() {
        @Override
        public Type getType() {
            return Entry.class;
        }
    };

    private static class Entry {
        public String command;
        public String snippet;
        public String detail;
        public String label;

        @Override
        public String toString() {
            return "Entry{" +
                "command='" + command + '\'' +
                ", snippet='" + snippet + '\'' +
                ", detail='" + detail + '\'' +
                ", label='" + label + '\'' +
                '}';
        }
    }

    public static void main(String[] args) throws Exception {
        try {
            System.out.println(Util.readModel("latex/commands.json", typeReference));
        } catch (Exception e) {
            System.out.println(e);

        }
    }
}
