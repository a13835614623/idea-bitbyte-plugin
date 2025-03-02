
package com.zzk.idea.bitbyte.action.test;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testIntegration.TestFramework;
import com.zzk.idea.bitbyte.constants.TestActionType;
import org.jetbrains.annotations.NotNull;

public abstract class BaseRightMenuCreateTestClassAction extends AnAction {

    private final TestActionType testActionType;


    public BaseRightMenuCreateTestClassAction(TestActionType testActionType) {
        this.testActionType = testActionType;
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        Editor editor = event.getDataContext().getData(CommonDataKeys.EDITOR);
        PsiFile psiFile = event.getDataContext().getData(CommonDataKeys.PSI_FILE);
        if (project == null || editor == null || psiFile == null) {
            return;
        }
        PsiElement element = psiFile.findElementAt(editor.getCaretModel().getOffset());
        PsiClass containingMethod = PsiTreeUtil.getParentOfType(element, PsiClass.class);
        if (containingMethod == null) {
            return;
        }
        TestFramework.EXTENSION_NAME.extensions()
                .filter(x -> "JUnit5".equalsIgnoreCase(x.getName()))
                .findFirst()
                .ifPresent(framework -> {
                    new CreateTestClassAction(containingMethod, framework, testActionType).doAction(event);
                });
    }

    @Override
    public void update(@NotNull final AnActionEvent event) {
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

        PsiClass containingMethod = PsiTreeUtil.getParentOfType(element, PsiClass.class);
        if (containingMethod == null) {
            event.getPresentation().setVisible(false);
            return;
        }

        // 如果当前光标所在的位置在 Java 类的范围中，则设置菜单项可见
        event.getPresentation().setVisible(true);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}