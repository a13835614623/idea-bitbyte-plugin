package com.zzk.idea.bitbyte.action.refactor;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.zzk.idea.bitbyte.util.PsiActionUtil;
import com.zzk.idea.bitbyte.util.PsiUtil;
import org.jetbrains.annotations.NotNull;

public class LombokBuilderRefactoringAction extends AnAction implements LocalQuickFix {

    public static final String LOMBOK_BUILDER = "lombok.Builder";
    public static final String LOMBOK_GETTER = "lombok.Getter";

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        PsiClass lombokBuilderClass = PsiUtil.findClass(project, LOMBOK_BUILDER);
        if (lombokBuilderClass == null) {
            return;
        }
        PsiActionUtil.getConstructor(event)
                .ifPresent(constructor -> {
                    MethodToLombokBuilderRefactorCommand command = MethodToLombokBuilderRefactorCommand.builder()
                            .constructor(constructor)
                            .srcClass(constructor.getContainingClass())
                            .project(project)
                            .lombokBuilderClass(lombokBuilderClass)
                            .lombokGetterClass(PsiUtil.findClass(project, LOMBOK_GETTER))
                            .build();
                    command.refactor();
                });
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setVisible(PsiActionUtil.isOnConstructor(e));
    }

    @Override
    public @IntentionFamilyName @NotNull String getFamilyName() {
        return "To lombok builder";
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
        PsiElement psiElement = problemDescriptor.getPsiElement();
        if (psiElement instanceof PsiMethod) {
            PsiMethod constructor = (PsiMethod) psiElement;
            PsiClass lombokBuilderClass = PsiUtil.findClass(project, LOMBOK_BUILDER);
            MethodToLombokBuilderRefactorCommand.builder()
                    .constructor(constructor)
                    .srcClass(constructor.getContainingClass())
                    .project(project)
                    .lombokBuilderClass(lombokBuilderClass)
                    .lombokGetterClass(PsiUtil.findClass(project, LOMBOK_GETTER))
                    .build().refactor();
        }
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.BGT;
    }

}
