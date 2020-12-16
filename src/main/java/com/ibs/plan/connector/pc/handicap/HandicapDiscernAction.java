package com.ibs.plan.connector.pc.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_handicap_item.entity.IbspHandicapItem;
import com.ibs.plan.module.cloud.ibsp_handicap_item.service.IbspHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 盘口识别
 *
 * @Author: null
 * @Date: 2020-05-25 11:06
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/handicap/discern", method = HttpConfig.Method.POST) public class HandicapDiscernAction
		extends CommCoreAction {
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
		if (StringTool.isEmpty(handicapUrl, handicapCaptcha)) {
			return bean.put401Data();
		}
		if (handicapCaptcha.trim().length() > 6) {
			bean.putEnum(CodeEnum.IBS_403_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		if (!handicapUrl.startsWith("http://") && !handicapUrl.startsWith("https://")) {
			handicapUrl = "http://".concat(handicapUrl);
		}
		if (handicapUrl.endsWith("/")) {
			handicapUrl = handicapUrl.substring(0, handicapUrl.length() - 1);
		}

		try {
			IbspHandicapItemService handicapItemService = new IbspHandicapItemService();
			Map<String, Object> info = handicapItemService.findInfo(handicapUrl, handicapCaptcha);

			//盘口识别
			if (StringTool.isEmpty(handicapCode)) {
				if (ContainerTool.isEmpty(info)) {
					//未能识别的盘口详情
					bean.putEnum(CodeEnum.IBS_404_HANDICAP_ITEM);
					bean.putSysEnum(CodeEnum.CODE_404);
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
			String handicapId =HandicapUtil.id(handicapCode.toString(),IbsTypeEnum.MEMBER);
			HandicapUtil.Code code = HandicapUtil.Code.valueOf(handicapCode.toString());

			String handicapItemId = handicapItemService.findId(handicapUrl, handicapCaptcha, handicapId);
			//输入盘口详情
			Date nowTime = new Date();
			if (StringTool.isEmpty(handicapItemId)) {
				//不存在-存储
				IbspHandicapItem handicapItem = new IbspHandicapItem();
				handicapItem.setHandicapId(handicapId);
				handicapItem.setHandicapCode(code.name());
				handicapItem.setHandicapName(code.getName());
				handicapItem.setHandicapUrl(handicapUrl);
				handicapItem.setHandicapCaptcha(handicapCaptcha);
				handicapItem.setCreateTime(nowTime);
				handicapItem.setCreateTimeLong(System.currentTimeMillis());
				handicapItem.setUpdateTimeLong(System.currentTimeMillis());
				handicapItem.setState(IbsStateEnum.OPEN.name());
				handicapItem.setDesc(this.getClass().getName().concat("新增盘口详情信息"));
				handicapItemId = handicapItemService.save(handicapItem);
			}
			Map<String, Object> data = new HashMap<>(4);
			data.put("HANDICAP_ITEM_ID_", handicapItemId);
			data.put("HANDICAP_CODE_", code.name());
			data.put("HANDICAP_NAME_", code.getName());

			return bean.success(data);
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "识别盘口失败", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
			return bean;
		}
	}
}
