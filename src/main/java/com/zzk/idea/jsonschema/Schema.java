package com.zzk.idea.jsonschema;


import java.util.List;

/**
 * Schema对象
 * @author 张子宽
 * @date 2022/08/14
 */
public class Schema {
    /**
     * 类型
     */
    private Type type;
    /**
     * 属性列表
     */
    private List<Property> properties;
    /**
     * 描述
     */
    private String description;

    /**
     * 中文名
     */
    private String title;

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

    public Type getType() {
        return type;
    }

    public Schema setType(Type type) {
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
        return "Schema{" +
                "type=" + type +
                ", properties=" + properties +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
