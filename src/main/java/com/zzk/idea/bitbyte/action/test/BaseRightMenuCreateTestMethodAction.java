
package com.zzk.idea.bitbyte.action.test;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testIntegration.TestFramework;
import com.zzk.idea.bitbyte.constants.TestActionType;
import com.zzk.idea.bitbyte.util.PsiActionUtil;
import org.jetbrains.annotations.NotNull;

public abstract class BaseRightMenuCreateTestMethodAction extends AnAction {

    private final TestActionType testActionType;


    public BaseRightMenuCreateTestMethodAction(TestActionType testActionType) {
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
        PsiMethod containingMethod = PsiTreeUtil.getParentOfType(element, PsiMethod.class);
        if (containingMethod == null) {
            return;
        }
        TestFramework.EXTENSION_NAME.extensions()
                .filter(x -> "JUnit5".equalsIgnoreCase(x.getName()))
                .findFirst()
                .ifPresent(framework -> {
                    new BaseCreateTestAction(containingMethod, framework, testActionType).doAction(event);
                });
    }

    @Override
    public void update(@NotNull final AnActionEvent event) {
        event.getPresentation().setVisible(PsiActionUtil.isOnMethod(event));
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }
}