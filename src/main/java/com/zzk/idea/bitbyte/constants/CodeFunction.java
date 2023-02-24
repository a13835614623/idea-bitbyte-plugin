package com.zzk.idea.bitbyte.constants;
/**
 * CodeFunction
 * @author 张子宽
 * @date 2023/02/15
 */
public enum CodeFunction {

	/**
	 * 优化
	 */
	OPTIMIZATION("Optimize this code:\n%s"),
	/**
	 * 单测
	 */
	TEST("Write a unit test for this code:\n%s")
	;


	private final String text;

	CodeFunction(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public String buildChatGptPrompt(String code){
		return String.format(text, code);
	}
}
