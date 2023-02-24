package com.zzk.idea.bitbyte.service;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSON;
import com.zzk.idea.bitbyte.constants.CodeFunction;
import com.zzk.idea.bitbyte.service.dto.chatgpt.ChatGptRequest;
import com.zzk.idea.bitbyte.service.dto.chatgpt.ChatGptResponse;
import com.zzk.idea.bitbyte.service.dto.chatgpt.ChoicesItem;
import com.zzk.idea.bitbyte.settings.AppSettingsState;
import com.zzk.idea.bitbyte.util.JsonUtil;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

/**
 * ChatGptService
 * @author 张子宽
 * @date 2023/02/15
 */
public class ChatGptService {
	private static final OkHttpClient client = new OkHttpClient().newBuilder()
			.callTimeout(30, TimeUnit.SECONDS)
			.connectTimeout(30, TimeUnit.SECONDS)
			.readTimeout(30, TimeUnit.SECONDS)
			.build();
	private static final MediaType mediaType = MediaType.parse("application/json");

	private static final String BASE_URL = "https://api.openai.com";


	public Optional<String> generateCode(CodeFunction function,String code) {
		ChatGptRequest chatGptRequest = buildChatGptRequest(function, code);
		return getChatGptResponse(chatGptRequest)
				.map(ChatGptResponse::getChoices)
				.map(x -> x.get(0))
				.map(ChoicesItem::getText);
	}

	@NotNull
	private static ChatGptRequest buildChatGptRequest(CodeFunction function, String code) {
		ChatGptRequest chatGptRequest = new ChatGptRequest();
		chatGptRequest.setTopP(1);
		chatGptRequest.setFrequencyPenalty(0);
		chatGptRequest.setMaxTokens(256);
		chatGptRequest.setPresencePenalty(0);
		chatGptRequest.setTemperature(0);
		chatGptRequest.setModel(AppSettingsState.getInstance().getCodeOptimizationState().getChatGptModel().getValue());
		chatGptRequest.setPrompt(function.buildChatGptPrompt(code));
		return chatGptRequest;
	}

	private static Optional<ChatGptResponse> getChatGptResponse(ChatGptRequest chatGptRequest) {
		String requestJson = JsonUtil.toJson(chatGptRequest);
		try {
			System.out.printf("请求chatGPT,参数[%s]%n", requestJson);
			RequestBody requestBody = RequestBody.create(requestJson, mediaType);
			String chatGptToken = AppSettingsState.getInstance().getCodeOptimizationState()
					.getChatGptToken();
			if (StringUtils.isEmpty(chatGptToken)){
				return Optional.empty();
			}
			Request request = new Request.Builder()
					.url(BASE_URL + "/v1/completions")
					.method("POST", requestBody)
					.addHeader("Authorization", "Bearer " + chatGptToken)
					.addHeader("Content-Type", "application/json")
					.addHeader("Content-Length", "")
					.build();
			Response response = client.newCall(request).execute();
			return Optional.ofNullable(response.body())
					.map(x -> {
						try {
							return x.string();
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					})
					.map(json -> {
						return JSON.parseObject(json, ChatGptResponse.class);
					});
		} catch (Exception e) {
			System.err.printf("请求chatGPT异常,参数[{}]%n", requestJson, e);
			return Optional.empty();
		}
	}


	public static void main(String[] args) throws IOException {

	}
}
