package Utils;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;

import java.util.List;

public class BuildCommon {

    private static String PEAK = "'", DOUBLE_PEAK = "\"";

    @Getter
    public enum specialTag{
        LT_TAG, GT_TAG, COMMERCIAL_TAG
    }

    @Getter
    private enum htmlTags {
        // The names of the html tags should be added here so that the converter doesn't go to convert the related <>
        html, head, body, meta, script, div, span, a, b, p, br, hr, strong, em, i, font, nel,
        style, table, th, tr, td, link, ul, ol, li, label, option, u, h1, h2, h3, h4, h5, h6
    }

    @Getter
    private enum tags {
        amp, gt, lt
    }

    public static String ERROR_CHARACTERS_1 = "\u001A";
    public static String ERROR_CHARACTERS_2 = "\u0080";
    public static String ERROR_CHARACTERS_3 = "\u0092";
    public static String ERROR_CHARACTERS_4 = "\u0093";
    public static String ERROR_CHARACTERS_5 = "\u0094";
    public static String ERROR_CHARACTERS_6 = "\u0095";
    public static String ERROR_CHARACTERS_7 = "\u0096";
    public static String ERROR_CHARACTERS_8 = "\u002A";

    public static String beautifyAlcHtml(String commento) {
        String beautifyHtml = StringUtils.EMPTY;

        if (StringUtils.isNotEmpty(commento)) {
            String unescapeHtml = StringEscapeUtils.unescapeHtml4(commento);
            unescapeHtml = unescapeHtml.replace("\n", StringUtils.EMPTY)
                    .replaceAll("\\<span.*?\\>", StringUtils.EMPTY)
                    .replaceAll("\\</span.*?\\>", StringUtils.EMPTY)
                    .replaceAll("\\<u .*?\\>", "<u>")
                    .replaceAll("\\<!--.*?\\>", StringUtils.EMPTY)
                    .replaceAll("\\<a.*?\\>", StringUtils.EMPTY)
                    .replaceAll("\\</a.*?\\>", StringUtils.EMPTY)
                    .replaceAll("\\<div.*?\\>", "<br>")
                    .replaceAll("\\</div.*?\\>", "<br>")
                    .replaceAll("\\<hr.*?\\>", StringUtils.EMPTY)
                    .replaceAll("\\</hr.*?\\>", StringUtils.EMPTY)
                    .replaceAll("\\<strong.*?\\>", "<b>")
                    .replace("</strong>", "</b>")
                    .replaceAll("\\<em.*?\\>", "<em>")
                    .replace("<em>", "<i>")
                    .replace("</em>", "</i>")
                    .replaceAll("\\<br.*?\\>", "<br>")
                    .replaceAll("\\<i.*?\\>", "<i>")
                    .replaceAll("\\<b .*?\\>", "<b>")
                    .replaceAll("\\<font.*?\\>", StringUtils.EMPTY)
                    .replaceAll("\\</font.*?\\>", StringUtils.EMPTY)
                    .replace("<o:p>", StringUtils.EMPTY)
                    .replace("</o:p>", StringUtils.EMPTY)
                    .replaceAll("\\<nel.*?\\>", StringUtils.EMPTY)
                    .replaceAll("\\</nel.*?\\>", StringUtils.EMPTY)
                    .replace("<nel>", StringUtils.EMPTY)
                    .replace("</nel>", StringUtils.EMPTY)
                    .replaceAll("<(?=\\S*@)", "(")
                    .replaceAll("(?<=.it|.com)>", ")")
                    .replace("\u0000", StringUtils.EMPTY)
                    .replace("\u00A3", "£")
                    .replace("\u00F2", "ò")
                    .replace("\u00E0", "à")
                    .replace("\u00F9", "ù")
                    .replace("\u00E8", "è")
                    .replace("\u00E9", "é")
                    .replace("\u00A7", "§")
                    .replace("\u00B0", "°")
                    .replace("u0000", StringUtils.EMPTY)
                    .replace("‎", StringUtils.EMPTY)
                    .replace(" ", " ")
                    .replaceAll("&lt", specialTag.LT_TAG.name())
                    .replaceAll("&gt", specialTag.GT_TAG.name())
                    .replaceAll("&amp;", specialTag.COMMERCIAL_TAG.name())
                    .replaceAll("(<(?=\\s))", specialTag.LT_TAG.name())
                    .replaceAll("(<(?!\\/*("+ StringUtils.join(htmlTags.values(), "|") +")))", specialTag.LT_TAG.name())
                    .replaceAll("(?<=\\s)>", specialTag.GT_TAG.name())
                    .replaceAll("(?<!(" + StringUtils.join(htmlTags.values(), "|") + "))>", specialTag.GT_TAG.name())
                    .replaceAll("(&(?!(" + StringUtils.join(tags.values(), "|") + ")))", specialTag.COMMERCIAL_TAG.name())
                    .replace("<br>", "<br/>");

            unescapeHtml = StringEscapeUtils.escapeHtml4(unescapeHtml);
            unescapeHtml = restoreSpecials(unescapeHtml);
            beautifyHtml = unescapeHtml;
        }

        return beautifyHtml;
    }

    public static String restoreSpecialCharacters(String xmlPdf) {
        return xmlPdf.replaceAll(specialTag.LT_TAG.name(), "<![CDATA[&#60;]]>")
                .replaceAll(specialTag.GT_TAG.name(), "<![CDATA[&#62;]]>")
                .replaceAll(specialTag.COMMERCIAL_TAG.name(), "<![CDATA[&#38;]]>");
    }

    private static String restoreSpecials(String unescapeHtml) {

        unescapeHtml = unescapeHtml.replace("&agrave;", "à")
                .replace("&egrave;", "è")
                .replace("&eacute;", "é")
                .replace("&ograve;", "ò")
                .replace("&ugrave;", "ù")
                .replace("&igrave;", "ì")
                .replace("&euro;", "€")
                .replace("&pound;", "£")
                .replace("&ccedil;", "ç")
                .replace("&deg;", "°")
                .replace("&sect;", "§")
                .replace("&quot;", "\"")
                .replace("&bull;", "•")
                .replace("&nbsp;", " ")
                .replace("&ndash;", "–")
                .replace("&ldquo;", "“")
                .replace("&rdquo;", "”")
                .replace("&middot;", "·")
                .replace("&Agrave;", "À")
                .replace("&Aacute;", "Á")
                .replace("&Acirc;", "Â")
                .replace("&Atilde;", "Ã")
                .replace("&Auml;", "Ä")
                .replace("&Aring;", "Å")
                .replace("&AElig;", "Æ")
                .replace("&Ccedil;", "Ç")
                .replace("&Egrave;", "È")
                .replace("&Eacute;", "É")
                .replace("&Ecirc;", "Ê")
                .replace("&Euml;", "Ë")
                .replace("&Igrave;", "Ì")
                .replace("&Iacute;", "Í")
                .replace("&Icirc;", "Î")
                .replace("&Iuml;", "Ï")
                .replace("&ETH;", "Ð")
                .replace("&Ntilde;", "Ñ")
                .replace("&Ograve;", "Ò")
                .replace("&Oacute;", "Ó")
                .replace("&Ocirc;", "Ô")
                .replace("&Otilde;", "Õ")
                .replace("&Ouml;", "Ö")
                .replace("&Oslash;", "Ø")
                .replace("&Ugrave;", "Ù")
                .replace("&Uacute;", "Ú")
                .replace("&Ucirc;", "Û")
                .replace("&Uuml;", "Ü")
                .replace("&Yacute;", "Ý")
                .replace("&THORN;", "Þ")
                .replace("&szlig;", "ß")
                .replace("&agrave;", "à")
                .replace("&aacute;", "á")
                .replace("&acirc;", "â")
                .replace("&atilde;", "ã")
                .replace("&auml;", "ä")
                .replace("&aring;", "å")
                .replace("&aelig;", "æ")
                .replace("&ccedil;", "ç")
                .replace("&egrave;", "è")
                .replace("&eacute;", "é")
                .replace("&ecirc;", "ê")
                .replace("&euml;", "ë")
                .replace("&igrave;", "ì")
                .replace("&iacute;", "í")
                .replace("&icirc;", "î")
                .replace("&iuml;", "ï")
                .replace("&eth;", "ð")
                .replace("&ntilde;", "ñ")
                .replace("&ograve;", "ò")
                .replace("&oacute;", "ó")
                .replace("&ocirc;", "ô")
                .replace("&otilde;", "õ")
                .replace("&ouml;", "ö")
                .replace("&oslash;", "ø")
                .replace("&ugrave;", "ù")
                .replace("&uacute;", "ú")
                .replace("&ucirc;", "û")
                .replace("&uuml;", "ü")
                .replace("&yacute;", "ý")
                .replace("&thorn;", "þ")
                .replace("&yuml;", "ÿ")
                .replace("&iexcl;", "¡")
                .replace("&cent;", "¢")
                .replace("&pound;", "£")
                .replace("&curren;", "¤")
                .replace("&yen;", "¥")
                .replace("&brvbar;", "¦")
                .replace("&sect;", "§")
                .replace("&uml;", "¨")
                .replace("&copy;", "©")
                .replace("&ordf;", "ª")
                .replace("&laquo;", "«")
                .replace("&not;", "¬")
                .replace("&shy;", "­")
                .replace("&reg;", "®")
                .replace("&macr;", "¯")
                .replace("&deg;", "°")
                .replace("&plusmn;", "±")
                .replace("&sup2;", "²")
                .replace("&sup3;", "³")
                .replace("&acute;", "´")
                .replace("&micro;", "µ")
                .replace("&para;", "¶")
                .replace("&cedil;", "¸")
                .replace("&sup1;", "¹")
                .replace("&ordm;", "º")
                .replace("&raquo;", "»")
                .replace("&frac14;", "¼")
                .replace("&frac12;", "½")
                .replace("&frac34;", "¾")
                .replace("&iquest;", "¿")
                .replace("&times;", "×")
                .replace("&divide;", "÷")
                .replace("&forall;", "∀")
                .replace("&part;", "∂")
                .replace("&exist;", "∃")
                .replace("&empty;", "∅")
                .replace("&nabla;", "∇")
                .replace("&isin;", "∈")
                .replace("&notin;", "∉")
                .replace("&ni;", "∋")
                .replace("&prod;", "∏")
                .replace("&sum;", "∑")
                .replace("&minus;", "−")
                .replace("&lowast;", "∗")
                .replace("&radic;", "√")
                .replace("&prop;", "∝")
                .replace("&infin;", "∞")
                .replace("&ang;", "∠")
                .replace("&and;", "∧")
                .replace("&or;", "∨")
                .replace("&cap;", "∩")
                .replace("&cup;", "∪")
                .replace("&int;", "∫")
                .replace("&there4;", "∴")
                .replace("&sim;", "∼")
                .replace("&cong;", "≅")
                .replace("&asymp;", "≈")
                .replace("&ne;", "≠")
                .replace("&equiv;", "≡")
                .replace("&le;", "≤")
                .replace("&ge;", "≥")
                .replace("&sub;", "⊂")
                .replace("&sup;", "⊃")
                .replace("&nsub;", "⊄")
                .replace("&sube;", "⊆")
                .replace("&supe;", "⊇")
                .replace("&oplus;", "⊕")
                .replace("&otimes;", "⊗")
                .replace("&perp;", "⊥")
                .replace("&sdot;", "⋅")
                .replace("&Alpha;", "Α")
                .replace("&Beta;", "Β")
                .replace("&Gamma;", "Γ")
                .replace("&Delta;", "Δ")
                .replace("&Epsilon;", "Ε")
                .replace("&Zeta;", "Ζ")
                .replace("&Eta;", "Η")
                .replace("&Theta;", "Θ")
                .replace("&Iota;", "Ι")
                .replace("&Kappa;", "Κ")
                .replace("&Lambda;", "Λ")
                .replace("&Mu;", "Μ")
                .replace("&Nu;", "Ν")
                .replace("&Xi;", "Ξ")
                .replace("&Omicron;", "Ο")
                .replace("&Pi;", "Π")
                .replace("&Rho;", "Ρ")
                .replace("&Sigma;", "Σ")
                .replace("&Tau;", "Τ")
                .replace("&Upsilon;", "Υ")
                .replace("&Phi;", "Φ")
                .replace("&Chi;", "Χ")
                .replace("&Psi;", "Ψ")
                .replace("&Omega;", "Ω")
                .replace("&alpha;", "α")
                .replace("&beta;", "β")
                .replace("&gamma;", "γ")
                .replace("&delta;", "δ")
                .replace("&epsilon;", "ε")
                .replace("&zeta;", "ζ")
                .replace("&eta;", "η")
                .replace("&theta;", "θ")
                .replace("&iota;", "ι")
                .replace("&kappa;", "κ")
                .replace("&lambda;", "λ")
                .replace("&mu;", "μ")
                .replace("&nu;", "ν")
                .replace("&xi;", "ξ")
                .replace("&omicron;", "ο")
                .replace("&pi;", "π")
                .replace("&rho;", "ρ")
                .replace("&sigmaf;", "ς")
                .replace("&sigma;", "σ")
                .replace("&tau;", "τ")
                .replace("&upsilon;", "υ")
                .replace("&phi;", "φ")
                .replace("&chi;", "χ")
                .replace("&psi;", "ψ")
                .replace("&omega;", "ω")
                .replace("&thetasym;", "ϑ")
                .replace("&upsih;", "ϒ")
                .replace("&piv;", "ϖ")
                .replace("&OElig;", "Œ")
                .replace("&oelig;", "œ")
                .replace("&Scaron;", "Š")
                .replace("&scaron;", "š")
                .replace("&Yuml;", "Ÿ")
                .replace("&fnof;", "ƒ")
                .replace("&circ;", "ˆ")
                .replace("&tilde;", "˜")
                .replace("&tilde;", "˜")
                .replace("&ndash;", "–")
                .replace("&mdash;", "—")
                .replace("&lsquo;", "‘")
                .replace("&rsquo;", "’")
                .replace("&sbquo;", "‚")
                .replace("&ldquo;", "“")
                .replace("&rdquo;", "”")
                .replace("&bdquo;", "„")
                .replace("&dagger;", "†")
                .replace("&Dagger;", "‡")
                .replace("&bull;", "•")
                .replace("&hellip;", "…")
                .replace("&permil;", "‰")
                .replace("&prime;", "′")
                .replace("&Prime;", "″")
                .replace("&lsaquo;", "‹")
                .replace("&rsaquo;", "›")
                .replace("&oline;", "‾")
                .replace("&euro;", "€")
                .replace("&trade;", "™")
                .replace("&larr;", "←")
                .replace("&uarr;", "↑")
                .replace("&rarr;", "→")
                .replace("&darr;", "↓")
                .replace("&harr;", "↔")
                .replace("&crarr;", "↵")
                .replace("&lceil;", "⌈")
                .replace("&rceil;", "⌉")
                .replace("&lfloor;", "⌊")
                .replace("&rfloor;", "⌋")
                .replace("&loz;", "◊")
                .replace("&spades;", "♠")
                .replace("&clubs;", "♣")
                .replace("&hearts;", "♥")
                .replace("&diams;", "♦");

        return unescapeHtml;
    }

    public static String removeDirtyCharacters(String desNote) {
        return desNote.replaceAll("\u001A", StringUtils.SPACE)
                .replaceAll("\u0080", StringUtils.SPACE)
                .replaceAll("\u0092", PEAK)
                .replaceAll("\u0093", DOUBLE_PEAK)
                .replaceAll("\u0094", DOUBLE_PEAK)
                .replaceAll("\u0095", StringUtils.SPACE)
                .replaceAll("\u0096", "-");
    }

    public static void checkForErrorInDocLines(List<String> docLines, List<Integer> errors) {
        for (int i = 0; i < docLines.size(); i++) {
            String line = docLines.get(i);
            if (line.contains(ERROR_CHARACTERS_1)) {
                errors.add(i+1);
            }
        }
    }
}
