package com.zzk.idea.jsonschema.service.dto.chatgpt;

import com.alibaba.fastjson.annotation.JSONField;

public class Usage{

	@JSONField(name="completion_tokens")
	private Integer completionTokens;

	@JSONField(name="prompt_tokens")
	private Integer promptTokens;

	@JSONField(name="total_tokens")
	private Integer totalTokens;

	public void setCompletionTokens(Integer completionTokens){
		this.completionTokens = completionTokens;
	}

	public Integer getCompletionTokens(){
		return completionTokens;
	}

	public void setPromptTokens(Integer promptTokens){
		this.promptTokens = promptTokens;
	}

	public Integer getPromptTokens(){
		return promptTokens;
	}

	public void setTotalTokens(Integer totalTokens){
		this.totalTokens = totalTokens;
	}

	public Integer getTotalTokens(){
		return totalTokens;
	}
}