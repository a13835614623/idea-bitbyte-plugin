package com.zzk.idea.jsonschema.constants;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * StringConvertFunction
 *
 * @author 张子宽
 * @date 2023/02/22
 */
public enum StringConvertFunction implements Function<String, String> {
    TO_UNDERLINE_UPPER_CASE(s -> {
        String ss = s.trim();
        int length = ss.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            // 前一个不是空格
            if (c == ' ') {
                sb.append("_");
                continue;
            } else if (c >= 'A' && c <= 'Z' && i > 0 && s.charAt(i - 1) != ' ') {
                sb.append("_");
            }
            sb.append(c);
        }
        return sb.toString().toUpperCase();
    }),
    TO_UPPER_CASE(String::toUpperCase),
    TO_LOWER_CASE(String::toLowerCase),
    ;

    private final Function<String, String> converter;

    private final String text;

    StringConvertFunction(Function<String, String> converter, String text) {
        this.converter = converter;
        this.text = text;
    }

    StringConvertFunction(Function<String, String> converter) {
        this.converter = converter;
        this.text = toFirstUpperCaseElseLowerCase(name());
    }

    @NotNull
    private static String toFirstUpperCaseElseLowerCase(String name) {
        String replace = name.toLowerCase().replace("_", " ");
        int length = replace.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (i == 0) {
                sb.append(String.valueOf(replace.charAt(i)).toUpperCase());
            } else {
                sb.append(replace.charAt(i));
            }
        }
        return sb.toString();
    }

    @Override
    public String apply(String s) {
        if (StringUtils.isEmpty(s)) {
            return "";
        }
        return converter.apply(s);
    }

    public String getText() {
        return text;
    }

    public static void main(String[] args) {
        System.out.println(TO_UNDERLINE_UPPER_CASE.apply("firstNameAbc"));
        System.out.println(TO_UNDERLINE_UPPER_CASE.apply("first name abc"));
    }
}
