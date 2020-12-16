package org.doming.example.container.sort.arr.eg1;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-10-11 16:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SortUtil<T> {

	public void swap(T[] targetArr, int i, int j) {
		T tmp = targetArr[i];
		targetArr[i] = targetArr[j];
		targetArr[j] = tmp;
	}
}
