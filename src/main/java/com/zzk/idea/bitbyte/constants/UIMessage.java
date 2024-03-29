package com.zzk.idea.bitbyte.constants;

import com.intellij.ui.components.JBLabel;

/**
 * UILabel
 *
 * @author 张子宽
 * @date 2023/02/22
 */
public enum UIMessage {

    COPY_SETTING("Copy Setting"),
    ENUM_DESCRIPTION_SEPARATOR("Enum description separator"),
    ENUM_PARAMETER_SEPARATOR("Enum parameter separator"),
    PARAM("Param"),
    CREATE_TEST_METHOD("Create test method"),
    TEST_METHOD_NAMING_METHOD("Test method naming method"),
    TEST_METHOD_NAME_PREFIX("Test method prefix"),

    ;


    private final String text;

    UIMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


    public JBLabel label(){
        return new JBLabel(text);
    }
}
