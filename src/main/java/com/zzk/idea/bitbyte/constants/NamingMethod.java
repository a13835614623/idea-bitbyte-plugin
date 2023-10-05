package com.zzk.idea.bitbyte.constants;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 命名方式
 *
 * @author 张子宽
 * @date 2023/10/05
 */
@Getter
@AllArgsConstructor
public enum NamingMethod {
    CAMEL_CASE("camelCase", Function.identity()),
    SNAKE_CASE("snake_case", StringConvertFunction.TO_UNDERLINE_LOWER_CASE),
    PASCAL_CASE("PascalCase", StringConvertFunction.TO_PASCAL_CASE),
    UPPER_CASE("UPPER_CASE", StringConvertFunction.TO_UPPER_CASE),
    ;

    private final String example;

    private final Function<String, String> fromCamelCaseFunction;


    @Override
    public String toString() {
        return example;
    }
}
