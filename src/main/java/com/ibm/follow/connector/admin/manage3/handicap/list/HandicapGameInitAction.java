package com.ibm.follow.connector.admin.manage3.handicap.list;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.tools.ParamTool;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.follow.servlet.client.core.controller.ClientModuleDefine;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.jsoup.HttpJsoupTool;

/**
 * @Description: 盘口游戏初始化信息
 * @Author: null
 * @Date: 2020-03-10 15:14
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/handicap/init", method = HttpConfig.Method.GET)
public class HandicapGameInitAction extends CommAdminAction {
    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
//        if (!threadJrb.isSuccess()) {
//            return returnJson(threadJrb);
//        }
        HandicapGameUtil.initInfo();
        //发消息到MQ端
        JSONObject data = new JSONObject();
        data.put("command", IbmMethodEnum.ADMIN_INFO.name());
        data.put("requestType", IbmStateEnum.INIT.name());
        String resultStr=RabbitMqTool.sendConfigReceipt(data.toString(), "set");
        if (StringTool.isEmpty(resultStr)) {
            bean.putEnum(IbmCodeEnum.IBM_403_MQ);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return bean;
        }
        if (!Boolean.parseBoolean(resultStr)) {
            bean.putEnum(IbmCodeEnum.IBM_403_MQ);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return bean;
        }
        //发请求到请求端
        ClientModuleDefine define=new ClientModuleDefine();
        String url = define.host() + define.project() + "/ibm/admin/manage/handicap/requestPortInit";
        String html = HttpJsoupTool.doGetJson(60 * 1000, url, ParamTool.paramJson(data));
        JSONObject result = JSON.parseObject(html);
        if (!result.getBoolean("success")) {
            bean.putEnum(IbmCodeEnum.IBM_403_HTTP);
            bean.putSysEnum(IbmCodeEnum.CODE_403);
            return bean;
        }

        bean.success();
        return bean.toJsonString();
    }
}
