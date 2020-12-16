package com.ibm.follow.connector.pc.customer.login;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.core.CommQueryAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_event_client_close.service.IbmEventClientCloseService;
import com.ibm.follow.servlet.cloud.ibm_event_login.service.IbmEventLoginService;
import com.ibm.follow.servlet.cloud.ibm_event_login_vali.service.IbmEventLoginValiService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;
/**
 * @Description: 登录结果信息查找
 * @Author: Dongming
 * @Date: 2019-09-03 16:45
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/login/result", method = HttpConfig.Method.GET) public class LoginResultAction
		extends CommQueryAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findDateMap();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		Object loginCode = dataMap.getOrDefault("loginCode", "");
		String eventId = dataMap.getOrDefault("eventId", "").toString();
		if (StringTool.isEmpty(eventId, loginCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {

			Map<String, Object> resultMap;
			if ("valiLogin".equals(loginCode)) {
				//验证登录
				resultMap = new IbmEventLoginValiService().findResult(eventId);
			} else if ("login".equals(loginCode)) {
				//登录
				resultMap = new IbmEventLoginService().findResult(eventId);
			} else if ("logout".equals(loginCode)) {
				//登出
				resultMap = new IbmEventClientCloseService().findResult(eventId);

			} else {
				//不存在的登录code
				bean.putEnum(IbmCodeEnum.CODE_403);
				bean.putSysEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
				return bean;
			}
			// 登录中
			if (StringTool.isEmpty(resultMap)) {
				return bean.process(IbmCodeEnum.IBM_202_EVENT);
			}
			JSONObject result = JSONObject.parseObject(resultMap.get("EVENT_RESULT_").toString());
			IbmTypeEnum type=IbmTypeEnum.valueCustomerTypeOf(resultMap.get("CUSTOMER_TYPE_").toString());
			if(type==null){
				log.error("错误的角色类型"+resultMap);
				bean.putEnum(IbmCodeEnum.IBM_500);
				return bean;
			}
			JSONObject content=JSONObject.parseObject(resultMap.get("EVENT_CONTENT_").toString());
			switch (type) {
				case MEMBER:
					result.put("data",content.getString("HANDICAP_MEMBER_ID_"));
					break;
				case AGENT:
					result.put("data",content.getString("HANDICAP_AGENT_ID_"));
					break;
				default:
					break;
			}

			// 登录结果
			return bean.success(result);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("查找登录结果信息错误"),e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
