package com.zzk.idea.bitbyte.action.copy.classname;

import com.intellij.ide.actions.CopyReferenceUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.zzk.idea.bitbyte.util.CopyUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * CopyClassNameAction
 *
 * @author 张子宽
 * @date 2023/02/18
 */
public class CopyClassNameAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile virtualFile = e.getRequiredData(CommonDataKeys.VIRTUAL_FILE);
        Project project = e.getRequiredData(CommonDataKeys.PROJECT);
        String virtualFileFqn = CopyReferenceUtil.getVirtualFileFqn(virtualFile, project);
        CopyUtil.setClipBoardContent(toPackageName(virtualFileFqn));
    }

    @NotNull
    private static String toPackageName(String virtualFileFqn) {
        if (StringUtils.isEmpty(virtualFileFqn)){
            return "";
        }
        return virtualFileFqn.replace("/", ".")
                .replace(".java", "");
    }
}
