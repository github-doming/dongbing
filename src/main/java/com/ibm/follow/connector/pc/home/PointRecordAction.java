package com.ibm.follow.connector.pc.home;

import c.a.config.SysConfig;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommCoreAction;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.follow.servlet.cloud.ibm_rep_manage_point.service.IbmRepManagePointService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpTool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 积分记录
 * @Author: wwj
 * @Date: 2020/5/13 10:04
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/pc/home/pointRecord")
public class PointRecordAction extends CommCoreAction {
	public PointRecordAction() {
		super.isTime = false;
	}

	@Override
	public Object run() throws Exception {
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		// 分页
		Integer pageIndex = NumberTool.getInteger(dataMap.get("pageIndex"), 0);
		Integer pageSize = NumberTool.getInteger(dataMap.get("pageSize"), 15);

		Map<String, Object> data = new HashMap<>(3);
		try {

			PageCoreBean<Map<String, Object>> pageDate = new IbmRepManagePointService().pageUserPointRecord(appUserId, pageIndex, pageSize);
			List<Map<String, Object>> list = pageDate.getList();
			double balance, number, preBalance;
			for (Map<String, Object> map : list) {
				balance = NumberTool.doubleT(map.get("BALANCE_T_"));
				number = NumberTool.doubleT(map.remove("NUMBER_T_"));
				preBalance = NumberTool.doubleT(map.remove("PRE_T_"));
				map.put("BALANCE_T_", balance);
				if (preBalance < balance) {
					map.put("pointIn", number);
					map.put("pointOut", "--");
				} else {
					map.put("pointIn", "--");
					map.put("pointOut", number);
				}
			}

			double point = 0;
			JSONObject result = getPointInfo();
			if (result.getBoolean("success")) {
				point = NumberTool.doubleT(result.getJSONObject("data").getString("BALANCE_T_"));
			}

			data.put("userName", appUser.getNickname());
			data.put("point", point);
			data.put("rows", pageDate.getList());
			data.put("index", pageIndex);
			data.put("total", pageDate.getTotalCount());
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("查询积分记录错误"), e);
			data.put("rows", null);
			data.put("index", 0);
			data.put("total", 0);
		}
		return data;
	}

	private JSONObject getPointInfo() {
		try {
			JSONObject parameter = new JSONObject();
			parameter.put("userId", appUserId);
			String time = System.currentTimeMillis() + "";
			parameter.put("time", time);
			parameter.put("valiDate", Md5Tool.generate(time));
			Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/point/repPoint";
			String html = HttpTool.doGet(cloudUrl, HttpTool.paramJson(parameter));
			return JSON.parseObject(html);
		} catch (Exception e) {
			log.error(IbmMainConfig.LOG_SIGN.concat("查询积分记录错误"), e);
			return new JSONObject();
		}
	}
}