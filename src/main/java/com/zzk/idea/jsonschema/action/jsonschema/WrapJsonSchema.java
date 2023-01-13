package com.zzk.idea.jsonschema.action.jsonschema;

import com.zzk.idea.jsonschema.constants.SchemaType;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * WrapJsonSchema
 *
 * @author 张子宽
 * @date 2022/09/20
 */
public class WrapJsonSchema {
    /**
     * 类型
     */
    private SchemaType type;
    /**
     * 属性
     */
    private Map<String, WrapJsonSchema> properties;
    /**
     * 数组元素
     */
    private WrapJsonSchema items;

    /**
     * 描述
     */
    private String description;

    /**
     * 中文名
     */
    private String title;

    public SchemaType getType() {
        return type;
    }

    public WrapJsonSchema setType(SchemaType type) {
        this.type = type;
        return this;
    }

    public Map<String, WrapJsonSchema> getProperties() {
        return properties;
    }

    public WrapJsonSchema setProperties(Map<String, WrapJsonSchema> properties) {
        this.properties = properties;
        return this;
    }

    public WrapJsonSchema getItems() {
        return items;
    }

    public WrapJsonSchema setItems(WrapJsonSchema items) {
        this.items = items;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WrapJsonSchema setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public WrapJsonSchema setTitle(String title) {
        this.title = title;
        return this;
    }

    public static WrapJsonSchema build(Schema schema) {
        WrapJsonSchema wrapJsonSchema = new WrapJsonSchema();
        if (schema.getItems() != null) {
            wrapJsonSchema.setItems(build(schema.getItems()));
        }
        wrapJsonSchema.setDescription(schema.getDescription());
        wrapJsonSchema.setTitle(schema.getTitle());
        List<Property> schemaProperties = schema.getProperties();
        if (schemaProperties != null) {
            Map<String,WrapJsonSchema> schemaMap = new LinkedHashMap<>(schemaProperties.size());
            for (Property schemaProperty : schemaProperties) {
                schemaMap.put(schemaProperty.getName(),build(schemaProperty.getSchema()));
            }
            wrapJsonSchema.setProperties(schemaMap);
        }
        wrapJsonSchema.setType(schema.getType());
        return wrapJsonSchema;
    }
}
