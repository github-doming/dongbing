package com.ibm.old.v1.pc.ibm_hm_game_set.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.controller.mq.SetGameBetDetailController;
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
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;

/**
 * 
 * 
 * @Description: 盘口游戏设置
 * @date 2019年3月4日 上午11:12:20 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmHandicapGameSetAction extends BaseAppAction {

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
		// 投注模式
		String betMode = BeanThreadLocal.find(dataMap.get("BET_MODE_"), "");
		// 新增状态
		String increaseState = BeanThreadLocal.find(dataMap.get("INCREASE_STATE_"), "");
		// 停止新增时间
		String increaseStopTime = BeanThreadLocal.find(dataMap.get("INCREASE_STOP_TIME_"), "");
		// 每期投注时刻
		String betSecond = BeanThreadLocal.find(dataMap.get("BET_SECOND_"), "");
		// 双面分投额度
		String splitTwoSideAmount = BeanThreadLocal.find(dataMap.get("SPLIT_TWO_SIDE_AMOUNT_"), "0");
		// 号码分投额度
		String splitNumberAmount = BeanThreadLocal.find(dataMap.get("SPLIT_NUMBER_AMOUNT_"), "0");
		
		if(StringTool.isEmpty(handicapMemberId, gameCode, betSecond)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		if(!IbmStateEnum.betModeState(betMode)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_BET_MODE);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		if(!IbmStateEnum.increaseState(increaseState)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_STATE);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			IbmPcHandicapMemberTService ibmHandicapMemberTService = new IbmPcHandicapMemberTService();
			Map<String,Object> handicapMemberMap = ibmHandicapMemberTService.findHandicapIdAndLoginState(handicapMemberId);
			// 判断盘口用户是否存在
			if (ContainerTool.isEmpty(handicapMemberMap)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}

			IbmPcHandicapGameTService ibmHandicapGameTService = new IbmPcHandicapGameTService();
			String handicapGameId = ibmHandicapGameTService.findId(handicapMemberMap.get("HANDICAP_ID_").toString(), gameCode);
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
			//查询盘口会员游戏设置
			IbmHmGameSetTService ibmHmGameSetTService = new IbmHmGameSetTService();
			IbmHmGameSetT ibmHmGameSetT = ibmHmGameSetTService.find(handicapMemberId,
					handicapGameId);
			//判断盘口会员游戏设置是否存在
			if (ibmHmGameSetT == null) {
				ibmHmGameSetT = new IbmHmGameSetT();
				ibmHmGameSetT.setHandicapMemberId(handicapMemberId);
				ibmHmGameSetT.setHandicapGameId(handicapGameId);
				ibmHmGameSetT.setHandicapId(handicapMemberMap.get("HANDICAP_ID_").toString());
				ibmHmGameSetT.setUserId(appUserId);
				ibmHmGameSetT.setGameId(gameId);
				ibmHmGameSetT.setBetMode(betMode);
				ibmHmGameSetT.setIncreaseState(increaseState);
				if (StringTool.notEmpty(increaseStopTime)){
					ibmHmGameSetT.setIncreaseStopTime(new Date(NumberTool.getLong(increaseStopTime)));
				}
				ibmHmGameSetT.setBetSecond(Integer.parseInt(betSecond));
				ibmHmGameSetT.setSplitTwoSideAmount(Integer.parseInt(splitTwoSideAmount));
				ibmHmGameSetT.setSplitNumberAmount(Integer.parseInt(splitNumberAmount));
				ibmHmGameSetT.setCreateTime(nowTime);
				ibmHmGameSetT.setCreateTimeLong(nowTime.getTime());
				ibmHmGameSetT.setState(IbmStateEnum.OPEN.name());
				ibmHmGameSetTService.save(ibmHmGameSetT);

			} else {
				ibmHmGameSetT.setBetMode(betMode);
				ibmHmGameSetT.setIncreaseState(increaseState);
				if (StringTool.notEmpty(increaseStopTime)){
					ibmHmGameSetT.setIncreaseStopTime(new Date(NumberTool.getLong(increaseStopTime)));
				}
				ibmHmGameSetT.setBetSecond(Integer.parseInt(betSecond));
				ibmHmGameSetT.setSplitTwoSideAmount(Integer.parseInt(splitTwoSideAmount));
				ibmHmGameSetT.setSplitNumberAmount(Integer.parseInt(splitNumberAmount));
				ibmHmGameSetT.setUpdateTime(nowTime);
				ibmHmGameSetT.setUpdateTimeLong(nowTime.getTime());
				ibmHmGameSetTService.update(ibmHmGameSetT);
			}

			//处于登陆状态
			if (IbmStateEnum.LOGIN.name().equals(handicapMemberMap.get("LOGIN_STATE_"))){
				//发送盘口游戏会员设置信息
				jrb =	new SetGameBetDetailController().execute(handicapMemberId,gameId,gameCode);
				//设置失败
				if (!jrb.isSuccess()){
					super.transactionRoll(jdbcTool);
					return this.returnJson(jrb);
				}
			}

			IbmLogHandicapMemberT ibmLogHandicapMemberT=new IbmLogHandicapMemberT();
			IbmLogHandicapMemberTService ibmLogHandicapMemberTService=new IbmLogHandicapMemberTService();
			ibmLogHandicapMemberT.setHandicapMemberId(handicapMemberId);
			ibmLogHandicapMemberT.setHandicapId(handicapMemberMap.get("HANDICAP_ID_").toString());
			ibmLogHandicapMemberT.setAppUserId(appUserT.getAppUserId());
			ibmLogHandicapMemberT.setHandleType("HMGAMESET");
			ibmLogHandicapMemberT.setCreateTime(nowTime);
			ibmLogHandicapMemberT.setCreateTimeLong(nowTime.getTime());
			ibmLogHandicapMemberT.setState(IbmStateEnum.OPEN.name());
			ibmLogHandicapMemberT.setDesc(",BET_MODE_:"
					+ ibmHmGameSetT.getBetMode()+ ",INCREASE_STATE_:"
					+ ibmHmGameSetT.getIncreaseState() + ",INCREASE_STOP_TIME_:"
					+ ibmHmGameSetT.getIncreaseStopTime() + ",BET_SECOND_:"
					+ ibmHmGameSetT.getBetSecond().toString() + ",SPLIT_TWO_SIDE_AMOUNT_:"
					+ ibmHmGameSetT.getSplitTwoSideAmount() + ",SPLIT_NUMBER_AMOUNT_:"
					+ ibmHmGameSetT.getSplitNumberAmount());
			
			ibmLogHandicapMemberTService.save(ibmLogHandicapMemberT);

			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			throw e;
		}
		return this.returnJson(jrb);
	}

}
