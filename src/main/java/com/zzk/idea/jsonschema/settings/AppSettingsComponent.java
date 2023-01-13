package com.zzk.idea.jsonschema.settings;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * Supports creating and managing a {@link JPanel} for the Settings Dialog.
 */
public class AppSettingsComponent {

  private final JPanel myMainPanel;
  private final JBTextField enumDescSplitText = new JBTextField();
  private final JBTextField enumParamSplitText = new JBTextField();
  private static final String enumDescSplitLabel = "枚举描述分隔符";
  private static final String enumParamSplitLabel = "枚举参数分隔符";
  public AppSettingsComponent() {

    myMainPanel = FormBuilder.createFormBuilder()
            .addLabeledComponent(new JBLabel(enumDescSplitLabel), enumDescSplitText, 1, false)
            .addLabeledComponent(new JBLabel(enumParamSplitLabel), enumParamSplitText, 1, false)
            .addLabeledComponent(new JBLabel(enumParamSplitLabel), enumParamSplitText, 1, false)
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

  public String getEnumDescSplit(){
    return enumDescSplitText.getText();
  }

  public String getEnumParamSplit(){
    return enumParamSplitText.getText();
  }
}