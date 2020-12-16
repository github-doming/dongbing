package com.ibm.old.v1.pc.ibm_handicap_user.t.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_handicap_user.t.service.IbmPcHandicapUserTService;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 选择盘口账号
 * @Author: Dongming
 * @Author: wck
 * @Date: 2019-01-11 10:48
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SelectHandicapAccountAction extends BaseAppAction {

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
		//盘口code
		String handicapCode = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("HANDICAP_CODE_"), "");
		if (StringTool.isEmpty(handicapCode)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			IbmHandicapTService handicapTService = new IbmHandicapTService();
			String handicapId = handicapTService.findIdByCode(handicapCode);
			if (StringTool.isEmpty(handicapId)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}
			Map<String, Object> data = findHmAccount(handicapId, appUserId);
			jrb.success();
			jrb.setData(data);
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "获取已开启的用户信息失败", e);
			jrb.putEnum(IbmCodeEnum.IBM_500);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			throw e;
		}
		return returnJson(jrb);
	}

	/**
	 * 
	 * @Description: 获取托管和未托管账户信息
	 *
	 * 参数说明 
	 * @param handicapId 盘口id
	 * @param appUserId 用户id
	 * @return 托管和未托管账户信息
	 */
	static Map<String, Object> findHmAccount(String handicapId, String appUserId) throws SQLException {
		Map<String, Object> data = new HashMap<>(2);
		//获取已开启托管的用户信息
		IbmPcHandicapUserTService handicapUserTService = new IbmPcHandicapUserTService();
		data.put("onHostingInfo",handicapUserTService.listOnHostingHMInfo(appUserId, handicapId));
		//获取未开启托管的用户信息
		data.put("unHostingInfo",handicapUserTService.listUnHostingHMInfo(appUserId,handicapId));
		return data;
	}

}
