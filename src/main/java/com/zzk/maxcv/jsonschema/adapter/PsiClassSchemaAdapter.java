package com.zzk.maxcv.jsonschema.adapter;

import com.intellij.lang.ASTNode;
import com.intellij.psi.JavaDocTokenType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import com.intellij.psi.javadoc.PsiDocComment;
import com.zzk.maxcv.jsonschema.Property;
import com.zzk.maxcv.jsonschema.Schema;
import com.zzk.maxcv.jsonschema.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class PsiClassSchemaAdapter implements JsonSchemaAdapter<PsiClass>{
    @Override
    public Schema getSchema(PsiClass psiClass) {
        PsiField[] allFields = psiClass.getAllFields();
        ArrayList<Property> properties = Arrays.stream(allFields)
                .map(field -> {
                    String name = field.getName();
                    PsiDocComment docComment = field.getDocComment();
                    if (docComment instanceof PsiDocCommentImpl doc) {
                        ASTNode node = doc.findChildByType(JavaDocTokenType.DOC_COMMENT_DATA);
                        String comment = Optional.ofNullable(node).map(ASTNode::getText).map(String::trim).orElse("");

                        return Property.builder().name(name).schema(new Schema());
                    }
                })
                ;
        for (PsiField field : allFields) {
            PsiDocComment docComment = field.getDocComment();
            if(docComment instanceof PsiDocCommentImpl doc){
                ASTNode node = doc.findChildByType(JavaDocTokenType.DOC_COMMENT_DATA);
                String comment = Optional.ofNullable(node).map(ASTNode::getText).map(String::trim).orElse("");
                System.out.println(field.getName()+" "+comment);
            }
        }
        return Schema.builder().properties(properties).type(Type.OBJECT).build();
    }
}
