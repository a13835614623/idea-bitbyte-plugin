package com.zzk.idea.bitbyte.action.copy.json;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import com.zzk.idea.bitbyte.action.copy.jsonschema.Schema;
import com.zzk.idea.bitbyte.util.CopyUtil;
import com.zzk.idea.bitbyte.util.JsonSchemaUtil;
import com.zzk.idea.bitbyte.util.JsonUtil;
import com.zzk.idea.bitbyte.util.PsiUtil;
import com.zzk.idea.bitbyte.util.VfsUtil;
import org.jetbrains.annotations.NotNull;

/**
 * CopyJsonAction
 * @author 张子宽
 * @date 2023/02/19
 */
public class CopyJsonWithDefaultValueAction extends AnAction {

	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
		if (!VfsUtil.isJavaFile(file)) {
			return;
		}
		PsiUtil.firstPsiJavaClass(e.getProject(), file)
				.map(JsonSchemaUtil::getJsonSchema)
				.map(Schema::defaultValue)
				.map(JsonUtil::toJson)
				.ifPresent(CopyUtil::setClipBoardContent);
	}
}
