package cn.net.cvtt.lian.common.serialization.protobuf;

import java.util.List;

public class Node {

	private boolean isLeaf;

	private String name;

	private String value;

	private List<Node> nodes;

	private Node(boolean isLeaf, String name, String value, List<Node> nodes) {
		this.isLeaf = isLeaf;
		this.name = name;
		this.value = value;
		this.nodes = nodes;
	}

	public static Node createLeafNode(String name, String value) {
		return new Node(true, name, value, null);
	}

	public static Node createDirNode(String name, List<Node> nodes) {
		return new Node(false, name, null, nodes);
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

}
