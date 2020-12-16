package com.ibs.plan.module.cloud.core.controller.mq;

import com.ibs.common.enums.IbsMethodEnum;
import com.ibs.common.enums.IbsTypeEnum;
import com.ibs.common.utils.GameUtil;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.tools.RabbitMqTool;
import com.ibs.plan.module.cloud.ibsp_client_hm.service.IbspClientHmService;
import com.ibs.plan.module.cloud.ibsp_hm_bet_item.service.IbspHmBetItemService;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;

import java.util.Map;

/**
 * @Description: 手动投注
 * @Author: null
 * @Date: 2020-05-29 14:23
 * @Version: v1.0
 */
public class ManualBetController {
	private String hmBetItemId;
	private String handicapMemberId;
	private String gameDrawType;
	private GameUtil.Code game;
	private HandicapUtil.Code handicapCode;
	private Object period;
	public ManualBetController(String hmBetItemId, String handicapMemberId, GameUtil.Code game,
			HandicapUtil.Code handicapCode, String gameDrawType,Object period) {
		this.hmBetItemId = hmBetItemId;
		this.handicapMemberId = handicapMemberId;
		this.game=game;
		this.handicapCode=handicapCode;
		this.gameDrawType=gameDrawType;
		this.period=period;
	}

	public String execute() throws Exception {
		Map<String,Object> existInfo =new IbspClientHmService().findExistHmInfo(handicapMemberId);
		if (ContainerTool.isEmpty(existInfo)){
			return IbsTypeEnum.FALSE.name();
		}

		String clientCode = existInfo.remove("CLIENT_CODE_").toString();

		Map<String,Object> betInfo =new IbspHmBetItemService().findBetInfo(hmBetItemId);
		if (ContainerTool.isEmpty(betInfo)){
			return IbsTypeEnum.FALSE.name();
		}
		JSONObject content = new JSONObject();
		content.putAll(betInfo);
		content.put("GAME_CODE_", game.name());
		content.put("PERIOD_", period);
		content.put("HANDICAP_CODE_", handicapCode.name());
		content.put("GAME_DRAW_TYPE_",gameDrawType);
		content.putAll(existInfo);
		content.put("METHOD_", IbsMethodEnum.MANUAL_BET.name());
		content.remove("ROW_NUM");
		return RabbitMqTool.sendMember(content.toString(),clientCode,"info");
	}
}
