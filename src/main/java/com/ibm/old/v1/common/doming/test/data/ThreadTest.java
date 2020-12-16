package com.ibm.old.v1.common.doming.test.data;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;

import java.util.List;
/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-05-10 14:58
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ThreadTest extends BaseCommThread {
	public static final int MAX_LEN = 800;
	public static final int MAX_COUNT = 80000;
	List list;
	public ThreadTest(List list) {
		this.list = list;
	}

	@Override public String execute(String ignore) throws Exception {
		IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();
		int size = list.size();
		for (int i = 1; i <= MAX_COUNT / MAX_LEN; i++) {
			execBetItemTService.saveBigData(list, size, MAX_LEN);
			if (i % 10 == 0) {
				CurrentTransaction.transactionCommit();
			}
		}
		CurrentTransaction.transactionCommit();
		System.out.println("end");
		return null;
	}
}
