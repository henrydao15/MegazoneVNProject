package com.megazone.core.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;

public class JSONObject implements Map<String, Object> {
	public ObjectNode node;

	public JSONObject() {
		node = JsonNodeFactory.instance.objectNode();
	}

	public JSONObject(ObjectNode theNode) {
		node = theNode;
	}

	public JSONObject(Map<String, Object> obj) {
		node = JSON.mapper.valueToTree(obj);
	}

	public static <T> T parseObject(InputStream inputStream, Class<T> clazz) throws IOException {
		String str = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
		JsonNode theNode = JSON.mapper.readTree(str);
		return JSON.mapper.treeToValue(theNode, clazz);
	}

	public static JSONObject parseObjectOrdered(String str) {
		JSONObject obj = new JSONObject();
		try {
			obj.node = JSON.sortedMapper.readTree(str).deepCopy();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static JSONObject toJSON(Object child) {
		JSONObject obj = new JSONObject();
		obj.node = JSON.mapper.valueToTree(child);
		return obj;
	}

	public static <T> T toJavaObject(JSONObject child, Class<T> clazz) {
		try {
			return JSON.mapper.treeToValue(child.node, clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String toString() {
		return toJSONString();
	}

	public String toJSONString() {
		JSON.mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		try {
			return JSON.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public boolean containsKey(String key) {
		return node.has(key);
	}

	public String getString(String key) {
		return node.get(key).asText();
	}

	public Integer getInteger(String key) {
		return node.get(key).asInt();
	}

	public Long getLong(String key) {
		return node.get(key).asLong();
	}

	public Date getDate(String key) {
		try {
			return new StdDateFormat().withColonInTimeZone(true).parse(node.get(key).asText());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public BigDecimal getBigDecimal(String key) {
		return node.get(key).decimalValue();
	}

	public Object get(String key) {
		return node.get(key);
	}

	public double getDouble(String key) {
		return node.get(key).asDouble();
	}

	public JSONObject fluentPut(String key, Object value) {
		if (node == null) {
			node = JsonNodeFactory.instance.objectNode();
		}
		if (value instanceof JsonNode) {
			node.set(key, (JsonNode) value);
		} else if (value instanceof Boolean) {
			node.put(key, (boolean) value);
		} else if (value instanceof String) {
			node.put(key, (String) value);
		} else if (value instanceof Integer) {
			node.put(key, (Integer) value);
		} else if (value instanceof Long) {
			node.put(key, (Long) value);
		} else if (value instanceof Short) {
			node.put(key, (Short) value);
		} else if (value instanceof Byte) {
			node.put(key, (Byte) value);
		} else if (value instanceof Float) {
			node.put(key, (Float) value);
		} else if (value instanceof Double) {
			node.put(key, (Double) value);
		} else if (value instanceof BigInteger) {
			node.put(key, (BigInteger) value);
		} else if (value instanceof BigDecimal) {
			node.put(key, (BigDecimal) value);
		} else if (value instanceof JSONObject) {
			node.set(key, ((JSONObject) value).node);
		} else if (value instanceof Map) {
			Set<Map.Entry<String, Object>> entries = ((Map<String, Object>) value).entrySet();
			entries.forEach(entry -> {
				fluentPut(entry.getKey(), entry.getValue());
			});
		} else {
			JsonNode theNode = JSON.mapper.valueToTree(value);
			node.putPOJO(key, theNode);
		}

		return this;
	}

	public JSONObject fluentPutAll(Map<String, Object> theMap) {
		if (node == null) {
			node = JsonNodeFactory.instance.objectNode();
		}
		theMap.forEach(this::fluentPut);
		return this;
	}

	public JSONObject fluentRemove(String key) {
		node.remove(key);
		return this;
	}

	public JSONObject getJSONObject(String key) {
		JSONObject obj = new JSONObject();
		obj.node = node.with(key);
		return obj;
	}

	public JSONArray getJSONArray(String key) {
		JSONArray arr = new JSONArray();
		arr.node = node.withArray(key);
		return arr;
	}

	public Map<String, Object> getInnerMapObject() {
		if (this.isEmpty()) {
			return new HashMap<>();
		}
		Map<String, Object> result = JSON.mapper.convertValue(this.node, new TypeReference<>() {
		});
		return result;
	}

	public <T> T getObject(String str, Class<T> clazz) {
		try {
			JsonNode theNode = JSON.mapper.readTree(str);
			return JSON.mapper.treeToValue(theNode, clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int size() {
		if (node == null) {
			return 0;
		}
		return node.size();
	}

	public boolean isEmpty() {
		return node == null || node.isEmpty();
	}

	public boolean containsKey(Object key) {
		if (node == null) {
			return false;
		}
		return node.has((String) key);
	}

	public boolean containsValue(Object value) {
		if (node == null) {
			return false;
		}
		Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> entry = fields.next();
			if (entry.getValue() == value) {
				return true;
			}
		}
		return false;
	}

	public Object get(Object key) {
		if (node == null) {
			return null;
		}
		return getInnerMapObject().get(key);
	}

	public Object put(String key, Object value) {
		fluentPut(key, value);
		return value;
	}

	public Object remove(Object key) {
		if (node == null) {
			return null;
		}
		return node.remove((String) key);
	}

	public Object remove(String key, Object value) {
		if (node == null) {
			return null;
		}
		if (node.get(key) == value) {
			return node.remove(key);
		}
		return null;
	}

	public void putAll(Map<? extends String, ?> m) {
		Set<? extends Map.Entry<? extends String, ?>> entries = m.entrySet();
		entries.forEach(entry -> {
			fluentPut(entry.getKey(), entry.getValue());
		});
	}

	public void clear() {
		if (node == null) {
			return;
		}
		node.removeAll();
	}

	public Set<String> keySet() {
		if (node == null) {
			return new HashSet<>();
		}
		final Set<String> s = new HashSet<>();
		Iterator<String> fieldNames = node.fieldNames();
		while (fieldNames.hasNext()) {
			s.add(fieldNames.next());
		}
		return s;
	}

	public Collection<Object> values() {
		if (node == null) {
			return new ArrayList<>();
		}
		ArrayList<Object> list = new ArrayList<>();
		Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			list.add(fields.next().getValue());
		}
		return list;
	}

	public Set<Map.Entry<String, Object>> entrySet() {
		if (node == null) {
			return new HashSet<>();
		}
		final Set<Map.Entry<String, Object>> s = new HashSet<>();
		Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
		while (fields.hasNext()) {
			Map.Entry<String, JsonNode> entry = fields.next();
			Map.Entry<String, Object> theEntry = new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue());
			s.add(theEntry);
		}
		return s;
	}

	public <T> T toJavaObject(Class<T> clazz) {
		try {
			return JSON.mapper.treeToValue(node, clazz);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
