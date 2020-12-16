package org.doming.develop.key;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
/**
 * 分布式自增长ID
 *
 * @Author: Dongming
 * @Date: 2020-06-12 18:23
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SnowflakeIdWorker  {

	// 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
	private final static long CODE_TIME_START = 1590451500000L;
	// 机器标识位数
	private final static long WORKER_ID_BITS = 5L;
	// 数据中心标识位数
	private final static long DATACENTER_ID_BITS = 5L;
	// 机器ID最大值
	private final static long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
	// 数据中心ID最大值
	private final static long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);
	// 毫秒内自增位
	private final static long SEQUENCE_BITS = 12L;
	// 机器ID偏左移12位
	private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
	// 数据中心ID左移17位
	private final static long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
	// 时间毫秒左移22位
	private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

	private final static long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
	/* 上次生产id时间戳 */
	private static long lastTimestamp = -1L;
	// 0，并发控制
	private long sequence = 0L;

	// 机器标识id部分
	private final long workerId;
	// 数据标识id部分
	private final long datacenterId;

	public SnowflakeIdWorker () {
		this.datacenterId = getDatacenterId(MAX_DATACENTER_ID);
		this.workerId = getMaxWorkerId(datacenterId, MAX_WORKER_ID);
	}

	public SnowflakeIdWorker (long workerId, long datacenterId) {
		if (workerId > MAX_WORKER_ID || workerId < 0) {
			throw new IllegalArgumentException(
					String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
		}
		if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
			throw new IllegalArgumentException(
					String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATACENTER_ID));
		}
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}
	/**
	 * 获取下一个ID
	 *
	 * @return
	 */
	public synchronized long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) {
			throw new RuntimeException(
					String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds",
							lastTimestamp - timestamp));
		}

		if (lastTimestamp == timestamp) {
			// 当前毫秒内，则+1
			sequence = (sequence + 1) & SEQUENCE_MASK;
			if (sequence == 0) {
				// 当前毫秒内计数满了，则等待下一秒
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else {
			sequence = 0L;
		}
		lastTimestamp = timestamp;
		// ID偏移组合生成最终的ID，并返回ID

		return ((timestamp - CODE_TIME_START) << TIMESTAMP_LEFT_SHIFT) | (datacenterId << DATACENTER_ID_SHIFT) | (
				workerId << WORKER_ID_SHIFT) | sequence;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	/**
	 * <p>
	 * 数据标识id部分
	 * </p>
	 */
	private long getDatacenterId(long maxDatacenterId) {
		long id = 0L;
		try {
			InetAddress ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			if (network == null) {
				id = 1L;
			} else {
				byte[] mac = network.getHardwareAddress();
				id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8)))
						>> 6;
				id = id % (maxDatacenterId + 1);
			}
		} catch (Exception e) {
			System.out.println(" getDatacenterId: " + e.getMessage());
		}
		return id;
	}
	/**
	 * <p>
	 * 获取 maxWorkerId
	 * </p>
	 */
	private long getMaxWorkerId(long datacenterId, long maxWorkerId) {
		StringBuilder mPid = new StringBuilder();
		mPid.append(datacenterId);
		String name = ManagementFactory.getRuntimeMXBean().getName();
		if (!name.isEmpty()) {
			/*
			 * GET jvmPid
			 */
			mPid.append(name.split("@")[0]);
		}
		/*
		 * MAC + PID 的 hashcode 获取16个低位
		 */
		return (mPid.toString().hashCode() & 0xffff) % (maxWorkerId + 1);
	}



}
