package com.megazone.core.common;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.util.List;

public class JSONArray {
	public ArrayNode node;

	public JSONArray() {
		node = JsonNodeFactory.instance.arrayNode();
	}

	public JSONArray(List<Object> value) {
		node = JSON.mapper.valueToTree(value);
	}

	public <T> List<T> toJavaList(Class<T> clazz) {
		ObjectReader reader = JSON.mapper.readerForListOf(clazz);
		List<T> list = null;
		try {
			list = reader.readValue(node);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	public int size() {
		return node.size();
	}

	public boolean isEmpty() {
		return node.isEmpty();
	}

	public JSONObject getJSONObject(int i) {
		JSONObject obj = new JSONObject();
		obj.node = (ObjectNode) node.get(i);
		return obj;
	}

	public JSONArray getJSONArray(int i) {
		JSONArray arr = new JSONArray();
		arr.node = (ArrayNode) node.get(i);
		return arr;
	}

	public String toJSONString() {
		return node.toPrettyString();
	}

	public void clear() {
		node.removeAll();
	}

	public void add(JSONObject obj) {
		node.add(obj.node);
	}

	public JSONArray fluentAdd(JSONObject obj) {
		if (node == null) {
			node = JsonNodeFactory.instance.arrayNode();
		}
		node.add(obj.node);
		return this;
	}
}
