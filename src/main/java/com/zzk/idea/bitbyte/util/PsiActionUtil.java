package com.zzk.idea.bitbyte.util;

import java.util.Optional;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

public class PsiActionUtil {
    public static boolean isOnMethod(@NotNull AnActionEvent event) {
        return getPsiMethod(event).isPresent();
    }


    public static boolean isOnConstructor(@NotNull AnActionEvent event) {
        return getConstructor(event).isPresent();
    }

    public static Optional<PsiMethod> getConstructor(@NotNull AnActionEvent event) {
        return getPsiMethod(event)
                .filter(PsiMethod::isConstructor);
    }

    public static Optional<PsiMethod> getPsiMethod(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = event.getDataContext().getData(CommonDataKeys.EDITOR);
        PsiFile psiFile = event.getDataContext().getData(CommonDataKeys.PSI_FILE);
        if (project == null || editor == null || psiFile == null) {
            return Optional.empty();
        }
        PsiElement element = psiFile.findElementAt(editor.getCaretModel().getOffset());
        if (element == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(PsiTreeUtil.getParentOfType(element, PsiMethod.class));
    }

}
