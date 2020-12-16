package com.ibm.follow.connector.pc.handicap;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.service.IbmHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;
/**
 * @Description: 盘口详情修正
 * @Author: Dongming
 * @Date: 2019-09-03 10:02
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/handicap/itemEdit", method = HttpConfig.Method.GET) public class HandicapItemEditAction
		extends CommCoreAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapCode = dataMap.getOrDefault("HANDICAP_CODE_", "").toString();
		String handicapItemId = dataMap.getOrDefault("HANDICAP_ITEM_ID_", "").toString();
		String handicapUrl = dataMap.getOrDefault("HANDICAP_URL_", "").toString();
		String handicapCaptcha = dataMap.getOrDefault("HANDICAP_CAPTCHA_", "").toString();

		if (StringTool.isEmpty(handicapUrl, handicapCaptcha, handicapItemId,handicapCode)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
        if(handicapCaptcha.trim().length()>6){
            bean.putEnum(IbmCodeEnum.IBM_403_DATA_ERROR);
            bean.putSysEnum(IbmCodeEnum.CODE_401);
            return bean;
        }
        if(!handicapUrl.startsWith("http://")&&!handicapUrl.startsWith("https://")){
            handicapUrl="http://".concat(handicapUrl);
        }
        if(handicapUrl.endsWith("/")){
            handicapUrl=handicapUrl.substring(0,handicapUrl.length()-1);
        }
		try {
			IbmHandicapItemService handicapItemService = new IbmHandicapItemService();

			Map<String, Object> info = handicapItemService.findInfo(handicapItemId);
			if (ContainerTool.isEmpty(info)){
				//不存在的盘口详情
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_ITEM);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}
			String handicapId = info.getOrDefault("HANDICAP_ID_","").toString();
			HandicapUtil.Code code = HandicapUtil.code(handicapId);
			if (!code.equal(handicapCode)){
				//未能识别的盘口
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
			}

			//修改
			handicapItemService.update(handicapItemId,handicapUrl,handicapCaptcha);
			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("盘口详情修正失败")+e.getMessage());
			bean.error(e.getMessage());
		}
		return bean;
	}
}
