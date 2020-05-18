package net.mlcoder.unicode;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Main {
    private final Map<Integer, UnicodeData> unicodeDatas;
    private final TemplateEngine templateEngine;

    public Main() throws Exception {
        UniMath.init();
        unicodeDatas = loadUnicodeData();
        templateEngine = createTemplateEngine();
    }

    private TemplateEngine createTemplateEngine() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setSuffix(".java");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    private Map<Integer, UnicodeData> loadUnicodeData() throws Exception {
        List<String> lines = Resources.readLines(Resources.getResource("UnicodeData.txt"), Charset.defaultCharset());
        Map<Integer, UnicodeData> symbols = Maps.newLinkedHashMap();
        for (String l : lines) {
            UnicodeData ud = UnicodeData.of(l);
            symbols.put(ud.codePoint, ud);
        }

        return symbols;
    }

    private void createUnicodeSymbolJavaFile(String className, CodeBlock... blocks) throws IOException {
        Objects.requireNonNull(blocks);
        List<UnicodeData> results = Lists.newArrayList();
        List<CodeBlock> codeBlocks = Arrays.asList(blocks);

        for (Map.Entry<Integer, UnicodeData> entry : unicodeDatas.entrySet()) {
            UnicodeData ud = entry.getValue();
            codeBlocks.stream()
                .filter(codeBlock -> codeBlock.has(ud.codePoint))
                .findAny()
                .ifPresent(codeBlock -> results.add(ud));
        }

        templateEngine.process("symbol-template",
            new Context(Locale.UK,ImmutableMap.of("className", className, "items", results)), new OutputStreamWriter(Files.newOutputStream(Path.of(className + ".java")))
        );
    }


    public static void main(String[] args) throws Exception {
        Main main = new Main();

        main.createUnicodeSymbolJavaFile("MathLetter"
            , CodeBlock.of("0370..03FF; Greek and Coptic")
            , CodeBlock.of("1F00..1FFF; Greek Extended")
            , CodeBlock.of("2070..209F; Superscripts and Subscripts")
            , CodeBlock.of("1D400..1D7FF; Mathematical Alphanumeric Symbols")
        );

        main.createUnicodeSymbolJavaFile("MathOperator"
            , CodeBlock.of("2200..22FF; Mathematical Operators")
            , CodeBlock.of("27C0..27EF; Miscellaneous Mathematical Symbols-A")
            , CodeBlock.of("2980..29FF; Miscellaneous Mathematical Symbols-B")
            , CodeBlock.of("2A00..2AFF; Supplemental Mathematical Operators")
        );

        main.createUnicodeSymbolJavaFile("Arrow"
            , CodeBlock.of("2190..21FF; Arrows")
            , CodeBlock.of("27F0..27FF; Supplemental Arrows-A")
            , CodeBlock.of("2900..297F; Supplemental Arrows-B")
            , CodeBlock.of("2B00..2BFF; Miscellaneous Symbols and Arrows")
            , CodeBlock.of("1F800..1F8FF; Supplemental Arrows-C")
        );

        main.createUnicodeSymbolJavaFile("CombininingMark"
            , CodeBlock.of("0300..036F; Combining Diacritical Marks")
            , CodeBlock.of("1AB0..1AFF; Combining Diacritical Marks Extended")
            , CodeBlock.of("1DC0..1DFF; Combining Diacritical Marks Supplement")
            , CodeBlock.of("20D0..20FF; Combining Diacritical Marks for Symbols")
        );

        main.createUnicodeSymbolJavaFile("Shapes"
            , CodeBlock.of("2500..257F; Box Drawing")
            , CodeBlock.of("2580..259F; Block Elements")
            , CodeBlock.of("25A0..25FF; Geometric Shapes")
            , CodeBlock.of("2700..27BF; Dingbats")
            , CodeBlock.of("1F650..1F67F; Ornamental Dingbats")
            , CodeBlock.of("1F700..1F77F; Alchemical Symbols")
            , CodeBlock.of("1F780..1F7FF; Geometric Shapes Extended")
        );

        main.createUnicodeSymbolJavaFile("Pictographs"
            , CodeBlock.of("20A0..20CF; Currency Symbols")
            , CodeBlock.of("1F300..1F5FF; Miscellaneous Symbols and Pictographs")
            , CodeBlock.of("1F600..1F64F; Emoticons")
            , CodeBlock.of("1F680..1F6FF; Transport and Map Symbols")
            , CodeBlock.of("1F900..1F9FF; Supplemental Symbols and Pictographs")
            , CodeBlock.of("1FA70..1FAFF; Symbols and Pictographs Extended-A")
        );
    }
}
