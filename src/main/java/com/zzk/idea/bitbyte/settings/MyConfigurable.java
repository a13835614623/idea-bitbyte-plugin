package com.zzk.idea.bitbyte.settings;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;

import com.intellij.openapi.options.Configurable;
import com.zzk.idea.bitbyte.settings.ui.CreateTestConfigMethodPanel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

public class MyConfigurable implements Configurable {
    private CreateTestConfigMethodPanel configPanel;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "My Plugin";
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        configPanel = new CreateTestConfigMethodPanel();
        return configPanel;
    }

    @Override
    public boolean isModified() {
        List<CreateTestMethodConfigItem> items = AppSettingsState.getInstance().getCreateTestMethodState().getItems();
        Object[][] data = configPanel.getTableData();

        if (data.length != items.size()) {
            return true;
        }

        for (int i = 0; i < data.length; i++) {
            CreateTestMethodConfigItem item = items.get(i);
            if (!data[i][0].equals(item.getProjectName()) || !data[i][1].equals(item.getModuleName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void apply() {
        Object[][] data = configPanel.getTableData();
        List<CreateTestMethodConfigItem> items = new ArrayList<>();
        for (Object[] row : data) {
            CreateTestMethodConfigItem item = new CreateTestMethodConfigItem((String) row[0], (String) row[1]);
            items.add(item);
        }
        AppSettingsState.getInstance().getCreateTestMethodState().setItems(items);
    }

    @Override
    public void reset() {
        List<CreateTestMethodConfigItem> items = AppSettingsState.getInstance().getCreateTestMethodState().getItems();
        Object[][] data = items.stream().map(item -> new Object[] {item.getProjectName(), item.getModuleName()})
                .toArray(Object[][]::new);
        configPanel.setTableData(data);
    }
}