package com.jtech.helper.request;

import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class JtHttpEntity<T> extends HttpEntity<T> {

	protected JtHttpEntity() {
		this((T) null, (MultiValueMap<String, String>) null);
	}

	public JtHttpEntity(T body) {
		this(body, (MultiValueMap<String, String>) null);
	}

	public JtHttpEntity(MultiValueMap<String, String> headers) {
		this((T) null, headers);
	}

	public JtHttpEntity(T body, MultiValueMap<String, String> headers) {
		this(addCustomHeaders(headers), body);
	}

	public static MultiValueMap<String, String> addCustomHeaders(MultiValueMap<String, String> customHeaders) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		if (customHeaders != null) {
			headers.putAll(customHeaders);
		}
		return headers;
	}

	public JtHttpEntity(MultiValueMap<String, String> headers, T body) {
		super(body, headers);
	}
}