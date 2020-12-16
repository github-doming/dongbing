package com.ibm.follow.servlet.cloud.core.controller.strategy;
import com.ibm.common.enums.IbmTypeEnum;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.TransactionsBase;

import java.sql.SQLException;
import java.util.Map;
/**
 * @Description: 就近查找策略
 * @Author: Dongming
 * @Date: 2019-09-11 11:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@Deprecated
public class FindClientByNearby extends TransactionsBase implements FindClientStrategy {

	@Override public Map<String, Object> findUsableClient(String handicapCode, IbmTypeEnum type) throws SQLException {
		CurrentTransaction.transactionBegin();
		// FIXME: 2019/9/11 没有账户信息没办法就近查找客户端信息




		CurrentTransaction.transactionEnd();
		return null;
	}

	@Override public Map<String, Object> findVerifyClient(String handicapCode, IbmTypeEnum type) throws SQLException {
		CurrentTransaction.transactionBegin();
		// FIXME: 2019/9/11 没有账户信息没办法就近查找客户端信息



		CurrentTransaction.transactionEnd();
		return null;
	}
}
