package com.zzk.idea.bitbyte.constants;

/**
 * TestActionType
 *
 * @author 张子宽
 * @date 2023/07/25
 */
public enum TestActionType {

    UNIT_TEST("Unit Test", "UnitTest"),

    INTEGRATION_TEST("Integration Test", "IntegrationTest");

    private final String text;

    private final String defaultSuffix;

    TestActionType(String text, String defaultSuffix) {
        this.text = text;
        this.defaultSuffix = defaultSuffix;
    }

    public String getText() {
        return text;
    }

    public String getCreateText() {
        return String.format("Create %s", text);
    }

    public String getDefaultSuffix() {
        return defaultSuffix;
    }
}
