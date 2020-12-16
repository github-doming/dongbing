package com.ibm.old.v1.common.doming.test;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;

import java.util.List;
import java.util.Map;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-05-10 17:14
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SimulateExecBetItemThread extends BaseCommThread {
	List list;
	Map<String, Object> idMap;
	public SimulateExecBetItemThread(List list, Map<String, Object> idMap) {
		this.list = list;
		this.idMap = idMap;
	}
	@Override public String execute(String ignore) throws Exception {
		IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();
		execBetItemTService.saveBigData(list, idMap, 5);
		CurrentTransaction.transactionCommit();
		return null;
	}

}
