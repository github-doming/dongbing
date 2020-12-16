package org.doming.develop.system.service.impl;


import com.sun.management.OperatingSystemMXBean;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.system.bean.CpuInfoBean;
import org.doming.develop.system.bean.DiskInfoBean;
import org.doming.develop.system.bean.MemoryInfoBean;
import org.doming.develop.system.bean.MonitorInfoBean;
import org.doming.develop.system.service.MonitorService;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 获取系统信息的业务逻辑实现类
 *
 * @Author: Dongming
 * @Date: 2020-04-15 11:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MonitorServiceImpl implements MonitorService {

	private static final int HEX = 1024;

	@Override
	public MonitorInfoBean getMonitorInfoBean() throws Exception {
		CpuInfoBean cpuInfo = getCpuInfo();
		MemoryInfoBean memoryInfo = getMemoryInfo();
		List<DiskInfoBean> diskInfoBean = getDiskInfos();
		MonitorInfoBean infoBean = new MonitorInfoBean();
		return infoBean.attr(cpuInfo, memoryInfo, diskInfoBean);
	}

	@Override
	public CpuInfoBean getCpuInfo() throws Exception {
		// 构造返回对象
		CpuInfoBean infoBean = new CpuInfoBean();
		// 操作系统
		String osName = System.getProperty("os.name");
		// 获得线程总数
		int totalThread = getTotalThread();

		// cpu使用率
		double cpuRatio = getCpuRatio(osName);

		infoBean.setOsName(osName);
		infoBean.setTotalThread(totalThread);
		infoBean.setCpuRatio(cpuRatio);

		return infoBean;
	}

	@Override
	public MemoryInfoBean getMemoryInfo() {
		// 现有总内存
		long totalJvmMemory = Runtime.getRuntime().totalMemory() / HEX;
		// 剩余内存
		long freeJvmMemory = Runtime.getRuntime().freeMemory() / HEX;
		// 已使用内存
		long usedJvmMemory = totalJvmMemory - freeJvmMemory;
		// 最大
		long maxJvmMemory = Runtime.getRuntime().maxMemory() / HEX;

		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();

		// 总内存
		long totalPhysicalMemory = osmxb.getTotalPhysicalMemorySize() / HEX;
		// 剩余内存
		long freePhysicalMemory = osmxb.getFreePhysicalMemorySize() / HEX;
		// 已使用内存
		long usedPhysicalMemory = totalPhysicalMemory - freePhysicalMemory;

		// 构造返回对象
		MemoryInfoBean infoBean = new MemoryInfoBean();
		infoBean.setFreeJvmMemory(freeJvmMemory);
		infoBean.setTotalJvmMemory(totalJvmMemory);
		infoBean.setUsedJvmMemory(usedJvmMemory);
		infoBean.setMaxJvmMemory(maxJvmMemory);


		infoBean.setTotalPhysicalMemory(totalPhysicalMemory);
		infoBean.setFreePhysicalMemory(freePhysicalMemory);
		infoBean.setUsedPhysicalMemory(usedPhysicalMemory);
		return infoBean;
	}

	@Override
	public List<DiskInfoBean> getDiskInfos() {
		List<DiskInfoBean> diskInfos = new ArrayList<>();
		// 获取磁盘分区列表
		File[] roots = File.listRoots();
		for (File file : roots) {
			DiskInfoBean diskInfoBean = new DiskInfoBean();
			//磁盘名称
			String path = file.getPath();
			//空闲空间
			long freeSpace = file.getFreeSpace() / HEX;
			//总空间
			long totalSpace = file.getTotalSpace() / HEX;
			//使用空间
			long usableSpace = (totalSpace - freeSpace);

			diskInfoBean.setPath(path);
			diskInfoBean.setFreeSpace(freeSpace);
			diskInfoBean.setTotalSpace(totalSpace);
			diskInfoBean.setUsableSpace(usableSpace);
			diskInfos.add(diskInfoBean);
		}
		return diskInfos;
	}

	//region 获取CPU信息

	private static final int CPU_TIME = 500;

	private static final int PERCENT = 100;

	private static final int FAULT_LENGTH = 10;

	/**
	 * 获取线程数
	 *
	 * @return 线程数
	 */
	private int getTotalThread() {
		ThreadGroup parentThread;
		parentThread = Thread.currentThread().getThreadGroup();
		while (parentThread.getParent() != null) {
			parentThread = parentThread.getParent();
		}
		return parentThread.activeCount();
	}


	//region WINDOWS CPU获取

	/**
	 * 获取 cpu利用率
	 *
	 * @param osName 操作系统名称
	 * @return cpu利用率
	 */
	private double getCpuRatio(String osName) throws IOException, InterruptedException {
		if (osName.toLowerCase().startsWith("windows")) {
			return this.getCpuRatioForWindows();
		} else {
			return this.getCpuRateForLinux();
		}
	}

	/**
	 * 获得CPU使用率.
	 *
	 * @return 返回cpu使用率
	 */
	private double getCpuRatioForWindows() throws InterruptedException, IOException {
		String procCmd = System.getenv("windir")
				+ "//system32//wbem//wmic.exe process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
		// 取进程信息
		long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
		Thread.sleep(CPU_TIME);
		long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
		if (c0 != null && c1 != null) {
			long idletime = c1[0] - c0[0];
			long busytime = c1[1] - c0[1];
			return (double) (PERCENT * (busytime) / (busytime + idletime));
		} else {
			return 0.0;
		}

	}

	/**
	 * 读取CPU信息.
	 *
	 * @param proc 线程
	 * @return 线程 信息.
	 */
	private long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < FAULT_LENGTH) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;
			long usertime = 0;
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperation
				String caption = StringTool.subString(line, capidx, cmdidx - 1)
						.trim();
				String cmd = StringTool.subString(line, cmdidx, kmtidx - 1).trim();
				if (cmd.contains("wmic.exe")) {
					continue;
				}
				if ("System Idle Process".equals(caption) || "System".equals(caption)) {
					idletime += NumberTool.getLong(StringTool.subString(line, kmtidx, rocidx - 1), 0L);
					idletime += NumberTool.getLong(StringTool.subString(line, umtidx, wocidx - 1), 0L);
					continue;
				}
				kneltime += NumberTool.getLong(StringTool.subString(line, kmtidx, rocidx - 1), 0L);
				usertime += NumberTool.getLong(StringTool.subString(line, umtidx, wocidx - 1), 0L);
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;
			return retn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	//endregion

	//region Linux CPU获取

	/**
	 * 获取Linux CPU占用率
	 *
	 * @return CPU占用率
	 */
	private double getCpuRateForLinux() throws IOException {
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader brStat = null;
		StringTokenizer tokenStat;
		try {
			Process process = Runtime.getRuntime().exec("top -b -n 1");
			is = process.getInputStream();
			isr = new InputStreamReader(is);
			brStat = new BufferedReader(isr);
			brStat.readLine();
			brStat.readLine();

			tokenStat = new StringTokenizer(brStat.readLine());
			tokenStat.nextToken();
			tokenStat.nextToken();
			tokenStat.nextToken();
			tokenStat.nextToken();
			tokenStat.nextToken();
			tokenStat.nextToken();
			tokenStat.nextToken();

			String cpuUsage = tokenStat.nextToken();
			Float usage = new Float(cpuUsage.substring(0, cpuUsage.indexOf("%")));
			return (1 - usage / 100);

		} catch (Exception e) {
			return 0;
		} finally {
			freeResource(is, isr, brStat);
		}

	}

	/**
	 * 释放资源
	 *
	 * @param is  输入流
	 * @param isr 输入流读取
	 * @param br  读取
	 */
	private static void freeResource(InputStream is, InputStreamReader isr, BufferedReader br) {
		try {
			if (is != null) {
				is.close();
			}
			if (isr != null) {
				isr.close();
			}
			if (br != null) {
				br.close();
			}
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}
	//endregion
	//endregion


}
