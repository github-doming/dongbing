package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.ibmc_config.entity.IbmcConfig;
import com.ibm.follow.servlet.client.ibmc_config.service.IbmcConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.StringTool;

/**
 * @Description: 客户端容量设置
 * @Author: null
 * @Date: 2020-01-16 18:32
 * @Version: v1.0
 */
public class CapacitySetController implements ClientExecutor {
    protected static final Logger log = LogManager.getLogger(CapacitySetController.class);

    @Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        //客户端容量设置
        String clientCapacityMax=msgObj.getString("clientCapacityMax");
        if(StringTool.isEmpty(clientCapacityMax)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        //不存在的key重新添加一条
        IbmcConfigService configService=new IbmcConfigService();
        IbmcConfig config=configService.findByKey("CAPACITY_MAX");
        if(config==null){
            HandicapCapacitySetController.saveInfo(configService,"CAPACITY_MAX",clientCapacityMax);
        }else {
            config.setClientConfigValue(clientCapacityMax);
            configService.update(config);
        }
        bean.success();
        return bean;
    }


}
