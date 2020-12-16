package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.server.core.job.service.MigrateService;
import org.doming.core.tools.StringTool;

import java.util.Arrays;
import java.util.List;

/**
 * @Description: 清理数据控制器
 * @Author: null
 * @Date: 2020-05-13 16:46
 * @Version: v1.0
 */
public class ClearController implements ClientExecutor {

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String methodType = msgObj.getString("methodType");
		if (StringTool.isEmpty(methodType)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		List<String> tableNames = Arrays.asList("ibmc_ha_follow_bet","ibmc_hm_bet","ibmc_hm_bet_error",
				"ibmc_hm_bet_fail","ibmc_hm_bet_info","ibmc_hm_bet_item");
		//表时间
		MigrateService migrateService=new MigrateService();

		IbmMethodEnum method = IbmMethodEnum.valueOf(methodType);
		int days;
		switch (method) {
			case ALL:
				days=0;
				break;
			case WEEK:
				days=7;
				break;
			case DELIMIT:
				days=Integer.parseInt(msgObj.getString("days"));
				break;
			default:
				bean.putEnum(CodeEnum.IBS_404_METHOD);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
		}
		migrateService.clearByDays(tableNames,days);
		bean.success();
		return bean;
	}
}
