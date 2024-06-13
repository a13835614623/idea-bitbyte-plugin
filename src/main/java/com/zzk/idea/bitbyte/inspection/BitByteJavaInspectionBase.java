package com.zzk.idea.bitbyte.inspection;

import com.intellij.codeInspection.AbstractBaseJavaLocalInspectionTool;
import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtilCore;
import com.intellij.psi.JavaElementVisitor;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiMethod;
import com.zzk.idea.bitbyte.action.refactor.LombokBuilderRefactoringAction;
import com.zzk.idea.bitbyte.util.LombokUtil;

public class BitByteJavaInspectionBase extends AbstractBaseJavaLocalInspectionTool {
    @Override
    public final PsiElementVisitor buildVisitor(ProblemsHolder holder, boolean isOnTheFly) {
        Module module = ModuleUtilCore.findModuleForFile(holder.getFile());
        if (module == null) {
            return PsiElementVisitor.EMPTY_VISITOR;
        }
        if (!LombokUtil.hasLombok(module.getProject())) {
            return PsiElementVisitor.EMPTY_VISITOR;
        }
        return new LombokElementVisitor(holder);
    }


    private static class LombokElementVisitor extends JavaElementVisitor {
        private final ProblemsHolder holder;

        LombokElementVisitor(ProblemsHolder holder) {
            this.holder = holder;
        }

        @Override
        public void visitMethod(PsiMethod method) {
            super.visitMethod(method);
            if (!method.isConstructor()) {
                return;
            }
            holder.registerProblem(method,
                    "This constructor method can be converted to lombok builder",
                    ProblemHighlightType.INFORMATION,
                    new LombokBuilderRefactoringAction());
        }
    }
}
