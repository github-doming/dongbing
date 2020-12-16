package com.ibm.old.v1.pc.ibm_handicap_user.t.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_handicap_item.t.service.IbmPcHandicapItemTService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 识别盘口
 * @Author: Dongming
 * @Date: 2019-01-12 14:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class DiscernHandicapAction extends BaseAppAction {

	@Override public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		//盘口地址，盘口验证码
		String handicapUrl = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("HANDICAP_URL"), "");
		String handicapCaptcha = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("HANDICAP_CAPTCHA"), "");
		if (StringTool.isEmpty(handicapUrl, handicapCaptcha)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			//查询盘口相关信息
			IbmPcHandicapItemTService handicapItemTService = new IbmPcHandicapItemTService();
			Map<String, Object> handicapItem = handicapItemTService.findHandicapIdByUrlCaptcha(handicapUrl, handicapCaptcha);
			if(ContainerTool.isEmpty(handicapItem)){
				jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_URL_CAPTCHA);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}
			Map<String, Object> data = new HashMap<>(3);
			
			//识别成功，调用开启盘口
			Map<String, Object> accountMap = SelectHandicapAccountAction.findHmAccount(handicapItem.get("HANDICAP_ID_").toString(), appUserId);
			data.put("info", accountMap);
			data.put("handicapItemId", handicapItem.get("IBM_HANDICAP_ITEM_ID_"));
			data.put("handicapCode", handicapItem.get("HANDICAP_CODE_"));
			jrb.setData(data);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "识别盘口错误", e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return returnJson(jrb);
	}
}
