package com.zzk.idea.bitbyte.util;

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
    public static void onlyMethodEnablePresentation(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = event.getDataContext().getData(CommonDataKeys.EDITOR);
        PsiFile psiFile = event.getDataContext().getData(CommonDataKeys.PSI_FILE);

        if (project == null || editor == null || psiFile == null) {
            event.getPresentation().setVisible(false);
            return;
        }

        PsiElement element = psiFile.findElementAt(editor.getCaretModel().getOffset());
        if (element == null) {
            event.getPresentation().setVisible(false);
            return;
        }

        PsiMethod containingMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
        if (containingMethod == null) {
            event.getPresentation().setVisible(false);
            return;
        }

        // 如果当前光标所在的位置在 Java 方法的范围中，则设置菜单项可见
        event.getPresentation().setVisible(true);
    }
}
