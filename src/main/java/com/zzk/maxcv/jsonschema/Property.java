package com.zzk.maxcv.jsonschema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Property
 * @author 张子宽
 * @date 2022/08/14
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Property {
    /**
     * 属性名
     */
    private String name;
    /**
     * 模式
     */
    private Schema schema;
}
