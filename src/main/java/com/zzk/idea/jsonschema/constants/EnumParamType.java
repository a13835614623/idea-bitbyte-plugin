package com.zzk.idea.jsonschema.constants;

import com.intellij.psi.*;
import com.zzk.idea.jsonschema.util.Util;

import java.util.Optional;
import java.util.function.Function;

/**
 * 枚举参数类型
 *
 * @author 张子宽
 * @date 2023/01/13
 */
public enum EnumParamType {
    COMMENT("注释", Util::getComment),
    NAME("枚举名", PsiField::getName),
    PARAM_1("参数1", 1),
    PARAM_2("参数2", 2),
    PARAM_3("参数3", 3),
    PARAM_4("参数4", 4),
    PARAM_5("参数5", 5),

    ;

    private final String desc;

    private final Function<PsiEnumConstant, String> function;

    EnumParamType(String desc, Function<PsiEnumConstant, String> function) {
        this.desc = desc;
        this.function = function;
    }

    EnumParamType(String desc, int argOrder) {
        this.desc = desc;
        this.function = x -> Optional.ofNullable(x.getArgumentList())
                .map(PsiExpressionList::getExpressions)
                .filter(exps -> exps.length > argOrder - 1)
                .map(list -> list[argOrder])
                .map(PsiElement::getText)
                .map(text -> {
                    if (text.startsWith("\"")) {
                        return text.substring(1, text.length() - 1);
                    }
                    return text;
                })
                .orElse("");
    }


    public String getParam(PsiEnumConstant psiEnumConstant){
        return function.apply(psiEnumConstant);
    }
}
