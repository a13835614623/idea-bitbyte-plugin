package com.zzk.idea.bitbyte.action.test;

import com.intellij.psi.PsiMethod;
import com.intellij.testIntegration.TestFramework;
import com.zzk.idea.bitbyte.constants.TestActionType;

public class CreateIntegrationTestMethodAction extends CreateTestMethodAction {

    public CreateIntegrationTestMethodAction(PsiMethod psiMethod, TestFramework testFramework) {
        super(psiMethod, testFramework, TestActionType.INTEGRATION_TEST);
    }
}
