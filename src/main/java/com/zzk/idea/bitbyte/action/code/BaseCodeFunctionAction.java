package com.zzk.idea.bitbyte.action.code;

import java.util.Optional;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.SelectionModel;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.zzk.idea.bitbyte.constants.CodeFunction;
import com.zzk.idea.bitbyte.constants.Message;
import com.zzk.idea.bitbyte.service.ChatGptService;
import com.zzk.idea.bitbyte.ui.MyToolWindow;
import com.zzk.idea.bitbyte.ui.MyToolWindowFactory;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class BaseCodeFunctionAction extends AnAction {

	private final ChatGptService chatGptService = new ChatGptService();

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
		Optional.ofNullable(ToolWindowManager.getInstance(project).getToolWindow(MyToolWindow.ID))
				.ifPresent(ToolWindow::show);

		ProgressManager.getInstance().run(new Task.Backgroundable(project, Message.CHAT_GPT_BACKGROUND_TASK_TITLE.message(),false) {
			@Override
			public void run(@NotNull ProgressIndicator indicator) {
				chatGptService.generateCode(codeFunction, selectedText)
						.ifPresent(code -> {
							System.out.printf("[%s]优化后:\n%s%n", codeFunction, code);
							if (StringUtils.isNotEmpty(code)) {
								MyToolWindowFactory.TOOL_WINDOW.print(code);
							}
						});
			}
		});
	}
}
