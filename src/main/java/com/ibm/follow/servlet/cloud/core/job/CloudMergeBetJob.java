package com.ibm.follow.servlet.cloud.core.job;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.EncryptTool;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.GameMergeTool;
import com.ibm.follow.servlet.cloud.core.CloudCustomerTool;
import com.ibm.follow.servlet.cloud.core.controller.tool.ProfitPeriodTool;
import com.ibm.follow.servlet.cloud.ibm_ha_member_bet_item.service.IbmHaMemberBetItemService;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.entity.IbmHmBetItem;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service.IbmHmBetItemService;
import com.ibm.follow.servlet.cloud.vr_fm_member_bet_item.service.VrFmMemberBetItemService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 合并投注项
 * @Author: Dongming
 * @Date: 2019-09-21 15:24
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CloudMergeBetJob extends BaseCommJob {
    private String handicapMemberId;
    private GameUtil.Code gameCode;
    private Object period;
    private  String betMode;
    private String handicapId;
    private double betRate;

	@Override public void executeJob(JobExecutionContext context) throws Exception {
        if (context != null) {
            JobDataMap map = context.getMergedJobDataMap();
            handicapMemberId = map.getString("handicapMemberId");
            gameCode = (GameUtil.Code) map.get("gameCode");
            period = map.get("period");
            betMode = map.getString("betMode");
            handicapId = map.getString("handicapId");
            betRate = map.getDouble("betRate");
        }

		Map<String, int[][]> betInfo = CloudCustomerTool.getBetInfo(handicapMemberId, gameCode);
        String followBetIds=CloudCustomerTool.getFollowBetIds(handicapMemberId,gameCode);
		if (ContainerTool.isEmpty(betInfo)|| StringTool.isEmpty(followBetIds)) {
			log.error(String.format("用户【%s】的【%s】游戏在【%s】期，获取到的合并投注编码为空，无法合并,错误跟投id为【%s】", handicapMemberId, gameCode.getName(),
					period,followBetIds));
			return;
		}
		//合并投注项资金
		GameMergeTool.mergeInfo(gameCode, betInfo);

		//合并投注项
		List<Object> info = GameMergeTool.mergeItem(gameCode, betInfo, betRate);
		String betItem= (String) info.get(0);
		Integer fundsTh = (Integer) info.get(1);

		if(StringTool.isContains(period.toString(),"-")){
			period=period.toString().substring(4);
		}
		//存入投注数据
		IbmHmBetItem hmBetItem = new IbmHmBetItem();
		hmBetItem.setClientHaFollowBetId(followBetIds);
		hmBetItem.setHandicapId(handicapId);
		hmBetItem.setHandicapMemberId(handicapMemberId);
		hmBetItem.setGameId(GameUtil.id(gameCode));
		hmBetItem.setPeriod(period);
		hmBetItem.setBetMode(betMode);
		hmBetItem.setBetType(IbmTypeEnum.MERGE.ordinal());
		hmBetItem.setFollowMemberAccount(IbmTypeEnum.MERGE.name());
		hmBetItem.setBetContentLen(betItem.split("#").length);
		hmBetItem.setBetContent(betItem);
		hmBetItem.setFundT(fundsTh);
		hmBetItem.setBetInfoCode(EncryptTool.encode(EncryptTool.Type.MD5, hmBetItem.getBetContent()));
		hmBetItem.setCreateTime(new Date());
		hmBetItem.setCreateTimeLong(System.currentTimeMillis());
		hmBetItem.setUpdateTimeLong(System.currentTimeMillis());
		hmBetItem.setState(IbmStateEnum.OPEN.name());

        String execState=IbmStateEnum.PROCESS.name();
        //投注为虚拟投注时，汇总到当期投注信息
        if (IbmTypeEnum.VIRTUAL.name().equals(betMode)) {
            ProfitPeriodTool
                    .saveHmProfitPeriodVr(handicapMemberId, handicapId, GameUtil.id(gameCode), period, fundsTh, betItem.split("#").length);
            //修改代理跟投信息
            new IbmHaMemberBetItemService().updateProcessInfo(followBetIds);

			new VrFmMemberBetItemService().updateProcessInfo(followBetIds);
            execState=IbmStateEnum.SUCCESS.name();
        }
        if(StringTool.isEmpty(betItem)){
            execState=IbmStateEnum.FINISH.name();
            hmBetItem.setDesc("合并投注项为空");
        }
        hmBetItem.setExecState(execState);
        new IbmHmBetItemService().save(hmBetItem);
		log.info(String.format("用户【%s】的【%s】游戏在【%s】期，合并完成", handicapMemberId, gameCode.getName(), period));
	}

    public CloudMergeBetJob mergeBet(String handicapMemberId, GameUtil.Code gameCode, Object period, String betMode, String handicapId, double betRate) {
	    this.handicapMemberId=handicapMemberId;
	    this.gameCode=gameCode;
	    this.period=period;
	    this.betMode=betMode;
	    this.handicapId=handicapId;
	    this.betRate=betRate;
	    return this;
    }
}
