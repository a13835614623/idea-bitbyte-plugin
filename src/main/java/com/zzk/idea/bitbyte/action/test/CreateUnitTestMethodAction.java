package com.zzk.idea.bitbyte.action.test;

import com.intellij.psi.PsiMethod;
import com.intellij.testIntegration.TestFramework;
import com.zzk.idea.bitbyte.constants.TestActionType;

public class CreateUnitTestMethodAction extends CreateTestMethodAction {

    public CreateUnitTestMethodAction(PsiMethod psiMethod, TestFramework testFramework) {
        super(psiMethod, testFramework, TestActionType.UNIT_TEST);
    }
}
