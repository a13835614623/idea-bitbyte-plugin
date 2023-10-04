package com.zzk.idea.bitbyte.action.test;


import java.util.List;

import com.intellij.psi.PsiClass;
import com.intellij.testIntegration.TestFramework;
import com.zzk.idea.bitbyte.constants.TestActionType;

public class CreateUnitTestClassAction extends BaseCreateTestAction {

    public CreateUnitTestClassAction(PsiClass psiClass, TestFramework testFramework) {
        super(psiClass, List.of(), testFramework, TestActionType.UNIT_TEST);
    }
}
