package com.zzk.idea.bitbyte.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.RoamingType;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.zzk.idea.bitbyte.action.copy.enumdesc.CopyEnumState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Supports storing the application settings in a persistent way.
 * The {@link State} and {@link Storage} annotations define the name of the data and the file name where
 * these persistent application settings are stored.
 */
@State(
        name = "com.zzk.idea.bitbyte.settings.AppSettingsState",
        storages = @Storage(value = "BitByteConfig.xml", roamingType = RoamingType.PER_OS)
)
public class AppSettingsState implements PersistentStateComponent<AppSettingsState> {

  /**
   * 复制枚举参数
   */
  public CopyEnumState copyEnumState = CopyEnumState.defaultVal();

  /**
   * 创建测试方法参数
   */
  public CreateTestMethodState createTestMethodState = CreateTestMethodState.defaultVal();

  public CopyEnumState getCopyEnumState() {
    return copyEnumState;
  }

  public CreateTestMethodState getCreateTestMethodState() {
    return createTestMethodState;
  }

  public static AppSettingsState getInstance() {
    return ApplicationManager.getApplication().getService(AppSettingsState.class);
  }

  @Nullable
  @Override
  public AppSettingsState getState() {
    return this;
  }

  @Override
  public void loadState(@NotNull AppSettingsState state) {
    XmlSerializerUtil.copyBean(state, this);
  }

}
