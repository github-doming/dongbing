package com.ibm.follow.connector.pc.vr;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.follow.servlet.cloud.ibm_exp_user.service.IbmExpUserService;
import com.ibm.follow.servlet.cloud.ibm_ha_game_set.service.IbmHaGameSetService;
import com.ibm.follow.servlet.cloud.vr_client_member.service.VrClientMemberService;
import com.ibm.follow.servlet.cloud.vr_fm_game_set.entity.VrFmGameSet;
import com.ibm.follow.servlet.cloud.vr_fm_game_set.service.VrFmGameSetService;
import com.ibm.follow.servlet.cloud.vr_member.entity.VrMember;
import com.ibm.follow.servlet.cloud.vr_member.service.VrMemberService;
import com.ibm.follow.servlet.cloud.vr_profit_game.service.VrProfitGameService;
import com.ibm.follow.servlet.cloud.vr_rank_daily.service.VrRankDailyService;
import com.ibm.follow.servlet.cloud.vr_user_follow_member.entity.VrUserFollowMember;
import com.ibm.follow.servlet.cloud.vr_user_follow_member.service.VrUserFollowMemberService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 跟投会员
 * @Author: null
 * @Date: 2020-07-15 16:01
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/follow/member/followInfo", method = HttpConfig.Method.GET)
public class FollowMemberInfoAction extends CommCoreAction {
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

			//如果虚拟会员没有绑定客户端，暂定不可跟投
			VrClientMemberService clientMemberService=new VrClientMemberService();
			Map<String,Object> clientInfo=clientMemberService.findInfo(vrMemberId);
			if(ContainerTool.isEmpty(clientInfo)){
				bean.putEnum(IbmCodeEnum.IBM_403_LOGIN);
				bean.putSysEnum(IbmCodeEnum.CODE_403);
				return super.returnJson(bean);
			}
			Date nowTime=new Date();

			VrUserFollowMemberService userFollowMemberService=new VrUserFollowMemberService();
			VrUserFollowMember userFollowMember=userFollowMemberService.findUserInfo(appUserId,vrMember.getVrMemberId());
			if(userFollowMember==null){
				userFollowMember=new VrUserFollowMember();
				userFollowMember.setUserId(appUserId);
				userFollowMember.setVrMemberId(vrMember.getVrMemberId());
				userFollowMember.setVrMemberAccount(vrMember.getVrMemberAccount());
				userFollowMember.setVrHandicapCode(vrMember.getHandicapCode());
				userFollowMember.setFollowState(IbmTypeEnum.TRUE.name());
				userFollowMember.setCreateTime(nowTime);
				userFollowMember.setCreateTimeLong(nowTime.getTime());
				userFollowMember.setUpdateTime(nowTime);
				userFollowMember.setUpdateTimeLong(nowTime.getTime());
				userFollowMember.setState(IbmStateEnum.OPEN.name());
				userFollowMemberService.save(userFollowMember);

				initGameSet(vrMember,nowTime);
			}else{
				userFollowMember.setFollowState(IbmTypeEnum.TRUE.name());
				userFollowMember.setUpdateTime(nowTime);
				userFollowMember.setUpdateTimeLong(nowTime.getTime());
				userFollowMemberService.update(userFollowMember);
			}
			VrFmGameSetService gameSetService=new VrFmGameSetService();
			List<Map<String, Object>> gameSetInfos =gameSetService.listShow(appUserId,vrMember.getVrMemberId());
			if (ContainerTool.isEmpty(gameSetInfos)) {
				bean.putEnum(IbmCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return super.returnJson(bean);
			}
			Map<String, Object> data = new HashMap<>(5);
			Date time = new Date();
			Date startTime = DateTool.getBetTime(DateTool.getDate(DateTool.getYesterdayStart()));
			Date endTime=DateTool.getBetTime(DateTool.getDate(time));
			//排名信息
			Map<String,Object> rankInfo=new VrRankDailyService().getHistoryRank(vrMemberId,startTime,endTime);
			data.put("rankInfo", rankInfo);

			//游戏盈利信息
			List<Map<String,Object>> gameProfitInfo=new VrProfitGameService().getProfitInfo(vrMemberId,startTime,endTime);
			gameProfitInfo.forEach(info -> {
				info.put("PROFIT_MAX_", NumberTool.doubleT(info.remove("PROFIT_MAX_T_")));
				info.put("LOSS_MAX_", NumberTool.doubleT(info.remove("LOSS_MAX_T_")));
				info.put("PROFIT_", NumberTool.doubleT(info.remove("PROFIT_T_")));
				info.put("WIN_RATE_", NumberTool.doubleT(info.remove("WIN_RATE_T_")));
			});
			data.put("gameProfitInfo", gameProfitInfo);

			List<Map<String, Object>> gameInfos = gameSetService.listGameInfo(appUserId,vrMember.getVrMemberId());
			data.put("gameInfo", gameInfos);

			for (Map<String, Object> gameSetInfo : gameSetInfos) {
				gameSetInfo
						.put("BET_FOLLOW_MULTIPLE_", NumberTool.doubleT(gameSetInfo.remove("BET_FOLLOW_MULTIPLE_T_")));
				gameSetInfo.put("BET_LEAST_AMOUNT_", NumberTool.doubleT(gameSetInfo.remove("BET_LEAST_AMOUNT_T_")));
				gameSetInfo.put("BET_MOST_AMOUNT_", NumberTool.doubleT(gameSetInfo.remove("BET_MOST_AMOUNT_T_")));
				gameSetInfo.remove("ROW_NUM");
			}
			data.put("gameSetInfos", gameSetInfos);

			bean.success(data);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("获取虚拟会员的信息错误"), e);
			bean.error(e.getMessage());
		}
		return bean;
	}


	private void initGameSet(VrMember vrMember,Date nowTime) throws Exception {
		//第一次绑定的需要初始化游戏设置信息
		VrFmGameSetService gameSetService=new VrFmGameSetService();
		VrFmGameSet fmGameSet=new VrFmGameSet();
		//获取可用游戏
		Map<String,Object> availableMap=new IbmExpUserService().findAvailableHandicap(appUserId);
		String[] availableGame=availableMap.get("AVAILABLE_GAME_").toString().split(",");

		IbmHaGameSetService haGameSetService = new IbmHaGameSetService();
		Map<String, Object> haGameSetInfo = haGameSetService.findInitInfo();
		if (ContainerTool.isEmpty(haGameSetInfo)) {
			throw new RuntimeException("初始化盘口游戏设置信息不存在");
		}
		fmGameSet.setVrMemberId(vrMember.getVrMemberId());
		fmGameSet.setUserId(appUserId);
		fmGameSet.setHandicapCode(vrMember.getHandicapCode());
		fmGameSet.setBetState(haGameSetInfo.get("BET_STATE_"));
		fmGameSet.setBetFollowMultipleT(haGameSetInfo.get("BET_FOLLOW_MULTIPLE_T_"));
		fmGameSet.setBetLeastAmountT(haGameSetInfo.get("BET_LEAST_AMOUNT_T_"));
		fmGameSet.setBetMostAmountT(haGameSetInfo.get("BET_MOST_AMOUNT_T_"));
		fmGameSet.setBetFilterNumber(haGameSetInfo.get("BET_FILTER_NUMBER_"));
		fmGameSet.setBetFilterTwoSide(haGameSetInfo.get("BET_FILTER_TWO_SIDE_"));
		fmGameSet.setNumberOpposing(haGameSetInfo.get("NUMBER_OPPOSING_"));
		fmGameSet.setTwoSideOpposing(haGameSetInfo.get("TWO_SIDE_OPPOSING_"));
		fmGameSet.setCreateTime(nowTime);
		fmGameSet.setCreateTimeLong(nowTime.getTime());
		fmGameSet.setUpdateTime(nowTime);
		fmGameSet.setUpdateTimeLong(nowTime.getTime());
		fmGameSet.setState(IbmStateEnum.OPEN.name());
		for(String gameCode:availableGame){
			fmGameSet.setGameCode(gameCode);
			gameSetService.save(fmGameSet);
		}
	}
}
