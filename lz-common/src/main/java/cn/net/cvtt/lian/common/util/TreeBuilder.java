package cn.net.cvtt.lian.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 构造一棵多叉树
 * @author cqzong
 *
 */
public class TreeBuilder {
	private TreeNode root;
	private List<TreeNode> tempNodeList;
	private boolean isValidTree = true;

	public TreeBuilder() {
	}

	public TreeBuilder(List<TreeNode> treeNodeList) {
		tempNodeList = treeNodeList;
		generateTree();
	}
	
	public TreeNode getTreeNodeById(int id) {
		if (root == null)
			return null;
		TreeNode treeNode = root.findTreeNodeById(id);
		return treeNode;
	}
	
	public static TreeNode getTreeNodeById(TreeNode tree, int id) {
		if (tree == null)
			return null;
		TreeNode treeNode = tree.findTreeNodeById(id);
		return treeNode;
	}

	public void generateTree() {
		Map<String, TreeNode> nodeMap = putNodesIntoMap();
		putChildIntoParent(nodeMap);
	}

	private Map<String, TreeNode> putNodesIntoMap() {
		int maxId = 0;
		Map<String, TreeNode> nodeMap = new HashMap<String, TreeNode>();
		Iterator<TreeNode> it = tempNodeList.iterator();
		while (it.hasNext()) {
			TreeNode treeNode = (TreeNode) it.next();
			int id = treeNode.getSelfId();
			if (id > maxId) {
				maxId = id;
				this.root = treeNode;
			}
			String keyId = String.valueOf(id);

			nodeMap.put(keyId, treeNode);
		}
		return nodeMap;
	}

	protected void putChildIntoParent(Map<String, TreeNode> nodeMap) {
		Iterator<TreeNode> it = nodeMap.values().iterator();
		while (it.hasNext()) {
			TreeNode treeNode = (TreeNode) it.next();
			int parentId = treeNode.getParentId();
			String parentKeyId = String.valueOf(parentId);
			if (nodeMap.containsKey(parentKeyId)) {
				TreeNode parentNode = (TreeNode) nodeMap.get(parentKeyId);
				if (parentNode == null) {
					this.isValidTree = false;
					return;
				} else {
					parentNode.addChildNode(treeNode);
				}
			}
		}
	}

	public void addTreeNode(TreeNode treeNode) {
		if (this.tempNodeList == null) {
			this.tempNodeList = new ArrayList<TreeNode>();
		}
		this.tempNodeList.add(treeNode);
	}

	public boolean insertTreeNode(TreeNode treeNode) {
		boolean insertFlag = root.insertJuniorNode(treeNode);
		return insertFlag;
	}

	public boolean isValidTree() {
		return this.isValidTree;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public List<TreeNode> getTempNodeList() {
		return tempNodeList;
	}

	public void setTempNodeList(List<TreeNode> tempNodeList) {
		this.tempNodeList = tempNodeList;
	}

}
