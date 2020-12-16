package com.ibm.old.v1.cloud.core.controller;
import com.ibm.old.v1.cloud.ibm_cloud_client_hm.t.entity.IbmCloudClientHmT;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
/**
 * @Description: 中心端执行器
 * @Author: Dongming
 * @Date: 2019-03-09 10:29
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public interface CloudExecutor {

	/**
	 * 执行方法
	 *
	 * @param inVar 输入参数
	 * @return 执行结果
	 * @throws Exception 执行异常
	 */
	default JsonResultBeanPlus execute(String inVar) throws Exception {
		return null;
	}

	/**
	 * 执行方法
	 *
	 * @param inVar 输入参数
	 * @return 执行结果
	 * @throws Exception 执行异常
	 */
	default JsonResultBeanPlus execute(String... inVar) throws Exception {
		return null;
	}

	/**
	 * 执行方法
	 * @param clientHmT		已存在盘口会员
	 * @param closeClientBean		关闭客户端bean
	 * @param resultBean		结果bean
	 * @return
	 * @throws Exception
	 */
	default JsonResultBeanPlus execute(IbmCloudClientHmT clientHmT,Object closeClientBean, Object resultBean) throws Exception {
		return null;
	}


}
