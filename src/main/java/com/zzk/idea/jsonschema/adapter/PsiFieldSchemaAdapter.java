package com.zzk.idea.jsonschema.adapter;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.util.PsiTypesUtil;
import com.zzk.idea.Util;
import com.zzk.idea.jsonschema.Property;
import com.zzk.idea.jsonschema.Schema;

import java.util.Optional;

public class PsiFieldSchemaAdapter extends BaseJsonSchemaAdapter<PsiField> {


    @Override
    public Schema getSchema(PsiField field) {
        JsonSchemaAdapter<PsiClass> psiClassJsonSchemaAdapter = JsonSchemaAdapterFactory.get(PsiClass.class);
        PsiType type = field.getType();
        PsiDocComment docComment = field.getDocComment();
        PsiClass fieldClass = PsiTypesUtil.getPsiClass(type);
        String title = Util.getComment(docComment);
        Schema schema = psiClassJsonSchemaAdapter.getSchema(fieldClass);
        return new Schema().setTitle(title)
                .setProperties(Optional.ofNullable(schema).map(Schema::getProperties).orElse(null));    }

    @Override
    public Class<PsiField> clazz() {
        return PsiField.class;
    }
}
