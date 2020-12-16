package com.ibs.plan.module.client.core.job.bet;

import com.common.enums.HcCodeEnum;
import com.common.handicap.MemberOption;
import com.common.handicap.crawler.entity.MemberUser;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.controller.ClientDefineController;
import com.ibs.plan.module.client.core.controller.MemberConfigController;
import com.ibs.plan.module.client.ibsc_bet.service.IbscBetService;
import com.ibs.plan.module.client.ibsc_bet_error.service.IbscBetErrorService;
import com.ibs.plan.module.client.ibsc_bet_fail.service.IbscBetFailService;
import com.ibs.plan.module.client.ibsc_bet_info.service.IbscBetInfoService;
import com.ibs.plan.module.client.ibsc_hm_game_set.service.IbscHmGameSetService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 投注job
 * @Author: null
 * @Date: 2020-05-20 15:10
 * @Version: v1.0
 */
public abstract class BaseBetJob extends BaseCommJob {
	protected Date nowTime = new Date();
	public HandicapUtil.Code handicapCode;
	public String existHmId;
	public GameUtil.Code gameCode;
	public Object period;
	public List<String> betIds;
	public Map<String, Object> betMap;
	public Map<String, Object> gameSet;

	/**
	 * 跟随投注
	 *
	 * @param existHmId 存在盘口会员主键
	 * @param gameCode  游戏code
	 * @param period    期数
	 * @return 当前对象
	 */
	public BaseBetJob bet(String existHmId, GameUtil.Code gameCode, Object period, List<String> betIds) {
		this.existHmId = existHmId;
		this.gameCode = gameCode;
		this.period = period;
		this.betIds = betIds;
		return this;
	}

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		//定时投注
		if (context != null) {
			JobDataMap map = context.getMergedJobDataMap();
			existHmId = map.getString("existHmId");
			if (map.get("gameCode") instanceof Enum) {
				gameCode = (GameUtil.Code) map.get("gameCode");
			} else {
				gameCode = GameUtil.Code.valueOf(map.get("gameCode").toString());
			}
			period = map.get("period");
			if (map.get("betIds") instanceof List) {
				betIds = (List<String>) map.get("betIds");
			}
		}
		if (CustomerCache.stateInfo(existHmId) == null) {
			log.error("盘口会员【{}】游戏【{}】期数【{}】错误信息:{}", existHmId, gameCode, period, "会员已经登出，无需投注");
			return;
		}
		if (ContainerTool.isEmpty(betIds)) {
			log.error("盘口会员【{}】游戏【{}】期数【{}】错误信息:{}", existHmId, gameCode, period, "投注主键为空，投注错误");
			return;
		}
		IbscBetService betService = new IbscBetService();
		betMap = betService.mapByIds(betIds);

		gameSet = new IbscHmGameSetService().findLimit(existHmId, gameCode);
		if (ContainerTool.isEmpty(gameSet)) {
			log.error("盘口【{}】盘口会员【{}】错误信息:{}", handicapCode, existHmId, "游戏设置信息不存在");
			return;
		}
		//修改投注状态
		betService.update(betMap.keySet(), "开始投注", IbsStateEnum.PROCESS, nowTime);
		CurrentTransaction.transactionCommit();
	}
	/**
	 * 发送结果信息
	 * @param memberOption		会员操作基类
	 */
	public void sendResultReceipt(MemberOption memberOption) throws Exception {
		//发送结果信息
		BetJobDefine.sendResultReceipt(existHmId, period, gameCode);
		//修改投注状态,无论投注成功与否，该期的投注结果都不影响下一期的投注
		new IbscBetService().update(betMap.keySet(), "投注完成", IbsStateEnum.SUCCESS, IbsStateEnum.PROCESS, new Date());

		MemberUser userInfo = memberOption.userInfo(false);
		//发送用户信息
		if (userInfo == null) {
			return;
		}
		new ClientDefineController().sendUserInfo(existHmId, userInfo);
	}
	/**
	 * 验证信息
	 * @param memberOption		会员操作基类
	 * @param betInfoId			投注信息id
	 * @param betItems			投注详情
	 * @param betType				投注类型
	 * @param flag					是否复投
	 * @param funds				投注金额
	 * @return 不通过 true 通过 false
	 */
	public boolean verify(MemberOption memberOption, String betInfoId, List<String> betItems, String betType, boolean flag, double funds) throws Exception {
		MemberUser memberUser = memberOption.userInfo(false);
		if (memberUser == null) {
			saveErrorBetInfo(betInfoId, betItems, betType, flag, IbsStateEnum.OPEN, "用户信息不存在");
			return true;
		}
		//个人余额
		//判断余额是否充足
		if (memberUser.getUsedQuota() - funds < 0) {
			memberUser = memberOption.userInfo(true);
			if (memberUser == null) {
				saveErrorBetInfo(betInfoId, betItems, betType, flag, IbsStateEnum.OPEN, "用户信息不存在");
				return true;
			}
			if (memberUser.getUsedQuota() - funds < 0) {
				saveErrorBetInfo(betInfoId, betItems, betType, flag, IbsStateEnum.OPEN, "余额不足");
				return true;
			}
		}
		//判断游戏是否封盘
		long sealTime =
				CustomerCache.resetSealTime(existHmId, gameCode, handicapCode, period) - System.currentTimeMillis();
		if (sealTime < 0) {
			saveErrorBetInfo(betInfoId, betItems, betType, flag, IbsStateEnum.OPEN, "已封盘");
			return true;
		}
		return false;
	}
	/**
	 * 保存错误投注信息
	 *
	 * @param betInfoId 投注信息id
	 * @param betItems  投注信息
	 * @param betType	  投注类型
	 * @param msg       错误消息
	 * @param flag      执行类型
	 * @param state     状态
	 */
	protected void saveErrorBetInfo(String betInfoId, List<String> betItems,String betType, boolean flag, IbsStateEnum state,
			String msg) throws Exception {
		log.error("盘口会员【{}】游戏【{}】期数【{}】错误信息:{}", existHmId, gameCode, period, msg);
		String betInfo = String.join(",", betItems);
		if (flag) {
			if (IbsStateEnum.OPEN.equals(state)) {
				new IbscBetErrorService().save(existHmId, betInfoId, gameCode, period, betInfo, msg, nowTime);
				new IbscBetInfoService().update(betInfoId, IbsStateEnum.FAIL, msg, nowTime);
			} else if (IbsStateEnum.AGAIN.equals(state)) {
				new IbscBetFailService().save(existHmId, betInfoId, gameCode, period, betInfo,betType, msg, nowTime);
				new IbscBetInfoService().update(betInfoId, IbsStateEnum.AGAIN, msg, nowTime);
			} else {
				new IbscBetFailService().save(existHmId, betInfoId, gameCode, period, betInfo,betType, msg, nowTime);
			}
		} else {
			new IbscBetErrorService().save(existHmId, betInfoId, gameCode, period, betInfo, msg, nowTime);
		}
	}
	/**
	 * 投注错误处理
	 *
	 * @param hmBetInfoId 投注信息id
	 * @param betItems    投注信息
	 * @param betType     投注类型
	 * @param flag        执行类型
	 * @param code        错误code
	 */
	public void errorProcess(String hmBetInfoId, List<String> betItems,String betType, boolean flag, String code,
			HandicapUtil.Code handicapCode) throws Exception {
		if (HcCodeEnum.IBS_403_MORE_THAN_LIMIT.getCode().equals(code)) {
			//超过限额，重新获取限额，不复投
			IbscHmGameSetService hmGameSetService = new IbscHmGameSetService();
			String hmGameSetId = hmGameSetService.findId(existHmId, gameCode.name());
			if (StringTool.notEmpty(hmGameSetId)) {
				new MemberConfigController().gameLimit(existHmId, handicapCode, gameCode, hmGameSetId);
			}
			saveErrorBetInfo(hmBetInfoId, betItems, betType,flag, IbsStateEnum.OPEN, "投注失败,超过限额");
		} else if (HcCodeEnum.IBS_403_USER_STATE.getCode().equals(code)) {
			//被禁投也不复投
			saveErrorBetInfo(hmBetInfoId, betItems,betType, flag, IbsStateEnum.OPEN, "投注失败,已被禁投");
		} else if (HcCodeEnum.IBS_403_SEAL_HANDICAP.getCode().equals(code)) {
			//已经封盘
			saveErrorBetInfo(hmBetInfoId, betItems,betType, flag, IbsStateEnum.OPEN, "投注失败,已封盘");
		} else {
			saveErrorBetInfo(hmBetInfoId, betItems,betType, flag, IbsStateEnum.AGAIN, "投注失败");
		}
	}
}
