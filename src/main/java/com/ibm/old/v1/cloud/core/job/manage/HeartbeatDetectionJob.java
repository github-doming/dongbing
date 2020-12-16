package com.ibm.old.v1.cloud.core.job.manage;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.old.v1.cloud.core.thread.manage.HeartbeatDetectionThread;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.quartz.JobExecutionContext;

import java.util.List;
import java.util.concurrent.ExecutorService;
/**
 * @Description: 心跳检测定时器
 * @Author: zjj
 * @Date: 2019-04-12 09:43
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HeartbeatDetectionJob extends BaseCommJob {

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		List<String> list = new IbmHandicapTService().findAllCode();

		if (ContainerTool.isEmpty(list)) {
			log.info("没有符合校验时间的盘口");
			return;
		}
		//开启一个线程，进行计算。
		ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance().findLocal();
		ExecutorService executorService = threadExecutorService.findExecutorService();

		IbmHandicapEnum code;
		for (String handicapCode : list) {
			code = IbmHandicapEnum.valueOf(handicapCode);
			switch (code) {
				case WS2:
				case IDC:
					executorService.execute(new HeartbeatDetectionThread(handicapCode));
					break;
				default:
					log.info("不能识别的盘口code");
					return;
			}
		}
	}
}
