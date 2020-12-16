package com.ibs.plan.connector.app.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.service.IbspHmModeCutoverService;
import com.ibs.plan.module.cloud.ibsp_hm_set.service.IbspHmSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Map;

/**
 * @Description: 盘口模式切换
 * @Author: null
 * @Date: 2020-06-08 13:38
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/app/member/modeCutover", method = HttpConfig.Method.GET)
public class HandicapModeCutoverAction extends CommCoreAction {
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
		try {
			String handicapId = new IbspHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				return bean.put404HandicapMember();
			}
			;
			IbspHmModeCutoverService modeCutoverService=new IbspHmModeCutoverService();
			Map<String,Object> cutoverGroupData=modeCutoverService.findCutoverGroupData(handicapMemberId);
			if(ContainerTool.isEmpty(cutoverGroupData)){
				bean.putSysEnum(CodeEnum.IBS_404_DATA);
				bean.putEnum(CodeEnum.CODE_404);
				return bean;
			}
			JSONObject cutoverGroup=new JSONObject();
			if(StringTool.notEmpty(cutoverGroupData.get("CUTOVER_GROUP_DATA_"))){
				cutoverGroup=JSONObject.parseObject(cutoverGroupData.get("CUTOVER_GROUP_DATA_").toString());
			}
			cutoverGroupData.put("CUTOVER_GROUP_DATA_",cutoverGroup);
			cutoverGroupData.put("MODE_CUTOVER_STATE_",new IbspHmSetService().findModeCutoverState(handicapMemberId));

			bean.setData(cutoverGroupData);
			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "获取盘口模式切换信息错误", e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
