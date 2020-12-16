package com.ibm.common.test.wwj.handicap.logger;
import org.doming.core.common.thread.BaseCommThread;
/**
 * @Description: 保存日志的线程
 * @Author: Dongming
 * @Date: 2019-12-07 10:09
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SaveLogThread extends BaseCommThread {

	private HandicapLogger handicapLogger;
	private OptionLogger optionLogger;
	public Runnable logger(HandicapLogger logger) {
		this.handicapLogger = logger;
		return this;
	}
	public Runnable logger(OptionLogger logger) {
		this.optionLogger = logger;
		return this;
	}
	@Override public String execute(String ignore) throws Exception {

		SaveLogService service = new SaveLogService();
		if (handicapLogger != null){
			service.save(handicapLogger);
		}else if (optionLogger != null){
			service.save(optionLogger);
		}
		return null;
	}
}
