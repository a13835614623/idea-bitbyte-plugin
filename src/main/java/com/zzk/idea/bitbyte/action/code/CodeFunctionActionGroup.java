package com.zzk.idea.bitbyte.action.code;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.editor.Editor;

public class CodeFunctionActionGroup extends DefaultActionGroup {

	/**
	 * Given {@link CodeFunctionActionGroup} is derived from {@link com.intellij.openapi.actionSystem.ActionGroup},
	 * in this context {@code update()} determines whether the action group itself should be enabled or disabled.
	 * Requires an editor to be active in order to enable the group functionality.
	 *
	 * @param event Event received when the associated group-id menu is chosen.
	 * @see com.intellij.openapi.actionSystem.AnAction#update(AnActionEvent)
	 */
	@Override
	public void update(AnActionEvent event) {
		// Enable/disable depending on whether user is editing
		Editor editor = event.getData(CommonDataKeys.EDITOR);
		event.getPresentation().setEnabled(editor != null);
		// Take this opportunity to set an icon for the group.
	}
}
