package com.zzk.idea.bitbyte.settings.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.table.JBTable;
import com.zzk.idea.bitbyte.settings.AppSettingsState;
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

    public CreateTestConfigMethodPanel() {
        setLayout(new BorderLayout());

        String[] columnNames = {"Project Name", "Module Name"};

        CreateTestMethodState createTestMethodState = AppSettingsState.getInstance().getCreateTestMethodState();
        Object[][] data = createTestMethodState.getItems()
                .stream().map(item -> new Object[] {item.getProjectName(), item.getModuleName()})
                .toArray(Object[][]::new);

        tableModel = new DefaultTableModel(data, columnNames);
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
        tableModel.setDataVector(data, new Object[] {"Project", "Module"});
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
}