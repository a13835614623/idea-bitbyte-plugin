package com.zzk.idea.bitbyte.util;

import java.util.Optional;

import com.intellij.execution.junit.JUnitTestFramework;
import com.intellij.lang.Language;
import com.intellij.psi.PsiClass;
import com.intellij.testIntegration.TestFramework;

public class TestFrameworkUtil {

    public static final String JUNIT_5 = "JUnit5";

    public static Optional<TestFramework> getJunit5TestFramework(PsiClass containingClass) {
        return TestFramework.EXTENSION_NAME.getExtensionList()
                .stream()
                .filter(x -> x instanceof JUnitTestFramework)
                .filter(x -> JUNIT_5.equalsIgnoreCase(x.getName()))
                .filter(x -> x.getLanguage().isKindOf(Language.ANY))
                .findFirst();
    }
}
