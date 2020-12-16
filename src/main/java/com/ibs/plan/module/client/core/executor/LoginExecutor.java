package com.ibs.plan.module.client.core.executor;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.common.handicap.crawler.entity.MemberUser;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.common.tools.EncryptTool;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.core.servlet.boot.IbsModuleListener;
import com.ibs.plan.common.tools.QuartzTool;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.core.controller.MemberManageController;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.tools.StringTool;

import java.util.HashMap;
import java.util.Map;
/**
 * 登录控制器
 *
 * @Author: null
 * @Date: 2020-05-14 14:20
 * @Version: v1.0
 */
public class LoginExecutor  implements ClientMqExecutor{

	@Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		String handicapMemberId = msgObj.getString("HANDICAP_MEMBER_ID_");
		String code = msgObj.getString("HANDICAP_CODE_");
		String handicapUrl = msgObj.getString("HANDICAP_URL_");
		String handicapCaptcha = msgObj.getString("HANDICAP_CAPTCHA_");
		String memberAccount = msgObj.getString("MEMBER_ACCOUNT_");
		String memberPassword = msgObj.getString("MEMBER_PASSWORD_");
		if (StringTool.isEmpty(handicapMemberId, code, handicapUrl, handicapCaptcha, memberAccount, memberPassword)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean;
		}
		//获取盘口操作类
		HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(code);
		String password = EncryptTool.decode(EncryptTool.Type.ASE, memberPassword);

		Map<String, Object> data = new HashMap<>(3);
		CurrentTransaction.transactionBegin();
		try {
			MemberManageController controller = new MemberManageController();

			//查找个人存在主键
			String existHmId = controller.exist(handicapMemberId,code);
			//登录
			bean = handicapCode.getMemberFactory().login(existHmId,handicapUrl, handicapCaptcha, memberAccount, password);
			//登录失败,直接返回数据
			if (!bean.isSuccess()) {
				return bean;
			}
			// 存储个人信息
			controller.saveHandicapMember(handicapMemberId,handicapUrl,handicapCaptcha,memberAccount,memberPassword);
			QuartzTool.saveCheckJob(existHmId, handicapCode);
			controller.login();

			MemberUser userInfo = handicapCode.getMemberFactory().memberOption(existHmId).userInfo(true);
			controller.saveUserInfo(userInfo);
			CustomerCache.stateInfo(existHmId, IbsStateEnum.LOGIN);
			CustomerCache.usedQuota(existHmId,userInfo.usedQuota());

			data.put("HANDICAP_CODE_",handicapCode);
			data.put("EXIST_HM_ID_", existHmId);
			data.put("MEMBER_INFO_", userInfo);
			data.put("CLIENT_CODE_", IbsModuleListener.servletCode());
			bean.success(data);
		}catch (Exception e){
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		} finally {
			CurrentTransaction.transactionEnd();
		}
		return bean;
	}
}
