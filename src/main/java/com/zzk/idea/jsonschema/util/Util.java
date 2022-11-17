package com.zzk.idea.jsonschema.util;

import com.alibaba.fastjson.JSON;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Util {

    public static PsiFile psiFile(Project project, VirtualFile file) {
        return PsiManager.getInstance(project).findFile(file);
    }

    /**
     * 获取注释
     */
    public static String getComment(PsiDocComment docComment) {
        String comment = "";
        if (docComment instanceof PsiDocCommentImpl) {
            PsiDocCommentImpl doc = ((PsiDocCommentImpl) docComment);
            ASTNode node = doc.findChildByType(JavaDocTokenType.DOC_COMMENT_DATA);
            comment = Optional.ofNullable(node).map(ASTNode::getText).map(String::trim).orElse("");
        }
        return comment;
    }

    /**
     * 获取注释
     */
    public static String getComment(PsiField psiField) {
        return getComment(psiField.getDocComment());
    }
}
