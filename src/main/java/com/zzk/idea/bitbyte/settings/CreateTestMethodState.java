package com.zzk.idea.bitbyte.settings;

import java.util.ArrayList;
import java.util.List;

import com.zzk.idea.bitbyte.constants.NamingMethod;
import lombok.Data;

@Data
public class CreateTestMethodState {
    /**
     * 测试方法前缀
     */
    private String testMethodNamePreFix = "test";
    /**
     * 测试方法命名方式
     */
    private NamingMethod testMethodNamingMethod = NamingMethod.PASCAL_CASE;

    private List<CreateTestMethodConfigItem> items = new ArrayList<>();

    public static CreateTestMethodState defaultVal() {
        return new CreateTestMethodState();
    }

}
