package com.zzk.idea.jsonschema.action.jsonschema;


import com.alibaba.fastjson.JSON;

/**
 * Property
 * @author 张子宽
 * @date 2022/08/14
 */
public class Property {
    /**
     * 属性名
     */
    private String name;
    /**
     * 模式
     */
    private Schema schema;

    public String getName() {
        return name;
    }

    public Property setName(String name) {
        this.name = name;
        return this;
    }

    public Schema getSchema() {
        return schema;
    }

    public Property setSchema(Schema schema) {
        this.schema = schema;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
