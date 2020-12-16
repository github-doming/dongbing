package com.ibm.old.v1.pc.ibm_hm_game_set.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.entity.IbmHmGameSetT;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.cloud.ibm_log_handicap_member.t.entity.IbmLogHandicapMemberT;
import com.ibm.old.v1.cloud.ibm_log_handicap_member.t.service.IbmLogHandicapMemberTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.ibm_handicap_game.t.service.IbmPcHandicapGameTService;
import com.ibm.old.v1.pc.ibm_handicap_member.t.service.IbmPcHandicapMemberTService;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 
 * @Description: 自动开始投注
 * @date 2019年3月4日 上午11:35:13 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmHandicapAutoStartAction extends BaseAppAction {

	@Override
	public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		// 盘口会员id
		String handicapMemberId = BeanThreadLocal.find(dataMap.get("HANDICAP_MEMBER_ID_"), "");
		// 游戏code
		String gameCode = BeanThreadLocal.find(dataMap.get("GAME_CODE_"), "");
		// 自动开始投注
		String betAutoStart = BeanThreadLocal.find(dataMap.get("BET_AUTO_START_"), "");
		// 自动开始投注时间
		String betAutoStartTime = BeanThreadLocal.find(dataMap.get("BET_AUTO_START_TIME_"), "0");
		
		if(StringTool.isEmpty(handicapMemberId, gameCode)||"0".equals(betAutoStartTime)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		if(!IbmStateEnum.hmGameSetState(betAutoStart)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_STATE);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			IbmPcHandicapMemberTService handicapMemberTService = new IbmPcHandicapMemberTService();
			String handicapId = handicapMemberTService.findHandicapId(handicapMemberId);
			// 判断盘口用户是否存在
			if (StringTool.isEmpty(handicapId)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}
			IbmPcHandicapGameTService ibmHandicapGameTService = new IbmPcHandicapGameTService();
			String handicapGameId = ibmHandicapGameTService.findId(handicapId, gameCode);
			// 判断盘口游戏是否存在
			if (StringTool.isEmpty(handicapGameId)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}
			// 通过游戏Code查找游戏ID
			IbmGameTService gameTService = new IbmGameTService();
			String gameId = gameTService.findId(gameCode);

			// 判断游戏ID是否存在
			if (StringTool.isEmpty(gameId)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}

			Date nowTime = new Date();
			IbmHmGameSetTService ibmHmGameSetTService = new IbmHmGameSetTService();
			IbmHmGameSetT ibmHmGameSetT = ibmHmGameSetTService.find(handicapMemberId,
					handicapGameId);
			//判断盘口游戏是否存在
			if (ibmHmGameSetT == null) {
				ibmHmGameSetT = new IbmHmGameSetT();
				ibmHmGameSetT.setHandicapMemberId(handicapMemberId);
				ibmHmGameSetT.setHandicapGameId(handicapGameId);
				ibmHmGameSetT.setHandicapId(handicapId);
				ibmHmGameSetT.setUserId(appUserId);
				ibmHmGameSetT.setGameId(gameId);
				ibmHmGameSetT.setBetAutoStart(betAutoStart);
				ibmHmGameSetT.setBetAutoStartTime(DateTool.getTime(new SimpleDateFormat("HH:mm:ss").format(Long.parseLong(betAutoStartTime))));
				ibmHmGameSetT.setCreateTime(nowTime);
				ibmHmGameSetT.setCreateTimeLong(nowTime.getTime());
				ibmHmGameSetT.setState(IbmStateEnum.OPEN.name());
				ibmHmGameSetTService.save(ibmHmGameSetT);
			} else {
				ibmHmGameSetT.setBetAutoStart(betAutoStart);
				ibmHmGameSetT.setBetAutoStartTime(DateTool.getTime(new SimpleDateFormat("HH:mm:ss").format(Long.parseLong(betAutoStartTime))));
				ibmHmGameSetT.setUpdateTime(nowTime);
				ibmHmGameSetT.setUpdateTimeLong(nowTime.getTime());
				ibmHmGameSetTService.update(ibmHmGameSetT);
			}
			IbmLogHandicapMemberT ibmLogHandicapMemberT=new IbmLogHandicapMemberT();
			IbmLogHandicapMemberTService ibmLogHandicapMemberTService=new IbmLogHandicapMemberTService();
			ibmLogHandicapMemberT.setHandicapMemberId(handicapMemberId);
			ibmLogHandicapMemberT.setHandicapId(handicapId);
			ibmLogHandicapMemberT.setAppUserId(appUserT.getAppUserId());
			ibmLogHandicapMemberT.setHandleType("HMGAMESET");
			ibmLogHandicapMemberT.setCreateTime(nowTime);
			ibmLogHandicapMemberT.setCreateTimeLong(nowTime.getTime());
			ibmLogHandicapMemberT.setState(IbmStateEnum.OPEN.name());
			ibmLogHandicapMemberT.setDesc(",BET_AUTO_START_:"
					+ ibmHmGameSetT.getBetAutoStart() + ",BET_AUTO_START_TIME_:"
					+ ibmHmGameSetT.getBetAutoStartTime().toString());

			ibmLogHandicapMemberTService.save(ibmLogHandicapMemberT);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			throw e;
		}
		return this.returnJson(jrb);
	}

}
