package c.a.config.core;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Calendar;
/**
 * 环境上下文
 * 
 * @author cxy
 * @Email:
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class CommContextUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * 开始
	 */
	public long start() {
		// 时间
		long startCalendarLong = System.currentTimeMillis();
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil == null) {
			contextUtil = new ContextUtil();
			contextUtil.init();
			ContextThreadLocal.findThreadLocal().set(contextUtil);
		}
		return startCalendarLong;
	}
	/**
	 * 
	 * 结束
	 */
	public void end(long startCalendarLong) {
		ContextUtil contextUtil = ContextThreadLocal.findThreadLocal().get();
		if (contextUtil != null) {
			contextUtil.remove();
			ContextThreadLocal.findThreadLocal().remove();
		}
		// 时间
		Calendar endCalendar = Calendar.getInstance();
		long endCalendarLong = endCalendar.getTimeInMillis();
		long timeSpend = endCalendarLong - startCalendarLong;
		// mysql 花费时间timeSpend=710
		log.trace("花费时间timeSpend=" + timeSpend);
	}
}
