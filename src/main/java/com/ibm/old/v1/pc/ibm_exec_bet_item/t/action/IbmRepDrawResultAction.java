package com.ibm.old.v1.pc.ibm_exec_bet_item.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_rep_draw_pk10.t.service.IbmRepDrawPk10TService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAsynQueryAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_handicap_game.t.service.IbmPcHandicapGameTService;
import org.doming.core.common.servlet.ActionMapping;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Description: 获取开奖信息
 * @ClassName: IbmRepDrawResultAction
 * @author zjj
 * @date 2019年1月18日 上午10:24:00
 *
 */
@ActionMapping(name = "getRepDrawResult", value = "/ibm/pc/ibm_exec_bet_item/getRepDrawResult.dm")
public class IbmRepDrawResultAction extends BaseAsynQueryAction {
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
		String gameCode = BeanThreadLocal.find(dataMap.get("GAME_CODE_"), "");
		String handicapCode = BeanThreadLocal.find(dataMap.get("HANDICAP_CODE_"), "");

		if (StringTool.isEmpty(gameCode,handicapCode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			// 通过游戏Code查找游戏开奖表
			IbmGameTService gameTService = new IbmGameTService();
			String tableName = gameTService.findTableName(gameCode);

			// 判断游戏开奖表是否存在
			if (StringTool.isEmpty(tableName)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}
			Map<String, Object> data = new HashMap<>(4);
			IbmRepDrawPk10TService ibmRepDrawPk10TService = new IbmRepDrawPk10TService();
			List<Map<String, Object>> drawList = ibmRepDrawPk10TService.findDrawResult(tableName);

			//获取封盘时间
			IbmPcHandicapGameTService handicapGameTService = new IbmPcHandicapGameTService();
			String sealTime = handicapGameTService.findSealTime(gameCode,handicapCode);

			data.put("drawList", drawList);
			data.put("currentPeriod", PeriodTool.findPeriod(gameCode));
			data.put("currentDrawTime", PeriodTool.getDrawTime(gameCode));
			data.put("sealTime", sealTime);
			
			jrb.setData(data);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return this.returnJson(jrb);
	}

}
