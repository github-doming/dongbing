package org.doming.example.tree.binary.eg1.sort;
/**
 * @Description: 二叉树排序
 * @Author: Dongming
 * @Date: 2019-12-12 11:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BinaryTree<T extends Comparable<T>> {
	/**
	 * 声明一个节点类
	 *
	 * @param <T> 具有继承比较接口的泛型
	 * @see Comparable
	 */
	static class Node<T extends Comparable<T>> implements Comparable<Node<T>> {
		//节点的数据类型
		private T data;
		//保存左子树
		private Node<T> left;
		//保存右子树
		private Node<T> right;
		//构造函数
		public Node(T data) {
			this.data = data;
		}
		public void addNode(Node<T> newNode) {
			//确定是放在左子树还是右子树
			if (newNode.compareTo(this) < 0) {
				//新节点值小于当前节点
				if (this.left == null) {
					//左子树为空的话，新节点设为左子树
					this.left = newNode;
				} else {
					//否则继续向下判断
					this.left.addNode(newNode);
				}
			} else {
				//新节点的值大于或等于当前节点
				if (this.right == null) {
					this.right = newNode;
				} else {
					this.right.addNode(newNode);
				}
			}
		}

		/**
		 * 采用中序遍历
		 */
		public void printNodeLdr() {
			//如果不为空先输出左子树
			if (this.left != null) {
				this.left.printNodeLdr();
			}
			//输出当前根节点
			System.out.print(this.data + "\t");
			//输出右子树
			if (this.right != null) {
				this.right.printNodeLdr();
			}
		}

		public void printNodeDlr() {
			System.out.print(this.data + "\t");
			if (this.left != null) {
				this.left.printNodeDlr();
			}
			if (this.right != null) {
				this.right.printNodeDlr();
			}
		}

		public void printNodeLrd() {
			if (this.left != null) {
				this.left.printNodeLrd();
			}
			if (this.right != null) {
				this.right.printNodeLrd();
			}
			System.out.print(this.data + "\t");
		}

		@Override public int compareTo(Node<T> newNode) {
			return this.data.compareTo(newNode.data);
		}
	}
	/**
	 * 表示根元素
	 */
	private Node<T> root;
	/**
	 * 向二叉树中插入元素
	 *
	 * @param data 元素
	 */
	public void add(T data) {
		Node<T> newNode = new Node<>(data);
		//没有根节点
		if (root == null) {
			root = newNode;
		} else {
			//判断放在左子树还是右子树
			root.addNode(newNode);
		}
	}
	public void printNodeLdr() {
		//根据根节点输出
		root.printNodeLdr();
	}
	public void printNodeDlr() {
		//根据根节点输出
		root.printNodeDlr();
	}
	public void printNodeLrd() {
		//根据根节点输出
		root.printNodeLrd();
	}

	public static void main(String[] args) {
		BinaryTree<String> binaryTree = new BinaryTree<>();
		binaryTree.add("3");
		binaryTree.add("5");
		binaryTree.add("1");
		binaryTree.add("4");
		binaryTree.add("2");
		binaryTree.printNodeLdr();
		System.out.println();
		binaryTree.printNodeDlr();
		System.out.println();
		binaryTree.printNodeLrd();
	}
}
