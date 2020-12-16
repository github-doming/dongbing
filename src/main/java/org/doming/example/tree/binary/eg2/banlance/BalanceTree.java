package org.doming.example.tree.binary.eg2.banlance;
import org.junit.Test;
/**
 * @Description: 平衡二叉树
 * @Author: Dongming
 * @Date: 2019-12-13 14:00
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BalanceTree<T extends Comparable<T>> {

	static class Node<T> {
		Node<T> parent;
		Node<T> leftChild;
		Node<T> rightChild;
		T data;

		public Node(Node<T> parent, Node<T> leftChild, Node<T> rightChild, T data) {
			this.parent = parent;
			this.leftChild = leftChild;
			this.rightChild = rightChild;
			this.data = data;
		}

		public Node(T data) {
			this(null, null, null, data);
		}
		public Node(Node<T> parent, T data) {
			this(parent, null, null, data);
		}

		public void print() {
			if (this.leftChild != null) {
				this.leftChild.print();
			}
			System.out.print(data + ",");
			if (this.rightChild != null) {
				this.rightChild.print();
			}
		}
	}

	/**
	 * 表示根元素
	 */
	private Node<T> root;

	/**
	 * 左旋
	 *
	 * @param node A
	 * @return B
	 */
	public Node<T> leftRotation(Node<T> node) {
		if (node == null) {
			return null;
		}
		// 用变量存储node节点的右子节点
		Node<T> rightChild = node.rightChild;
		// 将 rightChild 节点的左子节点赋值给node节点的右节点
		node.rightChild = rightChild.leftChild;
		if (rightChild.leftChild != null) {
			rightChild.parent = node;
		}
		rightChild.parent = node.parent;
		// 即表明node节点为根节点
		if (node.parent == null) {
			this.root = rightChild;
		} else if (node.parent.rightChild == node) {
			node.parent.rightChild = rightChild;
			// 即node节点在它原父节点的右子树中
		} else if (node.parent.leftChild == node) {

			// 即node节点在它原父节点的左子树中
			node.parent.leftChild = rightChild;
		}
		rightChild.leftChild = node;
		node.parent = rightChild;
		return rightChild;
	}

	/**
	 * 右旋
	 *
	 * @param node A
	 * @return B
	 */
	public Node<T> rightRotation(Node<T> node) {
		if (node == null) {
			return null;
		}
		// 用变量存储node节点的左子节点
		Node<T> leftChild = node.leftChild;
		// 将leftChild节点的右子节点赋值给node节点的左节点
		node.leftChild = leftChild.rightChild;
		if (leftChild.rightChild != null) {
			leftChild.rightChild.parent = node;
		}
		leftChild.parent = node.parent;

		// 即表明node节点为根节点
		if (node.parent == null) {
			this.root = leftChild;
		} else if (node.parent.rightChild == node) {
			// 即node节点在它原父节点的右子树中
			node.parent.rightChild = leftChild;
		} else if (node.parent.leftChild == node) {
			// 即node节点在它原父节点的左子树中
			node.parent.leftChild = leftChild;
		}
		leftChild.rightChild = node;
		node.parent = leftChild;
		return leftChild;

	}

	int size = 0;

	public boolean put(T data) {
		return putVal(root, data);
	}

	private boolean putVal(Node<T> node, T data) {
		if (node == null) {
			node = new Node<>(data);
			root = node;
			size++;
			return true;
		}
		Node<T> temp = node, parent;
		int compare;
		do {
			parent = temp;
			compare = data.compareTo(temp.data);
			if (compare > 0) {
				temp = temp.rightChild;
			} else if (compare < 0) {
				temp = temp.leftChild;
			} else {
				temp.data = data;
				return false;
			}
		} while (temp != null);
		Node<T> newNode = new Node<>(parent, data);
		if (compare > 0) {
			parent.rightChild = newNode;
		} else {
			parent.leftChild = newNode;
		}
		// 使二叉树平衡
		rebuild(parent);
		size++;
		return true;
	}
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	private void rebuild(Node<T> node) {
		int balanceValue;
		while (node != null) {
			balanceValue = calcNodeBalanceValue(node);
			// 说明左子树高，需要右旋或者 先左旋后右旋
			if (balanceValue == 2) {
				// 调整操作
				fixAfterInsertion(node, LEFT);
			} else if (balanceValue == -2) {
				fixAfterInsertion(node, RIGHT);
			}
			node = node.parent;
		}



	}
	private int calcNodeBalanceValue(Node<T> node) {
		if (node == null) {
			return 0;
		}
		return getChildDepth(node.leftChild) - getChildDepth(node.rightChild);
	}
	public int getChildDepth(Node<T> node) {
		if (node == null) {
			return 0;
		}
		return 1 + Math.max(getChildDepth(node.leftChild), getChildDepth(node.rightChild));
	}
	private void fixAfterInsertion(Node<T> node, int type) {
		//需要右旋或者 先左旋后右旋
		if (type == LEFT) {
			final Node<T> leftChild = node.leftChild;
			if (leftChild.rightChild != null) {
				leftRotation(leftChild);
				rightRotation(node);
			} else if (leftChild.leftChild != null) {
				rightRotation(node);
			}
		} else {
			final Node<T> rightChild = node.rightChild;
			if (rightChild.leftChild != null) {
				rightRotation(node);
				leftRotation(rightChild);
			} else if (rightChild.rightChild != null) {
				leftRotation(node);
			}
		}
	}

	public void print() {
		if (root != null) {
			root.print();
		}
	}
	@Test public void test() {
		BalanceTree<Integer> balanceTree = new BalanceTree<>();
		balanceTree.put(10);
		balanceTree.put(9);
		balanceTree.put(11);
		balanceTree.put(7);
		balanceTree.put(12);
		balanceTree.put(8);
		balanceTree.put(38);
		balanceTree.put(24);
		balanceTree.put(17);
		balanceTree.put(4);
		balanceTree.put(3);
		balanceTree.put(1);
		balanceTree.print();
	}


	@Test public void test2(){
		BalanceTree<Integer> balanceTree = new BalanceTree<>();
		balanceTree.put(5);
		balanceTree.put(1);
		balanceTree.put(10);
		balanceTree.put(7);
		balanceTree.put(18);
		balanceTree.put(19);
		balanceTree.print();

	}
}
