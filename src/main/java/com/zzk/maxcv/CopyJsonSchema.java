package com.zzk.maxcv;

import android.annotation.SuppressLint;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.Optional;

public class CopyJsonSchema extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile file = e.getData(CommonDataKeys.VIRTUAL_FILE);
        if (file != null && file.getName().endsWith(".java")) {
            CopyPasteManager.getInstance().setContents(new StringSelection(getJsonSchema(e.getProject(),file)));
        }
    }

    private String getJsonSchema(Project project, VirtualFile file) {
        try {
            PsiFile psiFile = Util.psiFile(project, file);
            if (psiFile.getFileType()== JavaFileType.INSTANCE) {
                PsiJavaFileImpl psiJavaFile = (PsiJavaFileImpl) psiFile;
                PsiClass[] classes = psiJavaFile.getClasses();
                for (PsiClass aClass : classes) {
                    PsiField[] allFields = aClass.getAllFields();
                    for (PsiField allField : allFields) {
                        PsiDocComment docComment = allField.getDocComment();
                        if(docComment instanceof PsiDocCommentImpl doc){
                            ASTNode node = doc.findChildByType(JavaDocTokenType.DOC_COMMENT_DATA);
                            String comment = Optional.ofNullable(node).map(ASTNode::getText).map(String::trim).orElse("");
                            System.out.println(allField.getName()+" "+comment);
                        }
                    }
                }
            }
            System.out.println(psiFile);
            return new String(file.contentsToByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
