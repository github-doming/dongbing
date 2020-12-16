package com.ibm.old.v1.cloud.core.thread.manage;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.old.v1.cloud.core.controller.mq.CheckHmStateController;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.service.IbmCloudClientHmTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_sys_handicap.t.service.IbmSysHandicapTService;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
/**
 * @Description: 心跳检测
 * @Author: zjj
 * @Date: 2019-04-12 10:16
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class HeartbeatDetectionThread extends BaseCommThread {

	private String handicapCode;

	public HeartbeatDetectionThread(String handicapCode) {
		this.handicapCode = handicapCode;
	}
	@Override public String execute(String ignore) throws Exception {
		long codingStart = System.currentTimeMillis(), codingEnd;

		new IbmSysHandicapTService().updateLastCheckTime(handicapCode,this.getClass().getName());
		//获取该盘口所有登录状态用户
		IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
		List<String> handicapMemberIds = handicapMemberTService.findHandicapMemberIds(handicapCode);

		if (ContainerTool.isEmpty(handicapMemberIds)) {
			codingEnd = System.currentTimeMillis();
			log.trace("执行时长=" + (codingEnd - codingStart) + "ms,盘口【" + handicapCode + "】不存在需要校验用户");
			return null;
		}
		//获取已存在盘口会员ids
		IbmCloudClientHmTService cloudClientHmTService = new IbmCloudClientHmTService();
		List<Map<String, Object>> list = cloudClientHmTService.findExistHmIds(handicapMemberIds);

		int capacity = ContainerTool.CAPACITY_THREAD;
		if ( list.size() < ContainerTool.CAPACITY_THREAD){
			new CheckHmStateController(list).execute(null);
			return null;
		}

		//开启多个线程，进行分批校验
		ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance().findLocal();
		ExecutorService executorService = threadExecutorService.findExecutorService();
		for (int i = 0,listSize = list.size(); i < listSize; i += capacity) {
			//作用为capacity最后没有100条数据则剩余几条newList中就装几条
			if (i + 100 > listSize) {
				capacity = listSize - i;
			}
			//开启一个线程，进行计算。
			executorService.execute(new CheckHmStateController(list.subList(i, i + capacity)));
		}
		return null;
	}

}
