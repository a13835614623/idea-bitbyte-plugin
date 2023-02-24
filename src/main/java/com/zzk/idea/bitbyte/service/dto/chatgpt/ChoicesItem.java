package com.zzk.idea.bitbyte.service.dto.chatgpt;

import com.alibaba.fastjson.annotation.JSONField;

public class ChoicesItem{

	@JSONField(name="finish_reason")
	private String finishReason;

	@JSONField(name="index")
	private Integer index;

	@JSONField(name="text")
	private String text;

	@JSONField(name="logprobs")
	private Object logprobs;

	public void setFinishReason(String finishReason){
		this.finishReason = finishReason;
	}

	public String getFinishReason(){
		return finishReason;
	}

	public void setIndex(Integer index){
		this.index = index;
	}

	public Integer getIndex(){
		return index;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setLogprobs(Object logprobs){
		this.logprobs = logprobs;
	}

	public Object getLogprobs(){
		return logprobs;
	}
}