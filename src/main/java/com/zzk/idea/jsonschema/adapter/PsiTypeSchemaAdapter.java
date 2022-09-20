package com.zzk.idea.jsonschema.adapter;

import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.util.PsiTypesUtil;
import com.zzk.idea.jsonschema.Schema;
import com.zzk.idea.jsonschema.SchemaType;

import java.util.List;

public class PsiTypeSchemaAdapter extends BaseJsonSchemaAdapter<PsiType> {


    @Override
    public Schema getSchema(PsiType psiType) {
        if (psiType == null) {
            return null;
        }
        SchemaType type = SchemaType.parse(psiType);
        if (type == SchemaType.ARRAY) {
            if (psiType instanceof PsiArrayType psiArrayType) {
                PsiType componentType = psiArrayType.getComponentType();
                return new Schema().setType(SchemaType.ARRAY).setItems(getSchema(componentType));
            }
            if (psiType instanceof PsiClassReferenceType classReferenceType) {
                PsiType[] parameters = classReferenceType.getParameters();
                if (parameters.length == 1) {
                    return new Schema().setType(SchemaType.ARRAY).setItems(getSchema(parameters[0]));
                }
                return new Schema().setType(SchemaType.ARRAY).setItems(new Schema()
                        .setType(SchemaType.OBJECT)
                        .setProperties(List.of()));
            }
        }
        if (type == SchemaType.OBJECT){
            return JsonSchemaAdapterFactory.get(PsiClass.class).getSchema(PsiTypesUtil.getPsiClass(psiType));
        }
        return new Schema().setType(type);
    }

    @Override
    public Class<PsiType> clazz() {
        return PsiType.class;
    }
}
