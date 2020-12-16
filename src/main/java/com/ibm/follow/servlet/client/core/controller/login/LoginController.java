package com.ibm.follow.servlet.client.core.controller.login;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
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
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 登录控制器
 * @Author: zjj
 * @Date: 2019-08-30 16:07
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class LoginController implements ClientExecutor {
	protected Logger log = LogManager.getLogger(this.getClass());

	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	private IbmTypeEnum customerType;
	private String existId;
	private HandicapUtil.Code handicapCode;

	public LoginController(IbmTypeEnum customerType) {
		this.customerType = customerType;
	}

	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		//获取已存在信息

		handicapCode = HandicapUtil.Code.valueOf(msgObj.getString("HANDICAP_CODE_"));

		if(!existInfo(msgObj)){
			return bean;
		}
		//登录
		bean = login(msgObj);
		if (!bean.isSuccess()) {
			return bean;
		}

		ClientDefineController controller = new ClientDefineController(existId, new Date());
		switch (customerType) {
			case MEMBER:
				controller.saveHandicapMember(msgObj);
				QuartzTool.saveCheckHmJob(existId, handicapCode);
				break;
			case AGENT:
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
		//获取用户信息
		Object info = ClientDefineController.getUserInfo(existId, customerType, handicapCode);
		ClientDefineController.saveClientInfo(existId, info, customerType);
		Map<String, Object> data = new HashMap<>(3);
		data.put("HANDICAP_CODE_",handicapCode);
		data.put("EXIST_ID_", existId);
		data.put("CUSTOMER_INFO_", info);
		data.put("CLIENT_CODE_", IbmModuleListener.servletCode());

		bean.setData(data);
		bean.success();
		return bean;
	}

	private boolean existInfo(JSONObject msgObj) throws Exception {
		switch (customerType) {
			case MEMBER:
				String handicapMemberId=msgObj.getString("HANDICAP_MEMBER_ID_");
				IbmcExistHmService existHmService = new IbmcExistHmService();
				existId = existHmService.findIdByHmId(handicapMemberId);
				if(StringTool.isEmpty(existId)){
					existId = existHmService.save(null,handicapMemberId, handicapCode.name());
				}
				break;
			case AGENT:
				String handicapAgentId=msgObj.getString("HANDICAP_AGENT_ID_");
				IbmcExistHaService existHaService = new IbmcExistHaService();
				existId= existHaService.findIdByHaId(handicapAgentId);
				if(StringTool.isEmpty(existId)){
					existId = existHaService.save(null,handicapAgentId, handicapCode.name());
				}
				break;
			default:
				log.error("异常的客户类型捕捉：".concat(customerType.name()));
				bean.error("异常的客户类型捕捉");
				return false;
		}
		return true;
	}
	/**
	 * 登录
	 *
	 * @return 登录结果信息
	 */
	private JsonResultBeanPlus login(JSONObject msgObj) throws Exception {
		switch (customerType) {
			case MEMBER:
				String memberPassword = EncryptTool.decode(EncryptTool.Type.ASE, msgObj.getString("MEMBER_PASSWORD_"));
				return ClientDefineController
						.memberLogin(existId, handicapCode, msgObj.getString("MEMBER_ACCOUNT_"), memberPassword,
								msgObj.getString("HANDICAP_URL_"), msgObj.getString("HANDICAP_CAPTCHA_"));
			case AGENT:
				String agentPassword = EncryptTool.decode(EncryptTool.Type.ASE, msgObj.getString("AGENT_PASSWORD_"));
				return ClientDefineController
						.agentLogin(existId, handicapCode, msgObj.getString("AGENT_ACCOUNT_"), agentPassword,
								msgObj.getString("HANDICAP_URL_"), msgObj.getString("HANDICAP_CAPTCHA_"));
			default:
				log.error("异常的客户类型捕捉：".concat(customerType.name()));
				bean.error("异常的客户类型捕捉");
				return bean;
		}
	}

}
