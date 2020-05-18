package net.mlcoder.unicode.category;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class CombininingMark implements UniCode {
    public static final List<UniCode> symbols = new ArrayList<>();

    public final String desc;
    public final int codePoint;
    public final String chars;
    public final String generalCategory;
    public final int combiningClass;

    @Nullable
    public final String latex;

    @Override
    public String desc() {
        return desc;
    }

    @Override
    public int codePoint() {
        return codePoint;
    }

    @Override
    public String chars() {
        return chars;
    }

    @Override
    public String generalCategory() {
        return generalCategory;
    }

    @Override
    public int combiningClass() {
        return combiningClass;
    }

    @Override @Nullable
    public String latex() {
        return latex;
    }

    CombininingMark(String desc, int codePoint, String chars, String generalCategory, int combiningClass) {
        this(desc, codePoint, chars, generalCategory, combiningClass, null);
    }

    CombininingMark(String desc, int codePoint, String chars, String generalCategory, int combiningClass, String latex) {
        this.desc = desc;
        this.codePoint = codePoint;
        this.generalCategory = generalCategory;
        this.combiningClass = combiningClass;
        this.chars = chars;
        this.latex = latex;
    }

    @Override
    public String toString() {
        return "CombininingMark{" +
            "desc='" + desc + '\'' +
            ", codePoint=" + codePoint +
            ", chars='" + chars + '\'' +
            ", generalCategory='" + generalCategory + '\'' +
            ", combiningClass=" + combiningClass +
            ", latex='" + latex + '\'' +
            '}';
    }

    static {

        symbols.add(new CombininingMark("combining grave accent", 0x300, "̀", "Mn", 230, "\\grave"));
        symbols.add(new CombininingMark("combining acute accent", 0x301, "́", "Mn", 230, "\\acute"));
        symbols.add(new CombininingMark("combining circumflex accent", 0x302, "̂", "Mn", 230, "\\widehat"));
        symbols.add(new CombininingMark("combining tilde", 0x303, "̃", "Mn", 230, "\\widetilde"));
        symbols.add(new CombininingMark("combining macron", 0x304, "̄", "Mn", 230, "\\bar"));
        symbols.add(new CombininingMark("combining overline", 0x305, "̅", "Mn", 230, "\\wideoverbar"));
        symbols.add(new CombininingMark("combining breve", 0x306, "̆", "Mn", 230, "\\widebreve"));
        symbols.add(new CombininingMark("combining dot above", 0x307, "̇", "Mn", 230, "\\dot"));
        symbols.add(new CombininingMark("combining diaeresis", 0x308, "̈", "Mn", 230, "\\ddot"));
        symbols.add(new CombininingMark("combining hook above", 0x309, "̉", "Mn", 230, "\\ovhook"));
        symbols.add(new CombininingMark("combining ring above", 0x30a, "̊", "Mn", 230, "\\ocirc"));
        symbols.add(new CombininingMark("combining double acute accent", 0x30b, "̋", "Mn", 230));
        symbols.add(new CombininingMark("combining caron", 0x30c, "̌", "Mn", 230, "\\widecheck"));
        symbols.add(new CombininingMark("combining vertical line above", 0x30d, "̍", "Mn", 230));
        symbols.add(new CombininingMark("combining double vertical line above", 0x30e, "̎", "Mn", 230));
        symbols.add(new CombininingMark("combining double grave accent", 0x30f, "̏", "Mn", 230));
        symbols.add(new CombininingMark("combining candrabindu", 0x310, "̐", "Mn", 230, "\\candra"));
        symbols.add(new CombininingMark("combining inverted breve", 0x311, "̑", "Mn", 230));
        symbols.add(new CombininingMark("combining turned comma above", 0x312, "̒", "Mn", 230, "\\oturnedcomma"));
        symbols.add(new CombininingMark("combining comma above", 0x313, "̓", "Mn", 230));
        symbols.add(new CombininingMark("combining reversed comma above", 0x314, "̔", "Mn", 230));
        symbols.add(new CombininingMark("combining comma above right", 0x315, "̕", "Mn", 232, "\\ocommatopright"));
        symbols.add(new CombininingMark("combining grave accent below", 0x316, "̖", "Mn", 220));
        symbols.add(new CombininingMark("combining acute accent below", 0x317, "̗", "Mn", 220));
        symbols.add(new CombininingMark("combining left tack below", 0x318, "̘", "Mn", 220));
        symbols.add(new CombininingMark("combining right tack below", 0x319, "̙", "Mn", 220));
        symbols.add(new CombininingMark("combining left angle above", 0x31a, "̚", "Mn", 232, "\\droang"));
        symbols.add(new CombininingMark("combining horn", 0x31b, "̛", "Mn", 216));
        symbols.add(new CombininingMark("combining left half ring below", 0x31c, "̜", "Mn", 220));
        symbols.add(new CombininingMark("combining up tack below", 0x31d, "̝", "Mn", 220));
        symbols.add(new CombininingMark("combining down tack below", 0x31e, "̞", "Mn", 220));
        symbols.add(new CombininingMark("combining plus sign below", 0x31f, "̟", "Mn", 220));
        symbols.add(new CombininingMark("combining minus sign below", 0x320, "̠", "Mn", 220));
        symbols.add(new CombininingMark("combining palatalized hook below", 0x321, "̡", "Mn", 202));
        symbols.add(new CombininingMark("combining retroflex hook below", 0x322, "̢", "Mn", 202));
        symbols.add(new CombininingMark("combining dot below", 0x323, "̣", "Mn", 220));
        symbols.add(new CombininingMark("combining diaeresis below", 0x324, "̤", "Mn", 220));
        symbols.add(new CombininingMark("combining ring below", 0x325, "̥", "Mn", 220));
        symbols.add(new CombininingMark("combining comma below", 0x326, "̦", "Mn", 220));
        symbols.add(new CombininingMark("combining cedilla", 0x327, "̧", "Mn", 202));
        symbols.add(new CombininingMark("combining ogonek", 0x328, "̨", "Mn", 202));
        symbols.add(new CombininingMark("combining vertical line below", 0x329, "̩", "Mn", 220));
        symbols.add(new CombininingMark("combining bridge below", 0x32a, "̪", "Mn", 220));
        symbols.add(new CombininingMark("combining inverted double arch below", 0x32b, "̫", "Mn", 220));
        symbols.add(new CombininingMark("combining caron below", 0x32c, "̬", "Mn", 220));
        symbols.add(new CombininingMark("combining circumflex accent below", 0x32d, "̭", "Mn", 220));
        symbols.add(new CombininingMark("combining breve below", 0x32e, "̮", "Mn", 220));
        symbols.add(new CombininingMark("combining inverted breve below", 0x32f, "̯", "Mn", 220));
        symbols.add(new CombininingMark("combining tilde below", 0x330, "̰", "Mn", 220, "\\wideutilde"));
        symbols.add(new CombininingMark("combining macron below", 0x331, "̱", "Mn", 220));
        symbols.add(new CombininingMark("combining low line", 0x332, "̲", "Mn", 220, "\\mathunderbar"));
        symbols.add(new CombininingMark("combining double low line", 0x333, "̳", "Mn", 220));
        symbols.add(new CombininingMark("combining tilde overlay", 0x334, "̴", "Mn", 1));
        symbols.add(new CombininingMark("combining short stroke overlay", 0x335, "̵", "Mn", 1));
        symbols.add(new CombininingMark("combining long stroke overlay", 0x336, "̶", "Mn", 1));
        symbols.add(new CombininingMark("combining short solidus overlay", 0x337, "̷", "Mn", 1));
        symbols.add(new CombininingMark("combining long solidus overlay", 0x338, "̸", "Mn", 1, "\\notaccent"));
        symbols.add(new CombininingMark("combining right half ring below", 0x339, "̹", "Mn", 220));
        symbols.add(new CombininingMark("combining inverted bridge below", 0x33a, "̺", "Mn", 220));
        symbols.add(new CombininingMark("combining square below", 0x33b, "̻", "Mn", 220));
        symbols.add(new CombininingMark("combining seagull below", 0x33c, "̼", "Mn", 220));
        symbols.add(new CombininingMark("combining x above", 0x33d, "̽", "Mn", 230));
        symbols.add(new CombininingMark("combining vertical tilde", 0x33e, "̾", "Mn", 230));
        symbols.add(new CombininingMark("combining double overline", 0x33f, "̿", "Mn", 230));
        symbols.add(new CombininingMark("combining grave tone mark", 0x340, "̀", "Mn", 230));
        symbols.add(new CombininingMark("combining acute tone mark", 0x341, "́", "Mn", 230));
        symbols.add(new CombininingMark("combining perispomeni", 0x342, "͂", "Mn", 230));
        symbols.add(new CombininingMark("combining koronis", 0x343, "̓", "Mn", 230));
        symbols.add(new CombininingMark("combining dialytika tonos", 0x344, "̈́", "Mn", 230));
        symbols.add(new CombininingMark("combining ypogegrammeni", 0x345, "ͅ", "Mn", 240));
        symbols.add(new CombininingMark("combining bridge above", 0x346, "͆", "Mn", 230));
        symbols.add(new CombininingMark("combining equals sign below", 0x347, "͇", "Mn", 220));
        symbols.add(new CombininingMark("combining double vertical line below", 0x348, "͈", "Mn", 220));
        symbols.add(new CombininingMark("combining left angle below", 0x349, "͉", "Mn", 220));
        symbols.add(new CombininingMark("combining not tilde above", 0x34a, "͊", "Mn", 230));
        symbols.add(new CombininingMark("combining homothetic above", 0x34b, "͋", "Mn", 230));
        symbols.add(new CombininingMark("combining almost equal to above", 0x34c, "͌", "Mn", 230));
        symbols.add(new CombininingMark("combining left right arrow below", 0x34d, "͍", "Mn", 220, "\\underleftrightarrow"));
        symbols.add(new CombininingMark("combining upwards arrow below", 0x34e, "͎", "Mn", 220));
        symbols.add(new CombininingMark("combining grapheme joiner", 0x34f, "͏", "Mn", 0));
        symbols.add(new CombininingMark("combining right arrowhead above", 0x350, "͐", "Mn", 230));
        symbols.add(new CombininingMark("combining left half ring above", 0x351, "͑", "Mn", 230));
        symbols.add(new CombininingMark("combining fermata", 0x352, "͒", "Mn", 230));
        symbols.add(new CombininingMark("combining x below", 0x353, "͓", "Mn", 220));
        symbols.add(new CombininingMark("combining left arrowhead below", 0x354, "͔", "Mn", 220));
        symbols.add(new CombininingMark("combining right arrowhead below", 0x355, "͕", "Mn", 220));
        symbols.add(new CombininingMark("combining right arrowhead and up arrowhead below", 0x356, "͖", "Mn", 220));
        symbols.add(new CombininingMark("combining right half ring above", 0x357, "͗", "Mn", 230));
        symbols.add(new CombininingMark("combining dot above right", 0x358, "͘", "Mn", 232));
        symbols.add(new CombininingMark("combining asterisk below", 0x359, "͙", "Mn", 220));
        symbols.add(new CombininingMark("combining double ring below", 0x35a, "͚", "Mn", 220));
        symbols.add(new CombininingMark("combining zigzag above", 0x35b, "͛", "Mn", 230));
        symbols.add(new CombininingMark("combining double breve below", 0x35c, "͜", "Mn", 233));
        symbols.add(new CombininingMark("combining double breve", 0x35d, "͝", "Mn", 234));
        symbols.add(new CombininingMark("combining double macron", 0x35e, "͞", "Mn", 234));
        symbols.add(new CombininingMark("combining double macron below", 0x35f, "͟", "Mn", 233));
        symbols.add(new CombininingMark("combining double tilde", 0x360, "͠", "Mn", 234));
        symbols.add(new CombininingMark("combining double inverted breve", 0x361, "͡", "Mn", 234));
        symbols.add(new CombininingMark("combining double rightwards arrow below", 0x362, "͢", "Mn", 233));
        symbols.add(new CombininingMark("combining latin a", 0x363, "ͣ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin e", 0x364, "ͤ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin i", 0x365, "ͥ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin o", 0x366, "ͦ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin u", 0x367, "ͧ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin c", 0x368, "ͨ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin d", 0x369, "ͩ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin h", 0x36a, "ͪ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin m", 0x36b, "ͫ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin r", 0x36c, "ͬ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin t", 0x36d, "ͭ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin v", 0x36e, "ͮ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin x", 0x36f, "ͯ", "Mn", 230));
        symbols.add(new CombininingMark("combining doubled circumflex accent", 0x1ab0, "᪰", "Mn", 230));
        symbols.add(new CombininingMark("combining diaeresis-ring", 0x1ab1, "᪱", "Mn", 230));
        symbols.add(new CombininingMark("combining infinity", 0x1ab2, "᪲", "Mn", 230));
        symbols.add(new CombininingMark("combining downwards arrow", 0x1ab3, "᪳", "Mn", 230));
        symbols.add(new CombininingMark("combining triple dot", 0x1ab4, "᪴", "Mn", 230));
        symbols.add(new CombininingMark("combining x-x below", 0x1ab5, "᪵", "Mn", 220));
        symbols.add(new CombininingMark("combining wiggly line below", 0x1ab6, "᪶", "Mn", 220));
        symbols.add(new CombininingMark("combining open mark below", 0x1ab7, "᪷", "Mn", 220));
        symbols.add(new CombininingMark("combining double open mark below", 0x1ab8, "᪸", "Mn", 220));
        symbols.add(new CombininingMark("combining light centralization stroke below", 0x1ab9, "᪹", "Mn", 220));
        symbols.add(new CombininingMark("combining strong centralization stroke below", 0x1aba, "᪺", "Mn", 220));
        symbols.add(new CombininingMark("combining parentheses above", 0x1abb, "᪻", "Mn", 230));
        symbols.add(new CombininingMark("combining double parentheses above", 0x1abc, "᪼", "Mn", 230));
        symbols.add(new CombininingMark("combining parentheses below", 0x1abd, "᪽", "Mn", 220));
        symbols.add(new CombininingMark("combining parentheses overlay", 0x1abe, "᪾", "Me", 0));
        symbols.add(new CombininingMark("combining latin w below", 0x1abf, "ᪿ", "Mn", 220));
        symbols.add(new CombininingMark("combining latin turned w below", 0x1ac0, "ᫀ", "Mn", 220));
        symbols.add(new CombininingMark("combining dotted grave accent", 0x1dc0, "᷀", "Mn", 230));
        symbols.add(new CombininingMark("combining dotted acute accent", 0x1dc1, "᷁", "Mn", 230));
        symbols.add(new CombininingMark("combining snake below", 0x1dc2, "᷂", "Mn", 220));
        symbols.add(new CombininingMark("combining suspension mark", 0x1dc3, "᷃", "Mn", 230));
        symbols.add(new CombininingMark("combining macron-acute", 0x1dc4, "᷄", "Mn", 230));
        symbols.add(new CombininingMark("combining grave-macron", 0x1dc5, "᷅", "Mn", 230));
        symbols.add(new CombininingMark("combining macron-grave", 0x1dc6, "᷆", "Mn", 230));
        symbols.add(new CombininingMark("combining acute-macron", 0x1dc7, "᷇", "Mn", 230));
        symbols.add(new CombininingMark("combining grave-acute-grave", 0x1dc8, "᷈", "Mn", 230));
        symbols.add(new CombininingMark("combining acute-grave-acute", 0x1dc9, "᷉", "Mn", 230));
        symbols.add(new CombininingMark("combining latin r below", 0x1dca, "᷊", "Mn", 220));
        symbols.add(new CombininingMark("combining breve-macron", 0x1dcb, "᷋", "Mn", 230));
        symbols.add(new CombininingMark("combining macron-breve", 0x1dcc, "᷌", "Mn", 230));
        symbols.add(new CombininingMark("combining double circumflex above", 0x1dcd, "᷍", "Mn", 234));
        symbols.add(new CombininingMark("combining ogonek above", 0x1dce, "᷎", "Mn", 214));
        symbols.add(new CombininingMark("combining zigzag below", 0x1dcf, "᷏", "Mn", 220));
        symbols.add(new CombininingMark("combining is below", 0x1dd0, "᷐", "Mn", 202));
        symbols.add(new CombininingMark("combining ur above", 0x1dd1, "᷑", "Mn", 230));
        symbols.add(new CombininingMark("combining us above", 0x1dd2, "᷒", "Mn", 230));
        symbols.add(new CombininingMark("combining latin flattened open a above", 0x1dd3, "ᷓ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin ae", 0x1dd4, "ᷔ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin ao", 0x1dd5, "ᷕ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin av", 0x1dd6, "ᷖ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin c cedilla", 0x1dd7, "ᷗ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin insular d", 0x1dd8, "ᷘ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin eth", 0x1dd9, "ᷙ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin g", 0x1dda, "ᷚ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin G", 0x1ddb, "ᷛ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin k", 0x1ddc, "ᷜ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin l", 0x1ddd, "ᷝ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin L", 0x1dde, "ᷞ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin M", 0x1ddf, "ᷟ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin n", 0x1de0, "ᷠ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin N", 0x1de1, "ᷡ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin R", 0x1de2, "ᷢ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin r rotunda", 0x1de3, "ᷣ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin s", 0x1de4, "ᷤ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin long s", 0x1de5, "ᷥ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin z", 0x1de6, "ᷦ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin alpha", 0x1de7, "ᷧ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin b", 0x1de8, "ᷨ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin beta", 0x1de9, "ᷩ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin schwa", 0x1dea, "ᷪ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin f", 0x1deb, "ᷫ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin l with double middle tilde", 0x1dec, "ᷬ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin o with light centralization stroke", 0x1ded, "ᷭ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin p", 0x1dee, "ᷮ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin esh", 0x1def, "ᷯ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin u with light centralization stroke", 0x1df0, "ᷰ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin w", 0x1df1, "ᷱ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin a with diaeresis", 0x1df2, "ᷲ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin o with diaeresis", 0x1df3, "ᷳ", "Mn", 230));
        symbols.add(new CombininingMark("combining latin u with diaeresis", 0x1df4, "ᷴ", "Mn", 230));
        symbols.add(new CombininingMark("combining up tack above", 0x1df5, "᷵", "Mn", 230));
        symbols.add(new CombininingMark("combining kavyka above right", 0x1df6, "᷶", "Mn", 232));
        symbols.add(new CombininingMark("combining kavyka above left", 0x1df7, "᷷", "Mn", 228));
        symbols.add(new CombininingMark("combining dot above left", 0x1df8, "᷸", "Mn", 228));
        symbols.add(new CombininingMark("combining wide inverted bridge below", 0x1df9, "᷹", "Mn", 220));
        symbols.add(new CombininingMark("combining deletion mark", 0x1dfb, "᷻", "Mn", 230));
        symbols.add(new CombininingMark("combining double inverted breve below", 0x1dfc, "᷼", "Mn", 233));
        symbols.add(new CombininingMark("combining almost equal to below", 0x1dfd, "᷽", "Mn", 220));
        symbols.add(new CombininingMark("combining left arrowhead above", 0x1dfe, "᷾", "Mn", 230));
        symbols.add(new CombininingMark("combining right arrowhead and down arrowhead below", 0x1dff, "᷿", "Mn", 220));
        symbols.add(new CombininingMark("combining left harpoon above", 0x20d0, "⃐", "Mn", 230, "\\overleftharpoon"));
        symbols.add(new CombininingMark("combining right harpoon above", 0x20d1, "⃑", "Mn", 230, "\\overrightharpoon"));
        symbols.add(new CombininingMark("combining long vertical line overlay", 0x20d2, "⃒", "Mn", 1, "\\vertoverlay"));
        symbols.add(new CombininingMark("combining short vertical line overlay", 0x20d3, "⃓", "Mn", 1));
        symbols.add(new CombininingMark("combining anticlockwise arrow above", 0x20d4, "⃔", "Mn", 230));
        symbols.add(new CombininingMark("combining clockwise arrow above", 0x20d5, "⃕", "Mn", 230));
        symbols.add(new CombininingMark("combining left arrow above", 0x20d6, "⃖", "Mn", 230, "\\overleftarrow"));
        symbols.add(new CombininingMark("combining right arrow above", 0x20d7, "⃗", "Mn", 230, "\\vec"));
        symbols.add(new CombininingMark("combining ring overlay", 0x20d8, "⃘", "Mn", 1));
        symbols.add(new CombininingMark("combining clockwise ring overlay", 0x20d9, "⃙", "Mn", 1));
        symbols.add(new CombininingMark("combining anticlockwise ring overlay", 0x20da, "⃚", "Mn", 1));
        symbols.add(new CombininingMark("combining three dots above", 0x20db, "⃛", "Mn", 230, "\\dddot"));
        symbols.add(new CombininingMark("combining four dots above", 0x20dc, "⃜", "Mn", 230, "\\ddddot"));
        symbols.add(new CombininingMark("combining enclosing circle", 0x20dd, "⃝", "Me", 0, "\\enclosecircle"));
        symbols.add(new CombininingMark("combining enclosing square", 0x20de, "⃞", "Me", 0, "\\enclosesquare"));
        symbols.add(new CombininingMark("combining enclosing diamond", 0x20df, "⃟", "Me", 0, "\\enclosediamond"));
        symbols.add(new CombininingMark("combining enclosing circle backslash", 0x20e0, "⃠", "Me", 0));
        symbols.add(new CombininingMark("combining left right arrow above", 0x20e1, "⃡", "Mn", 230, "\\overleftrightarrow"));
        symbols.add(new CombininingMark("combining enclosing screen", 0x20e2, "⃢", "Me", 0));
        symbols.add(new CombininingMark("combining enclosing keycap", 0x20e3, "⃣", "Me", 0));
        symbols.add(new CombininingMark("combining enclosing upward pointing triangle", 0x20e4, "⃤", "Me", 0, "\\enclosetriangle"));
        symbols.add(new CombininingMark("combining reverse solidus overlay", 0x20e5, "⃥", "Mn", 1));
        symbols.add(new CombininingMark("combining double vertical stroke overlay", 0x20e6, "⃦", "Mn", 1));
        symbols.add(new CombininingMark("combining annuity symbol", 0x20e7, "⃧", "Mn", 230, "\\annuity"));
        symbols.add(new CombininingMark("combining triple underdot", 0x20e8, "⃨", "Mn", 220, "\\threeunderdot"));
        symbols.add(new CombininingMark("combining wide bridge above", 0x20e9, "⃩", "Mn", 230, "\\widebridgeabove"));
        symbols.add(new CombininingMark("combining leftwards arrow overlay", 0x20ea, "⃪", "Mn", 1));
        symbols.add(new CombininingMark("combining long double solidus overlay", 0x20eb, "⃫", "Mn", 1));
        symbols.add(new CombininingMark("combining rightwards harpoon with barb downwards", 0x20ec, "⃬", "Mn", 220, "\\underrightharpoondown"));
        symbols.add(new CombininingMark("combining leftwards harpoon with barb downwards", 0x20ed, "⃭", "Mn", 220, "\\underleftharpoondown"));
        symbols.add(new CombininingMark("combining left arrow below", 0x20ee, "⃮", "Mn", 220, "\\underleftarrow"));
        symbols.add(new CombininingMark("combining right arrow below", 0x20ef, "⃯", "Mn", 220, "\\underrightarrow"));
        symbols.add(new CombininingMark("combining asterisk above", 0x20f0, "⃰", "Mn", 230, "\\asteraccent"));
    }
}