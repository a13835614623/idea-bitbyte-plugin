package com.zzk.idea.jsonschema.adapter;

import com.google.common.collect.Maps;

import java.util.Map;

public class JsonSchemaAdapterFactory {

    private static final Map<Class<?>, JsonSchemaAdapter<?>> JSON_SCHEMA_ADAPTER_HASH_MAP = Maps.newHashMap();

    static {
        register(new PsiFieldSchemaAdapter());
        register(new PsiClassSchemaAdapter());
    }

    public static <T> JsonSchemaAdapter<T> get(Class<T> tClass) {
        return (JsonSchemaAdapter<T>) JSON_SCHEMA_ADAPTER_HASH_MAP.get(tClass);
    }


    public static <T> void register(JsonSchemaAdapter<T> jsonSchemaAdapter) {
        JSON_SCHEMA_ADAPTER_HASH_MAP.put(jsonSchemaAdapter.clazz(), jsonSchemaAdapter);
    }
}
