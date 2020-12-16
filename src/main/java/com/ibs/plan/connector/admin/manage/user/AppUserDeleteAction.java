package com.ibs.plan.connector.admin.manage.user;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.user.IbsAdminAppUserService;
import com.ibs.plan.module.cloud.core.controller.process.LogoutHmController;
import com.ibs.plan.module.cloud.ibsp_hm_info.service.IbspHmInfoService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpTool;

import java.util.Date;
import java.util.List;

/**
 * @Description: 删除用户信息
 * @Author: Dongming
 * @Date: 2019-11-09 09:55
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/delete")
public class AppUserDeleteAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}

		String delUserId = StringTool.getString(dataMap,"appUserId", "");
		if (StringTool.isEmpty(delUserId)) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return bean.toString();
		}
		try {
			String desc = this.getClass().getSimpleName().concat(",").concat(delUserId).concat(",删除用户信息:")
					.concat(delUserId);
			Date nowTime = new Date();

			// 退出相关会员
			delete(nowTime);

			IbsAdminAppUserService adminAppUserService = new IbsAdminAppUserService();
			// 删除所有盘口信息
			adminAppUserService.delByUserId(delUserId, nowTime, desc);

			// 移除用户信息，账号信息，token信息，点卡信息
			adminAppUserService.delUserInfo(delUserId,nowTime,desc);

			//校验云接口数据
			JSONObject data = new JSONObject();
			data.put("token", token);
			data.put("userId", delUserId);
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			Object url= SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url+"/cloud/user/api/delUser";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			JSONObject result = JSON.parseObject(html);
			if (!result.getBoolean("success")) {
				return result;
			}


			bean.success();
		} catch (Exception e) {
			log.error("删除用户信息错误",e);
			bean.putEnum(CodeEnum.IBS_500);
			bean.putSysEnum(CodeEnum.CODE_500);
		}
		return bean.toString();
	}

	private void delete(Date nowTime) throws Exception {
		IbspHmInfoService hmInfoService=new IbspHmInfoService();
		List<String> handicapMemberIds =hmInfoService.listHostingHmIdByUserId(appUserId);
		if (ContainerTool.notEmpty(handicapMemberIds)) {
			LogoutHmController hmController=new LogoutHmController();
			for(String handicapMemberId:handicapMemberIds){
				hmController.execute(handicapMemberId,nowTime);
			}
		}
	}
}
