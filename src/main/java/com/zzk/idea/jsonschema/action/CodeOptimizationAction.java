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

public class CodeOptimizationAction extends AnAction {

	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(CodeOptimizationAction.class);

	private final ChatGptService chatGptService = new ChatGptService();

	@Override
	public void actionPerformed(@NotNull AnActionEvent e) {
		Editor editor = e.getData(CommonDataKeys.EDITOR);
		if (editor == null) {
			return;
		}
		SelectionModel selectionModel = editor.getSelectionModel();
		String selectedText = selectionModel.getSelectedText();
		if (StringUtils.isEmpty(selectedText)) {
			return;
		}
		System.out.printf("选中文本内容:\n%s%n", selectedText);
		chatGptService.generateCode(CodeFunction.OPTIMIZATION, selectedText)
				.ifPresent(code -> {
					System.out.printf("优化后:\n%s%n", code);
					if (StringUtils.isNotEmpty(code)){
						CopyUtil.setClipBoardContent(code);
					}
				});
	}
}
