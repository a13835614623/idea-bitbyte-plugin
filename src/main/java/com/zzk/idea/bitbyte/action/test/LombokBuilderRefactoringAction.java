package com.zzk.idea.bitbyte.action.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiNewExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.refactoring.replaceConstructorWithBuilder.ParameterData;
import com.intellij.refactoring.util.FixableUsageInfo;
import com.zzk.idea.bitbyte.util.PsiActionUtil;
import com.zzk.idea.bitbyte.util.PsiUtil;
import org.jetbrains.annotations.NotNull;

public class LombokBuilderRefactoringAction extends AnAction {

    public static final String LOMBOK_BUILDER = "lombok.Builder";


    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = event.getDataContext().getData(CommonDataKeys.EDITOR);
        PsiFile psiFile = event.getDataContext().getData(CommonDataKeys.PSI_FILE);
        if (project == null || editor == null || psiFile == null) {
            return;
        }
        PsiElement element = psiFile.findElementAt(editor.getCaretModel().getOffset());
        PsiMethod constructor = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
        if (constructor == null) {
            return;
        }
        List<FixableUsageInfo> usages = findUsages(constructor);
        PsiClass psiClass = constructor.getContainingClass();
        PsiClass lombokBuilderClass = PsiUtil.findClass(project, LOMBOK_BUILDER);
        if (psiClass == null || lombokBuilderClass == null) {
            return;
        }
        WriteCommandAction.runWriteCommandAction(project, () -> {
            usages.forEach(FixableUsageInfo::fixUsage);
            // 加@Builder注解
            PsiUtil.addAnnotation(psiClass, lombokBuilderClass);
            // 加import @lombok.Builder
            PsiUtil.addImport(psiClass, project, List.of(lombokBuilderClass));
            // 删除构造器
            constructor.delete();
        });
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        PsiActionUtil.onlyMethodEnablePresentation(e);
    }

    protected List<FixableUsageInfo> findUsages(PsiMethod constructor) {
        Map<String, ParameterData> paramMap = getParamMap(constructor);
        String qualifiedName = Objects.requireNonNull(constructor.getContainingClass()).getName();
        List<FixableUsageInfo> usages = new ArrayList<>();
        for (PsiReference reference : ReferencesSearch.search(constructor)) {
            PsiElement element = reference.getElement();
            PsiNewExpression newExpression = PsiTreeUtil.getParentOfType(element, PsiNewExpression.class);
            usages.add(new ReplaceConstructorWithLombokBuilderChainInfo(newExpression, qualifiedName, paramMap));
        }
        return usages;
    }

    private static Map<String, ParameterData> getParamMap(PsiMethod constructor) {
        LinkedHashMap<String, ParameterData> paramMap = new LinkedHashMap<>();
        ParameterData.createFromConstructor(constructor, "", paramMap);
        return paramMap;
    }
}
