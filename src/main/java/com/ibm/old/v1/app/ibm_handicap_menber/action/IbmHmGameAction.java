package com.ibm.old.v1.app.ibm_handicap_menber.action;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAsynCommAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.List;
import java.util.Map;
/**
 * @Description: 盘口会员游戏
 * @Author: Dongming
 * @Date: 2019-08-08 14:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(name = "IbmHmGame", value = "/ibm/app/ibm_handicap_member/hm_game.dm") public class IbmHmGameAction
		extends BaseAsynCommAction {
	@Override public Object run() throws Exception {
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
		String handicapMemberId = BeanThreadLocal.findThreadLocal().get().find(dataMap.get("HANDICAP_MEMBER_ID_"), "");
		if (StringTool.isEmpty(handicapMemberId)) {
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		try {
			List<Map<String, Object>> gameInfo = new IbmHandicapMemberTService().listGameInfo(handicapMemberId);
			if (ContainerTool.isEmpty(gameInfo)) {
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return jrb;
			}
			jrb.setData(gameInfo);
			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN + "盘口设置修改错误", e);
			throw e;
		}
		return jrb;
	}

}
