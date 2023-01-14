package com.zzk.idea.jsonschema.settings;

import com.intellij.openapi.options.Configurable;
import com.zzk.idea.jsonschema.action.enumdesc.CopyEnumState;
import org.jetbrains.annotations.Nls;

import javax.swing.*;
import java.util.List;
import java.util.Objects;

/**
 * Provides controller functionality for application settings.
 */
public class AppSettingsConfigurable implements Configurable {

  private AppSettingsComponent mySettingsComponent;

  // A default constructor with no arguments is required because this implementation
  // is registered as an applicationConfigurable EP

  @Nls(capitalization = Nls.Capitalization.Title)
  @Override
  public String getDisplayName() {
    return "Copy Json Schema";
  }

  @Override
  public JComponent getPreferredFocusedComponent() {
    return mySettingsComponent.getPreferredFocusedComponent();
  }

  @Override
  public JComponent createComponent() {
    mySettingsComponent = new AppSettingsComponent();
    return mySettingsComponent.getPanel();
  }

  @Override
  public boolean isModified() {
    CopyEnumState copyEnumState = AppSettingsState.getInstance().getCopyEnumState();
    boolean modified = !mySettingsComponent.getEnumDescSplit().equals(copyEnumState.getDescSplit());
    modified |= !Objects.equals(mySettingsComponent.getEnumParamSplit(), copyEnumState.getParamSplit());
    modified |= !Objects.equals(mySettingsComponent.getEnumParamTypes(), copyEnumState.getParamTypes());
    return modified;
  }

  @Override
  public void apply() {
    CopyEnumState copyEnumState = AppSettingsState.getInstance().getCopyEnumState();
    copyEnumState.setParamSplit(mySettingsComponent.getEnumParamSplit());
    copyEnumState.setDescSplit(mySettingsComponent.getEnumDescSplit());
    copyEnumState.setParamTypes(mySettingsComponent.getEnumParamTypes());
  }

  @Override
  public void reset() {
    CopyEnumState copyEnumState = AppSettingsState.getInstance().getCopyEnumState();
    mySettingsComponent.setEnumDescSplit(copyEnumState.getDescSplit());
    mySettingsComponent.setEnumParamSplit(copyEnumState.getParamSplit());
    mySettingsComponent.setEnumParamTypes(copyEnumState.getParamTypes());
  }

  @Override
  public void disposeUIResources() {
    mySettingsComponent = null;
  }

}