package com.zzk.idea.bitbyte.action.test;


import java.util.List;

import com.intellij.psi.PsiClass;
import com.intellij.testIntegration.TestFramework;
import com.zzk.idea.bitbyte.constants.TestActionType;

public class CreateIntegrationTestClassAction extends BaseCreateTestAction {

    public CreateIntegrationTestClassAction(PsiClass psiClass, TestFramework testFramework) {
        super(psiClass, List.of(), testFramework, TestActionType.INTEGRATION_TEST);
    }
}
