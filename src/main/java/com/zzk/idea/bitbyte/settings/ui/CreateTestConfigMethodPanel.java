package com.zzk.idea.bitbyte.settings.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.table.JBTable;
import com.intellij.util.ui.FormBuilder;
import com.zzk.idea.bitbyte.constants.NamingMethod;
import com.zzk.idea.bitbyte.constants.UIMessage;
import com.zzk.idea.bitbyte.settings.AppSettingsState;
import com.zzk.idea.bitbyte.settings.CreateTestMethodConfigItem;
import com.zzk.idea.bitbyte.settings.CreateTestMethodState;
import org.jetbrains.annotations.NotNull;

/**
 * MyConfigPanelView
 *
 * @author 张子宽
 * @date 2023/07/24
 */
public class CreateTestConfigMethodPanel extends JPanel {
    private final JBTable table;
    private final DefaultTableModel tableModel;
    private final JBTextField testMethodPrefixField = new JBTextField();
    private final JComboBox<NamingMethod> testMethodNamingMethodBox = new ComboBox<>(new DefaultComboBoxModel<>(NamingMethod.values()));

    private static final String[] COLUMN_NAMES = new String[] {"Project Name", "Test Module Name", "Unit Test", "Integration Test"};

    public CreateTestConfigMethodPanel() {
        setLayout(new BorderLayout());
        tableModel = buildDefaultTableModel();
        table = buildTable(tableModel);
        add(FormBuilder.createFormBuilder()
                .addLabeledComponent(UIMessage.TEST_METHOD_NAME_PREFIX.label(), testMethodPrefixField)
                .addLabeledComponent(UIMessage.TEST_METHOD_NAMING_METHOD.label(), testMethodNamingMethodBox)
                .addComponent(new JBScrollPane(table))
                .addComponent(buildButtonPanel())
                .getPanel());
    }

    private static JBTable buildTable(DefaultTableModel tableModel) {
        JBTable table = new JBTable(tableModel);
        table.setFillsViewportHeight(true);
        // 设置表格的默认大小
        table.setPreferredScrollableViewportSize(new Dimension(500, 150));
        return table;
    }

    @NotNull
    private DefaultTableModel buildDefaultTableModel() {
        return new DefaultTableModel(getData(), COLUMN_NAMES) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    @NotNull
    private JPanel buildButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(createAddButton());
        buttonPanel.add(createRemoveButton());
        return buttonPanel;
    }

    @NotNull
    private JButton createRemoveButton() {
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            int[] selectedRows = table.getSelectedRows();
            for (int i = selectedRows.length - 1; i >= 0; i--) {
                tableModel.removeRow(selectedRows[i]);
            }
        });
        return removeButton;
    }

    @NotNull
    private JButton createAddButton() {
        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> {
            // 显示添加项目和模块的对话框
            CreateTestMethodAddItemDialog dialog = new CreateTestMethodAddItemDialog(tableModel);
            dialog.show();
        });
        return addButton;
    }

    @NotNull
    private static Object[][] getData() {
        CreateTestMethodState createTestMethodState = AppSettingsState.getInstance().getCreateTestMethodState();
        return createTestMethodState.getItems()
                .stream().map(item -> new Object[] {item.getProjectName(), item.getTestModuleName()})
                .toArray(Object[][]::new);
    }

    public static Object[][] getTableData(DefaultTableModel tableModel) {
        Object[][] data = new Object[tableModel.getRowCount()][tableModel.getColumnCount()];
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            for (int j = 0; j < tableModel.getColumnCount(); j++) {
                data[i][j] = tableModel.getValueAt(i, j);
            }
        }
        return data;
    }

    public void setCreateTestMethodState(CreateTestMethodState createTestMethodState) {
        Object[][] data = createTestMethodState.getItems().stream().map(x -> new Object[] {
                x.getProjectName(),
                x.getTestModuleName(),
                x.getEnableUnitTest(),
                x.getEnableIntegrationTest()
        }).toArray(Object[][]::new);
        tableModel.setDataVector(data, COLUMN_NAMES);
        testMethodPrefixField.setText(createTestMethodState.getTestMethodNamePreFix());
        testMethodNamingMethodBox.setSelectedItem(createTestMethodState.getTestMethodNamingMethod());
    }

    public List<CreateTestMethodConfigItem> getItems() {
        return Arrays.stream(getTableData(tableModel))
                .map(CreateTestConfigMethodPanel::toCreateTestMethodConfigItem)
                .collect(Collectors.toList());
    }


    public CreateTestMethodState getCreateTestMethodState() {
        CreateTestMethodState createTestMethodState = new CreateTestMethodState();
        createTestMethodState.setItems(getItems());
        createTestMethodState.setTestMethodNamePreFix(testMethodPrefixField.getText());
        createTestMethodState.setTestMethodNamingMethod((NamingMethod) testMethodNamingMethodBox.getSelectedItem());
        return createTestMethodState;
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