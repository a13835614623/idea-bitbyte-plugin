package com.zzk.idea.bitbyte.action.copy.jsonschema.adapter;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Optional;

public class JsonSchemaAdapterFactory {

    private static final Map<Class<?>, JsonSchemaAdapter<?>> JSON_SCHEMA_ADAPTER_HASH_MAP = Maps.newHashMap();

    static {
        register(new PsiFieldSchemaAdapter());
        register(new PsiClassSchemaAdapter());
        register(new PsiTypeSchemaAdapter());
    }

    public static <T> JsonSchemaAdapter<T> getRequire(Class<T> tClass) {
        assert JSON_SCHEMA_ADAPTER_HASH_MAP.containsKey(tClass);
        return (JsonSchemaAdapter<T>) JSON_SCHEMA_ADAPTER_HASH_MAP.get(tClass);
    }


    public static <T> Optional<JsonSchemaAdapter<T>> get(Class<T> tClass) {
        return Optional.ofNullable((JsonSchemaAdapter<T>) JSON_SCHEMA_ADAPTER_HASH_MAP.get(tClass));
    }

    public static <T> void register(JsonSchemaAdapter<T> jsonSchemaAdapter) {
        JSON_SCHEMA_ADAPTER_HASH_MAP.put(jsonSchemaAdapter.clazz(), jsonSchemaAdapter);
    }
}
