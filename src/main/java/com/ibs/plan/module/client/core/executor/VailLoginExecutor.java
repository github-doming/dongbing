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
 * 验证登录控制器
 *
 * @Author: null
 * @Date: 2020-05-14 13:48
 * @Version: v1.0
 */
public class VailLoginExecutor implements ClientMqExecutor {

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
		//验证登录
		bean = handicapCode.getMemberFactory().valiLogin(handicapUrl, handicapCaptcha, memberAccount, password);

		//验证登录失败,直接返回数据
		if (!bean.isSuccess()) {
			return bean;
		}

		String existId = bean.getData().toString();

		Map<String, Object> data = new HashMap<>(3);
		CurrentTransaction.transactionBegin();
		try {
			MemberManageController controller = new MemberManageController();
			// 存储存在信息
			controller.existId(existId);
			controller.exist(handicapMemberId, code);
			// 存储个人信息
			controller.saveHandicapMember(handicapMemberId, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
			QuartzTool.saveCheckJob(existId, handicapCode);

			MemberUser userInfo = handicapCode.getMemberFactory().memberOption(existId).userInfo(true);

			controller.login();
			controller.saveUserInfo(userInfo);
			CustomerCache.stateInfo(existId, IbsStateEnum.LOGIN);
			if (userInfo != null) {
				CustomerCache.usedQuota(existId, userInfo.usedQuota());
			}

			//回传
			data.put("HANDICAP_CODE_", handicapCode);
			data.put("EXIST_HM_ID_", existId);
			data.put("MEMBER_INFO_", userInfo);
			data.put("CLIENT_CODE_", IbsModuleListener.servletCode());
		}catch (Exception e){
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		} finally {
			CurrentTransaction.transactionEnd();
		}
		return bean.success(data);
	}
}
