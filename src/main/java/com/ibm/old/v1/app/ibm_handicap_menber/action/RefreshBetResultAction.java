package com.ibm.old.v1.app.ibm_handicap_menber.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.tool.GameTool;
import com.ibm.old.v1.cloud.core.tool.HandicapGameTool;
import com.ibm.old.v1.cloud.ibm_exec_bet_item.t.service.IbmExecBetItemTService;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAsynQueryAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;
/**
 * @Description: 获取投注结果
 * @Author: Dongming
 * @Date: 2019-08-12 15:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(name = "refreshBetInfo", value = "/ibm/app/ibm_handicap_member/refresh_bet_result.dm") public class RefreshBetResultAction
		extends BaseAsynQueryAction {
	@Override public Object run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return jrb;
		}

		String handicapMemberId = BeanThreadLocal.find(dataMap.get("HANDICAP_MEMBER_ID_"), "");
		String gameCode = BeanThreadLocal.find(dataMap.get("GAME_CODE_"), "");
		String checkNum = BeanThreadLocal.find(dataMap.get("checkNum"), "");
		if (StringTool.isEmpty(handicapMemberId, gameCode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return jrb;
		}
		try {
			String handicapId = new IbmHandicapMemberTService().findHandicapId(handicapMemberId);
			if (StringTool.isEmpty(handicapId)) {
				jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
				jrb.putSysEnum(IbmCodeEnum.CODE_401);
				return jrb;
			}
			String gameId = GameTool.findId(gameCode);
			// 判断游戏是否存在
			if (StringTool.isEmpty(gameId)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return jrb;
			}
			String tableName = HandicapGameTool.getTableNameById(gameId, handicapId);
			if (StringTool.isEmpty(tableName)) {
				log.error("盘口【" + handicapId + "】游戏【" + gameCode + "】不存在");
				jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_GAME);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return jrb;
			}

			Object[] periods;
			if (StringTool.isEmpty(checkNum)) {
				periods = PeriodTool.findHistoryPeriod(IbmGameEnum.valueOf(gameCode), 2);
			} else {
				periods = PeriodTool.findHistoryPeriod(IbmGameEnum.valueOf(gameCode), NumberTool.getInteger(checkNum));
			}


			//获取当期投注总额和当期投注总数
			List<Map<String, Object>> betResults =  new IbmExecBetItemTService()
					.findBetResult(handicapMemberId, tableName, periods);
			if (ContainerTool.notEmpty(betResults)) {
				for (Map<String, Object> betResult : betResults) {
					betResult.put("betAmount", NumberTool.doubleT(betResult.remove("betAmountT")));
					betResult.put("profit", NumberTool.doubleT(betResult.remove("profitT")));
				}
			}
			jrb.setData(betResults);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			jrb.setSuccess(false);
		}
		return jrb;
	}
}
