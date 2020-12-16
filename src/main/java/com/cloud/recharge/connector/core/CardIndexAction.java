package com.cloud.recharge.connector.core;

import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.ChannelTypeEnum;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.recharge.card_admin.service.CardAdminService;
import com.cloud.recharge.card_admin_token.entity.CardAdminToken;
import com.cloud.recharge.card_admin_token.service.CardAdminTokenService;
import com.cloud.recharge.card_operate_log.entity.CardOperateLog;
import com.cloud.recharge.card_operate_log.service.CardOperateLogService;
import com.cloud.user.app_token.service.AppTokenService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.Map;
/**
 * 用户主页-判断客户是否拥有访问权限并初始化
 *
 * @Author: Dongming
 * @Date: 2020-06-17 16:04
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/cloud/recharge/pc/index", method = HttpConfig.Method.POST)
public class CardIndexAction extends BaseUserAction {
	@Override public Object run() throws Exception {
		super.findJson();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return threadJrb;
		}
		JSONObject jsonData = JSON.parseObject(json);

		String name = StringTool.trimAll(StringTool.getString(jsonData, "name", ""));
		String token = StringTool.trimAll(StringTool.getString(jsonData, "token", ""));
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (StringTool.isEmpty(name, token)) {
			return bean.put401Data();
		}
		try {
			CardAdminTokenService adminTokenService = new CardAdminTokenService();
			CardAdminService adminService = new CardAdminService();
			String userId = adminTokenService.findUserId(token);
			//校验TOKEN，校验存在
			if (StringTool.notEmpty(userId) && adminService.findExist(userId)) {
				Map<String,Object> info = adminService.findUserInfo(userId,"");
				if (ContainerTool.notEmpty(info)){
					if (StateEnum.OPEN.name().equals(info.get("USER_STATE_"))) {
						return bean.success(info.get("USER_TYPE_"));
					}else {
						bean.putEnum(CodeEnum.CLOUD_403_DISABLE);
						bean.putSysEnum(CodeEnum.CODE_403);
						return bean;
					}
				} else {
					bean.putEnum(CodeEnum.CLOUD_403_REFRESH);
					bean.putSysEnum(CodeEnum.CODE_403);
					return bean;
				}
			}
			//验证用户是否存在
			userId = new AppTokenService().findUserIdByToken(token, ChannelTypeEnum.ADMIN.name());
			if (StringTool.isEmpty(userId)) {
				bean.putEnum(CodeEnum.CLOUD_403_PERMISSION);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}

			Map<String,Object> info = adminService.findUserInfo(userId,"");
			//校验结果-验证是否允许登录
			if (!StateEnum.OPEN.name().equals(info.get("USER_STATE_"))) {
				bean.putEnum(CodeEnum.CLOUD_403_PERMISSION);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			Date nowTime = new Date();
			long periodTimeLong = System.currentTimeMillis() + DateTool.getMillisecondsMinutes(30);
			CardAdminToken adminToken = new CardAdminToken();
			adminToken.setAppUserId(userId);
			adminToken.setValue(token);
			adminToken.setIp(findServletIp());
			adminToken.setPeriodTime(new Date(periodTimeLong));
			adminToken.setPeriodTimeLong(periodTimeLong);
			adminToken.setCreateTime(nowTime);
			adminToken.setCreateTimeLong(System.currentTimeMillis());
			adminToken.setUpdateTime(nowTime);
			adminToken.setUpdateTimeLong(System.currentTimeMillis());
			adminToken.setState(StateEnum.OPEN.name());
			adminTokenService.save(adminToken);
			adminService.updateUserName(userId,info.get("USER_NAME_").toString());

//			saveLog(nowTime,info,userId);
			return bean.success(info.get("USER_TYPE_"));
		} catch (Exception e) {
			log.error("用户登录失败，{}", e);
			bean.putEnum(ReturnCodeEnum.code500);
			bean.putSysEnum(ReturnCodeEnum.code500);
			return bean;
		}
	}

	private void saveLog(Date nowTime,Map<String,Object> info,String userId) throws Exception {
		CardOperateLog log = new CardOperateLog();
		log.setUserId(userId);
		log.setUserName(info.get("USER_NAME_"));
		log.setUserPath(info.get("USER_PATH_"));
		log.setOpertType("LOGIN");
		log.setIp(findServletIp());
		log.setOpertContent("登录系统");
		log.setCreateTime(nowTime);
		log.setCreateTimeLong(System.currentTimeMillis());
		log.setUpdateTime(nowTime);
		log.setUpdateTime(System.currentTimeMillis());
		log.setState(StateEnum.OPEN.name());
		new CardOperateLogService().save(log);
	}
}
