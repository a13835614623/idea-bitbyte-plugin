package com.zzk.maxcv.jsonschema.adapter;

import com.zzk.maxcv.jsonschema.Schema;

public interface JsonSchemaAdapter<T> {


    Schema getSchema(T t);
}
