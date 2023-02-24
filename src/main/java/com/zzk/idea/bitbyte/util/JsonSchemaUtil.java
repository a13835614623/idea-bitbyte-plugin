package com.zzk.idea.bitbyte.util;

import java.util.Optional;

import com.intellij.psi.PsiClass;
import com.zzk.idea.bitbyte.action.copy.jsonschema.Schema;
import com.zzk.idea.bitbyte.action.copy.jsonschema.adapter.JsonSchemaAdapter;
import com.zzk.idea.bitbyte.action.copy.jsonschema.adapter.JsonSchemaAdapterFactory;

public class JsonSchemaUtil {

	public static Schema getJsonSchema(PsiClass psiClass) {
		JsonSchemaAdapter<PsiClass> psiClassSchemaAdapter = JsonSchemaAdapterFactory.getRequire(PsiClass.class);
		return psiClassSchemaAdapter.getSchema(psiClass);
	}

	public static String getJsonSchemaJson(PsiClass psiClass){
		return Optional.of(getJsonSchema(psiClass))
				.map(Schema::wrap)
				.map(JsonUtil::toJson)
				.orElseThrow();
	}
}
