package com.zzk.idea.jsonschema.action.copy.enumdesc;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiEnumConstant;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.zzk.idea.jsonschema.action.copy.enumdesc.CopyEnumState;
import com.zzk.idea.jsonschema.settings.AppSettingsState;
import com.zzk.idea.jsonschema.util.CopyUtil;
import com.zzk.idea.jsonschema.util.PsiUtil;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if (file != null && file.getName().endsWith(".java")) {
            String enumDesc = getEnumDesc(e.getProject(), file);
            CopyUtil.setClipBoardContent(enumDesc);
        }
    }

    private String getEnumDesc(Project project, VirtualFile file) {
        PsiFile psiFile = PsiUtil.psiFile(project, file);
        if (psiFile.getFileType() == JavaFileType.INSTANCE) {
            PsiJavaFileImpl psiJavaFile = (PsiJavaFileImpl) psiFile;
            PsiClass[] classes = psiJavaFile.getClasses();
            for (PsiClass aClass : classes) {
                return getEnumDesc(aClass);
            }
        }
        return null;
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
