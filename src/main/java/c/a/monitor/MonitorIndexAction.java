package c.a.monitor;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Properties;
import com.sun.management.OperatingSystemMXBean;
import c.a.monitor.MonitorUtil;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.math.MathUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.AyAction;
public class MonitorIndexAction extends AyAction {
	@Override
	public String execute() throws RuntimeException, Exception {
		// http://localhost:8080/a/c/a/monitor/index.do
		/**
		 * 系统监控
		 */
		StringBuilder stringBuilder = new StringBuilder();
		try {
			//request.setAttribute("line", StringUtil.findLine2());
			stringBuilder.append(StringUtil.findLine2());
			stringBuilder.append("</br>");
			// ip
			String ip = RequestThreadLocal.getThreadLocal().get().findIP(request);
			String ipLocal = RequestThreadLocal.getThreadLocal().get().findIPLocal();
			//获取访问者ip
			stringBuilder.append("ip=" + ip);
			stringBuilder.append("</br>");
			// 找出本地服务器IP
			stringBuilder.append("ip local=" + ipLocal);
			stringBuilder.append("</br>");
			stringBuilder.append("ip X-Forwarded-For=" + request.getHeader("x-forwarded-for"));
			stringBuilder.append("</br>");
			//获取访问者ip
			stringBuilder.append("ip RemoteAddr=" + request.getRemoteAddr());
			stringBuilder.append("</br>");
			stringBuilder.append("ip X-Real-IP=" + request.getHeader("X-Real-IP"));
			stringBuilder.append("</br>");
			stringBuilder.append("ip Proxy-Client-IP=" + request.getHeader("Proxy-Client-IP"));
			stringBuilder.append("</br>");
			stringBuilder.append("ip WL-Proxy-Client-IP=" + request.getHeader("WL-Proxy-Client-IP"));
			stringBuilder.append("</br>");
			// 虚拟机级内存情况查询
			long vmUse = 0;
			long vmFree = 0;
			long vmTotal = 0;
			long vmMax = 0;
			int byteToMb = 1024 * 1024;
			OperatingSystemMXBean operatingSystemMXBean = (OperatingSystemMXBean) ManagementFactory
					.getOperatingSystemMXBean();
			String os = System.getProperty("os.name");
			stringBuilder.append("操作系统的名称=" + os);
			stringBuilder.append("</br>");
			// 获得线程总数
			ThreadGroup parentThread;
			int totalThread = 0;
			for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
					.getParent() != null; parentThread = parentThread.getParent()) {
				totalThread = parentThread.activeCount();
			}
			stringBuilder.append("获得线程总数=" + totalThread);
			stringBuilder.append("</br>");
			// 操作系统级内存情况查询
			long physicalFree = operatingSystemMXBean.getFreePhysicalMemorySize() / byteToMb;
			long physicalTotal = operatingSystemMXBean.getTotalPhysicalMemorySize() / byteToMb;
			long physicalUse = physicalTotal - physicalFree;
			stringBuilder.append("操作系统总物理内存=" + physicalTotal + " MB");
			stringBuilder.append("</br>");
			String memoryRate = MathUtil.doFormatNumber(physicalUse * 100 / (float) physicalTotal);
			request.setAttribute("memoryRate", memoryRate);
			stringBuilder.append("操作系统物理内存使用率为=" + memoryRate + "%");
			stringBuilder.append("</br>");
			stringBuilder.append("操作系统物理内存已用的空间为=" + physicalUse + " MB");
			stringBuilder.append("</br>");
			stringBuilder.append("操作系统物理内存的空闲空间为=" + physicalFree + " MB");
			stringBuilder.append("</br>");
			// vm内存情况查询
			Runtime runtime = Runtime.getRuntime();
			vmTotal = runtime.totalMemory() / byteToMb;
			vmFree = runtime.freeMemory() / byteToMb;
			vmMax = runtime.maxMemory() / byteToMb;
			vmUse = vmTotal - vmFree;
			String vmRate = MathUtil.doFormatNumber(vmUse * 100 / (float) vmTotal);
			request.setAttribute("vmRate", vmRate);
			stringBuilder.append("JVM内存使用率为=" + vmRate + "%");
			stringBuilder.append("</br>");
			stringBuilder.append("JVM最大内存空间为=" + vmMax + " MB");
			stringBuilder.append("</br>");
			stringBuilder.append("JVM总内存空间为=" + vmTotal + " MB");
			stringBuilder.append("</br>");
			stringBuilder.append("JVM内存已用的空间为=" + vmUse + " MB");
			stringBuilder.append("</br>");
			stringBuilder.append("JVM内存的空闲空间为=" + vmFree + " MB");
			stringBuilder.append("</br>");
			stringBuilder.append("CPU数量=" + runtime.availableProcessors());
			stringBuilder.append("</br>");
			stringBuilder.append(StringUtil.findLine2());
			stringBuilder.append("</br>");
			MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
			MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage();
			stringBuilder.append("INT HEAP=" + memoryUsage.getInit() / byteToMb + "Mb");
			stringBuilder.append("</br>");
			stringBuilder.append("MAX HEAP=" + memoryUsage.getMax() / byteToMb + "Mb");
			stringBuilder.append("</br>");
			stringBuilder.append("USED HEAP=" + memoryUsage.getUsed() / byteToMb + "Mb");
			stringBuilder.append("</br>");
			stringBuilder.append("Full Information=");
			stringBuilder.append("</br>");
			stringBuilder.append("Heap Memory Usage=" + memoryMXBean.getHeapMemoryUsage());
			stringBuilder.append("</br>");
			stringBuilder.append("Non-Heap Memory Usage=" + memoryMXBean.getNonHeapMemoryUsage());
			stringBuilder.append("</br>");
			RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
			List<String> inputArguments = runtimeMXBean.getInputArguments();
			stringBuilder.append("runtimeMXBean name=" + runtimeMXBean.getName());
			stringBuilder.append("</br>");
			MonitorUtil monitorUtil = new MonitorUtil();
			stringBuilder.append("PID=" + monitorUtil.findJvmPID());
			stringBuilder.append("</br>");
			stringBuilder.append("Locale=" + monitorUtil.findLocale());
			stringBuilder.append("</br>");
			// stringBuffer.append(StringUtil.findLine2());
			stringBuilder.append(StringUtil.findLine2() + "java options");
			stringBuilder.append("</br>");
			stringBuilder.append(inputArguments);
			stringBuilder.append("</br>");
			stringBuilder.append(StringUtil.findLine2() + "java options");
			stringBuilder.append("</br>");
			// 系统属性
			Properties properties = System.getProperties();
			stringBuilder.append("Java的运行环境版本=" + properties.getProperty("java.version"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java的运行环境供应商=" + properties.getProperty("java.vendor"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java供应商的URL=" + properties.getProperty("java.vendor.url"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java的安装路径=" + properties.getProperty("java.home"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java的虚拟机规范版本=" + properties.getProperty("java.vm.specification.version"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java的虚拟机规范供应商=" + properties.getProperty("java.vm.specification.vendor"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java的虚拟机规范名称=" + properties.getProperty("java.vm.specification.name"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java的虚拟机实现版本=" + properties.getProperty("java.vm.version"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java的虚拟机实现供应商=" + properties.getProperty("java.vm.vendor"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java的虚拟机实现名称=" + properties.getProperty("java.vm.name"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java运行时环境规范版本=" + properties.getProperty("java.specification.version"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java运行时环境规范供应商=" + properties.getProperty("java.specification.vender"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java运行时环境规范名称=" + properties.getProperty("java.specification.name"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java的类格式版本号=" + properties.getProperty("java.class.version"));
			stringBuilder.append("</br>");
			stringBuilder.append("Java的类路径=" + properties.getProperty("java.class.path"));
			stringBuilder.append("</br>");
			stringBuilder.append("加载库时搜索的路径列表=" + properties.getProperty("java.library.path"));
			stringBuilder.append("</br>");
			stringBuilder.append("默认的临时文件路径=" + properties.getProperty("java.io.tmpdir"));
			stringBuilder.append("</br>");
			stringBuilder.append("一个或多个扩展目录的路径=" + properties.getProperty("java.ext.dirs"));
			stringBuilder.append("</br>");
			stringBuilder.append("操作系统的名称=" + properties.getProperty("os.name"));
			stringBuilder.append("</br>");
			stringBuilder.append("操作系统的构架=" + properties.getProperty("os.arch"));
			stringBuilder.append("</br>");
			stringBuilder.append("操作系统的版本=" + properties.getProperty("os.version"));
			stringBuilder.append("</br>");
			// 在unix系统中是＂／＂
			stringBuilder.append("文件分隔符=" + properties.getProperty("file.separator"));
			stringBuilder.append("</br>");
			// 在 unix 系统中是＂:＂
			stringBuilder.append("路径分隔符=" + properties.getProperty("path.separator"));
			stringBuilder.append("</br>");
			// 在 unix 系统中是＂/n＂
			stringBuilder.append("行分隔符=" + properties.getProperty("line.separator"));
			stringBuilder.append("</br>");
			stringBuilder.append("用户的账户名称=" + properties.getProperty("user.name"));
			stringBuilder.append("</br>");
			stringBuilder.append("用户的主目录=" + properties.getProperty("user.home"));
			stringBuilder.append("</br>");
			stringBuilder.append("用户的当前工作目录=" + properties.getProperty("user.dir"));
			stringBuilder.append("</br>");
			stringBuilder.append("tomcat的bin=" + properties.getProperty("user.dir"));
			stringBuilder.append("</br>");
			String servletContextPath =request.getServletContext().getRealPath("/");
			stringBuilder.append("servletContextPath=" + servletContextPath);
			stringBuilder.append("</br>");
			String servletPath =request.getServletPath();
			stringBuilder.append("servletPath =" + servletPath );
			stringBuilder.append("</br>");
			String requestURI =request.getRequestURI();
			stringBuilder.append("requestURI =" + requestURI );
			stringBuilder.append("</br>");
			String requestURL =request.getRequestURL().toString();
			stringBuilder.append("requestURL =" + requestURL);
			stringBuilder.append("</br>");
			stringBuilder.append(StringUtil.findLine2());
			request.setAttribute("s", stringBuilder.toString());
			// response.getWriter().print(stringBuffer.toString());
		} catch (Exception e) {
			log.error("error");
			log.error("error", e);
		}
		return CommViewEnum.Default.toString();
	}
}
