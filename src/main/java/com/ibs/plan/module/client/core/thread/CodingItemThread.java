package com.ibs.plan.module.client.core.thread;

import com.common.game.Period;
import com.common.tools.CacheTool;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.module.client.ibsc_exist_hm.service.IbscExistHmService;
import com.ibs.plan.module.client.ibsc_hm_coding_item.service.IbscHmCodingItemService;
import com.ibs.plan.module.client.ibsc_hm_game_set.entity.IbscHmGameSet;
import com.ibs.plan.module.client.ibsc_hm_game_set.service.IbscHmGameSetService;
import com.ibs.plan.module.client.ibsc_hm_plan.service.IbscHmPlanService;
import com.ibs.plan.module.client.ibsc_hm_set.service.IbscHmSetService;
import com.ibs.plan.module.client.ibsc_plan_item.service.IbscPlanItemService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.thread.BaseCommThread;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 编码详情执行线程
 * @Author: null
 * @Date: 2020-06-12 17:48
 * @Version: v1.0
 */
public class CodingItemThread extends BaseCommThread {

	private String existHmId;
	private GameUtil.Code gameCode;

	public CodingItemThread(String existHmId, GameUtil.Code gameCode){
		this.existHmId=existHmId;
		this.gameCode=gameCode;
	}

	@Override
	public String execute(String inVar) throws Exception {
		CurrentTransaction.transactionCommit();
		String handicap=new IbscExistHmService().findHandicapCode(existHmId);
		if (StringTool.isEmpty(handicap)) {
			return null;
		}
		IbscHmGameSet gameSet=new IbscHmGameSetService().findEntity(existHmId,gameCode.name());
		if(gameSet==null){
			return null;
		}
		HandicapUtil.Code handicapCode=HandicapUtil.Code.valueOf(handicap);
		if (IbsTypeEnum.FALSE.name().equals(gameSet.getBetState())) {
			return null;
		}
		//判断是否有开启的方案
		List<String> openItemIds = new IbscHmPlanService().getOpenItemId(existHmId,gameCode.name());
		if(ContainerTool.isEmpty(openItemIds)){
			return null;
		}
		Period<?> periodOption = gameCode.getGameFactory().period(handicapCode);
		Object period=periodOption.findPeriod();
		//判断当前期生成方案是否执行完成了,不为空则已经编码完成
		if(new IbscHmCodingItemService().findCodingItem(existHmId,gameCode.name(),period)){
			return null;
		}
		//基准期数
		Object basePeriod = periodOption.findLotteryPeriod();
		//判断上一期开奖信息是否已存在,不存在，则直接返回，存在则主动进行编码
		if (!ContainerTool.notEmpty(CacheTool.getDraw(gameCode,handicapCode, gameSet.getGameDrawType(), basePeriod))) {
			return null;
		}
		//已开启基础信息列表
		List<Map<String, Object>> planItems = new IbscPlanItemService().listHmPlanItem(openItemIds);
		//获取会员游戏设置信息
		Map<String, Object> gameInfo=new HashMap<>(4);
		gameInfo.put("INCREASE_STATE_",gameSet.getIncreaseState());
		gameInfo.put("BET_SECOND_",gameSet.getBetSecond());
		gameInfo.put("BET_MODE_",gameSet.getBetMode());
		gameInfo.put("IS_INVERSE_",gameSet.getIsInverse());
		//获取会员设置信息
		Map<String, Object> setInfo= new IbscHmSetService().findSetInfo(existHmId);
		setInfo.put("HANDICAP_CODE_",handicapCode.name());

		new PlanItemThread(existHmId, planItems,gameCode,gameSet.getGameDrawType(),period,gameInfo,setInfo).execute(null);

		return null;
	}
}
