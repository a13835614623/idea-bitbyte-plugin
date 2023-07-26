package com.zzk.idea.bitbyte.settings;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CreateTestMethodConfigItem that = (CreateTestMethodConfigItem) o;

        if (!Objects.equals(projectName, that.projectName)) return false;
        if (!Objects.equals(testModuleName, that.testModuleName))
            return false;
        if (!Objects.equals(enableUnitTest, that.enableUnitTest))
            return false;
        return Objects.equals(enableIntegrationTest, that.enableIntegrationTest);
    }

    @Override
    public int hashCode() {
        int result = projectName != null ? projectName.hashCode() : 0;
        result = 31 * result + (testModuleName != null ? testModuleName.hashCode() : 0);
        result = 31 * result + (enableUnitTest != null ? enableUnitTest.hashCode() : 0);
        result = 31 * result + (enableIntegrationTest != null ? enableIntegrationTest.hashCode() : 0);
        return result;
    }
}