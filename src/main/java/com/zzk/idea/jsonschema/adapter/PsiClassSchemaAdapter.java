package com.zzk.idea.jsonschema.adapter;

import com.intellij.lang.ASTNode;
import com.intellij.lang.jvm.JvmModifier;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.javadoc.PsiDocCommentImpl;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.util.PsiTypesUtil;
import com.zzk.idea.Util;
import com.zzk.idea.jsonschema.Property;
import com.zzk.idea.jsonschema.Schema;
import com.zzk.idea.jsonschema.Type;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class PsiClassSchemaAdapter extends BaseJsonSchemaAdapter<PsiClass> {


    @Override
    public Schema getSchema(PsiClass psiClass) {
        if (psiClass == null) {
            return null;
        }
        String name = psiClass.getName();
        List.of(Integer.class,Long.class,Double.class,Boolean.class,Short.class,Byte.class)
                .stream().anyMatch(x-> x.getName().equals(name));
        if (psiClass.isEnum()) {
            PsiDocComment docComment = psiClass.getDocComment();
            String comment = Util.getComment(docComment);
            if (StringUtils.isEmpty(comment)) {
                comment = psiClass.getName();
            }
            return new Schema().setDescription(comment).setTitle(comment).setProperties(null);
        }
        JsonSchemaAdapter<PsiField> fieldSchemaAdapter = JsonSchemaAdapterFactory.get(PsiField.class);
        PsiField[] allFields = psiClass.getAllFields();
        List<Property> properties = Arrays.stream(allFields)
                .filter(field -> {
                    PsiModifierList modifierList = field.getModifierList();
                    return modifierList == null
                            || !modifierList.hasModifierProperty(PsiModifier.STATIC)
                            && !modifierList.hasModifierProperty(PsiModifier.TRANSIENT);
                })
                .map(field -> new Property().setName(field.getName()).setSchema(fieldSchemaAdapter.getSchema(field)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return new Schema().setProperties(properties).setType(Type.OBJECT);
    }

    @Override
    public Class<PsiClass> clazz() {
        return PsiClass.class;
    }
}
