package com.ibm.old.v1.admin.ibm_handicap.w.action;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;

import java.util.List;
import java.util.Map;
/**
 * @Description: 查询所有盘口
 * @Author: zjj
 * @Date: 2019-08-13 12:24
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmHandicapListJsonAction extends BaseAppAction {

	@Override public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		try {
			IbmHandicapTService handicapTService = new IbmHandicapTService();
			List<Map<String, Object>> map = handicapTService.findAll();

			jrb.setData(map);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN+"获取盘口失败",e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
		}
		return this.returnJson(jrb);
	}
}
