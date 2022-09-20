package com.zzk.idea.jsonschema.adapter;

import com.zzk.idea.jsonschema.Schema;

public interface JsonSchemaAdapter<T> {


    Schema getSchema(T t);

    Class<T> clazz();
}
