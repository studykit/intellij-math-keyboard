package net.mlcoder.codegen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import freemarker.template.Template;
import net.mlcoder.unimath.Block;
import net.mlcoder.unimath.Symbol;

import java.io.FileWriter;
import java.util.*;


public class JavaSymbolFileGenerator {
    private final Map<Integer, Symbol> symbols;
    private final Template template;


    public JavaSymbolFileGenerator() throws Exception {
        this.symbols = Symbol.load();
        this.template = Util.freeMarkerConfiguration().getTemplate("symbol-template.java.ftl");
    }

    private void createUnicodeSymbolJavaFile(String className, Block... blocks) throws Exception {
        Objects.requireNonNull(blocks);

        List<Symbol> results = Lists.newArrayList();
        List<Block> codeBlocks = Arrays.asList(blocks);

        for (Symbol symbol : symbols.values()) {
            codeBlocks.stream()
                .filter(block -> block.has(symbol.unicode.codePoint))
                .findAny()
                .ifPresent(block -> results.add(symbol));
        }
        HashMap<String, Object> root = Maps.newHashMap();
        root.put("symbols", results);
        root.put("className", className);
        template.process(root, new FileWriter(className + ".java"));

    }


    public static void main(String[] args) throws Exception {
        JavaSymbolFileGenerator javaSymbolFileGenerator = new JavaSymbolFileGenerator();

        javaSymbolFileGenerator.createUnicodeSymbolJavaFile("MathLetter"
            , Block.of("0370..03FF; Greek and Coptic")
            , Block.of("1F00..1FFF; Greek Extended")
            , Block.of("2070..209F; Superscripts and Subscripts")
            , Block.of("1D400..1D7FF; Mathematical Alphanumeric Symbols")
        );


        javaSymbolFileGenerator.createUnicodeSymbolJavaFile("MathOperator"
            , Block.of("2200..22FF; Mathematical Operators")
            , Block.of("27C0..27EF; Miscellaneous Mathematical Symbols-A")
            , Block.of("2980..29FF; Miscellaneous Mathematical Symbols-B")
            , Block.of("2A00..2AFF; Supplemental Mathematical Operators")
        );

        javaSymbolFileGenerator.createUnicodeSymbolJavaFile("Arrow"
            , Block.of("2190..21FF; Arrows")
            , Block.of("27F0..27FF; Supplemental Arrows-A")
            , Block.of("2900..297F; Supplemental Arrows-B")
            , Block.of("2B00..2BFF; Miscellaneous Symbols and Arrows")
            , Block.of("1F800..1F8FF; Supplemental Arrows-C")
        );

        javaSymbolFileGenerator.createUnicodeSymbolJavaFile("CombininingMark"
            , Block.of("0300..036F; Combining Diacritical Marks")
            , Block.of("1AB0..1AFF; Combining Diacritical Marks Extended")
            , Block.of("1DC0..1DFF; Combining Diacritical Marks Supplement")
            , Block.of("20D0..20FF; Combining Diacritical Marks for Symbols")
        );

        javaSymbolFileGenerator.createUnicodeSymbolJavaFile("Shapes"
            , Block.of("2500..257F; Box Drawing")
            , Block.of("2580..259F; Block Elements")
            , Block.of("25A0..25FF; Geometric Shapes")
            , Block.of("2700..27BF; Dingbats")
            , Block.of("1F650..1F67F; Ornamental Dingbats")
            , Block.of("1F700..1F77F; Alchemical Symbols")
            , Block.of("1F780..1F7FF; Geometric Shapes Extended")
        );

        javaSymbolFileGenerator.createUnicodeSymbolJavaFile("Pictographs"
            , Block.of("20A0..20CF; Currency Symbols")
            , Block.of("1F300..1F5FF; Miscellaneous Symbols and Pictographs")
            , Block.of("1F600..1F64F; Emoticons")
            , Block.of("1F680..1F6FF; Transport and Map Symbols")
            , Block.of("1F900..1F9FF; Supplemental Symbols and Pictographs")
            , Block.of("1FA70..1FAFF; Symbols and Pictographs Extended-A")
        );
    }
}
