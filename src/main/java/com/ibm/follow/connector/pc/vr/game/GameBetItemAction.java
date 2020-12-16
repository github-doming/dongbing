package com.ibm.follow.connector.pc.vr.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_profit_game.service.VrProfitGameService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 虚拟会员游戏投注详情
 * @Author: null
 * @Date: 2020-07-17 13:38
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/follow/member/gameBetItem")
public class GameBetItemAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String vrMemberId = dataMap.getOrDefault("VR_MEMBER_ID_", "").toString();

		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		if (StringTool.isEmpty(vrMemberId, gameCode)) {
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
			Date time = new Date();
			Date startTime = DateTool.getBeforeDays(time, 14);
			Date endTime=DateTool.getBetTime(DateTool.getDate(time));
			VrProfitGameService profitGameService = new VrProfitGameService();
			List<Map<String, Object>> profitInfo = profitGameService.getGameProfitInfo(vrMemberId, gameCode, startTime, endTime);
			profitInfo.forEach(info -> {
				info.put("PROFIT_MAX_", NumberTool.doubleT(info.remove("PROFIT_MAX_T_")));
				info.put("LOSS_MAX_", NumberTool.doubleT(info.remove("LOSS_MAX_T_")));
				info.put("PROFIT_", NumberTool.doubleT(info.remove("PROFIT_T_")));
				info.put("WIN_RATE_", NumberTool.doubleT(info.remove("WIN_RATE_T_")));
			});

			bean.success(profitInfo);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("修改游戏跟投状态失败"));
			bean.error(e.getMessage());
		}
		return bean;
	}
}
