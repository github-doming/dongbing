package com.ibs.plan.module.client.core.executor.set;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.executor.ClientMqExecutor;
import com.ibs.plan.module.client.ibsc_hm_game_set.entity.IbscHmGameSet;
import com.ibs.plan.module.client.ibsc_hm_game_set.service.IbscHmGameSetService;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 设置反投执行器
 * @Author: null
 * @Date: 2020-06-03 16:06
 * @Version: v1.0
 */
public class SetInverseExecutor implements ClientMqExecutor {

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String gameCode = msgObj.getString("GAME_CODE_");
		String isInverse = msgObj.getString("IS_INVERSE_");
		if (StringTool.isEmpty(gameCode, isInverse)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		String existHmId = msgObj.getString("EXIST_HM_ID_");
		if (CustomerCache.stateInfo(existHmId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		IbscHmGameSetService hmGameSetService=new IbscHmGameSetService();
		IbscHmGameSet gameSet=hmGameSetService.findEntity(existHmId,gameCode);
		if(gameSet==null){
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		gameSet.setIsInverse(isInverse);
		gameSet.setUpdateTime(new Date());
		gameSet.setUpdateTimeLong(System.currentTimeMillis());
		hmGameSetService.update(gameSet);

		bean.success();
		return bean;
	}
}
