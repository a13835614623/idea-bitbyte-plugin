package com.zzk.idea.bitbyte.settings;

import lombok.Data;

/**
 * CreateTestMethodConfigItem
 *
 * @author 张子宽
 * @date 2023/07/24
 */
@Data
public class CreateTestMethodConfigItem {
    /**
     * 项目名
     */
    private String projectName;

    /**
     * 测试类所在模块的名称
     */
    private String testModuleName;

    /**
     * 是否启用单元测试
     */
    private Boolean enableUnitTest;
    /**
     * 是否启用集成测试
     */
    private Boolean enableIntegrationTest;

}