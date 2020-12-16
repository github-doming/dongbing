package com.ibm.old.v1.pc.ibm_handicap_member.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.controller.mq.CloseClientController;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.pc.ibm_handicap_member.t.service.IbmPcHandicapMemberTService;
import org.doming.core.tools.StringTool;

/**
 * @Description: 盘口会员退出登录
 * @date 2019年2月26日 下午3:22:59 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 */
public class IbmHandicapMemberLogoutAction extends BaseAppAction{

	@Override
	public String run() throws Exception {
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
		//盘口会员ID
		String handicapMemberId = BeanThreadLocal.find(dataMap.get("HANDICAP_MEMBER_ID_"), "");
		if(StringTool.isEmpty(handicapMemberId)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return this.returnJson(jrb);
		}
		
		try {
			//修改登录状态
			IbmPcHandicapMemberTService handicapMemberTService = new IbmPcHandicapMemberTService();
			String loginState = handicapMemberTService.findLoginState(handicapMemberId);
			if(StringTool.isEmpty(loginState)){
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return this.returnJson(jrb);
			}
			//处于登陆状态
			if (IbmStateEnum.LOGIN.name().equals(loginState)){
				//发送盘口游戏会员设置信息
				jrb =	new CloseClientController().execute(handicapMemberId);
				if (!jrb.isSuccess()){
					//执行不成功，返回
					super.transactionRoll(jdbcTool);
					return this.returnJson(jrb);
				}
			}

			jrb.success();
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN, e);
			jrb.putSysEnum(IbmCodeEnum.CODE_500);
			jrb.setSuccess(false);
			throw e;
		}
		return this.returnJson(jrb);
	}
}
