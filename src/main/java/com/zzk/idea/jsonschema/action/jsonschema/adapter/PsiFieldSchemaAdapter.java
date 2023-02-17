package com.zzk.idea.jsonschema.action.jsonschema.adapter;

import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import com.intellij.psi.javadoc.PsiDocComment;
import com.zzk.idea.jsonschema.util.PsiUtil;
import com.zzk.idea.jsonschema.action.jsonschema.Schema;

public class PsiFieldSchemaAdapter extends BaseJsonSchemaAdapter<PsiField> {


    @Override
    public Schema getSchema(PsiField field) {
        PsiType type = field.getType();
        PsiDocComment docComment = field.getDocComment();
        String title = PsiUtil.getComment(docComment);
        Schema schema = JsonSchemaAdapterFactory.get(PsiType.class).getSchema(type);
        schema.setTitle(title).setDescription(schema.getDescription());
        return schema;
    }

    @Override
    public Class<PsiField> clazz() {
        return PsiField.class;
    }
}
