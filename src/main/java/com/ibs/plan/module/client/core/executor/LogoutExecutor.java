package com.ibs.plan.module.client.core.executor;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.tools.QuartzTool;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.ibsc_exist_hm.service.IbscExistHmService;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.StringTool;

/**
 * 登出控制器
 *
 * @Author: null
 * @Date: 2020-05-26 16:19
 * @Version: v1.0
 */
public class LogoutExecutor implements ClientMqExecutor {

	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		//获取已存在信息
		String existHmId = msgObj.getString("EXIST_HM_ID_");
		IbscExistHmService existHmService = new IbscExistHmService();
		CurrentTransaction.transactionBegin();
		try {
			String code = existHmService.findHandicapCode(existHmId);
			if (StringTool.isEmpty(code)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(code);

			//清除内存
			CustomerCache.clearUp(existHmId);
			//移除定时器，新加游戏需处理
			QuartzTool.removeCheckJob(existHmId, handicapCode.name());
			//清除客户盘口爬虫信息
			handicapCode.getMemberFactory().removeCrawler(existHmId);
			//清除客户客户机上的信息
			existHmService.removeExistInfo(existHmId);
			//删除定时任务信息
			existHmService.removeQuertzInfo(existHmId);

		} finally {
			CurrentTransaction.transactionEnd();
		}

		return bean.success();
	}
}
