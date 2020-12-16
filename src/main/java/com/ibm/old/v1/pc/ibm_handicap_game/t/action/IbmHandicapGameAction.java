package com.ibm.old.v1.pc.ibm_handicap_game.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.controller.mq.SetGameBetInfoController;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_handicap_game.t.service.IbmPcHandicapGameTService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * @author zjj
 * @ClassName: IbmHandicapGameZAction
 * @Description: 盘口游戏
 * @date 2019年1月8日 下午6:27:54
 */
public class IbmHandicapGameAction extends BaseAppAction {

	private JsonResultBeanPlus jrb = new JsonResultBeanPlus();

	@Override public String run() throws Exception {
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		//判断用户是否存在
		if (appUserT == null) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		//盘口会员id
		String handicapMemberId = BeanThreadLocal.find(dataMap.get("HANDICAP_MEMBER_ID_"), "");

		if (StringTool.isEmpty(handicapMemberId)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			//获取盘口会员游戏设置
			IbmPcHandicapGameTService ibmHandicapGameTService = new IbmPcHandicapGameTService();
			List<Map<String, Object>> gameSetList = ibmHandicapGameTService.listGameSet(handicapMemberId);

			//判断盘口会员游戏是否为空，
			if (ContainerTool.isEmpty(gameSetList)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_401);
				return this.returnJson(jrb);
			}

			for (Map<String, Object> map : gameSetList) {
				if(StringTool.isEmpty(map.get("BET_AUTO_STOP_TIME_"))){
					map.put("BET_AUTO_STOP_TIME_",DateTool.getLongTime("00:00:00"));
				}else{
					map.put("BET_AUTO_STOP_TIME_",DateTool.getLongTime(map.get("BET_AUTO_STOP_TIME_")));
				}
				if(StringTool.isEmpty(map.get("BET_AUTO_START_TIME_"))){
					map.put("BET_AUTO_START_TIME_",DateTool.getLongTime("00:00:00"));
				}else{
					map.put("BET_AUTO_START_TIME_",DateTool.getLongTime(map.get("BET_AUTO_START_TIME_")));
				}
				if(StringTool.isEmpty(map.get("INCREASE_STOP_TIME_"))){
					map.put("INCREASE_STOP_TIME_",DateTool.getLongTime("00:00:00"));
				}else{
					map.put("INCREASE_STOP_TIME_",DateTool.getLongTime(map.get("INCREASE_STOP_TIME_")));
				}
				new SetGameBetInfoController().execute(handicapMemberId, map.get("GAME_ID_").toString());
			}
			jrb.setData(gameSetList);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return this.returnJson(jrb);
	}

}
