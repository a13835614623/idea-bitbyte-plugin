package com.zzk.idea.bitbyte.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonUtil {


	public static String toJson(Object o){
		return JSON.toJSONString(o,
				SerializerFeature.WriteEnumUsingToString);
	}
}
