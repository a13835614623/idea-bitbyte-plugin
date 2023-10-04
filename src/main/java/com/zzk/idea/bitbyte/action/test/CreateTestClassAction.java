package com.zzk.idea.bitbyte.action.test;


import java.util.List;

import com.intellij.psi.PsiClass;
import com.intellij.testIntegration.TestFramework;
import com.zzk.idea.bitbyte.constants.TestActionType;

public class CreateTestClassAction extends BaseCreateTestAction {

    public CreateTestClassAction(PsiClass psiClass, TestFramework testFramework, TestActionType testActionType) {
        super(psiClass, List.of(), testFramework, testActionType);
    }
}
