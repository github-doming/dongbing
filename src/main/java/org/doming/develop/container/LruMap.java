package org.doming.develop.container;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 基于LRU算法的map集合
 *
 * @Author: Dongming
 * @Date: 2019-12-23 14:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LruMap<K, V> extends LinkedHashMap<K, V> {

	private static final float DEFAULT_LOAD_FACTOR = 0.75f;

	private final Lock lock = new ReentrantLock();

	private int capacity;

	public LruMap(int capacity) {
		super(capacity, DEFAULT_LOAD_FACTOR, true);
		this.capacity = capacity;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
		return size() > capacity;
	}

	@Override
	public boolean containsKey(Object key) {
		lock.lock();
		try {
			return super.containsKey(key);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public V get(Object key) {
		lock.lock();
		try {
			return super.get(key);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public V put(K key, V value) {
		lock.lock();
		try {
			return super.put(key, value);
		} finally {
			lock.unlock();
		}
	}

	@Override
	public int size() {
		lock.lock();
		try {
			return super.size();
		} finally {
			lock.unlock();
		}
	}

	@Override
	public void clear() {
		lock.lock();
		try {
			super.clear();
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 获取复制后的 map.entry 集合
	 * 操作不会修改原map
	 *
	 * @return 数据集
	 */
	public Collection<Map.Entry<K, V>> getAll() {
		lock.lock();
		try {
			return new ArrayList<>(super.entrySet());
		} finally {
			lock.unlock();
		}
	}
}
