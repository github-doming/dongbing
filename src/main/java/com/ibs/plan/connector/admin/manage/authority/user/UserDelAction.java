package com.ibs.plan.connector.admin.manage.authority.user;

import c.a.config.SysConfig;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.connector.admin.service.authority.AuthorityService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpTool;

import java.util.Map;

/**
 * 操作人员删除
 *
 * @Author: Dongming
 * @Date: 2020-04-06 17:26
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/authority/user/del")
public class UserDelAction extends CommAdminAction {
	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		if (!LogThreadLocal.isSuccess()) {
			return LogThreadLocal.findLog();
		}
		String userId = StringTool.getString(dataMap, "userId", null);

		try {
			AuthorityService authorityService = new AuthorityService();
			Map<String, Object> user = authorityService.findUser(userId);
			if (ContainerTool.isEmpty(user)) {
				bean.putEnum(CodeEnum.IBS_404_DATA);
				bean.putSysEnum(CodeEnum.CODE_404);
				return bean;
			}
			authorityService.deleteUser(userId);

			//校验云接口数据
			JSONObject data = new JSONObject();
			data.put("token", token);
			data.put("userId", userId);
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

			return bean.success();
		} catch (Exception e) {
			log.error("操作人员删除 错误");
			throw e;
		}
	}
}
