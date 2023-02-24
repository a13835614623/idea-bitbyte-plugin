package com.zzk.idea.bitbyte.ui;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.ui.components.JBTextArea;

public class MyToolWindow {
	public static final String ID = "Code Function Result";

	private final JBTextArea jbTextArea = new JBTextArea("");

	public MyToolWindow(ToolWindow toolWindow) {
	}

	public void print(String s){
		jbTextArea.setText(s);
	}

	public JBTextArea getContent() {
		return jbTextArea;
	}
}
