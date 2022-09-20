package com.zzk.idea.jsonschema;


import com.intellij.psi.PsiType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 类型
 *
 * @author 张子宽
 * @date 2022/09/20
 */
public enum Type {
    /**
     * 类型参数
     */
    STRING("string", "字符串", List.of(String.class)),
    NUMBER("number", "数字", List.of(Double.class, Long.class, double.class, long.class)),
    INTEGER("integer", "整数", List.of(Integer.class, Short.class, int.class, short.class)),
    OBJECT("object", "对象", List.of()),
    ARRAY("array", "数组", List.of()),
    BOOLEAN("boolean", "布尔值", List.of(Boolean.class, boolean.class)),
    NULL("null", "null", List.of());

    /**
     * 值
     */
    private final String value;
    /**
     * 描述
     */
    private final String desc;

    private final List<Class<?>> clazz;

    private static final Map<String, Type> ENUM_MAP = Arrays.stream(Type.values())
            .collect(Collectors.toMap(Type::getValue, Function.identity()));

    Type(String value, String desc, List<Class<?>> clazz) {
        this.value = value;
        this.desc = desc;
        this.clazz = clazz;
    }

    public List<Class<?>> getClazz() {
        return clazz;
    }

    public String getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 根据值获取枚举
     *
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

    /**
     * 根据className获取枚举
     *
     * @param className 值
     * @return OrderManualReviewRuleReason
     * @author 张子宽
     * @date 2022/8/14
     */
    public static Type parseByClassName(PsiType psiType) {
        for (Type value : values()) {
            if (value.getClazz().stream().anyMatch(x -> x.getName().equals(className))) {
                return value;
            }
        }
        return
    }
}
