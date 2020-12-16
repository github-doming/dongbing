package com.ibm.follow.connector.admin.manage3.sealTime;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_config.entity.IbmConfig;
import com.ibm.follow.servlet.cloud.ibm_config.service.IbmConfigService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;

/**
 * @Description: 封盘时间设置
 * @Author: null
 * @Date: 2020-02-20 16:43
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/sealTime/sealTimeSet",method = HttpConfig.Method.POST)
public class SealTimeSetAction extends CommAdminAction {

    @Override
    public Object run() throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        super.findAdminUser();
        JsonResultBean threadJrb = LogThreadLocal.findLog();
        if (!threadJrb.isSuccess()) {
            return returnJson(threadJrb);
        }
        String key = request.getParameter("key");
        String value = request.getParameter("value");

        IbmConfigService configService=new IbmConfigService();
        if(StringTool.isEmpty(configService.findConfigValue(key))){
            IbmConfig config=new IbmConfig();
            config.setConfigKey(key);
            config.setConfigValue(value);
            config.setCreateTime(new Date());
            config.setCreateTimeLong(System.currentTimeMillis());
            config.setUpdateTime(new Date());
            config.setUpdateTimeLong(System.currentTimeMillis());
            config.setState(IbmStateEnum.OPEN.name());
            configService.save(config);
        }else {
            configService.updateSealTime(key,value);
        }
        bean.success();
        return bean.toJsonString();
    }
}
