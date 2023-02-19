package com.zzk.idea.jsonschema.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;

public class JsonUtil {


	public static String toJson(Object o){
		return JSON.toJSONString(o,
				SerializerFeature.WriteEnumUsingToString);
	}
}
