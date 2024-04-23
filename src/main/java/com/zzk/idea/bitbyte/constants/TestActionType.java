package com.zzk.idea.bitbyte.constants;

/**
 * TestActionType
 *
 * @author 张子宽
 * @date 2023/07/25
 */
public enum TestActionType {

    UNIT_TEST("Unit Test", "UnitTest", "BaseUnitTest"),

    INTEGRATION_TEST("Integration Test", "IntegrationTest", "BaseIntegrationTest");

    private final String text;

    private final String defaultSuffix;

    private final String defaultSupperClassName;

    TestActionType(String text, String defaultSuffix, String defaultSupperClassName) {
        this.text = text;
        this.defaultSuffix = defaultSuffix;
        this.defaultSupperClassName = defaultSupperClassName;
    }

    public String getText() {
        return text;
    }

    public String getDefaultSupperClassName() {
        return defaultSupperClassName;
    }

    public String getCreateText() {
        return String.format("Create %s", text);
    }

    public String getDefaultSuffix() {
        return defaultSuffix;
    }
}
