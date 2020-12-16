package org.doming.example.tree.binary.eg3.segment;
/**
 * @Author: Dongming
 * @Date: 2020-01-16 17:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SegmentTree {
	class Tree {
		int left;
		int right;
		int sum;
	}
	Tree[] trees;
	int[] input;

	public void bind(int index, int left, int right) {
		trees[index].left = left;
		trees[index].right = right;
		if (left == right) {
			trees[index].sum = input[left];
		}
		int mid = (left + right) >> 1;
		//分别构造左子树和右子树
		bind(index * 2, left, mid);
		bind(index * 2 + 1, mid + 1, right);
		//线段和
		trees[index].sum = trees[index * 2].sum + trees[index * 2 + 1].sum;
	}

	public int search(int index, int left, int right) {
		//如果这个区间被完全包括在目标区间里面，直接返回这个区间的值
		if (trees[index].left >= left && trees[index].right <= right) {
			return trees[index].sum;
		}
		//如果这个区间和目标区间毫不相干，返回0
		if (trees[index].left < left || trees[index].right > right) {
			return 0;
		}
		int sum = 0;
		//如果这个区间的左儿子和目标区间又交集，那么搜索左儿子
		if (trees[index * 2].right >= left) {
			sum += search(index * 2, left, right);
		}
		//如果这个区间的右儿子和目标区间又交集，那么搜索右儿子
		if (trees[index * 2 + 1].left <= right) {
			sum += search(index * 2 + 1, left, right);
		}
		return sum;
	}

}
