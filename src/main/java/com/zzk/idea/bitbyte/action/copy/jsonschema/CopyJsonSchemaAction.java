package com.zzk.idea.bitbyte.action.copy.jsonschema;

import java.util.Optional;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.zzk.idea.bitbyte.util.CopyUtil;
import com.zzk.idea.bitbyte.util.JsonSchemaUtil;
import com.zzk.idea.bitbyte.util.PsiUtil;
import com.zzk.idea.bitbyte.util.VfsUtil;
import org.jetbrains.annotations.NotNull;

/**
 * 复制 json Schema
 * @author 张子宽
 * @date 2022/09/20
 */
public class CopyJsonSchemaAction extends AnAction {

	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
		if (VfsUtil.isJavaFile(file)) {
			getJsonSchema(e.getProject(), file)
					.ifPresent(CopyUtil::setClipBoardContent);
		}
	}

	private Optional<String> getJsonSchema(Project project, VirtualFile file) {
		return PsiUtil.firstPsiJavaClass(project, file)
				.map(JsonSchemaUtil::getJsonSchemaJson);
	}
}
