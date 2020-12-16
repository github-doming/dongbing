package com.ibm.old.v1.client.core.controller.manage;
import com.ibm.old.v1.client.core.controller.ClientExecutor;
import com.ibm.old.v1.client.ibm_client_config.t.service.IbmClientConfigTService;
import com.ibm.old.v1.common.doming.core.CommMethod;
import com.ibm.old.v1.common.doming.utils.http.IbmHttpConfig;
import net.sf.json.JSONObject;
import org.doming.core.common.TransactionsBase;
import org.doming.develop.http.jsoup.HttpJsoupTool;

import java.sql.Connection;
/**
 * @Description: 关闭客户机控制器
 * @Author: Dongming
 * @Date: 2019-01-02 15:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LogoutClientController extends TransactionsBase implements ClientExecutor, CommMethod {

	/**
	 * 中心端客户机
	 */
	private static final String MAIN_URL =
			IbmHttpConfig.HOST + IbmHttpConfig.PROJECT + "/ibm/cloud/t/manage/closeClient.do";

	@Override public String execute(String inVar) throws Exception {
		//关闭客户机中的数据
		super.transaction = Connection.TRANSACTION_REPEATABLE_READ;

		super.transactionBegin();
		new IbmClientConfigTService().logout();
		super.transactionEnd();

		//关闭中心机中的数据
		JSONObject data = new JSONObject();
		JSONObject jObj = new JSONObject();
		jObj.put("clientId", inVar);
		data.put("data", jObj);
		HttpJsoupTool.doGetJson(MAIN_URL, paramJson(data));
		return null;
	}
}
