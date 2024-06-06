package com.zzk.idea.bitbyte.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFactory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiImportList;
import com.intellij.psi.PsiImportStatement;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.impl.source.PsiJavaFileImpl;
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public static void addImport(PsiClass testClass, PsiElementFactory factory, List<PsiClass> mockAnnotationClass) {
        PsiFile containingFile = testClass.getContainingFile();
        if (!(containingFile instanceof PsiJavaFile)) {
            return;
        }
        PsiImportList importList = ((PsiJavaFile) containingFile).getImportList();
        PsiImportStatement[] importStatements = importList.getImportStatements();
        Set<String> existImports = Arrays.stream(importStatements)
                .map(PsiImportStatement::getQualifiedName)
                .collect(Collectors.toSet());
        List<PsiClass> needImportClasses = mockAnnotationClass.stream()
                .filter(x -> !existImports.contains(x.getQualifiedName()))
                .collect(Collectors.toList());
        for (PsiClass needImportClass : needImportClasses) {
            PsiImportStatement importStatement = factory.createImportStatement(needImportClass);
            importList.addBefore(importStatement, Arrays.stream(importStatements).findFirst().orElse(null));
        }
    }

    public static void addImport(PsiClass testClass, Project project, List<PsiClass> mockAnnotationClass) {
        addImport(testClass, PsiElementFactory.getInstance(project), mockAnnotationClass);
    }


    @Nullable
    public static PsiClass findClass(Project project, String fqName) {
        GlobalSearchScope scope = GlobalSearchScope.allScope(project);
        return JavaPsiFacade.getInstance(project).findClass(fqName, scope);
    }


    public static void addAnnotation(@NotNull PsiClass targetElement, PsiClass lombokBuilderClass) {
        final PsiAnnotation newPsiAnnotation = createPsiAnnotation(targetElement, lombokBuilderClass.getName());
        addAnnotation(targetElement, newPsiAnnotation, lombokBuilderClass.getQualifiedName());
    }

    @NotNull
    public static PsiAnnotation createPsiAnnotation(@NotNull PsiModifierListOwner psiModifierListOwner, String annotationClassName) {
        return createPsiAnnotation(psiModifierListOwner, "", annotationClassName);
    }

    @NotNull
    public static PsiAnnotation createPsiAnnotation(@NotNull PsiModifierListOwner psiModifierListOwner,
            String value,
            String annotationClassName) {
        final PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiModifierListOwner.getProject());
        final PsiClass psiClass = PsiTreeUtil.getParentOfType(psiModifierListOwner, PsiClass.class);
        final String valueString = StringUtil.isNotEmpty(value) ? "(" + value + ")" : "";
        return elementFactory.createAnnotationFromText("@" + annotationClassName + valueString, psiClass);
    }

    private static void addAnnotation(@NotNull PsiModifierListOwner targetElement,
            @NotNull PsiAnnotation newPsiAnnotation,
            String annotationClassName) {
        final PsiAnnotation presentAnnotation = findAnnotation(targetElement, annotationClassName);
        if (null != presentAnnotation) {
            return;
        }
        PsiModifierList modifierList = targetElement.getModifierList();
        if (null != modifierList) {
            final Project project = targetElement.getProject();
            final JavaCodeStyleManager javaCodeStyleManager = JavaCodeStyleManager.getInstance(project);
            javaCodeStyleManager.shortenClassReferences(newPsiAnnotation);
            modifierList.addAfter(newPsiAnnotation, null);
        }
    }

    public static PsiAnnotation findAnnotation(@NotNull PsiModifierListOwner psiModifierListOwner, @NotNull String annotationFQN) {
        return psiModifierListOwner.getAnnotation(annotationFQN);
    }
}
