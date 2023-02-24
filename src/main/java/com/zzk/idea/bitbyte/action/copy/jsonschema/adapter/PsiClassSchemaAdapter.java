package com.zzk.idea.bitbyte.action.copy.jsonschema.adapter;

import com.intellij.psi.*;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.psi.util.PsiTypesUtil;
import com.zzk.idea.bitbyte.action.copy.enumdesc.CopyEnumDescAction;
import com.zzk.idea.bitbyte.util.PsiUtil;
import com.zzk.idea.bitbyte.action.copy.jsonschema.Property;
import com.zzk.idea.bitbyte.action.copy.jsonschema.Schema;
import com.zzk.idea.bitbyte.constants.SchemaType;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PsiClassSchemaAdapter extends BaseJsonSchemaAdapter<PsiClass> {


    @Override
    public Schema getSchema(PsiClass psiClass) {
        if (psiClass == null) {
            return null;
        }
        if (psiClass.isEnum()) {
            PsiDocComment docComment = psiClass.getDocComment();
            String comment = PsiUtil.getComment(docComment);
            if (StringUtils.isEmpty(comment)) {
                comment = psiClass.getName();
            }
            String enumDesc = CopyEnumDescAction.getEnumDesc(psiClass);
            List<String> enumValues = Stream.of(psiClass.getAllFields())
                    .filter(x -> x instanceof PsiEnumConstant)
                    .map(x -> (PsiEnumConstant) x)
                    .map(PsiField::getName)
                    .collect(Collectors.toList());
            return new Schema().setType(SchemaType.STRING)
                    .setDescription(enumDesc)
                    .setTitle(comment)
                    .setEnumValues(enumValues)
                    .setProperties(null);
        }
        PsiClassType classType = PsiTypesUtil.getClassType(psiClass);
        SchemaType type = SchemaType.parse(classType);
        JsonSchemaAdapter<PsiField> fieldSchemaAdapter = JsonSchemaAdapterFactory.getRequire(PsiField.class);
        if (type == SchemaType.OBJECT) {
            PsiField[] allFields = psiClass.getAllFields();
            List<Property> properties = Arrays.stream(allFields)
                    .filter(PsiClassSchemaAdapter::isNeedGenerateSchema)
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

    private static boolean isNeedGenerateSchema(PsiField field) {
        PsiModifierList modifierList = field.getModifierList();
        return modifierList == null
                || !modifierList.hasModifierProperty(PsiModifier.STATIC)
                && !modifierList.hasModifierProperty(PsiModifier.TRANSIENT);
    }

    @Override
    public Class<PsiClass> clazz() {
        return PsiClass.class;
    }
}
