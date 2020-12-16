package com.ibs.plan.connector.app.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_handicap_item.service.IbspHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * 盘口详情修正
 *
 * @Author: Dongming
 * @Date: 2020-05-25 13:53
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/handicap/itemEdit", method = HttpConfig.Method.POST)
public class HandicapItemEditAction extends CommCoreAction {
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
			return bean.put401Data();
		}
		if(handicapCaptcha.trim().length()>6){
			bean.putEnum(CodeEnum.IBS_403_ERROR);
			bean.putSysEnum(CodeEnum.CODE_403);
			return bean;
		}
		if(!handicapUrl.startsWith("http://")&&!handicapUrl.startsWith("https://")){
			handicapUrl="http://".concat(handicapUrl);
		}
		if(handicapUrl.endsWith("/")){
			handicapUrl=handicapUrl.substring(0,handicapUrl.length()-1);
		}
		try {
			IbspHandicapItemService handicapItemService = new IbspHandicapItemService();
			Map<String, Object> info = handicapItemService.findInfo(handicapItemId);
			if (ContainerTool.isEmpty(info)){
				//不存在的盘口详情
				bean.putEnum(CodeEnum.IBS_404_HANDICAP_ITEM);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			String handicapId = info.getOrDefault("HANDICAP_ID_","").toString();
			HandicapUtil.Code code = HandicapUtil.code(handicapId);
			if (!code.equal(handicapCode)){
				//未能识别的盘口
				bean.putEnum(CodeEnum.IBS_404_HANDICAP);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			//修改
			handicapItemService.update(handicapItemId,handicapUrl,handicapCaptcha);
			return bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "识别盘口失败", e);
			throw e;
		}
	}
}
