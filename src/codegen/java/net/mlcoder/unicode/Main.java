package net.mlcoder.unicode;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.io.Resources;
import org.apache.commons.lang3.Range;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.io.OutputStreamWriter;
import java.lang.Character.UnicodeBlock;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.lang.Character.UnicodeBlock.*;


public class Main {
    private List<CodeBlock> mathSymbol = Arrays.asList(
        // Letters
        CodeBlock.of(SUPERSCRIPTS_AND_SUBSCRIPTS, "2070..209F; Superscripts and Subscripts"),
        CodeBlock.of(UnicodeBlock.GREEK, "0370..03FF; Greek and Coptic"),
        CodeBlock.of( "1F00..1FFF; Greek Extended"),
        CodeBlock.of(MATHEMATICAL_ALPHANUMERIC_SYMBOLS, "1D400..1D7FF; Mathematical Alphanumeric Symbols"),

        // Operators
        CodeBlock.of(MATHEMATICAL_OPERATORS, "2200..22FF; Mathematical Operators"),
        CodeBlock.of(MISCELLANEOUS_MATHEMATICAL_SYMBOLS_A, "27C0..27EF; Miscellaneous Mathematical Symbols-A"),
        CodeBlock.of(MISCELLANEOUS_MATHEMATICAL_SYMBOLS_B, "2980..29FF; Miscellaneous Mathematical Symbols-B"),
        CodeBlock.of(SUPPLEMENTAL_MATHEMATICAL_OPERATORS, "2A00..2AFF; Supplemental Mathematical Operators")
    );

    private List<CodeBlock> combiningMark = Arrays.asList(
        CodeBlock.of(COMBINING_DIACRITICAL_MARKS, "0300..036F; Combining Diacritical Marks"),
        CodeBlock.of(COMBINING_DIACRITICAL_MARKS_EXTENDED, "1AB0..1AFF; Combining Diacritical Marks Extended"),
        CodeBlock.of(COMBINING_DIACRITICAL_MARKS_SUPPLEMENT, "1DC0..1DFF; Combining Diacritical Marks Supplement"),
        CodeBlock.of(COMBINING_MARKS_FOR_SYMBOLS, "20D0..20FF; Combining Diacritical Marks for Symbols")
    );

    private List<CodeBlock> emoticons = Arrays.asList(
        CodeBlock.of(GEOMETRIC_SHAPES, "25A0..25FF; Geometric Shapes"),
        CodeBlock.of(DINGBATS, "2700..27BF; Dingbats"),
        CodeBlock.of(EMOTICONS, "1F600..1F64F; Emoticons"),
        CodeBlock.of("1F650..1F67F; Ornamental Dingbats"),
        CodeBlock.of(TRANSPORT_AND_MAP_SYMBOLS, "1F680..1F6FF; Transport and Map Symbols"),
        CodeBlock.of(ALCHEMICAL_SYMBOLS, "1F700..1F77F; Alchemical Symbols"),
        CodeBlock.of("1F780..1F7FF; Geometric Shapes Extended"),
        CodeBlock.of("1F900..1F9FF; Supplemental Symbols and Pictographs"),
        CodeBlock.of( "1FA70..1FAFF; Symbols and Pictographs Extended-A"),
        CodeBlock.of(UnicodeBlock.MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS, "1F300..1F5FF; Miscellaneous Symbols and Pictographs"),
        CodeBlock.of(UnicodeBlock.CURRENCY_SYMBOLS, "20A0..20CF; Currency Symbols"),
        CodeBlock.of(UnicodeBlock.BOX_DRAWING, "2500..257F; Box Drawing"),
        CodeBlock.of(UnicodeBlock.BLOCK_ELEMENTS, "2580..259F; Block Elements")
    );

    private List<CodeBlock> arrows = Arrays.asList(
        CodeBlock.of("2190..21FF; Arrows"),
        CodeBlock.of("27F0..27FF; Supplemental Arrows-A"),
        CodeBlock.of("2900..297F; Supplemental Arrows-B"),
        CodeBlock.of("1F800..1F8FF; Supplemental Arrows-C")
//        "2B00..2BFF; Miscellaneous Symbols and Arrows",
    );

    public Range<Integer> makeRange(String rangeColumn) {
        String[] fromTo = rangeColumn.split("\\.\\.", 2);
        return Range.between(Integer.parseInt(fromTo[0], 16), Integer.parseInt(fromTo[1], 16));
    }

    private Map<Range<Integer>, String> makeBlockRange(String[] blocks) throws Exception {
        Map<Range<Integer>, String> blockMap = Maps.newLinkedHashMap();

        for (String line : blocks) {
            String[] columns = line.split(";", 2);
            blockMap.put(makeRange(columns[0]), columns[1].trim());
        }
        return blockMap;
    }

    private TemplateEngine createTemplateEngine() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setSuffix(".java");

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }

    private void run() throws Exception {
        List<String> lines = Resources.readLines(Resources.getResource("UnicodeData.txt"), Charset.defaultCharset());
        List<UnicodeData> maths = Lists.newArrayList();
        for (String l : lines) {
            UnicodeData ud = UnicodeData.of(l);

            mathSymbol.stream()
                .filter(codeBlock -> codeBlock.has(ud.codePoint))
                .findAny()
                .ifPresent(codeBlock -> maths.add(ud));
        }

        ;
        TemplateEngine templateEngine = createTemplateEngine();
        templateEngine.process("MathSymbol",
            new Context(Locale.UK,ImmutableMap.of("items", maths)), new OutputStreamWriter(Files.newOutputStream(Path.of("MathSymbol.java")))
        );
    }

    public static void main(String[] args) throws Exception {
        UniMath.init();
        new Main().run();
    }
}
