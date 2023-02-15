package com.zzk.idea.jsonschema.settings;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.panels.HorizontalBox;
import com.intellij.util.ui.FormBuilder;
import com.zzk.idea.jsonschema.constants.EnumParamType;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

import java.util.List;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

	private final JPanel myMainPanel;
	private final JBTextField enumDescSplitText = new JBTextField();
	private final JBTextField enumParamSplitText = new JBTextField();
	private final JComboBox<EnumParamType> paramTypeJComboBox1 = new ComboBox<>(new DefaultComboBoxModel<>(EnumParamType.values()));
	private final JComboBox<EnumParamType> paramTypeJComboBox2 = new ComboBox<>(new DefaultComboBoxModel<>(EnumParamType.values()));
	private final JBTextField chatGptTokenText = new JBTextField();

	public AppSettingsComponent() {
		HorizontalBox horizontalBox = new HorizontalBox();
		horizontalBox.add(paramTypeJComboBox1);
		horizontalBox.add(paramTypeJComboBox2);
		myMainPanel = FormBuilder.createFormBuilder()
				.addLabeledComponent(new JBLabel("Enum description separator"), enumDescSplitText, 1, false)
				.addLabeledComponent(new JBLabel("Enum parameter separator"), enumParamSplitText, 1, false)
				.addLabeledComponent(new JBLabel("Param"), horizontalBox, 1)
				.addLabeledComponent(new JBLabel("ChatGPT token"), chatGptTokenText, 1,false)
				.addComponentFillVertically(new JPanel(), 0)
				.getPanel();
	}

	public JPanel getPanel() {
		return myMainPanel;
	}

	public JComponent getPreferredFocusedComponent() {
		return enumDescSplitText;
	}

	/**
	 * 枚举描述分隔符
	 */
	public void setEnumDescSplit(@NotNull String newText) {
		enumDescSplitText.setText(newText);
	}

	/**
	 * 枚举参数分隔符
	 */
	public void setEnumParamSplit(@NotNull String newText) {
		enumParamSplitText.setText(newText);
	}

	public void setEnumParamTypes(List<EnumParamType> paramTypes) {
		paramTypeJComboBox1.setSelectedItem(paramTypes.get(0));
		paramTypeJComboBox2.setSelectedItem(paramTypes.get(1));
	}

	public String getEnumDescSplit() {
		return enumDescSplitText.getText();
	}

	public String getEnumParamSplit() {
		return enumParamSplitText.getText();
	}

	public List<EnumParamType> getEnumParamTypes() {
		EnumParamType selectedItem = ((EnumParamType) paramTypeJComboBox1.getSelectedItem());
		EnumParamType selectedItem1 = ((EnumParamType) paramTypeJComboBox2.getSelectedItem());
		return List.of(selectedItem, selectedItem1);
	}

	public void setChatGptToken(String token){
		chatGptTokenText.setText(token);
	}

	public String getChatGptToken(){
		return chatGptTokenText.getText();
	}
}