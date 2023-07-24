package com.zzk.idea.bitbyte.settings;

import java.util.ArrayList;
import java.util.List;

public class CreateTestMethodState {

    private List<CreateTestMethodConfigItem> items = new ArrayList<>();

    public static CreateTestMethodState defaultVal() {
        return new CreateTestMethodState();
    }

    public List<CreateTestMethodConfigItem> getItems() {
        return items;
    }

    public void setItems(List<CreateTestMethodConfigItem> items) {
        this.items = items;
    }
}
