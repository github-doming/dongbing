package com.ibs.plan.connector.pc.member.game;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommCoreAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.cloud.ibsp_handicap_member.service.IbspHandicapMemberService;
import com.ibs.plan.module.cloud.ibsp_hm_bet_item.service.IbspHmBetItemService;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

/**
 * @Description: 清空表格
 * @Author: null
 * @Date: 2020-07-07 10:32
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/pc/member/clearForm", method = HttpConfig.Method.GET)
public class ClearFormAction extends CommCoreAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String handicapMemberId = dataMap.getOrDefault("HANDICAP_MEMBER_ID_", "").toString();
		String gameCode = dataMap.getOrDefault("GAME_CODE_", "").toString();
		if(StringTool.isEmpty(handicapMemberId,gameCode)){
			return bean.put401Data();
		}
		try {
			// 获取该盘口会员的信息
			String loginState= new IbspHmInfoService().findLoginState(handicapMemberId);
			//是否登录
			if (StringTool.isEmpty(loginState) || !IbsStateEnum.LOGIN.name().equals(loginState)) {
				bean.putEnum(CodeEnum.IBS_404_LOGIN);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			String gameId = GameUtil.id(gameCode);
			if (StringTool.isEmpty(gameId)) {
				return bean.put401Data();
			}
			GameUtil.Code game=GameUtil.code(gameId);

			String handicapId = new IbspHandicapMemberService().findHandicapId(handicapMemberId, appUserId);
			if (StringTool.isEmpty(handicapId)) {
				return bean.put404HandicapMember();
			}
			HandicapUtil.Code handicap=HandicapUtil.code(handicapId);

			long time = game.getGameFactory().period(handicap).getLotteryDrawTime();

			new IbspHmBetItemService().clearForm(gameId,handicapMemberId,time);


			bean.success();
		} catch (Exception e) {
			log.error(IbsConfig.LOG_SIGN + "清空表格数据信息错误", e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean;
	}
}
