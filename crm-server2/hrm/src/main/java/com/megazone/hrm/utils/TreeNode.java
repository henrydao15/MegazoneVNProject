package com.megazone.hrm.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 */
public class TreeNode<T> {
	/**
	 *
	 */
	private String id;

	/**
	 *
	 */
	private String parentId;

	/**
	 *
	 */
	private String text;

	private String code;

	private String key;

	private Integer level;


	/**
	 *
	 */
	private String name;

	/**
	 *
	 */
	private Boolean leaf = true;
	private Boolean expanded = false;
	private T nodeData;

	/**
	 *
	 */
	private Boolean isParent = false;

	/**
	 *
	 */
	private List<TreeNode<T>> children = new ArrayList<TreeNode<T>>();

	/**
	 * @param nodes
	 * @return
	 */
	public static <T> TreeNode<T> buildTree(List<TreeNode<T>> nodes) {
		if (nodes == null || nodes.size() == 0) {
			return null;
		}

		if (nodes.size() == 1) {
			TreeNode<T> root;
			root = new TreeNode<T>();
			root.setLeaf(false);
			root.setId("-1");
			root.setName("root");
			root.setParentId("");
			root.getChildren().addAll(nodes);
			return root;
		}


		List<TreeNode<T>> tops = new ArrayList<TreeNode<T>>();

		boolean hasParent = false;

		for (TreeNode<T> child : nodes) {
			hasParent = false;


			String pid = child.getParentId();


			if (pid == null || pid.equals("")) {

				tops.add(child);

				continue;
			}


			for (TreeNode<T> parent : nodes) {
				String id = parent.getId();


				if (id != null && id.equals(pid)) {


					parent.getChildren().add(child);
					parent.setLeaf(false);

					//child
					hasParent = true;

					continue;
				}
			}


			if (!hasParent) {
				tops.add(child);
			}
		}

		TreeNode<T> root;
		if (tops.size() == 1) {

			root = tops.get(0);
		} else {

			root = new TreeNode<T>();
			root.setLeaf(false);
			root.setId("-1");
			root.setName("root");
			root.setParentId("");

			root.getChildren().addAll(tops);
		}

		return root;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.name = text;
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.text = name;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public List<TreeNode<T>> getChildren() {
		return children;
	}

	public Boolean getLeaf() {
		return this.leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
		this.isParent = !leaf;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
		this.leaf = !isParent;
	}

	public T getNodeData() {
		return nodeData;
	}

	public void setNodeData(T nodeData) {
		this.nodeData = nodeData;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
