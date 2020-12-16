package com.ibm.follow.connector.admin.manage.user.vruser;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_handicap_game.service.IbmHandicapGameService;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_member_game_set.service.VrMemberGameSetService;
import com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.entity.VrPiMemberPlanItem;
import com.ibm.follow.servlet.cloud.vr_pi_member_plan_item.service.VrPiMemberPlanItemService;
import com.ibm.follow.servlet.cloud.vr_plan_game.service.VrPlanGameService;
import com.ibm.follow.servlet.cloud.vr_plan_hm.service.VrPlanHmService;
import com.ibm.follow.servlet.cloud.vr_plan_item.service.VrPlanItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 新增虚拟用户
 * @Author: admin1
 * @Date: 2020/7/16 14:28
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/vruser/save")
public class VrUserSaveAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String userName = StringTool.getString(dataMap.get("userName"), "");
		String memberName = StringTool.getString(dataMap.get("memberName"), "");
		String handicapCode = StringTool.getString(dataMap.get("handicapCode"), "");
		Date nowTime = new Date();
		try {
			VrMemberService memberService = new VrMemberService();
			if (memberService.isExist(handicapCode, memberName, userName)) {
				bean.putEnum(IbmCodeEnum.IBM_403_EXIST);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return bean.toJsonString();
			}
			VrMember member = new VrMember();
			member.setHandicapCode(handicapCode);
			member.setVrUserName(userName);
			member.setVrMemberAccount(memberName);
			member.setCreateTime(nowTime);
			member.setCreateTimeLong(System.currentTimeMillis());
			member.setUpdateTime(nowTime);
			member.setUpdateTimeLong(System.currentTimeMillis());
			member.setState(IbmStateEnum.OPEN.name());
			String memberId = memberService.save(member);
			//初始化游戏信息
			List<String> gameCodes = new IbmHandicapGameService().findHandicapGameCode(handicapCode);
			if (ContainerTool.notEmpty(gameCodes)) {
				new VrMemberGameSetService().save(gameCodes, memberId, nowTime);
				VrPlanGameService planGameService = new VrPlanGameService();
				VrPiMemberPlanItemService piMemberPlanItemService = new VrPiMemberPlanItemService();
				for (String gameCode : gameCodes){
					List<Map<String, Object>> planItem = planGameService.findGamePlanItem(gameCode,type(gameCode));
					piMemberPlanItemService.save(planItem,gameCode,memberId,nowTime);
				}
			}

			bean.success();
		} catch (Exception e) {
			log.error("用户列表错误", e);
			bean.error(e.getMessage());
			return bean;
		}
		return bean;
	}

	public static String type(String gameCode) {
		switch (gameCode) {
			case "JSSSC":
			case "CQSSC":
			case "COUNTRY_SSC":
			case "SELF_SSC_5":
				return "BALL";
			case "XYNC":
			case "GDKLC":
			case "GXKLSF":
				return "HAPPY";
			case "XYFT":
			case "JS10":
			case "PK10":
			case "COUNTRY_10":
			case "SELF_10_2":
			default:
				return "NUMBER";
		}
	}
}
