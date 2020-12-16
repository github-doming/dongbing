package com.ibm.follow.servlet.cloud.core.controller.mq;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.follow.servlet.cloud.ibm_client_hm.service.IbmClientHmService;
import com.ibm.follow.servlet.cloud.ibm_hm_bet_item.service.IbmHmBetItemService;
import net.sf.json.JSONObject;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.ContainerTool;

import java.util.Map;
/**
 * @Description: 手动投注
 * @Author: Dongming
 * @Date: 2019-09-10 16:31
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ManualBetController {
	private String hmBetItemId;
	private String handicapMemberId;
	private String period;
	public ManualBetController(String hmBetItemId, String handicapMemberId,String period) {
		this.hmBetItemId = hmBetItemId;
		this.handicapMemberId = handicapMemberId;
		this.period=period;
	}
	public String execute() throws Exception {
		Map<String,Object>  existInfo =  new IbmClientHmService().findExistHmId(handicapMemberId);
		if (ContainerTool.isEmpty(existInfo)){
			return TypeEnum.FALSE.name();
		}
		String clientCode = existInfo.remove("CLIENT_CODE_").toString();

		Map<String,Object> betInfo = new IbmHmBetItemService().findBetInfo(hmBetItemId);
		if (ContainerTool.isEmpty(betInfo)){
			return TypeEnum.FALSE.name();
		}
		betInfo.put("GAME_CODE_", GameUtil.code(betInfo.remove("GAME_ID_")).name());
		betInfo.put("PERIOD_", period);
		JSONObject context = new JSONObject();
		context.putAll(betInfo);
		context.putAll(existInfo);
		context.put("METHOD_", IbmMethodEnum.MANUAL_BET.name());
		context.remove("ROW_NUM");
		return RabbitMqTool.sendMemberBetInfo(context.toString(),clientCode);
	}
}
