package com.ibm.follow.servlet.client.core.job.bet.member;

import com.common.enums.HcCodeEnum;
import com.common.handicap.MemberOption;
import com.common.handicap.crawler.entity.MemberUser;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.core.cache.CustomerCache;
import com.ibm.follow.servlet.client.core.controller.config.SetToolController;
import com.ibm.follow.servlet.client.ibmc_hm_bet.service.IbmcHmBetService;
import com.ibm.follow.servlet.client.ibmc_hm_bet_error.service.IbmcHmBetErrorService;
import com.ibm.follow.servlet.client.ibmc_hm_bet_fail.service.IbmcHmBetFailService;
import com.ibm.follow.servlet.client.ibmc_hm_bet_info.service.IbmcHmBetInfoService;
import com.ibm.follow.servlet.client.ibmc_hm_game_set.service.IbmcHmGameSetService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: null
 * @Date: 2019-12-27 16:44
 * @Version: v1.0
 */
public class BetJob extends BaseCommJob {
    public String existHmId;
    public GameUtil.Code gameCode;
    public Object period;
    public String hmBetId;
    public Map<String, Object> hmBetMap;
    public Map<String, Object> hmGameSet;
    public HandicapUtil.Code handicapCode;

    @Override
    public void executeJob(JobExecutionContext context) throws Exception {
        //定时投注
        if (context != null) {
            JobDataMap map = context.getMergedJobDataMap();
            existHmId = map.getString("existHmId");
            if(map.get("gameCode") instanceof Enum){
                gameCode = (GameUtil.Code) map.get("gameCode");
            }else{
                gameCode=GameUtil.Code.valueOf(map.get("gameCode").toString());
            }
            period = map.get("period");
        }
        if (CustomerCache.stateInfo(existHmId) == null){
            log.error("盘口会员【{}】游戏【{}】期数【{}】错误信息:{}",existHmId,gameCode,period,"会员已经登出，无需投注");
            return;
        }
        IbmcHmBetService hmBetService = new IbmcHmBetService();
        if (hmBetId != null) {
            //手动投注
            hmBetMap = hmBetService.findById(hmBetId);
        } else {
            //跟随投注
            hmBetMap = hmBetService.findBetInfo(existHmId, gameCode, period);
        }
        if (ContainerTool.isEmpty(hmBetMap)) {
            log.info("盘口【{}】盘口会员【{}】游戏【{}】期数【{}】错误信息:{}",handicapCode,existHmId,gameCode,period,"不存在投注项");
            return;
        }
        hmGameSet = new IbmcHmGameSetService().findLimit(existHmId, gameCode);
        if (ContainerTool.isEmpty(hmGameSet)) {
            log.error("盘口【{}】盘口会员【{}】错误信息:{}",handicapCode,existHmId,"游戏设置信息不存在");
            return;
        }
        //修改投注状态
        hmBetService.updateState(String.join(",", hmBetMap.keySet()), IbmStateEnum.BET.name());
        CurrentTransaction.transactionCommit();
    }
	public boolean verify(MemberOption memberOption, String hmBetInfoId, List<String> betItems, String betType, boolean flag, double funds) throws Exception {
		//获取用户信息
		MemberUser memberUser = memberOption.userInfo(false);
		if (memberUser == null) {
			saveErrorBetInfo(hmBetInfoId, betItems, betType, flag, IbmStateEnum.OPEN.name(), "用户信息不存在");
			return true;
		}
		//个人余额
		if (memberUser.getUsedQuota() - funds < 0) {
			memberUser = memberOption.userInfo(true);
			if (memberUser == null) {
				saveErrorBetInfo(hmBetInfoId, betItems, betType, flag, IbmStateEnum.OPEN.name(), "用户信息不存在");
				return true;
			}
			if (memberUser.getUsedQuota() - funds < 0) {
				saveErrorBetInfo(hmBetInfoId, betItems, betType, flag, IbmStateEnum.OPEN.name(), "余额不足");
				return true;
			}
		}
		//判断游戏是否封盘
		long sealTime = CustomerCache.resetSealTime(existHmId, gameCode, handicapCode, period) - System
				.currentTimeMillis();
		if (sealTime < 0) {
			saveErrorBetInfo(hmBetInfoId, betItems, betType, flag, IbmStateEnum.OPEN.name(), "已封盘");
			return true;
		}
		return false;
	}
	/**
	 * 发送结果信息
	 *
	 * @param memberOption 盘口会员工具类
	 * @param methodType 方法类型
	 */
	public void sendResultReceipt(MemberOption memberOption, String methodType) throws Exception {
		//发送结果信息
		BetJobDefine.sendResultReceipt(existHmId, period, gameCode, methodType);
		//发送用户信息
		MemberUser userInfo = memberOption.userInfo(false);
		//发送用户信息
		if (userInfo == null) {
			return;
		}
		BetJobDefine.sendUserInfo(existHmId, userInfo);
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
    public void errorProcess(String hmBetInfoId, List<String> betItems, String betType, boolean flag, String code,HandicapUtil.Code handicapCode) throws Exception {
        if (HcCodeEnum.IBS_403_MORE_THAN_LIMIT.getCode().equals(code)) {
            //超过限额，重新获取限额，不复投
            IbmcHmGameSetService hmGameSetService = new IbmcHmGameSetService();
            String hmGameSetId = hmGameSetService.findId(existHmId, gameCode.name());
            if (StringTool.notEmpty(hmGameSetId)) {
                SetToolController.gameLimit(existHmId, handicapCode, gameCode, hmGameSetId);
            }
            saveErrorBetInfo(hmBetInfoId, betItems, betType, flag, IbmStateEnum.OPEN.name(), "投注失败,超过限额");
        } else if (HcCodeEnum.IBS_403_USER_BAN_BET.getCode().equals(code)) {
            //被禁投也不复投
            saveErrorBetInfo(hmBetInfoId, betItems, betType, flag, IbmStateEnum.OPEN.name(), "投注失败,已被禁投");
        } else if (HcCodeEnum.IBS_403_SEAL_HANDICAP.getCode().equals(code)) {
            //已经封盘
            saveErrorBetInfo(hmBetInfoId, betItems, betType, flag, IbmStateEnum.OPEN.name(), "投注失败,已封盘");
        } else {
            saveErrorBetInfo(hmBetInfoId, betItems, betType, flag, IbmStateEnum.AGAIN.name(), "投注失败");
        }
    }
    /**
     * 保存错误投注信息
     *
     * @param hmBetInfoId 投注信息id
     * @param betItems    投注信息
     * @param msg         错误消息
     * @param flag        执行类型
     * @param state       状态
     * @param betType     投注类型
     */
    public void saveErrorBetInfo(String hmBetInfoId, List<String> betItems, String betType, boolean flag, String state,
                                  String msg) throws Exception {
        log.error("盘口会员【{}】游戏【{}】期数【{}】错误信息:{}",existHmId,gameCode,period,msg);
        String betInfo = String.join(",", betItems);
        if (flag) {
            if (IbmStateEnum.OPEN.name().equals(state)) {
                new IbmcHmBetErrorService().save(existHmId, hmBetInfoId, gameCode, period, betInfo, msg, state);
                new IbmcHmBetInfoService().updateState(hmBetInfoId, IbmStateEnum.FAIL.name());
            } else if (IbmStateEnum.AGAIN.name().equals(state)) {
                new IbmcHmBetFailService().save(existHmId, hmBetInfoId, gameCode, period, betInfo, betType, msg);
                new IbmcHmBetInfoService().updateState(hmBetInfoId, IbmStateEnum.AGAIN.name());
            } else {
                new IbmcHmBetFailService().save(existHmId, hmBetInfoId, gameCode, period, betInfo, betType, msg);
            }
        } else {
            new IbmcHmBetErrorService().save(existHmId, hmBetInfoId, gameCode, period, betInfo, msg, IbmStateEnum.OPEN.name());
        }
    }
    /**
     * 跟随投注
     *
     * @param existHmId 存在盘口会员主键
     * @param gameCode  游戏code
     * @param period    期数
     * @return 当前对象
     */
    public BetJob followBet(String existHmId, GameUtil.Code gameCode, Object period) {
        this.existHmId = existHmId;
        this.gameCode = gameCode;
        this.period = period;
        return this;
    }
    /**
     * @param existHmId 存在盘口会员主键
     * @param gameCode  游戏code
     * @param period    期数
     * @param hmBetId   投注信息主键
     * @return 当前对象
     */
    public BetJob manualBet(String existHmId, GameUtil.Code gameCode, Object period, String hmBetId) {
        this.existHmId = existHmId;
        this.gameCode = gameCode;
        this.period = period;
        this.hmBetId = hmBetId;
        return this;
    }
    /**
     * 获取投注job
     * @param handicapCode
     * @return
     */
    public static BetJob getJob(HandicapUtil.Code handicapCode){
        switch (handicapCode) {
            case IDC:
                return new BetIdcJob();
            case HQ:
                return new BetHqJob();
            case SGWIN:
                return new BetSgwinJob();
            case NCOM1:
                return new BetNcom1Job();
            case NCOM2:
                return new BetNcom2Job();
            case BW:
                return new BetBwJob();
            case COM:
                return new BetComJob();
            case NEWCC:
                return new BetNewCcJob();
            case UN:
                return new BetUNJob();
            case NEWWS:
                return new BetNewWsJob();
            case FC:
                return new BetFCJob();
            default:
                throw new IllegalArgumentException("错误的盘口类型捕捉");
        }
    }
}
