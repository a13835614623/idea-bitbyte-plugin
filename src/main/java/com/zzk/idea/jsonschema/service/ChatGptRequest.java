package com.zzk.idea.jsonschema.service;

import com.alibaba.fastjson.annotation.JSONField;

public class ChatGptRequest{

	@JSONField(name="top_p")
	private Integer topP;

	@JSONField(name="frequency_penalty")
	private Integer frequencyPenalty;

	@JSONField(name="max_tokens")
	private Integer maxTokens;

	@JSONField(name="presence_penalty")
	private Integer presencePenalty;

	@JSONField(name="temperature")
	private Integer temperature;

	@JSONField(name="model")
	private String model;

	@JSONField(name="prompt")
	private String prompt;

	public void setTopP(Integer topP){
		this.topP = topP;
	}

	public Integer getTopP(){
		return topP;
	}

	public void setFrequencyPenalty(Integer frequencyPenalty){
		this.frequencyPenalty = frequencyPenalty;
	}

	public Integer getFrequencyPenalty(){
		return frequencyPenalty;
	}

	public void setMaxTokens(Integer maxTokens){
		this.maxTokens = maxTokens;
	}

	public Integer getMaxTokens(){
		return maxTokens;
	}

	public void setPresencePenalty(Integer presencePenalty){
		this.presencePenalty = presencePenalty;
	}

	public Integer getPresencePenalty(){
		return presencePenalty;
	}

	public void setTemperature(Integer temperature){
		this.temperature = temperature;
	}

	public Integer getTemperature(){
		return temperature;
	}

	public void setModel(String model){
		this.model = model;
	}

	public String getModel(){
		return model;
	}

	public void setPrompt(String prompt){
		this.prompt = prompt;
	}

	public String getPrompt(){
		return prompt;
	}
}