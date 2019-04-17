package com.jtech.helper.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jtech.helper.logger.JtLogManager;
import com.jtech.helper.logger.JtLogger;
import com.jtech.helper.model.RestErrorModel;
import com.jtech.helper.model.RestResponseModel;

public class JtUtil {

	private static final JtLogger logger = JtLogManager.getLogger(JtUtil.class);

	public static String[] getValuesFromEnum(Class<? extends Enum<?>> e) {
		return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
	}

	public static String generateUniqueId() {
		UUID uniqueKey = UUID.randomUUID();
		String uuid = uniqueKey.toString();
		return uuid;
	}

	public static boolean isNullOrBlank(String key) {
		int strLen;
		if (key == null || (strLen = key.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(key.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNullOrEmpty(Collection<?> list) {
        return (list == null || list.isEmpty());
    }

	public static String objectToJson(Object object) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonToString = mapper.writeValueAsString(object);
			return jsonToString;
		} catch (Exception e) {
			logger.error("[objectToJson] Exception", e);
			return null;
		}
	}

	public static <T> List<T> jsonToObjectList(String json, Class<T> clz) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<T> objectList = objectMapper.readValue(json,
					TypeFactory.defaultInstance().constructCollectionType(List.class, clz));
			return objectList;
		} catch (Exception e) {
			logger.error("[jsonToObjectList] Exception: ", e);
			return null;
		}
	}

	public static <Any> Any jsonToObject(String json, Class<Any> clz) {

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Any output = (Any) objectMapper.readValue(json, clz);
			return output;
		} catch (Exception e) {
			logger.error("[jsonToObject] Exception: ", e);
			return null;
		}
	}

	public static boolean isBetween(int a, int b, int c) {
		return b > a ? c > a && c < b : c > b && c < a;
	}

	public static void parseRequest(RestResponseModel<?> restResponseDTO) throws Exception {
		if (JtUtil.isBetween(199, 300, restResponseDTO.getStatus())) {

		} else if (restResponseDTO.getStatus() >= 300 && restResponseDTO.getError() != null) {
			generateExceptionFromError(restResponseDTO.getError());
		} else {
			throw new RuntimeException("Some error occurred");
		}
	}

	public static void generateExceptionFromError(RestErrorModel error) throws Exception {
		throw new RuntimeException(error.getMessage() + " " + error.getDeveloperCode());
	}
}