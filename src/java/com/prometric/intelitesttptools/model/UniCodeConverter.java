package com.prometric.intelitesttptools.model;

/**
 * @author patrick.maccnaimhin
 */
public class UniCodeConverter {
    private static final String UNICODE_CONTROL_SPACE = "&#10;";
    private static final String CONTROL_SPACE = "\n";
    private static final String UNICODE_SPACE = "&#32;";
    private static final String SPACE = " ";
    private static final String UNICODE_EXCLAMATION_MARK = "&#33;";
    private static final String EXCLAMATION_MARK = "!";
    private static final String UNICODE_QUOTATION_MARK  = "&#34;";
    private static final String QUOTATION_MARK  = "\"";
    private static final String UNICODE_NUMBER_SIGN = "&#35;";
    private static final String NUMBER_SIGN = "#";
    private static final String UNICODE_PERCENT_SIGN  = "&#37;";
    private static final String PERCENT_SIGN  = "%";
    private static final String UNICODE_AMPERSAND = "&#38;";
    private static final String AMPERSAND = "&";
    private static final String UNICODE_APOSTROPHE = "&#39;";
    private static final String APOSTROPHE = "'";
    private static final String UNICODE_LEFT_PARENTHESIS  = "&#40;";
    private static final String LEFT_PARENTHESIS = "(";
    private static final String UNICODE_RIGHT_PARENTHESIS  = "&#41;";
    private static final String RIGHT_PARENTHESIS  = ")";
    private static final String UNICODE_ASTERISK  = "&#42;";
    private static final String ASTERISK  = "*";
    private static final String UNICODE_PLUS_SIGN  = "&#43;";
    private static final String PLUS_SIGN  = "+";
    private static final String UNICODE_COMMA = "&#44;";
    private static final String COMMA = ",";
    private static final String UNICODE_HYPHEN_MINUS = "&#45;";
    private static final String HYPHEN_MINUS = "-";
    private static final String UNICODE_FULL_STOP  = "&#46;";
    private static final String FULL_STOP  = ".";
    private static final String UNICODE_SOLIDUS = "&#47;";
    private static final String SOLIDUS = "/";
    private static final String UNICODE_COLON = "&#58;";
    private static final String COLON = ":";
    private static final String UNICODE_SEMICOLON = "&#59;";
    private static final String SEMICOLON = ";";
    private static final String UNICODE_LESS_THAN = "&#60;";
    private static final String LESS_THAN = "<";
    private static final String UNICODE_EQUALS  = "&#61;";
    private static final String EQUALS  = "=";
    private static final String UNICODE_GREATER_THAN = "&#62;";
    private static final String GREATER_THAN = ">";
    private static final String UNICODE_QUESTION_MARK = "&#63;";
    private static final String QUESTION_MARK = "?";
    private static final String UNICODE_AT_SIGN = "&#64;";
    private static final String AT_SIGN = "@";
    private static final String UNICODE_LEFT_SQUARE_BRACKET = "&#91;";
    private static final String LEFT_SQUARE_BRACKET = "[";
    private static final String UNICODE_REVERSE_SOLIDUS = "&#92;";
    private static final String REVERSE_SOLIDUS = "\\";
    private static final String UNICODE_RIGHT_SQUARE_BRACKET = "&#93;";
    private static final String RIGHT_SQUARE_BRACKET = "]";
    private static final String UNICODE_ACCENT = "&#94;";
    private static final String ACCENT = "^";
    private static final String UNICODE_LOW_LINE  = "&#95;";
    private static final String LOW_LINE = "_";
    private static final String UNICODE_GRAVE_ACCENT = "&#96;";
    private static final String GRAVE_ACCENT = "`";
    private static final String UNICODE_LEFT_CURLY_BRACKET  = "&#123;";
    private static final String LEFT_CURLY_BRACKET = "{";
    private static final String UNICODE_VERTICAL_LINE = "&#124;";
    private static final String VERTICAL_LINE  = "|";
    private static final String UNICODE_RIGHT_CURLY_BRACKET = "&#125;";
    private static final String RIGHT_CURLY_BRACKET = "}";
    private static final String UNICODE_TILDE = "&#126;";
    private static final String TILDE = "~";
    private static final String UNICODE_POUND_SIGN = "&#163;";
    private static final String POUND_SIGN = "\\£";
    private static final String UNICODE_DOLLOR_SIGN = "&#36;";
    private static final String DOLLOR_SIGN = "\\$";

    public String unicodeClean(String dirtyText) {
        dirtyText = buildHtmlEntityCode(dirtyText);
        return  replaceUniCode(dirtyText);
    }
    
    private String replaceUniCode(String uniCodeString) {
        uniCodeString = uniCodeString.replaceAll(UNICODE_CONTROL_SPACE, CONTROL_SPACE);
        uniCodeString = uniCodeString.replaceAll(UNICODE_SPACE, SPACE);
        uniCodeString = uniCodeString.replaceAll(UNICODE_EXCLAMATION_MARK, EXCLAMATION_MARK);
        uniCodeString = uniCodeString.replaceAll(UNICODE_QUOTATION_MARK, QUOTATION_MARK);
        uniCodeString = uniCodeString.replaceAll(UNICODE_NUMBER_SIGN, NUMBER_SIGN);
        uniCodeString = uniCodeString.replaceAll(UNICODE_PERCENT_SIGN, PERCENT_SIGN);
        uniCodeString = uniCodeString.replaceAll(UNICODE_AMPERSAND, AMPERSAND);
        uniCodeString = uniCodeString.replaceAll(UNICODE_APOSTROPHE, APOSTROPHE);
        uniCodeString = uniCodeString.replaceAll(UNICODE_LEFT_PARENTHESIS, LEFT_PARENTHESIS);
        uniCodeString = uniCodeString.replaceAll(UNICODE_RIGHT_PARENTHESIS, RIGHT_PARENTHESIS);
        uniCodeString = uniCodeString.replaceAll(UNICODE_ASTERISK, ASTERISK);
        uniCodeString = uniCodeString.replaceAll(UNICODE_COMMA, COMMA);
        uniCodeString = uniCodeString.replaceAll(UNICODE_PLUS_SIGN, PLUS_SIGN);
        uniCodeString = uniCodeString.replaceAll(UNICODE_HYPHEN_MINUS, HYPHEN_MINUS);
        uniCodeString = uniCodeString.replaceAll(UNICODE_FULL_STOP, FULL_STOP);
        uniCodeString = uniCodeString.replaceAll(UNICODE_SOLIDUS, SOLIDUS);
        uniCodeString = uniCodeString.replaceAll(UNICODE_COLON, COLON);
        uniCodeString = uniCodeString.replaceAll(UNICODE_SEMICOLON, SEMICOLON);
        uniCodeString = uniCodeString.replaceAll(UNICODE_LESS_THAN, LESS_THAN);
        uniCodeString = uniCodeString.replaceAll(UNICODE_EQUALS, EQUALS); 
        uniCodeString = uniCodeString.replaceAll(UNICODE_GREATER_THAN, GREATER_THAN);
        uniCodeString = uniCodeString.replaceAll(UNICODE_QUESTION_MARK, QUESTION_MARK);
        uniCodeString = uniCodeString.replaceAll(UNICODE_AT_SIGN, AT_SIGN);
        uniCodeString = uniCodeString.replaceAll(UNICODE_LEFT_SQUARE_BRACKET, LEFT_SQUARE_BRACKET);
        uniCodeString = uniCodeString.replaceAll(UNICODE_REVERSE_SOLIDUS, REVERSE_SOLIDUS);
        uniCodeString = uniCodeString.replaceAll(UNICODE_RIGHT_SQUARE_BRACKET, RIGHT_SQUARE_BRACKET);
        uniCodeString = uniCodeString.replaceAll(UNICODE_ACCENT, ACCENT);
        uniCodeString = uniCodeString.replaceAll(UNICODE_LOW_LINE, LOW_LINE);
        uniCodeString = uniCodeString.replaceAll(UNICODE_GRAVE_ACCENT, GRAVE_ACCENT);
        uniCodeString = uniCodeString.replaceAll(UNICODE_LEFT_CURLY_BRACKET, LEFT_CURLY_BRACKET);
        uniCodeString = uniCodeString.replaceAll(UNICODE_VERTICAL_LINE, VERTICAL_LINE);
        uniCodeString = uniCodeString.replaceAll(UNICODE_RIGHT_CURLY_BRACKET, RIGHT_CURLY_BRACKET);
        uniCodeString = uniCodeString.replaceAll(UNICODE_TILDE, TILDE);
        uniCodeString = uniCodeString.replaceAll(UNICODE_POUND_SIGN, POUND_SIGN);
        uniCodeString = uniCodeString.replaceAll(UNICODE_DOLLOR_SIGN, DOLLOR_SIGN);
        return uniCodeString;    
    }

    private String buildHtmlEntityCode(String input) {
        StringBuilder output = new StringBuilder(input.length() * 2);

        int len = input.length();
        int code, code1, code2, code3, code4;
        char ch;

        for (int i = 0; i < len;) {
            code1 = input.codePointAt(i);
            if (code1 >> 3 == 30) {
                code2 = input.codePointAt(i + 1);
                code3 = input.codePointAt(i + 2);
                code4 = input.codePointAt(i + 3);
                code = ((code1 & 7) << 18) | ((code2 & 63) << 12) | ((code3 & 63) << 6) | (code4 & 63);
                i += 4;
                output.append("&#").append(code).append(";");
            } else if (code1 >> 4 == 14) {
                code2 = input.codePointAt(i + 1);
                code3 = input.codePointAt(i + 2);
                code = ((code1 & 15) << 12) | ((code2 & 63) << 6) | (code3 & 63);
                i += 3;
                output.append("&#").append(code).append(";");
            } else if (code1 >> 5 == 6) {
                code2 = input.codePointAt(i + 1);
                code = ((code1 & 31) << 6) | (code2 & 63);
                i += 2;
                output.append("&#").append(code).append(";");
            } else {
                code = code1;
                i += 1;

                ch = (char) code;
                if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9') {
                    output.append(ch);
                } else {
                    output.append("&#").append(code).append(";");
                }
            }
        }
        return output.toString();
    }
}
