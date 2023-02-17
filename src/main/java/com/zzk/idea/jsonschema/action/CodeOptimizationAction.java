package com.zzk.idea.jsonschema.action;

import com.intellij.ide.util.TipDialog;
import com.intellij.ide.util.TipPanel;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.ui.DialogBuilder;
import com.zzk.idea.jsonschema.constants.CodeFunction;
import com.zzk.idea.jsonschema.service.ChatGptService;
import com.zzk.idea.jsonschema.util.CopyUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ThreadUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeOptimizationAction extends BaseCodeFunctionAction {

	public CodeOptimizationAction() {
		super(CodeFunction.OPTIMIZATION);
	}
}
