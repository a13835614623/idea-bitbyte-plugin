package com.zzk.idea.jsonschema.action.copy.enumdesc;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiEnumConstant;
import com.zzk.idea.jsonschema.settings.AppSettingsState;
import com.zzk.idea.jsonschema.util.CopyUtil;
import com.zzk.idea.jsonschema.util.PsiUtil;
import com.zzk.idea.jsonschema.util.VfsUtil;
import org.jetbrains.annotations.NotNull;

/**
 * 复制枚举的描述
 *
 * @author 张子宽
 * @date 2022/09/20
 */
public class CopyEnumDescAction extends AnAction {


    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (VfsUtil.isJavaFile(file)) {
            getEnumDesc(e.getProject(), file)
                    .ifPresent(CopyUtil::setClipBoardContent);
        }
    }

    private Optional<String> getEnumDesc(Project project, VirtualFile file) {
        return PsiUtil.firstPsiJavaClass(project, file)
                .map(CopyEnumDescAction::getEnumDesc);
    }

    /**
     * 获取枚举描述
     */
    public static String getEnumDesc(PsiClass psiClass) {
        CopyEnumState copyEnumState = AppSettingsState.getInstance().getCopyEnumState();
        return Stream.of(psiClass.getAllFields())
                .filter(x -> x instanceof PsiEnumConstant)
                .map(x -> (PsiEnumConstant) x)
                .map(x -> copyEnumState.getParamTypes().stream()
                        .map(type -> type.getParam(x))
                        .collect(Collectors.joining(copyEnumState.getParamSplit())))
                .collect(Collectors.joining(copyEnumState.getDescSplit()));
    }
}
