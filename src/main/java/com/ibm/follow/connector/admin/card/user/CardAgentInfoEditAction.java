package com.ibm.follow.connector.admin.card.user;

import all.app.common.service.AppAccountService;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.follow.servlet.cloud.ibm_card_admin.service.IbmCardAdminService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.Map;

/**
 * 修改充值卡管理员基本信息
 *
 * @Author: Dongming
 * @Date: 2020-04-11 14:46
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/card/agent/editInfo")
public class CardAgentInfoEditAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		String subAgentId = StringTool.getString(dataMap,"subAgentId", "");
		String userPassWord = StringTool.getString(dataMap,"password", "");
		String oldPassword = StringTool.getString(dataMap,"oldPassword", "");
		String isAdd = StringTool.getString(dataMap,"isAdd", "false");
		String userState = StringTool.getString(dataMap,"userState", "");
		String nikeName = StringTool.getString(dataMap.get("nikeName"), "false");
		if(StringTool.isEmpty(subAgentId)){
			subAgentId = adminUserId;
		}
		try {
			IbmCardAdminService cardAdminService = new IbmCardAdminService();
			if (cardAdminService.hasEditPermission(bean, subAgentId, adminUserId)) {
				return bean;
			}
			if (StringTool.notEmpty(userPassWord)) {
				String regExpPwd = "(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}";
				if(!userPassWord.matches(regExpPwd)){
					bean.putEnum(ReturnCodeEnum.app409RegisterMatcher);
					bean.putSysEnum(ReturnCodeEnum.code409);
					return bean;
				}
				AppAccountService accountService = new AppAccountService();
				if (StringTool.notEmpty(oldPassword)){
					if (accountService.findAppAccountByPassword(subAgentId, oldPassword) == null){
						bean.putEnum(IbmCodeEnum.IBM_403_PASSWORD_IS_INCORRECT);
						bean.putSysEnum(IbmCodeEnum.CODE_403);
						return bean;
					}
				}
				accountService.updatePassword(subAgentId, userPassWord);
			}
			Map<String, Object> data = (Map<String, Object>) bean.getData();
			boolean isSelf = (boolean) data.get("isSelf");
			if (StringTool.notEmpty(isAdd) && !isSelf) {
				cardAdminService.updateIsAdd(subAgentId, Boolean.parseBoolean(isAdd));
			}
			if (StringTool.notEmpty(userState) && !isSelf) {
				String state = "false".equals(userState) ? IbmStateEnum.OPEN.name() : IbmStateEnum.CLOSE.name();
				new AuthorityService().updateUser(nikeName, state, subAgentId, adminUserId);
				cardAdminService.updateState(subAgentId,state);
			}
			bean.success();
		} catch (Exception e) {
			log.error("修改充值卡管理员基本信息出错", e);
			throw e;
		}
		return bean;
	}
}
