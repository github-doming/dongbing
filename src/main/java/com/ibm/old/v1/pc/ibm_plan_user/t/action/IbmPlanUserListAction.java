package com.ibm.old.v1.pc.ibm_plan_user.t.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_plan_user.t.service.IbmPcPlanUserTService;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;

/**
 * @Description: 用户方案列表
 * @Author: Dongming
 * @Date: 2019-01-14 15:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class IbmPlanUserListAction extends BaseAppAction {

	@Override public String run() throws Exception {
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
		String gameCode = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("GAME_CODE_"), "");
		if (StringTool.isEmpty(gameCode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}

		try {
			IbmGameTService gameTService = new IbmGameTService();
			String gameId = gameTService.findId(gameCode);
			if (StringTool.isEmpty(gameId)){
				jrb.putEnum(IbmCodeEnum.IBM_404_GAME);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(jrb);
			}
			IbmPcPlanUserTService planUserTService = new IbmPcPlanUserTService();
			List<Map<String,Object>> data = planUserTService.listPlanInfo4GameUser(gameId,appUserId);
			jrb.success();
			jrb.setData(data);
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "打开用户方案列表错误", e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return returnJson(jrb);
	}
}
