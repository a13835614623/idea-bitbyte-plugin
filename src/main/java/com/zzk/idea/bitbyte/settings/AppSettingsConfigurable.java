package com.zzk.idea.bitbyte.settings;

import java.util.Objects;

import javax.swing.JComponent;

import com.intellij.openapi.options.Configurable;
import com.zzk.idea.bitbyte.action.copy.enumdesc.CopyEnumState;
import com.zzk.idea.bitbyte.settings.ui.AppSettingsComponent;
import org.jetbrains.annotations.Nls;

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
        AppSettingsState instance = AppSettingsState.getInstance();
        CopyEnumState copyEnumState = instance.getCopyEnumState();
        boolean modified = !mySettingsComponent.getEnumDescSplit().equals(copyEnumState.getDescSplit());
        modified |= !Objects.equals(mySettingsComponent.getEnumParamSplit(), copyEnumState.getParamSplit());
        modified |= !Objects.equals(mySettingsComponent.getEnumParamTypes(), copyEnumState.getParamTypes());

        CreateTestMethodState createTestMethodState = instance.getCreateTestMethodState();
        modified |= !Objects.equals(mySettingsComponent.getCreateTestMethodState(), createTestMethodState);
        return modified;
    }

    @Override
    public void apply() {
        AppSettingsState instance = AppSettingsState.getInstance();
        CopyEnumState copyEnumState = instance.getCopyEnumState();
        copyEnumState.setParamSplit(mySettingsComponent.getEnumParamSplit());
        copyEnumState.setDescSplit(mySettingsComponent.getEnumDescSplit());
        copyEnumState.setParamTypes(mySettingsComponent.getEnumParamTypes());

        CreateTestMethodState createTestMethodState = instance.getCreateTestMethodState();
        CreateTestMethodState nowState = mySettingsComponent.getCreateTestMethodState();
        createTestMethodState.setItems(nowState.getItems());
        createTestMethodState.setTestMethodNamePreFix(nowState.getTestMethodNamePreFix());
        createTestMethodState.setTestMethodNamingMethod(nowState.getTestMethodNamingMethod());
    }

    @Override
    public void reset() {
        AppSettingsState instance = AppSettingsState.getInstance();
        CopyEnumState copyEnumState = instance.getCopyEnumState();
        mySettingsComponent.setEnumDescSplit(copyEnumState.getDescSplit());
        mySettingsComponent.setEnumParamSplit(copyEnumState.getParamSplit());
        mySettingsComponent.setEnumParamTypes(copyEnumState.getParamTypes());
        mySettingsComponent.setCreateTestMethodState(instance.getCreateTestMethodState());
    }

    @Override
    public void disposeUIResources() {
        mySettingsComponent = null;
    }

}