package com.megazone.core.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

public class JSON {
	static final ObjectMapper mapper = new ObjectMapper();
	static final ObjectMapper sortedMapper = new ObjectMapper();

	static {
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
		sortedMapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
	}

	public static JSONObject parseObject(String str) {
		try {
			JSONObject obj = new JSONObject();
			obj.node = (ObjectNode) mapper.readTree(str);
			return obj;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static <T> T parseObject(String str, Class<T> clazz) {
		try {
			JsonNode theNode = mapper.readTree(str);
			return mapper.treeToValue(theNode, clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static JSONObject toJSON(Serializable object) {
		try {
			return parseObject(mapper.writeValueAsString(object));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object parse(String str) {
		try {
			return mapper.readTree(str);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String toJSONString(Object obj) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static <T> List<T> parseArray(String str, Class<T> clazz) {
		ObjectReader reader = JSON.mapper.readerForListOf(clazz);
		List<T> list = null;
		try {
			JSONArray arr = new JSONArray();
			arr.node = (ArrayNode) reader.readTree(str);
			list = arr.toJavaList(clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static JSONArray parseArray(String str) {
		ObjectReader reader = JSON.mapper.readerForListOf(Object.class);
		try {
			JSONArray arr = new JSONArray();
			arr.node = (ArrayNode) reader.readTree(str);
			return arr;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
