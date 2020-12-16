package com.ibm.follow.connector.app.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.entity.IbmHandicapItem;
import com.ibm.follow.servlet.cloud.ibm_handicap_item.service.IbmHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 盘口详情识别
 * @Author: null
 * @Date: 2019-11-23 16:32
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/app/handicap/itemDiscern", method = HttpConfig.Method.GET)
public class HandicapItemDiscernAction extends CommCoreAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		Object handicapCode = dataMap.getOrDefault("HANDICAP_CODE_", "");
		String handicapUrl = dataMap.getOrDefault("HANDICAP_URL_", "").toString();
		String handicapCaptcha = dataMap.getOrDefault("HANDICAP_CAPTCHA_", "").toString();
		String handicapCategory = dataMap.getOrDefault("HANDICAP_CATEGORY_", "").toString();


		if (StringTool.isEmpty(handicapUrl, handicapCaptcha, handicapCategory)) {
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
		IbmTypeEnum category = IbmTypeEnum.valueCustomerTypeOf(handicapCategory);
		if (category == null) {
			bean.putEnum(IbmCodeEnum.IBM_401_METHOD);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			IbmHandicapItemService handicapItemService = new IbmHandicapItemService();
			Map<String, Object> info = handicapItemService.findInfo(handicapUrl, handicapCaptcha, category);

			//盘口识别
			if (StringTool.isEmpty(handicapCode)) {
				if (ContainerTool.isEmpty(info)) {
					//未能识别的盘口详情
					bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_ITEM);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return bean;
				}
				String handicapId = info.remove("HANDICAP_ID_").toString();
				HandicapUtil.Code code = HandicapUtil.code(handicapId);
				Map<String, Object> data = new HashMap<>(3);
				data.putAll(info);
				data.put("HANDICAP_CODE_", code.name());
				data.put("HANDICAP_NAME_", code.getName());
				return bean.success(data);
			}

			HandicapUtil.Code code = HandicapUtil.Code.valueOf(handicapCode.toString());
			String handicapId = HandicapUtil.id(handicapCode.toString(),category);

			String handicapItemId = handicapItemService.findId(handicapUrl, handicapCaptcha, handicapId, category);

			//输入盘口详情
			Date nowTime = new Date();
			if (StringTool.isEmpty(handicapItemId)) {
				//不存在-存储
				IbmHandicapItem handicapItem = new IbmHandicapItem();
				handicapItem.setHandicapId(handicapId);
				handicapItem.setHandicapName(code.getName());
				handicapItem.setHandicapUrl(handicapUrl);
				handicapItem.setHandicapCaptcha(handicapCaptcha);
				handicapItem.setHandicapCategory(handicapCategory);
				handicapItem.setCreateTime(nowTime);
				handicapItem.setCreateTimeLong(System.currentTimeMillis());
				handicapItem.setUpdateTimeLong(System.currentTimeMillis());
				handicapItem.setState(IbmStateEnum.OPEN.name());
				handicapItem.setDesc(this.getClass().getName().concat("新增盘口详情信息"));
				handicapItemId = handicapItemService.save(handicapItem);
			}
			Map<String, Object> data = new HashMap<>(4);
			data.put("HANDICAP_ITEM_ID_", handicapItemId);
			data.put("HANDICAP_CODE_", code.name());
			data.put("HANDICAP_NAME_", code.getName());

			bean.success(data);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("识别盘口失败"),e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
