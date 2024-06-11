package com.zzk.idea.bitbyte.action.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNewExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.replaceConstructorWithBuilder.ParameterData;
import com.intellij.refactoring.util.FixableUsageInfo;
import com.zzk.idea.bitbyte.util.PsiUtil;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MethodToLombokBuilderRefactorCommand {
    private final PsiMethod constructor;
    private final Project project;
    private final PsiClass srcClass;
    private final PsiClass lombokBuilderClass;

    public void refactor() {
        WriteCommandAction.runWriteCommandAction(getProject(), this::doRefactor);
    }

    private void doRefactor() {
        refactorUsage();
        refactorSrcClass();
    }

    private void refactorUsage() {
        findUsages(getConstructor())
                .forEach(FixableUsageInfo::fixUsage);
    }

    private void refactorSrcClass() {
        // 加@Builder注解
        addLombokBuilderAnnotation();
        // 加import @lombok.Builder
        addLombokBuilderImport();
        // 删除构造器
        deleteConstructor();
    }

    public void addLombokBuilderAnnotation() {
        PsiUtil.addAnnotation(getSrcClass(), getLombokBuilderClass());
    }

    public void addLombokBuilderImport() {
        PsiUtil.addImport(getSrcClass(), getProject(), List.of(getLombokBuilderClass()));
    }

    public void deleteConstructor() {
        getConstructor().delete();
    }

    private static List<FixableUsageInfo> findUsages(PsiMethod constructor) {
        Map<String, ParameterData> paramMap = PsiUtil.getParamMap(constructor);
        String qualifiedName = Objects.requireNonNull(constructor.getContainingClass()).getName();
        List<FixableUsageInfo> usages = new ArrayList<>();
        for (PsiReference reference : ReferencesSearch.search(constructor)) {
            PsiElement element = reference.getElement();
            PsiNewExpression newExpression = PsiTreeUtil.getParentOfType(element, PsiNewExpression.class);
            usages.add(new ReplaceConstructorWithLombokBuilderChainInfo(newExpression, qualifiedName, paramMap));
        }
        return usages;
    }

}
