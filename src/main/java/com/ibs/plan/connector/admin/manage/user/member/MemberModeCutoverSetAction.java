package com.ibs.plan.connector.admin.manage.user.member;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.entity.IbspHmModeCutover;
import com.ibs.plan.module.cloud.ibsp_hm_mode_cutover.service.IbspHmModeCutoverService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

/**
 * @Description: 会员真实模拟切换
 * @Author: admin1
 * @Date: 2020/6/17 16:16
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/member/modeCutover/set")
public class MemberModeCutoverSetAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//盘口会员id
		String handicapMemberId = StringTool.getString(dataMap.get("handicapMemberId"), "");
		//切换租key
		String cutoverKey = StringTool.getString(dataMap.get("handicapMemberId"), "");
		//切换信息
		String groupData = StringTool.getString(dataMap.get("groupData"), "");
		if (StringTool.isEmpty(handicapMemberId)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		try {
			IbspHmModeCutoverService modeCutoverService = new IbspHmModeCutoverService();
			IbspHmModeCutover hmModeCutover = modeCutoverService.findEntity(handicapMemberId);
			if (hmModeCutover == null) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}

			//获取当前方案信息
			if (StringTool.notEmpty(groupData) && StringTool.notEmpty(cutoverKey)) {
				JSONObject groupDataJson = JSONObject.parseObject(groupData);
				JSONObject current = groupDataJson.getJSONObject(hmModeCutover.getCurrentIndex() + "");
				//如果旧当前方案不存在，默认设置为第一条数据
				if (current == null) {
					current = groupDataJson.getJSONObject("0");
					hmModeCutover.setCurrentIndex(0);
				}

				hmModeCutover.setCurrent(current.getString("CURRENT_"));
				hmModeCutover.setCutoverProfitT(NumberTool.intValueT(current.getInteger("CUTOVER_PROFIT_")));
				hmModeCutover.setCutoverLossT(NumberTool.intValueT(current.getInteger("CUTOVER_LOSS_")));
				hmModeCutover.setResetProfit(current.getBoolean("RESET_PROFIT_"));
			}
			hmModeCutover.setCutoverGroupData(groupData);
			hmModeCutover.setCutoverKey(cutoverKey);
			modeCutoverService.save(hmModeCutover);


		} catch (Exception e) {
			log.error("会员真实模拟切换设置失败", e);
			bean.error(e.getMessage());
		}
		return bean.toString();
	}
}
