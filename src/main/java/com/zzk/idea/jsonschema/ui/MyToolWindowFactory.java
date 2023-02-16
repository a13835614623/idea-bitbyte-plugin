// Copyright 2000-2022 JetBrains s.r.o. and other contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.

package com.zzk.idea.jsonschema.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

public class MyToolWindowFactory implements ToolWindowFactory {

  public static MyToolWindow TOOL_WINDOW;

  public static final String ID = "Android Profiler";

  /**
   * Create the tool window content.
   *
   * @param project    current project
   * @param toolWindow current tool window
   */
  @Override
  public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
    TOOL_WINDOW = new MyToolWindow(toolWindow);
    Content content = ContentFactory.SERVICE.getInstance().createContent(TOOL_WINDOW.getContent(), "", false);
    toolWindow.getContentManager().addContent(content);
  }

}
