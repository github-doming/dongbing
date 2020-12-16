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
 * 设置停止新增
 *
 * @Author: null
 * @Date: 2020-06-04 11:46
 * @Version: v1.0
 */
public class SetIncreaseExecutor implements ClientMqExecutor {
	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String gameCode = msgObj.getString("GAME_CODE_");
		String increase = msgObj.getString("INCREASE_");
		if (StringTool.isEmpty(gameCode, increase)) {
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
		gameSet.setIncreaseState(increase);
		gameSet.setUpdateTime(new Date());
		gameSet.setUpdateTimeLong(System.currentTimeMillis());
		hmGameSetService.update(gameSet);
		return bean.success();
	}
}
