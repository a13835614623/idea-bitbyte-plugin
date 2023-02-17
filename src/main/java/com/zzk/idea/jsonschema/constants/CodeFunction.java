package com.zzk.idea.jsonschema.constants;
/**
 * CodeFunction
 * @author 张子宽
 * @date 2023/02/15
 */
public enum CodeFunction {

	/**
	 * 优化
	 */
	OPTIMIZATION("优化一下这段代码:\n%s"),
	/**
	 * 单测
	 */
	TEST("为这段代码写一下单元测试:\n%s")
	;


	private String text;

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
