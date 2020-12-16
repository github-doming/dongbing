package org.doming.example.tree.binary.eg3.segment;
/**
 * @Author: Dongming
 * @Date: 2020-02-19 15:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SegmentTree01 {

	//父节点
	Node root;

	class Node {
		int left, right;//左右区间的值
		boolean cover;//表示是否被覆盖
		int count;//表示此节点表示的线段区间出现的次数（被覆盖的次数），
		Node leftChild;
		Node rightChild;

		/**
		 * 默认没有被覆盖
		 * 默认被覆盖的次数为0
		 *
		 * @param left  左区间的值
		 * @param right 右区间的值
		 */
		public Node(int left, int right) {
			this.left = left;
			this.right = right;
			cover = false;
			count = 0;
		}
	}

	/**
	 * 外部接口
	 * 建立一棵线段树
	 */
	public void build(int left, int right) {
		root = new Node(left, right);
		build(root);
	}

	/**
	 * 内部接口
	 * 建立一棵线段树
	 */
	private void build(Node root) {
		int left = root.left;
		int right = root.right;
		//root节点为叶子节点
		if (right - left == 1) {
			return;
		}
		if (right - left > 1) {
			//将左右区间平分
			int mid = (left + right) >> 1;
			Node leftNode = new Node(left, mid);
			Node rightNode = new Node(mid, right);
			root.leftChild = leftNode;
			root.rightChild = rightNode;
			// 递归的创建左右子树
			build(leftNode);
			build(rightNode);
		}
	}

	/**
	 * 插入一条线段[left,right]的内部接口
	 *
	 * @param left  左端点
	 * @param right 右端点
	 */
	public void insert(int left, int right) {
		insert(left, right, root);
	}

	/**
	 * 插入一条线段[left,right]的内部接口
	 *
	 * @param left  左端点
	 * @param right 右端点
	 * @param node  此线段树的根节点
	 */
	private void insert(int left, int right, Node node) {
		//线段不包含
		if (node == null || left < node.left || right > node.right) {
			System.out.println("输入的参数不合法!" + "left:" + left + " " + "right:" + right);
			System.out.println("root:" + node.left + " " + node.right);
			return;
		}
		//线段全包含
		if (left == node.left && right == node.right) {
			node.count++;
			node.cover = true;
			return;
		}
		//将左右区间平分
		int mid = (node.left + node.right) >> 1;
		if (right <= mid) {
			//线段包含在左子树
			insert(left, right, node.leftChild);
		} else if (left >= mid) {
			//线段包含在右子树
			insert(left, right, node.rightChild);
		} else {
			//线段在左右子树都包含
			insert(left, mid, node.leftChild);
			insert(mid, right, node.rightChild);
		}
	}

	/**
	 * 删除一条线段left,right的外部接口
	 *
	 * @param left  删除线段的左端点
	 * @param right 删除线段的右端点
	 */
	public void delete(int left, int right) {
		delete(left, right, root);
	}

	/**
	 * 删除一条线段left,right的外部接口
	 *
	 * @param left  删除线段的左端点
	 * @param right 删除线段的右端点
	 * @param node  删除线段树的根结点
	 */
	private void delete(int left, int right, Node node) {
		//线段不包含
		if (node == null || left < node.left || right > node.right) {
			System.out.println("输入的参数不合法!" + "left:" + left + " " + "right:" + right);
			return;
		}
		if (left == node.left && right == node.right) {
			if (--node.count == 0) {
				node.cover = false;
			}
			return;
		}
		//将左右区间平分
		int mid = (node.left + node.right) >> 1;
		if (right <= mid) {
			//线段包含在左子树
			delete(left, right, node.leftChild);
		} else if (left >= mid) {
			//线段包含在右子树
			delete(left, right, node.rightChild);
		} else {
			//线段在左右子树都包含
			delete(left, mid, node.leftChild);
			delete(mid, right, node.rightChild);
		}
	}

	/**
	 * 前序遍历
	 * 外部接口
	 */
	public void preOrder() {
		preOrder(root);
	}

	/**
	 * 前序遍历
	 * 内部接口
	 */
	private void preOrder(Node node) {
		// 叶子节点
		if (node.right - node.left == 1) {
			System.out.println("["+node.left+","+node.right+"]:"+node.count);
		}else if (node.right - node.left > 1){
			System.out.println("["+node.left+","+node.right+"]:"+node.count);
			preOrder(node.leftChild);
			preOrder(node.rightChild);
		}
	}

	/**
	 * 外部接口
	 * 统计线段树中cover为true的线段的总长度
	 * @return  总长度
	 */
	public  int count(){
		return count(root);
	}

	/**
	 * 内部接口
	 * 统计线段树中cover为true的线段的总长度
	 * @param node 统计节点
	 * @return 总长度
	 */
	private int count(Node node) {
		if (node == null){
			return 0;
		}
		if (node.cover){
			return node.right - node.left;
		}
		if (node.right - node.left == 1){
			return 0;
		}
		return count(node.leftChild) + count(node.rightChild);
	}
	public static void main(String[] args) {
		SegmentTree01 segmentTree = new SegmentTree01();
		segmentTree.build(1,10);
		segmentTree.insert(3,5);
		segmentTree.insert(3,5);
		segmentTree.insert(2,5);
		segmentTree.insert(3,9);
		segmentTree.insert(1,10);
		System.out.println("前序遍历线段树:");
		segmentTree.preOrder();
	}
}
