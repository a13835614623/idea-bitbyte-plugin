package com.zzk.idea.bitbyte.action.copy.jsonschema;


import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.zzk.idea.bitbyte.constants.SchemaType;
import com.zzk.idea.bitbyte.util.JsonUtil;
import org.jetbrains.annotations.NotNull;

/**
 * Schema对象
 *
 * @author 张子宽
 * @date 2022/08/14
 */
public class Schema {
    /**
     * 类型
     */
    private SchemaType type;
    /**
     * 属性列表
     */
    private List<Property> properties;
    /**
     * 数组的schema
     */
    private Schema items;
    /**
     * 描述
     */
    private String description;
    /**
     * 中文名
     */
    private String title;

    private List<String> enumValues;

    public Schema getItems() {
        return items;
    }

    public Schema setItems(Schema items) {
        this.items = items;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Schema setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Schema setTitle(String title) {
        this.title = title;
        return this;
    }

    public List<String> getEnumValues() {
        return enumValues;
    }

    public Schema setEnumValues(List<String> enumValues) {
        this.enumValues = enumValues;
        return this;
    }

    public SchemaType getType() {
        return type;
    }

    public Schema setType(SchemaType type) {
        this.type = type;
        return this;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public Schema setProperties(List<Property> properties) {
        this.properties = properties;
        return this;
    }


    @Override
    public String toString() {
        return JsonUtil.toJson(this);
    }


    public Object defaultValue() {
        return switch (type) {
            case ARRAY -> defaultArray();
            case OBJECT -> defaultObject();
            case STRING -> defaultString();
            default -> type.getDefaultVal();
        };
    }

    @NotNull
    private List<Object> defaultArray() {
        return List.of(items.defaultValue());
    }

    private String defaultString() {
        return Optional.ofNullable(enumValues)
                .map(Collection::stream)
                .flatMap(Stream::findFirst)
                .orElse((String) type.getDefaultVal());
    }

    @NotNull
    private Map<String, Object> defaultObject() {
        return properties.stream()
                .map(p -> Map.entry(p.getName(), p.getSchema().defaultValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public WrapJsonSchema wrap() {
        return WrapJsonSchema.build(this);
    }
}
