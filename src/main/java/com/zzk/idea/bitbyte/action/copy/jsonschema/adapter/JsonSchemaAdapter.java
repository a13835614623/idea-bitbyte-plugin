package com.zzk.idea.bitbyte.action.copy.jsonschema.adapter;

import com.zzk.idea.bitbyte.action.copy.jsonschema.Schema;

public interface JsonSchemaAdapter<T> {


    Schema getSchema(T t);

    Class<T> clazz();
}
