package com.ibs.plan.connector.admin.manage.user;

import c.a.config.SysConfig;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.common.enums.TypeEnum;
import com.ibs.common.configs.IbsConfig;
import com.ibs.common.core.CommAdminAction;
import com.ibs.plan.module.cloud.ibsp_exp_user.service.IbspExpUserService;
import com.ibs.plan.module.cloud.ibsp_hm_user.service.IbspHmUserService;
import com.ibs.plan.module.cloud.ibsp_sys_manage_time.service.IbspSysManageTimeService;
import com.ibs.plan.module.cloud.ibsp_user.service.IbspUserService;
import com.ibs.plan.module.cloud.ibsp_user_token.service.IbspUserTokenService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 用户列表
 * @Author: Dongming
 * @Date: 2019-11-07 14:19
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/user/list")
public class AppUserListAction extends CommAdminAction {

	@Override
	public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		String userName = StringTool.getString(dataMap.get("userName"), "");
		long startTime = NumberTool.getLong(dataMap.get("startTime"), 0L);
		long endTime = NumberTool.getLong(dataMap.get("endTime"), 0L);

		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);
		Map<String, Object> data = new HashMap<>(3);

		try {
			IbspUserService userService = new IbspUserService();

			PageCoreBean<Map<String, Object>> basePage = userService.listShow(userName,  startTime, endTime, pageIndex, pageSize);
			List<Map<String, Object>> userInfos = basePage.getList();

			List<String> handicapNames;
			List<String> userIdList = new ArrayList<>();
			for (Map<String, Object> userInfo : userInfos) {
				userIdList.add(userInfo.get("APP_USER_ID_").toString());
			}
			Map<String,Object> timeInfos = new IbspSysManageTimeService().getUsersEndTime(userIdList);
			Map<String, List<String>> hmInfos =new IbspHmUserService().listHandicapNameByUserIds(userIdList);
			Map<String, Map<String, Object>> onlineInfos = new IbspExpUserService().findUsersHandicapInfo(userIdList);
			Map<String,Object> loginInfos=new IbspUserTokenService().findList(userIdList, TypeEnum.USER.name());

			Map<String,Object> pointInfos = getUsersPointInfo();
			//用户信息
			for (Map<String, Object> userInfo : userInfos) {
				String userId = userInfo.get("APP_USER_ID_").toString();

				//可用积分-结束时间
				userInfo.put("USEABLE_POINT_", NumberTool.doubleT(pointInfos.get(userId)));
				userInfo.put("END_TIME_",timeInfos.getOrDefault(userId, 0));

				//拥有会员盘口
				handicapNames = hmInfos.get(userId);
				if (ContainerTool.notEmpty(handicapNames)) {
					userInfo.put("MEMBER_HANDICAP_", String.join(",", handicapNames));
				}
				if (ContainerTool.isEmpty(onlineInfos.get(userId))) {
					userInfo.put("MEMBER_ONLINE_", 0);
				} else {
					userInfo.putAll(onlineInfos.get(userId));
				}
				//登录信息
				if(loginInfos.containsKey(userId)){
					userInfo.put("STATE_", "在线");
					userInfo.put("IP_", loginInfos.get(userId));
				}else{
					userInfo.put("STATE_", "离线");
					userInfo.put("IP_", "-");
				}
			}

			//回传数据
			data.put("userName",userName);
			data.put("startTime",startTime);
			data.put("endTime",endTime);
			data.put("rows", userInfos);
			data.put("index", pageIndex);
			data.put("total", basePage.getTotalCount());
		} catch (Exception e) {
			log.error("用户列表错误", e);
			data.clear();
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}

	private Map<String,Object> getUsersPointInfo() {
		try {
			//校验云接口数据
			JSONObject data = new JSONObject();
			String time = System.currentTimeMillis() + "";
			data.put("time", time);
			data.put("valiDate", Md5Tool.generate(time));
			Object url= SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbsConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/point/usersPointInfo";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			JSONObject result = JSON.parseObject(html);

			if (!result.getBoolean("success")) {
				return result;
			}
			return (Map<String, Object>) result.get("data");
		} catch (Exception e) {
			log.error("请求云服务端错误",e);
			return new JSONObject();
		}
	}
}
