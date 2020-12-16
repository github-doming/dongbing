package com.ibm.old.v1.pc.ibm_hm_game_set.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import c.a.util.core.thread.pool.ThreadPoolExecutorService;
import c.a.util.core.thread.pool.ThreadPoolExecutorServiceListUtil;
import com.ibm.old.v1.cloud.core.controller.mq.SetGameBetInfoController;
import com.ibm.old.v1.cloud.core.thread.StartBetThread;
import com.ibm.old.v1.cloud.core.tool.HandicapGameTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_exec_plan_group.t.service.IbmExecPlanGroupTService;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.service.IbmHandicapGameTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.entity.IbmHmGameSetT;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import com.ibm.old.v1.cloud.ibm_log_handicap_member.t.entity.IbmLogHandicapMemberT;
import com.ibm.old.v1.cloud.ibm_log_handicap_member.t.service.IbmLogHandicapMemberTService;
import com.ibm.old.v1.cloud.ibm_plan_hm.t.service.IbmPlanHmTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.ContainerTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 *
 *
 * @Description: 投注状态
 * @date 2019年3月4日 上午11:35:49
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmHandicapBetStateAction extends BaseAppAction {

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
		// 投注状态
		String betState = BeanThreadLocal.find(dataMap.get("BET_STATE_"), "");

		if(StringTool.isEmpty(handicapMemberId, gameCode,betState)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}

		if(!IbmStateEnum.hmGameSetState(betState)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_STATE);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}


		try {

			IbmHandicapMemberTService ibmHandicapMemberTService = new IbmHandicapMemberTService();
			Map<String,Object> handicapMemberInfo = ibmHandicapMemberTService.findHandicapIdAndLoginState(handicapMemberId);
			// 判断盘口用户是否存在
			if (ContainerTool.isEmpty(handicapMemberInfo)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_MEMBER);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}

			String handicapId = handicapMemberInfo.get("HANDICAP_ID_").toString();

			IbmHandicapGameTService ibmHandicapGameTService = new IbmHandicapGameTService();
			String handicapGameId = ibmHandicapGameTService.find(handicapId,gameCode);
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
			IbmHmGameSetT ibmHmGameSetT = ibmHmGameSetTService.find(handicapMemberId,handicapGameId);

			if (ibmHmGameSetT == null) {
				ibmHmGameSetT = new IbmHmGameSetT();
				ibmHmGameSetT.setHandicapMemberId(handicapMemberId);
				ibmHmGameSetT.setHandicapGameId(handicapGameId);
				ibmHmGameSetT.setHandicapId(handicapId);
				ibmHmGameSetT.setUserId(appUserId);
				ibmHmGameSetT.setGameId(gameId);
				ibmHmGameSetT.setBetState(betState);
				ibmHmGameSetT.setCreateTime(nowTime);
				ibmHmGameSetT.setCreateTimeLong(nowTime.getTime());
				ibmHmGameSetT.setState(IbmStateEnum.OPEN.name());
				ibmHmGameSetTService.save(ibmHmGameSetT);

			} else {
				ibmHmGameSetT.setBetState(betState);
				ibmHmGameSetT.setUpdateTime(nowTime);
				ibmHmGameSetT.setUpdateTimeLong(nowTime.getTime());
				ibmHmGameSetTService.update(ibmHmGameSetT);
			}

			//处于登陆状态
			if (IbmStateEnum.LOGIN.name().equals(handicapMemberInfo.get("LOGIN_STATE_"))){
				//发送盘口游戏会员设置信息
				jrb =new SetGameBetInfoController().execute(handicapMemberId,gameId,gameCode);
				//设置失败
				if (!jrb.isSuccess()){
					super.transactionRoll(jdbcTool);
					return this.returnJson(jrb);
				}
			}

			//期数
			Object period = PeriodTool.findPeriod(gameCode);

			switch (betState){
				case "TRUE":
					IbmPlanHmTService planHmTService = new IbmPlanHmTService();
					planHmTService.updateState(handicapMemberId,gameId,this.getClass().getName());

					//开启一个线程，手动生成投注信息
					ThreadPoolExecutorService threadExecutorService = ThreadPoolExecutorServiceListUtil.findInstance().findLocal();
					ExecutorService executorService = threadExecutorService.findExecutorService();

					executorService.execute(new StartBetThread(gameCode, handicapId, period,handicapMemberId));
					break;
				case "FALSE":
					String tableName= HandicapGameTool.getTableNameById(gameId,handicapId);
					if(tableName==null){
						log.error("盘口【"+handicapId+"】游戏【" + gameCode + "】不存在");
						return null;
					}
					//删除已生成但未发送的投注项
					IbmExecBetItemTService execBetItemTService = new IbmExecBetItemTService();
					execBetItemTService.delByHmIdAndPeriod(handicapMemberId,period,tableName);
					//重置方案投注项状态
					IbmExecPlanGroupTService execPlanGroupTService = new IbmExecPlanGroupTService();
					execPlanGroupTService.resetPlan(handicapMemberId,gameId);
					break;
				default:
					jrb.putEnum(IbmCodeEnum.IBM_404_BET_STATE);
					jrb.putSysEnum(IbmCodeEnum.CODE_404);
					return this.returnJson(jrb);
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
			ibmLogHandicapMemberT.setDesc("BET_STATE_:" + ibmHmGameSetT.getBetState());
			ibmLogHandicapMemberTService.save(ibmLogHandicapMemberT);

			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			throw e;
		}
		return this.returnJson(jrb);
	}

}
