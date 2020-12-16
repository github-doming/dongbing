package c.a.monitor.thread;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.mysql.PageMysqlBean;
import c.x.platform.root.common.action.AyAction;
public class MonitorThreadAction extends AyAction {
	@Override
	public String execute() throws RuntimeException, Exception {
		// http://localhost:8080/a/c/a/monitor/thread.do
		/**
		 * 监控所有线程
		 */
		ThreadPoolExecutor executorMvc = (ThreadPoolExecutor) request.getServletContext()
				.getAttribute(SysConfig.ThreadMvcExecutor);
		request.setAttribute("thread_mvc_queue_size", executorMvc.getQueue().size());
		System.out.println("mvc CorePoolSize=" + executorMvc.getCorePoolSize());
		// 线程池的线程数量
		System.out.println("mvc PoolSize=" + executorMvc.getPoolSize());
		// 线程池曾经创建过的最大线程数量。
		System.out.println("mvc LargestPoolSize=" + executorMvc.getLargestPoolSize());
		System.out.println("mvc MaximumPoolSize=" + executorMvc.getMaximumPoolSize());
		// 获取活动的线程数。
		System.out.println("mvc ActiveCount=" + executorMvc.getActiveCount());
		// 线程池需要执行的任务数量。
		System.out.println("mvc TaskCount=" + executorMvc.getTaskCount());
		// 线程池在运行过程中已完成的任务数量。小于或等于taskCount。
		System.out.println("mvc CompletedTaskCount=" + executorMvc.getCompletedTaskCount());
		System.out.println("mvc KeepAliveTime=" + executorMvc.getKeepAliveTime(TimeUnit.MILLISECONDS));
		request.setAttribute("thread_mvc_executor_keepAliveTime", executorMvc.getKeepAliveTime(TimeUnit.MILLISECONDS));
		ThreadPoolExecutor executorLocal = (ThreadPoolExecutor) request.getServletContext()
				.getAttribute(SysConfig.ThreadLocalExecutor);
		request.setAttribute("thread_local_queue_size", executorLocal.getQueue().size());
		System.out.println("Local Queue().size()=" + executorLocal.getQueue().size());
		System.out.println("Local CorePoolSize=" + executorLocal.getCorePoolSize());
		System.out.println("Local PoolSize=" + executorLocal.getPoolSize());
		System.out.println("Local LargestPoolSize=" + executorLocal.getLargestPoolSize());
		System.out.println("Local MaximumPoolSize=" + executorLocal.getMaximumPoolSize());
		System.out.println("Local ActiveCount=" + executorLocal.getActiveCount());
		System.out.println("Local TaskCount=" + executorLocal.getTaskCount());
		System.out.println("Local CompletedTaskCount=" + executorLocal.getCompletedTaskCount());
		System.out.println("Local KeepAliveTime=" + executorLocal.getKeepAliveTime(TimeUnit.MILLISECONDS));
		request.setAttribute("thread_local_executor_keepAliveTime",
				executorLocal.getKeepAliveTime(TimeUnit.MILLISECONDS));
		ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
		int threadSize = currentGroup.activeCount();
		Thread[] threadArray = new Thread[threadSize];
		// 复制该线程组及其子组中的所有活动线程到指定的数组
		currentGroup.enumerate(threadArray);
		for (int i = 0; i < threadSize; i++) {
			System.out.print("线程号=" + i);
			System.out.print(";");
			System.out.print("线程id= " + threadArray[i].getId());
			System.out.print(";");
			System.out.print("线程state= " + threadArray[i].getState());
			System.out.print(";");
			System.out.println("线程名称= " + threadArray[i].getName());
		}
		List<Thread> threadList = Arrays.asList(threadArray);
		// 分页
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageIndexName),
				1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageSizeName),
				threadSize);
		PageCoreBean<Thread> basePage = new PageMysqlBean<Thread>(pageIndex, pageSize);
		basePage.setTotalCount(new Long(threadSize));
		// basePage.setTotalPages(1l);
		// basePage.executeFindTotalCount(new Long(threadSize));
		basePage.setList(threadList);
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", threadList);
		return CommViewEnum.Default.toString();
	}
}
