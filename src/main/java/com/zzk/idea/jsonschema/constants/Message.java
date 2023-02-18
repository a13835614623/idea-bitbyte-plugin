package com.zzk.idea.jsonschema.constants;

/**
 * Message
 * @author 张子宽
 * @date 2023/02/18
 */
public enum Message {

	CHAT_GPT_BACKGROUND_TASK_TITLE("Requesting chatgpt to get results"),

	;

	private final String desc;

	Message(String desc) {
		this.desc = desc;
	}

	public String message(){
		return desc;
	}
}
