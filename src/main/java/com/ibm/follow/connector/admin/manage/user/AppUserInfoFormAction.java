package com.ibm.follow.connector.admin.manage.user;

import c.a.config.SysConfig;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.follow.connector.admin.manage3.handicap.user.IbmAdminAppUserService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpTool;

import java.util.Date;
import java.util.Map;

/**
 * @Description: 用户信息表单页面
 * @Author: null
 * @Date: 2020-03-13 15:47
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/infoForm")
public class AppUserInfoFormAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String userId =  dataMap.getOrDefault("appUserId","").toString();
        try {
            //获取用户信息
            Map<String, Object> userInfo = new IbmAdminAppUserService().userInfo(userId);
            if (ContainerTool.notEmpty(userInfo)) {
                userInfo.put("END_TIME_", DateTool.getMinute(new Date(NumberTool.getLong(userInfo.remove("END_TIME_LONG_")))));

            }
            //获取积分
            JSONObject result = getPoint(userId);
            if (!result.getBoolean("success")) {
                return result;
            }
            String balanceT = result.getJSONObject("data").getString("BALANCE_T_");
            userInfo.put("USEABLE_POINT_", NumberTool.doubleT(balanceT));
            bean.setData(userInfo);
            bean.success();
        } catch (Exception e) {
            log.error("用户表单页面错误", e);
            return bean.error(e.getMessage());
        }
        return bean.toJsonString();
    }

    private JSONObject getPoint(String userId) {
        //校验云接口数据
		try {
			JSONObject data = new JSONObject();
			data.put("userId", userId);
			String timeStr = System.currentTimeMillis() + "";
			data.put("time", timeStr);
			data.put("valiDate", Md5Tool.generate(timeStr));
			Object url= SysConfig.findInstance().findMap().getOrDefault("cloud_url", IbmMainConfig.CLOUD_URL);
			String cloudUrl = url + "/cloud/user/api/point/repPoint";
			String html= HttpTool.doGet(cloudUrl, HttpTool.paramJson(data));
			return JSONObject.parseObject(html);
		} catch (Exception e) {
			log.error("用户表单页面错误", e);
			return new JSONObject();
		}
	}
}
