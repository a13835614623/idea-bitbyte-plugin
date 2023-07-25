package com.zzk.idea.bitbyte.action.linemarker;

import java.util.Optional;

import com.intellij.codeInsight.daemon.GutterName;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiIdentifier;
import com.intellij.psi.PsiMethod;
import com.intellij.testIntegration.TestFramework;
import com.intellij.testIntegration.TestIntegrationUtils;
import com.intellij.util.FunctionUtil;
import com.zzk.idea.bitbyte.action.test.CreateIntegrationTestMethodAction;
import com.zzk.idea.bitbyte.action.test.CreateUnitTestMethodAction;
import com.zzk.idea.bitbyte.constants.Message;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * CreateTestLineMarkerProvider
 *
 * @author 张子宽
 * @date 2023/07/25
 */
public class CreateTestLineMarkerProvider extends LineMarkerProviderDescriptor {

    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
        // This must be an element with a literal expression as a parent
        PsiElement parent = element.getParent();
        if (!(element instanceof PsiIdentifier) || !(parent instanceof PsiMethod)) {
            return null;
        }
        if (TestIntegrationUtils.isTest(element)) {
            return null;
        }
        Optional<TestFramework> testFramework = TestFramework.EXTENSION_NAME.extensions()
                .filter(x -> "JUnit5".equalsIgnoreCase(x.getName()))
                .findFirst();
        if (testFramework.isEmpty()) {
            return null;
        }
        TestFramework framework = testFramework.get();
        PsiMethod method = (PsiMethod) parent;

        DefaultActionGroup myActionGroup = new DefaultActionGroup(
                new CreateUnitTestMethodAction(method, framework),
                new CreateIntegrationTestMethodAction(method, framework)
        );
        return new ToTestMarkerInfo(element, framework, myActionGroup);
    }


    @Override
    public @Nullable("null means disabled") @GutterName String getName() {
        return Message.GENERATE_TEST_METHOD.message();
    }

    private static final class ToTestMarkerInfo extends LineMarkerInfo<PsiElement> {

        private final DefaultActionGroup myActionGroup;

        private ToTestMarkerInfo(@NotNull PsiElement psiElement, TestFramework testFramework, DefaultActionGroup myActionGroup) {
            super(psiElement, psiElement.getTextRange(), testFramework.getIcon(),
                    FunctionUtil.constant(Message.GENERATE_TEST_METHOD.message()),
                    (e, elt) -> {
                    },
                    GutterIconRenderer.Alignment.RIGHT);
            this.myActionGroup = myActionGroup;
        }

        @Override
        public GutterIconRenderer createGutterRenderer() {
            if (myIcon == null) {
                return null;
            }
            return new LineMarkerGutterIconRenderer<>(this) {
                @Override
                public AnAction getClickAction() {
                    return null;
                }


                @Override
                public ActionGroup getPopupMenuActions() {
                    return myActionGroup;
                }
            };
        }
    }

}