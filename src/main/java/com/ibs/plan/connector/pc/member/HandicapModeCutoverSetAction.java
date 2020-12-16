package com.ibs.plan.connector.pc.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.entity.IbspHmModeCutover;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.service.IbspHmModeCutoverService;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 盘口模式切换设置
 * @Author: null
 * @Date: 2020-06-08 14:15
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/member/modeCutoverSet", method = HttpConfig.Method.POST)
public class HandicapModeCutoverSetAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();

		if (StringTool.isEmpty(handicapMemberId)) {
			return bean.put401Data();
		}
		if (super.denyTime()) {
			bean.putEnum(CodeEnum.IBS_503_TIME);
			bean.putSysEnum(CodeEnum.CODE_503);
			return bean;
		}
		try {
			IbspHmModeCutoverService modeCutoverService=new IbspHmModeCutoverService();
			IbspHmModeCutover modeCutover=modeCutoverService.findEntity(handicapMemberId);
			if(modeCutover==null){
				bean.putSysEnum(CodeEnum.IBS_404_DATA);
				bean.putEnum(CodeEnum.CODE_404);
				return bean;
			}
			IbspHmSetService hmSetService=new IbspHmSetService();
			String modeCutoverState=hmSetService.findModeCutoverState(handicapMemberId);
			if(StringTool.isEmpty(modeCutoverState)){
				bean.putSysEnum(CodeEnum.IBS_404_DATA);
				bean.putEnum(CodeEnum.CODE_404);
				return bean;
			}
			String cutoverGroupData = dataMap.getOrDefault("CUTOVER_GROUP_DATA_", "").toString();

			String cutoverKey = dataMap.getOrDefault("CUTOVER_KEY_", "").toString();

			if(StringTool.isEmpty(cutoverGroupData,cutoverKey)){
				modeCutover.setCurrentIndex(null);
				modeCutover.setProfitT(0);
				modeCutover.setCurrent(null);
				modeCutover.setCutoverProfitT(0);
				modeCutover.setCutoverLossT(0);
				modeCutover.setCutover(null);
				modeCutover.setResetProfit(null);
			}else{
				if(IbsStateEnum.OPEN.name().equals(modeCutoverState)){
					boolean keyFlag=!cutoverKey.equals(modeCutover.getCutoverKey());

					boolean dataFlag=!cutoverGroupData.equals(modeCutover.getCutoverGroupData());

					if(keyFlag||dataFlag){
						String[] keys=cutoverKey.split(",");
						JSONObject data=JSONObject.parseObject(cutoverGroupData).getJSONObject(keys[0]);
						modeCutover.setCurrentIndex(keys[0]);
						modeCutover.setProfitT(0);
						modeCutover.setCurrent(data.getString("CURRENT_"));
						modeCutover.setCutoverProfitT(NumberTool.longValueT(data.get("CUTOVER_PROFIT_")));
						modeCutover.setCutoverLossT(NumberTool.longValueT(data.get("CUTOVER_LOSS_")));
						modeCutover.setCutover(data.getString("VIRTUAL"));
						modeCutover.setResetProfit(data.get("RESET_PROFIT_"));
					}
				}
			}
			modeCutover.setCutoverGroupData(cutoverGroupData);
			modeCutover.setCutoverKey(cutoverKey);
			modeCutover.setUpdateTime(new Date());
			modeCutover.setUpdateTimeLong(System.currentTimeMillis());
			modeCutoverService.update(modeCutover);

			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "盘口模式切换设置错误", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
}
