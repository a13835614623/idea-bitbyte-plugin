package com.zzk.idea;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.zzk.idea.jsonschema.Schema;
import com.zzk.idea.jsonschema.adapter.JsonSchemaAdapter;
import com.zzk.idea.jsonschema.adapter.JsonSchemaAdapterFactory;
import com.zzk.idea.jsonschema.adapter.PsiClassSchemaAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.datatransfer.StringSelection;
import java.io.IOException;

/**
 * CopyJsonSchema
 * @author 张子宽
 * @date 2022/09/20
 */
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
            JsonSchemaAdapter<PsiClass> psiClassSchemaAdapter = JsonSchemaAdapterFactory.get(PsiClass.class);
            PsiFile psiFile = Util.psiFile(project, file);
            if (psiFile.getFileType()== JavaFileType.INSTANCE) {
                PsiJavaFileImpl psiJavaFile = (PsiJavaFileImpl) psiFile;
                PsiClass[] classes = psiJavaFile.getClasses();
                for (PsiClass aClass : classes) {
                    Schema schema = psiClassSchemaAdapter.getSchema(aClass);
                    System.out.println(schema);
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
