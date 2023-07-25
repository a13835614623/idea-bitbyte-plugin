package com.zzk.idea.bitbyte.settings.ui;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.ui.components.JBCheckBox;
import org.jetbrains.annotations.Nullable;

/**
 * CreateTestMethodAddItemDialog
 *
 * @author 张子宽
 * @date 2023/07/25
 */
public class CreateTestMethodAddItemDialog extends DialogWrapper {

    private final JTextField projectField = new JTextField();
    private final JTextField testModuleField = new JTextField();
    private final JCheckBox enableForUnitTest = new JBCheckBox("Unit test");
    private final JCheckBox enableForIntegrationTest = new JBCheckBox("Integration test");
    private final DefaultTableModel tableModel;

    public CreateTestMethodAddItemDialog(DefaultTableModel tableModel) {
        super(true);
        init();
        this.tableModel = tableModel;
        setTitle("Add Item");
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JLabel projectLabel = new JLabel("Project:");
        JLabel moduleLabel = new JLabel("Test Module:");

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(projectLabel);
        panel.add(projectField);
        panel.add(moduleLabel);
        panel.add(testModuleField);
        panel.add(enableForUnitTest);
        panel.add(enableForIntegrationTest);
        return panel;
    }

    @Override
    protected void doOKAction() {
        super.doOKAction();
        tableModel.addRow(new Object[] {getProject(), getTestModule(), enableForUnitTest.isSelected(), enableForIntegrationTest.isSelected()});
    }

    public String getProject() {
        return projectField.getText();
    }

    public String getTestModule() {
        return testModuleField.getText();
    }

    public boolean enableForUnitTest() {
        return enableForUnitTest.isSelected();
    }

    public boolean enableForIntegrationTest() {
        return enableForIntegrationTest.isSelected();
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (getProject().isEmpty()) {
            return new ValidationInfo("Please enter project", projectField);
        }
        if (getTestModule().isEmpty()) {
            return new ValidationInfo("Please enter module", testModuleField);
        }
        return null;
    }

}