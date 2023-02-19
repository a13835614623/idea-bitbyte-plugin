package com.zzk.idea.jsonschema.action.copy.jsonschema;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.twelvemonkeys.util.CollectionUtil;
import com.zzk.idea.jsonschema.constants.SchemaType;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Schema对象
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
		return JSON.toJSONString(this);
	}


	public Object defaultValue() {
		switch (type) {
		case ARRAY:
			return List.of(items.defaultValue());
		case OBJECT:
			Map<String, Object> map = new HashMap<>();
			properties.forEach(p -> {
				map.put(p.getName(), p.getSchema().defaultValue());
			});
			return map;
		case STRING:
			return Optional.ofNullable(enumValues)
					.map(Collection::stream)
					.flatMap(Stream::findFirst)
					.orElse((String) type.getDefaultVal());
		default:
			return type.getDefaultVal();
		}
	}

	public WrapJsonSchema wrap(){
		return WrapJsonSchema.build(this);
	}
}
