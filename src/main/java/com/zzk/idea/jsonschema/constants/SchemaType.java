package com.zzk.idea.jsonschema.constants;


import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.util.PsiTypesUtil;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
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
public enum SchemaType {
    /**
     * 类型参数
     */
    STRING("string", "字符串", List.of(String.class)),
    NUMBER("number", "数字", List.of(Double.class, Long.class,BigDecimal.class, double.class, long.class)),
    INTEGER("integer", "整数", List.of(Integer.class, Short.class, Byte.class,
            int.class, short.class, byte.class)),
    OBJECT("object", "对象", List.of()),
    ARRAY("array", "数组", List.of(Collection.class)),
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

    private static final Map<String, SchemaType> ENUM_MAP = Arrays.stream(SchemaType.values())
            .collect(Collectors.toMap(SchemaType::getValue, Function.identity()));

    SchemaType(String value, String desc, List<Class<?>> clazz) {
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


    @Override
    public String toString() {
        return getValue();
    }

    /**
     * 根据值获取枚举
     *
     * @param value 值
     * @return OrderManualReviewRuleReason
     * @author 张子宽
     * @date 2022/8/14
     */
    public static SchemaType parse(String value) {
        if (value == null) {
            return null;
        }
        return ENUM_MAP.get(value);
    }

    /**
     * 根据className获取枚举
     *
     * @param psiClass 值
     * @return OrderManualReviewRuleReason
     * @author 张子宽
     * @date 2022/8/14
     */
    public static SchemaType parse(PsiClass psiClass) {
        return parse(PsiTypesUtil.getClassType(psiClass));
    }

    public static SchemaType parse(PsiType psiType) {
        if (psiType == null) {
            return NULL;
        }
        if (psiType instanceof PsiArrayType) {
            return ARRAY;
        }
        if (psiType instanceof PsiPrimitiveType) {
            PsiPrimitiveType psiPrimitiveType = ((PsiPrimitiveType) psiType);
            SchemaType value1 = parseByClassName(psiPrimitiveType.getCanonicalText());
            if (value1 != null) {
                return value1;
            }
            throw new RuntimeException("非法的类型:" + psiPrimitiveType);
        }
        if (psiType instanceof PsiClassReferenceType) {
            PsiClassReferenceType psiClassReferenceType = ((PsiClassReferenceType) psiType);
            String canonicalText = psiClassReferenceType.getCanonicalText();
            if (canonicalText.startsWith(CommonClassNames.JAVA_UTIL_COLLECTION)
                    || Arrays.stream(psiClassReferenceType.getSuperTypes())
                    .map(PsiType::getCanonicalText)
                    .anyMatch(x -> x.startsWith(CommonClassNames.JAVA_UTIL_COLLECTION))) {
                return ARRAY;
            }
            if (canonicalText.equals(CommonClassNames.JAVA_LANG_STRING)) {
                return STRING;
            }
            SchemaType value1 = parseByClassName(canonicalText);
            if (value1 != null) {
                return value1;
            }
        }
        return OBJECT;
    }

    @Nullable
    private static SchemaType parseByClassName(String canonicalText) {
        for (SchemaType value : values()) {
            if (value.getClazz().stream().anyMatch(x -> x.getName().equals(canonicalText))) {
                return value;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(Integer.class.getName());
    }
}
