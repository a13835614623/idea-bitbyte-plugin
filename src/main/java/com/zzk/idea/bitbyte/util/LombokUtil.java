package com.zzk.idea.bitbyte.util;

import com.intellij.openapi.project.Project;

public class LombokUtil {
    public static final String LOMBOK_BUILDER = "lombok.Builder";


    public static boolean hasLombok(Project project) {
        return PsiUtil.findClass(project, LOMBOK_BUILDER) != null;
    }
}
