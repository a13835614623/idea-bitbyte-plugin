package com.zzk.idea.jsonschema.constants;

import com.intellij.openapi.ui.Messages;

/**
 * Message
 * @author 张子宽
 * @date 2023/02/18
 */
public enum Message {

	CHAT_GPT_BACKGROUND_TASK_TITLE("Requesting chatgpt to get results"),
	GENERATE_TEST_METHOD("Generate Test Method"),
	NOT_FOUND_TEST_FRAMEWORK("The test framework could not be found"),
	TEST_CLASS_CREATE_FAIL("Test class creation failed"),
	NOT_FOUND_SRC_PACKAGE("The source package could not be found"),
	NOT_FOUND_SRC_CLASS("The source class could not be found"),
	NOT_FOUND_SRC_MODULE("The source module could not be found"),

	;

	private final String desc;

	Message(String desc) {
		this.desc = desc;
	}

	public String message(){
		return desc;
	}



	public void showErrorDialog(){
		Messages.showErrorDialog(message(),"Error");
	}
}
