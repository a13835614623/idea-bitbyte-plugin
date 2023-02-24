package com.zzk.idea.bitbyte.constants;

import com.intellij.psi.*;
import com.zzk.idea.bitbyte.util.PsiUtil;

import java.util.Optional;
import java.util.function.Function;

/**
 * 枚举参数类型
 *
 * @author 张子宽
 * @date 2023/01/13
 */
public enum EnumParamType {
    COMMENT("comment", PsiUtil::getComment),
    NAME("Enum Name", PsiField::getName),
    PARAM_1("Enum Param 1", 1),
    PARAM_2("Enum Param 2", 2),
    PARAM_3("Enum Param 3", 3),
    PARAM_4("Enum Param 4", 4),
    PARAM_5("Enum Param 5", 5),

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
                .map(list -> list[argOrder-1])
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

    @Override
    public String toString() {
        return desc;
    }
}
