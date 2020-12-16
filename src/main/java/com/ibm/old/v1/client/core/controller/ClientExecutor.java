package com.ibm.old.v1.client.core.controller;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import net.sf.json.JSONObject;
import org.doming.core.Executor;
/**
 * @Description: 成员执行行动接口类
 * @Author: Dongming
 * @Date: 2018-12-05 11:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface ClientExecutor extends Executor {
	/**
	 * 执行方法
	 *
	 * @param msgObj   输入消息
	 * @param existHmT 存在于服务的盘口会员信息
	 * @return 执行结果
	 * @throws Exception 执行异常
	 */
	default JsonResultBeanPlus execute(JSONObject msgObj, IbmClientExistHmT existHmT) throws Exception{
		return null;
	}
	/**
	 * 执行方法
	 *
	 * @param msgObj   输入消息
	 * @param existHmT 存在于服务的盘口会员信息
	 * @return 执行结果
	 * @throws Exception 执行异常
	 */
	default JsonResultBeanPlus execute(JSONObject msgObj, IbmClientExistHmT existHmT,String messageId) throws Exception{
		return null;
	}
	/**
	 * 执行方法
	 *
	 * @param msgObj   输入消息
	 * @return 执行结果
	 * @throws Exception 执行异常
	 */
	default JsonResultBeanPlus execute(JSONObject msgObj) throws Exception{
		return null;
	}

}
