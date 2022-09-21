package com.zzk.idea.jsonschema.action;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiEnumConstant;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.zzk.idea.jsonschema.util.Util;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CopyJsonSchema
 * @author 张子宽
 * @date 2022/09/20
 */
public class CopyEnumDesc extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (file != null && file.getName().endsWith(".java")) {
            String enumDesc = getEnumDesc(e.getProject(), file);
            if (StringUtils.isNoneBlank(enumDesc)) {
                StringSelection content = new StringSelection(enumDesc);
                CopyPasteManager.getInstance().setContents(content);
            }
        }
    }

    private String getEnumDesc(Project project, VirtualFile file) {
        PsiFile psiFile = Util.psiFile(project, file);
        if (psiFile.getFileType()== JavaFileType.INSTANCE) {
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
        return Stream.of(psiClass.getAllFields())
                .filter(x -> x instanceof PsiEnumConstant)
                .map(x -> (PsiEnumConstant) x)
                .map(x -> x.getName() + ":" + Util.getComment(x)).collect(Collectors.joining(","));
    }
}
