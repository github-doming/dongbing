package com.ibm.follow.connector.pc.vr;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_member_game_set.service.VrMemberGameSetService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 虚拟会员详情信息
 * @Author: null
 * @Date: 2020-07-17 14:08
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/follow/member/item")
public class MemberItemAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String vrMemberId = dataMap.getOrDefault("VR_MEMBER_ID_", "").toString();

		if (StringTool.isEmpty(vrMemberId)) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			VrMemberService vrMemberService = new VrMemberService();
			VrMember vrMember = vrMemberService.find(vrMemberId);
			if (vrMember == null) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			Map<String, Object> data = new HashMap<>(4);
			data.put("VR_USER_NAME_", vrMember.getVrUserName());
			data.put("VR_MEMBER_ACCOUNT_", vrMember.getVrMemberAccount());
			data.put("HANDICAP_CODE_", vrMember.getHandicapCode());

			//盘口游戏信息获取
			data.put("gameInfo", new VrMemberGameSetService().listGameInfo(vrMemberId,vrMember.getHandicapCode()));

			bean.success(data);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("修改游戏跟投状态失败"));
			bean.error(e.getMessage());
		}
		return bean;
	}
}
