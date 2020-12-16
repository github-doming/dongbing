package com.ibm.follow.servlet.cloud.core.controller.mq;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.vr_client_member.service.VrClientMemberService;
import com.ibm.follow.servlet.cloud.vr_member_bet_item.entity.VrMemberBetItem;
import com.ibm.follow.servlet.cloud.vr_member_bet_item.service.VrMemberBetItemService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 跟随会员控制器
 * @Author: null
 * @Date: 2020-07-14 15:52
 * @Version: v1.0
 */
public class FollowMemberController {
	private GameUtil.Code gameCode;
	private Object period;
	private String betContent;

	public FollowMemberController(GameUtil.Code gameCode, Object period, String betContent) {
		this.gameCode = gameCode;
		this.period = period;
		this.betContent = betContent;
	}

	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		Date nowTime=new Date();

		String existMemberVrId = msgObj.getString("EXIST_MEMBER_VR_ID_");
		Map<String,Object> vrMemberInfo=new VrClientMemberService().findMemberId(existMemberVrId);
		if (ContainerTool.isEmpty(vrMemberInfo)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		String vrMemberId=vrMemberInfo.get("VR_MEMBER_ID_").toString();
		//保存虚拟会员投注信息
		saveVrBet(msgObj,vrMemberId,nowTime);

		bean.success();
		return bean;
	}

	private void saveVrBet(JSONObject msgObj, String vrMemberId,Date nowTime) throws Exception {
		if (StringTool.isContains(period.toString(), "-")) {
			period = period.toString().substring(4);
		}
		VrMemberBetItem betItem=new VrMemberBetItem();
		betItem.setVrFollowBetId(msgObj.get("FOLLOW_BET_ID_"));
		betItem.setVrMemberId(vrMemberId);
		betItem.setGameCode(gameCode.name());
		betItem.setHandicapCode(msgObj.get("HANDICAP_CODE_"));
		betItem.setPeriod(period);
		betItem.setBetContentLen(betContent.split("#").length);
		betItem.setBetContent(betContent);
		betItem.setBetFundT(msgObj.getLong("FUNDS_T_"));
		betItem.setCreateTime(nowTime);
		betItem.setCreateTimeLong(nowTime.getTime());
		betItem.setUpdateTime(nowTime);
		betItem.setUpdateTimeLong(nowTime.getTime());
		betItem.setState(IbmStateEnum.OPEN.name());
		new VrMemberBetItemService().save(betItem);
	}
}
