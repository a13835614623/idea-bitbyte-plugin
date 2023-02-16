package com.zzk.idea.jsonschema.action;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.zzk.idea.jsonschema.constants.CodeFunction;
import com.zzk.idea.jsonschema.service.ChatGptService;
import com.zzk.idea.jsonschema.ui.MyToolWindow;
import com.zzk.idea.jsonschema.ui.MyToolWindowFactory;
import com.zzk.idea.jsonschema.util.CopyUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseCodeFunctionAction extends AnAction {

	private final ChatGptService chatGptService = new ChatGptService();
	private static final ExecutorService executorService = Executors.newFixedThreadPool(10);

	private CodeFunction codeFunction;

	public BaseCodeFunctionAction(CodeFunction codeFunction) {
		this.codeFunction = codeFunction;
	}

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
		Project project = e.getRequiredData(CommonDataKeys.PROJECT);
		System.out.printf("[%s]选中文本内容:\n%s%n", codeFunction, selectedText);
		executorService.execute(() -> {
			chatGptService.generateCode(codeFunction, selectedText)
					.ifPresent(code -> {
						System.out.printf("[%s]优化后:\n%s%n", codeFunction, code);
						if (StringUtils.isNotEmpty(code)) {
							Optional.ofNullable(ToolWindowManager.getInstance(project).getToolWindow(MyToolWindow.ID))
									.ifPresent(toolWindow -> toolWindow.show(() -> {
										MyToolWindowFactory.TOOL_WINDOW.print(code);
									}));
						}
					});
		});
	}
}
