package com.zzk.idea.bitbyte.util;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import com.intellij.psi.javadoc.PsiDocComment;

import java.util.List;
import java.util.Optional;

public class PsiUtil {

    public static Optional<PsiFile> psiFile(Project project, VirtualFile file) {
        return Optional.ofNullable(PsiManager.getInstance(project).findFile(file));
    }

    public static Optional<PsiJavaFileImpl> psiJavaFile(Project project, VirtualFile file){
        return PsiUtil.psiFile(project, file)
                .filter(f -> f instanceof PsiJavaFileImpl)
                .map(f -> ((PsiJavaFileImpl) f));
    }
    public static Optional<List<PsiClass>> psiJavaClasses(Project project, VirtualFile file){
        return PsiUtil.psiJavaFile(project, file)
                .map(f-> List.of(f.getClasses()));
    }

    public static Optional<PsiClass> firstPsiJavaClass(Project project, VirtualFile file){
        return PsiUtil.psiJavaFile(project, file)
                .map(f-> List.of(f.getClasses()))
                .flatMap(x->x.stream().findFirst());
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
