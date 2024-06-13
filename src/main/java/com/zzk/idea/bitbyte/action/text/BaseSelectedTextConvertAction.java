package com.zzk.idea.bitbyte.action.text;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.codeInspection.util.IntentionFamilyName;
import com.intellij.codeInspection.util.IntentionName;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import com.zzk.idea.bitbyte.constants.Message;
import com.zzk.idea.bitbyte.constants.StringConvertFunction;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public abstract class BaseSelectedTextConvertAction extends PsiElementBaseIntentionAction {


    private final StringConvertFunction stringConvertFunction;

    public BaseSelectedTextConvertAction(StringConvertFunction stringConvertFunction) {
        this.stringConvertFunction = stringConvertFunction;
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement element) {
        return StringUtils.isNoneBlank(editor.getSelectionModel().getSelectedText());
    }

    @Override
    public @NotNull @IntentionFamilyName String getFamilyName() {
        return Message.CONVERT_SELECTED_TEXT.message();
    }

    @Override
    public @IntentionName @NotNull String getText() {
        return stringConvertFunction.getText();
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement element) throws IncorrectOperationException {
        SelectionModel selectionModel = editor.getSelectionModel();
        String selectedText = selectionModel.getSelectedText();
        if (StringUtils.isEmpty(selectedText)) {
            return;
        }
        String replaceString = stringConvertFunction.apply(selectedText);
        editor.getDocument()
                .replaceString(selectionModel.getSelectionStart(), selectionModel.getSelectionEnd(), replaceString);

    }


}
