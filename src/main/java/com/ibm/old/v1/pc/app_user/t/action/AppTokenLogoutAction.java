package com.ibm.old.v1.pc.app_user.t.action;
import all.gen.app_token.t.entity.AppTokenT;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.UserStateEnum;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.uuid.Uuid;
import com.ibm.old.v1.cloud.core.controller.mq.CloseClientController;
import com.ibm.old.v1.cloud.ibm_handicap_member.t.service.IbmHandicapMemberTService;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 注销登录
 * @Author: zjj
 * @Date: 2019-05-05 10:50
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppTokenLogoutAction extends BaseAppAction {

	@Override public String run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findJson();
		super.findAppUser();
		if (StringTool.isEmpty(json)) {
			bean.putEnum(ReturnCodeEnum.app400Session);
			bean.putSysEnum(ReturnCodeEnum.code400);
			return returnJson(bean);
		}
		if (appUserT == null) {
			bean.putEnum(IbmCodeEnum.IBM_404_NOT_FIND_USER);
			bean.putSysEnum(IbmCodeEnum.CODE_404);
			return this.returnJson(bean);
		}
		try {

			Map<String, Object> dataMapCustom = JsonThreadLocal.findThreadLocal().get().json2map(json);
			String token = BeanThreadLocal.find(dataMapCustom.get("token"), "");

			// AppToken
			AppTokenT appTokenT = appTokenRedisService.findAppTokenByToken(token);
			if (appTokenT == null) {
				bean.putEnum(ReturnCodeEnum.app401Token);
				bean.putSysEnum(ReturnCodeEnum.code401);
				return this.returnJson(bean);
			}
			// 从redis删除Token
			if (StringTool.notEmpty(token)) {
				// value sent to redis cannot be null
				appTokenRedisService.delTokenByRedis(token);
			}

			Date date = new Date();
			// 更新Token
			String tokenNew = Uuid.create().toString();
			// String tokenNew =null;
			appTokenT.setValue(tokenNew);
			appTokenT.setState(UserStateEnum.LOGOUT.getCode());
			appTokenT.setUpdateTime(date);
			appTokenT.setUpdateTimeLong(date.getTime());
			appTokenRedisService.update(appTokenT);
			// 返回json
			// jrb.setData(appTokenT);

			//退出该用户所有盘口托管状态
			IbmHandicapMemberTService handicapMemberTService = new IbmHandicapMemberTService();
			List<String> handicapMemberIds = handicapMemberTService.listIdByUserId(appUserId);
			if (!ContainerTool.isEmpty(handicapMemberIds)) {
				for (String handicapMemberId : handicapMemberIds) {
					new CloseClientController().execute(handicapMemberId);
				}
			}

			bean.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			throw e;
		}
		return this.returnJson(bean);
	}
}
