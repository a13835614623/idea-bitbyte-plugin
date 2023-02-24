package com.zzk.idea.jsonschema.constants;

/**
 * ChatGptModel
 * @author 张子宽
 * @date 2023/02/15
 */
public enum ChatGptModel {

	CODE_DAVINCI_002("code-davinci-002"),
	CODE_CUSHMAN_001("code-cushman-001"),


	;

	private final String value;


	ChatGptModel(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
