package com.zzk.idea.bitbyte.settings;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBTextField;
import com.intellij.ui.components.panels.HorizontalBox;
import com.intellij.util.ui.FormBuilder;
import com.zzk.idea.bitbyte.constants.EnumParamType;
import com.zzk.idea.bitbyte.constants.UIMessage;
import org.jetbrains.annotations.NotNull;

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
				.addComponent(UIMessage.COPY_SETTING.label())
				.addLabeledComponent(UIMessage.ENUM_DESCRIPTION_SEPARATOR.label(), enumDescSplitText, 1, false)
				.addLabeledComponent(UIMessage.ENUM_PARAMETER_SEPARATOR.label(), enumParamSplitText, 1, false)
				.addLabeledComponent(UIMessage.PARAM.label(), horizontalBox, 1)
				// .addLabeledComponent(new JBLabel("ChatGPT token"), chatGptTokenText, 1,false)
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
		return Stream.of(selectedItem, selectedItem1)
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	public void setChatGptToken(String token){
		chatGptTokenText.setText(token);
	}

	public String getChatGptToken(){
		return chatGptTokenText.getText();
	}
}