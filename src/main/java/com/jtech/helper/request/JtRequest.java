package com.jtech.helper.request;

import java.io.StringWriter;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jtech.helper.logger.JtLogManager;
import com.jtech.helper.logger.JtLogger;
import com.jtech.helper.model.RestResponseModel;
import com.jtech.helper.util.JtUtil;

@EnableAspectJAutoProxy
@Service
@Configurable
public class JtRequest {

	private static final JtLogger logger = JtLogManager.getLogger(JtRequest.class);
	private static ObjectMapper objectMapper;
	protected TypeFactory typeFactory;
	private RestTemplate restTemplate;
	private int readTimeout;
	private int connectionTimeout;

	public JtRequest(@Value("${request.connectionTimeout:30000}") int connectionTimeout,
			@Value("${request.readTimeout:30000}") int readTimeout) {

		logger.info("[JtRequest] connectionTimeout: " + connectionTimeout + " readTimeout: " + readTimeout);
		this.readTimeout = readTimeout;
		this.connectionTimeout = connectionTimeout;
		restTemplate = new RestTemplate(clientHttpRequestFactory());
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		typeFactory = objectMapper.getTypeFactory();
	}

	private ClientHttpRequestFactory clientHttpRequestFactory() {
		
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(readTimeout);
		factory.setConnectTimeout(connectionTimeout);
		factory.setOutputStreaming(false);
		return factory;
	}
	
	public <T> T request(String url, HttpMethod type, Object obj, MultiValueMap<String, String> headers, Class<T> javaType) {
		return this.request(url, type, obj, headers, this.typeFactory.constructType(javaType));
	}

	public <T> T request(String url, HttpMethod type, Object obj, MultiValueMap<String, String> headers, JavaType javaType) {
		
		RestResponseModel<T> restResponseDTO;
		if (type.equals(HttpMethod.GET)) {
			restResponseDTO = getObject(url, headers);
		} else if (type.equals(HttpMethod.POST)) {
			restResponseDTO = postObject(url, obj, headers);
		} else if (type.equals(HttpMethod.PUT)) {
			restResponseDTO = putObject(url, obj, headers);
		} else if (type.equals(HttpMethod.PATCH)) {
			restResponseDTO = patchObject(url, obj, headers);
		} else if (type.equals(HttpMethod.DELETE)) {
			restResponseDTO = deleteObject(url, obj, headers);
		} else {
			throw new RuntimeException("[request] Method is not supported.");
		}
		try {
			return parseRequestAfterReturn(restResponseDTO, javaType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private <T> RestResponseModel<T> getObject(String url, MultiValueMap<String, String> headers) {
		
		JtHttpEntity<T> rt = null;
		if (headers == null) {
			rt = new JtHttpEntity<>();
		} else {
			rt = new JtHttpEntity<>(headers);
		}
		ResponseEntity<RestResponseModel<T>> restResponseDTO = restTemplate.exchange(url, HttpMethod.GET, rt,
				new ParameterizedTypeReference<RestResponseModel<T>>() {
				});
		return restResponseDTO.getBody();
	}

	private <T> RestResponseModel<T> postObject(String url, Object obj, MultiValueMap<String, String> headers) {
		
		JtHttpEntity<?> rt = null;
		if (headers == null) {
			rt = new JtHttpEntity<>(obj);
		} else {
			rt = new JtHttpEntity<>(obj, headers);
		}
		ResponseEntity<RestResponseModel<T>> restResponseDTO = restTemplate.exchange(url, HttpMethod.POST, rt,
				new ParameterizedTypeReference<RestResponseModel<T>>() {
				});
		return restResponseDTO.getBody();
	}

	private <T> RestResponseModel<T> putObject(String url, Object obj, MultiValueMap<String, String> headers) {
		
		JtHttpEntity<?> rt = null;
		if (headers == null) {
			rt = new JtHttpEntity<>(obj);
		} else {
			rt = new JtHttpEntity<>(obj, headers);
		}
		ResponseEntity<RestResponseModel<T>> response = restTemplate.exchange(url, HttpMethod.PUT, rt,
				new ParameterizedTypeReference<RestResponseModel<T>>() {
				});
		return response.getBody();
	}

	private <T> RestResponseModel<T> deleteObject(String url, Object obj, MultiValueMap<String, String> headers) {
		
		JtHttpEntity<?> rt = null;
		if (headers == null) {
			rt = new JtHttpEntity<>(obj);
		} else {
			rt = new JtHttpEntity<>(obj, headers);
		}
		ResponseEntity<RestResponseModel<T>> restResponseDTO = restTemplate.exchange(url, HttpMethod.DELETE, rt,
				new ParameterizedTypeReference<RestResponseModel<T>>() {
				});
		return restResponseDTO.getBody();
	}

	private <T> RestResponseModel<T> patchObject(String url, Object obj, MultiValueMap<String, String> headers) {
		
		JtHttpEntity<?> rt = null;
		if (headers == null) {
			rt = new JtHttpEntity<>(obj);
		} else {
			rt = new JtHttpEntity<>(obj, headers);
		}
		ResponseEntity<RestResponseModel<T>> restResponseDTO = restTemplate.exchange(url, HttpMethod.PATCH, rt,
				new ParameterizedTypeReference<RestResponseModel<T>>() {
				});
		return restResponseDTO.getBody();
	}

	private <T> T parseRequestAfterReturn(RestResponseModel<T> response, JavaType type) throws Exception {
		
		JtUtil.parseRequest(response);
		T rt = response.getResult();
		StringWriter stringWriter = new StringWriter();
		objectMapper.writeValue(stringWriter, rt);
		return objectMapper.readValue(stringWriter.toString(), type);
	}
}