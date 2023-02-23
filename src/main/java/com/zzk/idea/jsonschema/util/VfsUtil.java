package com.zzk.idea.jsonschema.util;

import com.intellij.openapi.vfs.VirtualFile;

public class VfsUtil {

    private static final String JAVA_FILE_EXT_NAME = ".java";

    public static boolean isJavaFile(VirtualFile file) {
        return file != null && file.getName().endsWith(JAVA_FILE_EXT_NAME);
    }
}
