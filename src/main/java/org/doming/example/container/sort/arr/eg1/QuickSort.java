package org.doming.example.container.sort.arr.eg1;
import org.doming.core.tools.RandomTool;
import org.junit.Test;

import java.util.Arrays;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-10-11 16:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class QuickSort {

	@Test public void test01() {

		int[] arr = RandomTool.getInt(1, 50000000, 50000000);
		int[] arr1 = Arrays.copyOf(arr, arr.length);

		long startTime = System.currentTimeMillis(), endTime;
		quickSort(arr, 0, arr.length - 1);
		endTime = System.currentTimeMillis();
		System.out.println("耗时为：" + (endTime - startTime) + "nms");
//		System.out.println(Arrays.toString(arr));

		startTime = System.currentTimeMillis();
		quickSort1(arr1, 0, arr1.length - 1);
		endTime = System.currentTimeMillis();
		System.out.println("耗时为：" + (endTime - startTime) + "nms");
//		System.out.println(Arrays.toString(arr1));

	}
	private void quickSort(int[] arr, int left, int right) {

		//设置最左边的元素为基准值
		int key = arr[left];
		//数组中比key小的放在左边，比key大的放在右边，key值下标为i
		int i = left;
		int j = right;
		while (i < j) {
			//j向左移，直到遇到比key小的值
			while (i < j && arr[j] > key) {
				j--;
			}
			//i向右移，直到遇到比key大的值
			while (i < j && arr[i] < key) {
				i++;
			}
			//i向右移，数值相等
			if (i < j && arr[i] == arr[j]) {
				i++;
			} else {
				//i和j指向的元素交换
				int tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
			}
		}
		//左侧排序
		if (i - 1 > left) {
			quickSort(arr, left, i - 1);
		}
		//右侧排序
		if (j + 1 < right) {
			quickSort(arr, j + 1, right);
		}
	}

	private void quickSort1(int[] arr, int left, int right) {
		int i = left, j = right;
		int key = arr[left];
		while (i < j) {
			/*按j--方向遍历目标数组，直到比key小的值为止*/
			while (i < j && arr[j] >= key) {
				j--;
			}
			if (i < j) {
				/*targetArr[i]已经保存在key中，可将后面的数填入*/
				arr[i] = arr[j];
				i++;
			}
			/*按i++方向遍历目标数组，直到比key大的值为止*/
			while (i < j && arr[i]<=key)
				/*此处一定要小于等于零，假设数组之内有一亿个1，0交替出现的话，而key的值又恰巧是1的话，那么这个小于等于的作用就会使下面的if语句少执行一亿次。*/ {
				i++;
			}
			if (i < j) {
				/*targetArr[j]已保存在targetArr[i]中，可将前面的值填入*/
				arr[j] = arr[i];
				j--;
			}
		}
		/*此时i==j*/
		arr[i] = key;

		/*递归调用，把key前面的完成排序*/
		if (i - 1 > left) {
			quickSort1(arr, left, i - 1);
		}

		/*递归调用，把key后面的完成排序*/
		if (j + 1 < right) {
			quickSort1(arr, j + 1, right);
		}
	}

	public <T extends Comparable<? super T>> T[] quickSort(T[] targetArr, int start, int end) {
		int i = start + 1, j = end;
		T key = targetArr[start];

		SortUtil<T> util = new SortUtil<>();
		if (start == end) {
			return (targetArr);
		}
		/*
		 * 从i++和j--两个方向搜索不满足条件的值并交换
		 * 条件为：i++方向小于key，j--方向大于key
		 */
		while (true) {
			while (targetArr[j].compareTo(key) > 0) {
				j--;
			}
			while (targetArr[i].compareTo(key) < 0 && i < j) {
				i++;
			}
			if (i >= j) {
				break;
			}
			util.swap(targetArr, i, j);
			if (targetArr[i] == key) {
				j--;
			} else {
				i++;
			}

		}
		/* 关键数据放到‘中间’*/
		util.swap(targetArr, start, j);
		if (i - 1 > start) {
			quickSort(targetArr, start, i - 1);
		}
		if (j + 1 < end) {
			this.quickSort(targetArr, j + 1, end);
		}
		return targetArr;
	}
	public <T extends Comparable<? super T>> T[] quickSort1(T[] targetArr, int start, int end) {
		int i = start, j = end;
		T key = targetArr[start];
		while (i < j) {
			/*按j--方向遍历目标数组，直到比key小的值为止*/
			while (i < j && targetArr[j].compareTo(key) >= 0) {
				j--;
			}
			if (i < j) {
				/*targetArr[i]已经保存在key中，可将后面的数填入*/
				targetArr[i] = targetArr[j];
				i++;
			}
			/*按i++方向遍历目标数组，直到比key大的值为止*/
			while (i < j && targetArr[i].compareTo(key) <= 0)
				/*此处一定要小于等于零，假设数组之内有一亿个1，0交替出现的话，而key的值又恰巧是1的话，那么这个小于等于的作用就会使下面的if语句少执行一亿次。*/ {
				i++;
			}
			if (i < j) {
				/*targetArr[j]已保存在targetArr[i]中，可将前面的值填入*/
				targetArr[j] = targetArr[i];
				j--;
			}
		}
		/*此时i==j*/
		targetArr[i] = key;

		/*递归调用，把key前面的完成排序*/
		if (i - 1 > start) {
			quickSort1(targetArr, start, i - 1);
		}

		/*递归调用，把key后面的完成排序*/
		if (j + 1 < end) {
			quickSort1(targetArr, j + 1, end);
		}
		return targetArr;
	}
}
