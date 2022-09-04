package com.zzk.maxcv.jsonschema;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Type {

    STRING("string","字符串"),
    NUMBER("number","数字"),
    INTEGER("integer","整数"),
    OBJECT("object","对象"),
    ARRAY("array","数组"),
    BOOLEAN("boolean","布尔值"),
    NULL("null","null");

    ;

    /**
     * 值
     */
    private final String value;
    /**
     * 描述
     */
    private final String desc;

    private static final Map<String, Type> ENUM_MAP = Arrays.stream(Type.values())
            .collect(Collectors.toMap(Type::getValue, Function.identity()));

    /**
     * 根据值获取枚举
     * @param value 值
     * @return OrderManualReviewRuleReason
     * @author 张子宽
     * @date 2022/8/14
     */
    public static Type parse(String value) {
        if (value == null) {
            return null;
        }
        return ENUM_MAP.get(value);
    }

}
