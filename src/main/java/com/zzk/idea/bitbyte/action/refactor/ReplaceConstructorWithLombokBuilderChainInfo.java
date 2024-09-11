package com.zzk.idea.bitbyte.action.refactor;

import java.util.Map;

import com.intellij.openapi.util.Comparing;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiExpression;
import com.intellij.psi.PsiExpressionList;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNewExpression;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.codeStyle.VariableKind;
import com.intellij.refactoring.replaceConstructorWithBuilder.ParameterData;
import com.intellij.refactoring.util.FixableUsageInfo;
import com.intellij.util.IncorrectOperationException;

public class ReplaceConstructorWithLombokBuilderChainInfo extends FixableUsageInfo {
    private final String srcClass;
    private final Map<String, ParameterData> myParametersMap;

    public ReplaceConstructorWithLombokBuilderChainInfo(PsiNewExpression constructorReference, String builderClass, Map<String, ParameterData> parametersMap) {
        super(constructorReference);
        this.srcClass = builderClass;
        this.myParametersMap = parametersMap;
    }

    public void fixUsage() throws IncorrectOperationException {
        PsiNewExpression expr = (PsiNewExpression) this.getElement();
        if (expr != null) {
            PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(expr.getProject());
            PsiMethod constructor = expr.resolveConstructor();
            if (constructor != null) {
                StringBuilder buf = new StringBuilder();
                PsiExpressionList argumentList = expr.getArgumentList();
                if (argumentList != null) {
                    PsiExpression[] args = argumentList.getExpressions();
                    PsiParameter[] parameters = constructor.getParameterList().getParameters();
                    JavaCodeStyleManager styleManager = JavaCodeStyleManager.getInstance(constructor.getProject());

                    for (int i = 0; i < Math.min(constructor.getParameterList()
                            .getParametersCount(), args.length); ++i) {
                        StringBuilder arg = new StringBuilder(args[i].getText());
                        if (parameters[i].isVarArgs()) {
                            for (int ia = i + 1; ia < args.length; ++ia) {
                                arg.append(", ").append(args[ia].getText());
                            }
                        }

                        String pureParamName = styleManager.variableNameToPropertyName(parameters[i].getName(), VariableKind.PARAMETER);
                        ParameterData data = this.myParametersMap.get(pureParamName);
                        if (!Comparing.strEqual(arg.toString(), data.getDefaultValue()) || data.isInsertSetter()) {
                            buf.append(data.getSetterName()).append("(").append(arg).append(")\n.");
                        }
                    }
                    // TestParam.builder().build()
                    PsiExpression settersChain = elementFactory.createExpressionFromText(srcClass + ".builder()\n." + buf + "build()", null);
                    styleManager.shortenClassReferences(expr.replace(settersChain));
                }
            }
        }

    }
}