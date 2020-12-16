package c.a.util.core.thread.pool;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
public class ThreadPoolRejectedExecutionHandler implements RejectedExecutionHandler {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
		String str = "线程Task[" + runnable.toString() + "] rejected from [" + executor.toString()+ "]";
		log.error(str);
		throw new RejectedExecutionException(str);
	}
}