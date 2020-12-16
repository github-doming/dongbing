package com.ibm.follow.connector.admin.manage.user.vruser.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.util.BaseGameUtil;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_member_game_set.service.VrMemberGameSetService;
import com.ibm.follow.servlet.cloud.vr_plan_hm.service.VrPlanHmService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 虚拟用户盘口游戏信息
 * @Author: admin1
 * @Date: 2020/7/16 16:04
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/vruser/game/list")
public class VrUserGameInfoAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String memberId = StringTool.getString(dataMap.get("MEMBER_ID_"), "");
		try {
			VrMember member = new VrMemberService().find(memberId);
			if (member == null) {
				bean.putEnum(IbmCodeEnum.IBM_401_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_401);
				return bean;
			}
			List<Map<String, Object>> gameInfos = new VrMemberGameSetService().findMemberGameInfo(memberId);
			for (Map<String, Object> map : gameInfos) {
				String gameCode = map.get("GAME_CODE_").toString();
				map.put("gameName", BaseGameUtil.value(gameCode).getName());
			}
			Map<String, Object> data = new HashMap<>(3);
			data.put("gameInfo", gameInfos);

			data.put("userNam", member.getVrUserName());
			data.put("handicapCode", member.getHandicapCode());
			bean.success(data);
		} catch (Exception e) {
			log.error("用户列表错误", e);
			bean.error(e.getMessage());
			return bean;
		}
		return bean;
	}
}
