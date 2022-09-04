package com.zzk.maxcv.jsonschema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Schema对象
 * @author 张子宽
 * @date 2022/08/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schema {
    /**
     * 类型
     */
    private Type type;
    /**
     * 属性列表
     */
    private List<Property> properties;
}
