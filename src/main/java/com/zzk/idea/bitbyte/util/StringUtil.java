package com.zzk.idea.bitbyte.util;

public class StringUtil {

    /**
     * 将字符串的首字母转为小写。
     *
     * @param input 输入字符串
     * @return 首字母已转为小写的字符串
     */
    public static String toLowerCaseFirstChar(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        char firstChar = input.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            // 如果首字母是大写字母，使用toLowerCase()将其转为小写
            return Character.toLowerCase(firstChar) + input.substring(1);
        } else {
            // 如果首字母已经是小写或非字母字符，直接返回原字符串
            return input;
        }
    }
}
