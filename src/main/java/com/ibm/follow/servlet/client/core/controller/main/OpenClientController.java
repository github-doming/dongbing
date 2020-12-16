package com.ibm.follow.servlet.client.core.controller.main;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description: 开启客户端控制器
 * @Author: zjj
 * @Date: 2019-08-30 17:30
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class OpenClientController implements ClientExecutor {
	protected Logger log = LogManager.getLogger(this.getClass());

	private IbmTypeEnum customerType;

	public OpenClientController(IbmTypeEnum customerType) {
		this.customerType = customerType;
	}
	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        String code = msgObj.getString("HANDICAP_CODE_");
        if (StringTool.isEmpty(code)) {
            bean.putEnum(CodeEnum.IBS_401_DATA);
            bean.putSysEnum(CodeEnum.CODE_401);
            return bean;
        }
        HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(code);

		Map<String, Object> data = new HashMap<>(3);

		//获取本机容量信息

		String existId;
		switch (customerType) {
			case MEMBER:
				IbmcExistHmService existHmService = new IbmcExistHmService();
				existId = existHmService.save(null,msgObj.getString("HANDICAP_MEMBER_ID_"), handicapCode.name());
				data.put("EXIST_HM_ID_", existId);
				break;
			case AGENT:
				IbmcExistHaService existHaService = new IbmcExistHaService();
				existId = existHaService.save(null,msgObj.getString("HANDICAP_AGENT_ID_"), handicapCode.name());
				data.put("EXIST_HA_ID_", existId);
				break;
			default:
				log.error("异常的客户类型捕捉：".concat(customerType.name()));
				bean.error("异常的客户类型捕捉");
				return bean;
		}

		//存到内存
		CustomerCache.stateInfo(existId, IbmStateEnum.LOGIN);
		//获取本机容量信息

		bean.setData(existId);
		bean.success();
		return bean;
	}
}
