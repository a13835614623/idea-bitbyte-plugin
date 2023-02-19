package com.zzk.idea.jsonschema.constants;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import com.intellij.lang.jvm.JvmParameter;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiTypeParameter;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * SpringRequest
 * @author 张子宽
 * @date 2023/02/18
 */
public enum SpringRequestMapping {

	POST_MAPPING("PostMapping", RequestMethod.POST),
	GET_MAPPING("GetMapping", RequestMethod.GET),
	DELETE_MAPPING("DeleteMapping", RequestMethod.DELETE),
	PUT_MAPPING("PutMapping", RequestMethod.PUT),
	PATCH_MAPPING("PatchMapping", RequestMethod.PATCH);

	private final String qualifiedName;

	private final RequestMethod requestMethod;

	private static final String BASE_PACKAGE = "org.springframework.web.bind.annotation.";

	SpringRequestMapping(String qualifiedName, RequestMethod requestMethod) {
		this.qualifiedName = BASE_PACKAGE + qualifiedName;
		this.requestMethod = requestMethod;
	}

	public String getQualifiedName() {
		return qualifiedName;
	}


	public static Optional<Request> getRequestMethod(PsiMethod psiMethod) {
		for (SpringRequestMapping requestMapping : values()) {
			PsiAnnotation annotation = psiMethod.getAnnotation(requestMapping.getQualifiedName());
			if (annotation == null) {
				continue;
			}
			MediaType mediaType = null;
			boolean hasRequestBody = Arrays.stream(psiMethod.getTypeParameters()).anyMatch(SpringRequestMapping::hasRequestBody);
			if (hasRequestBody){
				mediaType = MediaType.parse(com.google.common.net.MediaType.JSON_UTF_8.toString());
			}else {
				mediaType = MediaType.parse(com.google.common.net.MediaType.PLAIN_TEXT_UTF_8.toString());
			}

			return Optional.of(new Request.Builder()
					.method(requestMapping.requestMethod.name(), RequestBody.create("null",mediaType))

					.build());
		}

		return Optional.empty();
	}

	private static boolean hasRequestBody(PsiTypeParameter jvmParameter) {
		return jvmParameter.hasAnnotation(BASE_PACKAGE + "RequestBody");
	}


	public static void main(String[] args) {
		System.out.println();
	}
}
