package com.zzk.idea.bitbyte.settings;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class CreateTestMethodState {

    private List<CreateTestMethodConfigItem> items = new ArrayList<>();

    public static CreateTestMethodState defaultVal() {
        return new CreateTestMethodState();
    }

}
