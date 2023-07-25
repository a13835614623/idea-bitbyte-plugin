package com.zzk.idea.bitbyte.settings;

/**
 * CreateTestMethodConfigItem
 *
 * @author 张子宽
 * @date 2023/07/24
 */
public class CreateTestMethodConfigItem {
    private String projectName;

    private String testModuleName;

    private Boolean enableUnitTest;

    private Boolean enableIntegrationTest;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTestModuleName() {
        return testModuleName;
    }

    public CreateTestMethodConfigItem setTestModuleName(String testModuleName) {
        this.testModuleName = testModuleName;
        return this;
    }

    public Boolean getEnableUnitTest() {
        return enableUnitTest;
    }

    public CreateTestMethodConfigItem setEnableUnitTest(Boolean enableUnitTest) {
        this.enableUnitTest = enableUnitTest;
        return this;
    }

    public Boolean getEnableIntegrationTest() {
        return enableIntegrationTest;
    }

    public CreateTestMethodConfigItem setEnableIntegrationTest(Boolean enableIntegrationTest) {
        this.enableIntegrationTest = enableIntegrationTest;
        return this;
    }
}