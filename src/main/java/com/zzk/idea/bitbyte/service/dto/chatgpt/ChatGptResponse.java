package com.zzk.idea.bitbyte.service.dto.chatgpt;

import java.util.List;
import com.alibaba.fastjson.annotation.JSONField;

public class ChatGptResponse{

	@JSONField(name="created")
	private Integer created;

	@JSONField(name="usage")
	private Usage usage;

	@JSONField(name="model")
	private String model;

	@JSONField(name="id")
	private String id;

	@JSONField(name="choices")
	private List<ChoicesItem> choices;

	@JSONField(name="object")
	private String object;

	public void setCreated(Integer created){
		this.created = created;
	}

	public Integer getCreated(){
		return created;
	}

	public void setUsage(Usage usage){
		this.usage = usage;
	}

	public Usage getUsage(){
		return usage;
	}

	public void setModel(String model){
		this.model = model;
	}

	public String getModel(){
		return model;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setChoices(List<ChoicesItem> choices){
		this.choices = choices;
	}

	public List<ChoicesItem> getChoices(){
		return choices;
	}

	public void setObject(String object){
		this.object = object;
	}

	public String getObject(){
		return object;
	}
}