package com.zzk.idea.jsonschema.adapter;

import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.util.PsiTypesUtil;
import com.zzk.idea.jsonschema.action.CopyEnumDesc;
import com.zzk.idea.jsonschema.util.Util;
import com.zzk.idea.jsonschema.action.jsonschema.Property;
import com.zzk.idea.jsonschema.action.jsonschema.Schema;
import com.zzk.idea.jsonschema.constants.SchemaType;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class PsiClassSchemaAdapter extends BaseJsonSchemaAdapter<PsiClass> {


    @Override
    public Schema getSchema(PsiClass psiClass) {
        if (psiClass == null) {
            return null;
        }
        if (psiClass.isEnum()) {
            PsiDocComment docComment = psiClass.getDocComment();
            String comment = Util.getComment(docComment);
            if (StringUtils.isEmpty(comment)) {
                comment = psiClass.getName();
            }
            String enumDesc = CopyEnumDesc.getEnumDesc(psiClass);
            return new Schema().setType(SchemaType.STRING).setDescription(enumDesc)
                    .setTitle(comment).setProperties(null);
        }
        PsiClassType classType = PsiTypesUtil.getClassType(psiClass);
        SchemaType type = SchemaType.parse(classType);
        JsonSchemaAdapter<PsiField> fieldSchemaAdapter = JsonSchemaAdapterFactory.get(PsiField.class);
        if (type == SchemaType.OBJECT) {
            PsiField[] allFields = psiClass.getAllFields();
            List<Property> properties = Arrays.stream(allFields)
                    .filter(field -> {
                        PsiModifierList modifierList = field.getModifierList();
                        return modifierList == null
                                || !modifierList.hasModifierProperty(PsiModifier.STATIC)
                                && !modifierList.hasModifierProperty(PsiModifier.TRANSIENT);
                    })
                    .map(field -> new Property()
                            .setName(field.getName())
                            .setSchema(fieldSchemaAdapter.getSchema(field)))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            return new Schema().setProperties(properties).setType(type);
        }
        String desc = type.getDesc();
        return new Schema().setType(type).setTitle(desc).setDescription(desc);
    }

    @Override
    public Class<PsiClass> clazz() {
        return PsiClass.class;
    }
}
