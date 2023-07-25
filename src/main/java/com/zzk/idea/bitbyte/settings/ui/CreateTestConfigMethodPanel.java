package com.zzk.idea.bitbyte.settings.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.zzk.idea.bitbyte.settings.AppSettingsState;
import com.zzk.idea.bitbyte.settings.CreateTestMethodConfigItem;
import com.zzk.idea.bitbyte.settings.CreateTestMethodState;

/**
 * MyConfigPanelView
 *
 * @author 张子宽
 * @date 2023/07/24
 */
public class CreateTestConfigMethodPanel extends JPanel {
    private JBTable table;
    private final DefaultTableModel tableModel;

    private static final String[] COLUMN_NAMES = new String[] {"Project Name", "Test Module Name", "Unit Test", "Integration Test"};

    public CreateTestConfigMethodPanel() {
        setLayout(new BorderLayout());

        CreateTestMethodState createTestMethodState = AppSettingsState.getInstance().getCreateTestMethodState();
        Object[][] data = createTestMethodState.getItems()
                .stream().map(item -> new Object[] {item.getProjectName(), item.getTestModuleName()})
                .toArray(Object[][]::new);

        tableModel = new DefaultTableModel(data, COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JBTable(tableModel);
        table.setFillsViewportHeight(true);
        // 设置表格的默认大小
        table.setPreferredScrollableViewportSize(new Dimension(500, 150));
        JScrollPane scrollPane = new JBScrollPane(table);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            // 显示添加项目和模块的对话框
            CreateTestMethodAddItemDialog dialog = new CreateTestMethodAddItemDialog(tableModel);
            dialog.show();
        });

        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            int[] selectedRows = table.getSelectedRows();
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                tableModel.removeRow(selectedRows[i]);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setTableData(Object[][] data) {
        tableModel.setDataVector(data, COLUMN_NAMES);
    }

    public Object[][] getTableData() {
        Object[][] data = new Object[tableModel.getRowCount()][tableModel.getColumnCount()];
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                data[i][j] = tableModel.getValueAt(i, j);
            }
        }
        return data;
    }

    public void setItems(List<CreateTestMethodConfigItem> items) {
        Object[][] data = items.stream().map(x -> new Object[] {
                x.getProjectName(),
                x.getTestModuleName(),
                x.getEnableUnitTest(),
                x.getEnableIntegrationTest()
        }).toArray(Object[][]::new);
        setTableData(data);
    }

    public List<CreateTestMethodConfigItem> getItems() {
        return Arrays.stream(getTableData())
                .map(CreateTestConfigMethodPanel::toCreateTestMethodConfigItem)
                .collect(Collectors.toList());
    }


    public static CreateTestMethodConfigItem toCreateTestMethodConfigItem(Object[] row) {
        CreateTestMethodConfigItem item = new CreateTestMethodConfigItem();
        item.setProjectName((String) row[0]);
        item.setTestModuleName((String) row[1]);
        item.setEnableUnitTest((Boolean) row[2]);
        item.setEnableIntegrationTest((Boolean) row[3]);
        return item;
    }
}