package com.zzk.idea.bitbyte.settings;

/**
 * CreateTestMethodConfigItem
 *
 * @author 张子宽
 * @date 2023/07/24
 */
public class CreateTestMethodConfigItem {
    private String projectName;
    private String moduleName;

    public CreateTestMethodConfigItem(String projectName, String moduleName) {
        this.projectName = projectName;
        this.moduleName = moduleName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}