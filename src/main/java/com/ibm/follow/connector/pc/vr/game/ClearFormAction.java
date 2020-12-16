package com.ibm.follow.connector.pc.vr.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.vr_fm_member_bet_item.service.VrFmMemberBetItemService;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-07-17 11:39
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/follow/member/clearForm", method = HttpConfig.Method.GET)
public class ClearFormAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();

		String vrMemberId = dataMap.getOrDefault("VR_MEMBER_ID_", "").toString();
		if (StringTool.isEmpty(gameCode, vrMemberId)) {
			bean.putEnum(IbmCodeEnum.IBM_404_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return super.returnJson(bean);
		}
		try {
			VrMemberService vrMemberService=new VrMemberService();
			VrMember vrMember=vrMemberService.find(vrMemberId);
			if(vrMember==null){
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			HandicapUtil.Code handicap =HandicapUtil.Code.value(vrMember.getHandicapCode());
			GameUtil.Code game = GameUtil.Code.valueOf(gameCode);

			long time =game.getGameFactory().period(handicap).getLotteryDrawTime();

			VrFmMemberBetItemService memberBetItemService=new VrFmMemberBetItemService();
			memberBetItemService.clearForm(game, vrMemberId,appUserId, time);

			bean.success();
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("清空表格数据信息错误"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}
}
