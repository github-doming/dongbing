package com.ibm.follow.servlet.client.core.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.core.servlet.boot.IbmModuleListener;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.EncryptTool;
import com.ibm.common.tools.QuartzTool;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.ClientDefineController;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 验证登录控制器
 * @Author: zjj
 * @Date: 2019-08-29 16:25
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class VailLoginController implements ClientExecutor {
	protected Logger log = LogManager.getLogger(this.getClass());

	private JsonResultBeanPlus bean = new JsonResultBeanPlus();
	private HandicapUtil.Code handicapCode;
	private IbmTypeEnum customerType;

	public VailLoginController(IbmTypeEnum customerType) {
		this.customerType = customerType;
	}

	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        String code = msgObj.getString("HANDICAP_CODE_");
        if (StringTool.isEmpty(code)) {
            bean.putEnum(CodeEnum.IBS_401_DATA);
            bean.putSysEnum(CodeEnum.CODE_401);
            return bean;
        }
        handicapCode = HandicapUtil.Code.value(code);
		//获取本机容量信息，

		//不同盘口验证登录,验证登录成功直接保存数据
		bean = vailLogin(msgObj);
		if (!bean.isSuccess()) {
			return bean;
		}

		//存在信息
		String existId = bean.getData().toString();
		ClientDefineController controller = new ClientDefineController(existId, new Date());
		switch (customerType) {
			case MEMBER:
				IbmcExistHmService existHmService = new IbmcExistHmService();
				existId = existHmService.save(existId, msgObj.getString("HANDICAP_MEMBER_ID_"), handicapCode.name());
				CurrentTransaction.transactionCommit();
				controller.saveHandicapMember(msgObj);
				QuartzTool.saveCheckHmJob(existId, handicapCode);
				break;
			case AGENT:
				IbmcExistHaService existHaService = new IbmcExistHaService();
				existId = existHaService.save(existId, msgObj.getString("HANDICAP_AGENT_ID_"), handicapCode.name());
				CurrentTransaction.transactionCommit();
				controller.saveHandicapAgent(msgObj);
                QuartzTool.saveCheckHaJob(existId, handicapCode);
				break;
			default:
				log.error("异常的客户类型捕捉：".concat(customerType.name()));
				bean.error("异常的客户类型捕捉");
				return bean;
		}
		//存到内存
		CustomerCache.stateInfo(existId, IbmStateEnum.LOGIN);
		//获取本机容量信息

		//放入用户信息到返回结果中CUSTOMER_INFO_
		Object info = ClientDefineController.getUserInfo(existId, customerType, handicapCode);

        ClientDefineController.saveClientInfo(existId, info, customerType);
		Map<String, Object> data = new HashMap<>(4);
		data.put("HANDICAP_CODE_", handicapCode);
		data.put("EXIST_ID_", existId);
		data.put("CUSTOMER_INFO_", info);
		data.put("CLIENT_CODE_", IbmModuleListener.servletCode());
		return bean.success(data);
	}

	/**
	 * 验证登录
	 *
	 * @return 验证登录结果
	 */
	private JsonResultBeanPlus vailLogin(JSONObject msgObj) throws Exception {
		switch (customerType) {
			case MEMBER:
				String memberPassword = EncryptTool.decode(EncryptTool.Type.ASE, msgObj.getString("MEMBER_PASSWORD_"));
				return ClientDefineController.memberVailLogin(handicapCode, msgObj.getString("MEMBER_ACCOUNT_"),memberPassword,
						msgObj.getString("HANDICAP_URL_"),msgObj.getString("HANDICAP_CAPTCHA_"));
			case AGENT:
				String agentPassword = EncryptTool.decode(EncryptTool.Type.ASE, msgObj.getString("AGENT_PASSWORD_"));
				return ClientDefineController
						.agentVailLogin(handicapCode, msgObj.getString("AGENT_ACCOUNT_"), agentPassword,
								msgObj.getString("HANDICAP_URL_"), msgObj.getString("HANDICAP_CAPTCHA_"));
			default:
				log.error("异常的客户类型捕捉：".concat(customerType.name()));
				bean.error("异常的客户类型捕捉");
				return bean;
		}
	}
}
