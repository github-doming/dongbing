package c.a.monitor.thread;
import java.util.Arrays;
import java.util.List;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.mysql.PageMysqlBean;
import c.x.platform.root.common.action.AyAction;
public class MonitorThreadStopAction extends AyAction {
	@Override
	public String execute() throws RuntimeException, Exception {
		// http://localhost:8080/a/c/a/monitor/thread/stop.do
		/**
		 * 监控所有线程
		 */
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
			String threadName=threadArray[i].getName();
			if (threadName.contains("quartz_")) {
				 //安全结束sleep中的进程
				threadArray[i].interrupt();
			}
		}
		List<Thread> threadList = Arrays.asList(threadArray);
		// 分页
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageIndexName),
				1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageSizeName),
				threadSize);
		PageCoreBean<Thread> basePage = new PageMysqlBean<Thread>(pageIndex, pageSize);
		basePage.setTotalCount(new Long(threadSize));
		//basePage.setTotalPages(1l);
		//basePage.executeFindTotalCount(new Long(threadSize));
		basePage.setList(threadList);
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", threadList);
		return CommViewEnum.Default.toString();
	}
}
