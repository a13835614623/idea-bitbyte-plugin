package com.zzk.idea.jsonschema.action.code;

import com.zzk.idea.jsonschema.constants.ChatGptModel;

/**
 * 代码优化
 * @author 张子宽
 * @date 2023/02/15
 */
public class CodeOptimizationState {

	/**
	 * chat gpt Token
	 */
	private String chatGptToken;
	/**
	 * 模型
	 */
	private ChatGptModel chatGptModel;

	public static CodeOptimizationState defaultVal() {
		CodeOptimizationState codeOptimizationState = new CodeOptimizationState();
		codeOptimizationState.setChatGptToken("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");
		codeOptimizationState.setChatGptModel(ChatGptModel.CODE_DAVINCI_002);
		return codeOptimizationState;
	}

	public ChatGptModel getChatGptModel() {
		return chatGptModel;
	}

	public void setChatGptModel(ChatGptModel chatGptModel) {
		this.chatGptModel = chatGptModel;
	}

	public String getChatGptToken() {
		return chatGptToken;
	}

	public void setChatGptToken(String chatGptToken) {
		this.chatGptToken = chatGptToken;
	}
}
