package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.ibmc_config.entity.IbmcConfig;
import com.ibm.follow.servlet.client.ibmc_config.service.IbmcConfigService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.StringTool;

import java.util.Date;

/**
 * @Description: 盘口容量设置
 * @Author: null
 * @Date: 2020-01-16 18:34
 * @Version: v1.0
 */
public class HandicapCapacitySetController implements ClientExecutor {
    protected static final Logger log = LogManager.getLogger(HandicapCapacitySetController.class);

    @Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        //盘口容量设置
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        String handicapCode=msgObj.getString("handicapCode");
        String capacityHandicapMax= msgObj.getString("capacityHandicapMax");
        String capacityHaMax =msgObj.getString("capacityHaMax");
        String capacityHmMax=msgObj.getString("capacityHmMax");

        if(StringTool.isEmpty(handicapCode,capacityHandicapMax,capacityHaMax,capacityHmMax)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        IbmcConfigService configService=new IbmcConfigService();

        //添加或者修改盘口容量信息
        IbmcConfig config=configService.findByKey(handicapCode.concat("_CAPACITY_MAX"));
        if(config==null){
            saveInfo(configService,handicapCode.concat("_CAPACITY_MAX"), capacityHandicapMax);
        }else{
            config.setClientConfigValue(capacityHandicapMax);
            configService.update(config);
        }
        //添加或者修改盘口代理容量信息
        config=configService.findByKey(handicapCode.concat("_AGENT_CAPACITY_MAX"));
        if(config==null){
            saveInfo(configService,handicapCode.concat("_AGENT_CAPACITY_MAX"), capacityHaMax);
        }else{
            config.setClientConfigValue(capacityHaMax);
            configService.update(config);
        }
        //添加或者修改盘口会员容量信息
        config= configService.findByKey(handicapCode.concat("_MEMBER_CAPACITY_MAX"));
        if(config==null){
            saveInfo(configService,handicapCode.concat("_MEMBER_CAPACITY_MAX"), capacityHmMax);
        }else{
            config.setClientConfigValue(capacityHmMax);
            configService.update(config);
        }
        bean.success();
        return bean;
    }

    /**
     * 添加设置信息
     * @param configService     服务类
     * @param key               设置key
     * @param value             设置value
     */
    public static void saveInfo(IbmcConfigService configService, String key, String value) throws Exception {
        IbmcConfig config = new IbmcConfig();
        config.setClientConfigKey(key);
        config.setClientConfigValue(value);
        config.setCreateTime(new Date());
        config.setCreateTimeLong(System.currentTimeMillis());
        config.setUpdateTime(new Date());
        config.setUpdateTimeLong(System.currentTimeMillis());
        config.setState(IbmStateEnum.OPEN.name());
        configService.save(config);
    }
}
