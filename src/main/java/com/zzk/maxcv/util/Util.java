package com.zzk.maxcv.util;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;

public class Util {

    public static PsiFile psiFile(Project project, VirtualFile file){
        return PsiManager.getInstance(project).findFile(file);
    }
}
