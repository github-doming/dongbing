package com.ibs.plan.module.client.core.executor.set;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.executor.ClientMqExecutor;
import com.ibs.plan.module.client.ibsc_hm_set.entity.IbscHmSet;
import com.ibs.plan.module.client.ibsc_hm_set.service.IbscHmSetService;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 设置盘口执行器
 * @Author: null
 * @Date: 2020-05-29 16:15
 * @Version: v1.0
 */
public class SetHandicapExecutor implements ClientMqExecutor {

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String existHmId = msgObj.getString("EXIST_HM_ID_");
		if (CustomerCache.stateInfo(existHmId) == null) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		String betMerger = msgObj.getString("BET_MERGER_");
		String blastStop=msgObj.getString("BLAST_STOP_");
		int betRateT = msgObj.getInteger("BET_RATE_T_");
		if (StringTool.isEmpty(betMerger, betRateT)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}

		IbscHmSetService hmSetService=new IbscHmSetService();
		IbscHmSet hmSet = hmSetService.findEntity(existHmId);
		Date nowTime = new Date();
		if (hmSet==null) {
			hmSet=new IbscHmSet();
			hmSet.setExistHmId(existHmId);
			hmSet.setBetRateT(betRateT);
			hmSet.setBetMerger(betMerger);
			hmSet.setBlastStop(blastStop);
			hmSet.setCreateTime(nowTime);
			hmSet.setCreateTimeLong(nowTime.getTime());
			hmSet.setUpdateTime(nowTime);
			hmSet.setUpdateTimeLong(nowTime.getTime());
			hmSet.setState(IbsStateEnum.OPEN.name());
			hmSetService.save(hmSet);
		}else{
			hmSet.setBetRateT(betRateT);
			hmSet.setBetMerger(betMerger);
			hmSet.setBlastStop(blastStop);
			hmSet.setUpdateTime(nowTime);
			hmSet.setUpdateTimeLong(nowTime.getTime());
			hmSetService.update(hmSet);
		}

		bean.success();
		return bean;
	}
}
