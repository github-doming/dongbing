package com.ibs.plan.module.client.core.mq.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.client.ibsc_config.entity.IbscConfig;
import com.ibs.plan.module.client.ibsc_config.service.IbscConfigService;
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
public class HandicapCapacitySetController {
    protected static final Logger log = LogManager.getLogger(HandicapCapacitySetController.class);

    public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        //盘口容量设置
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        String handicapCode=msgObj.getString("handicapCode");
        String capacityHandicapMax= msgObj.getString("capacityHandicapMax");


        if(StringTool.isEmpty(handicapCode,capacityHandicapMax)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        IbscConfigService configService=new IbscConfigService();

        //添加或者修改盘口容量信息
        IbscConfig config=configService.findByKey(handicapCode.concat("_CAPACITY_MAX"));
        if(config==null){
            saveInfo(configService,handicapCode.concat("_CAPACITY_MAX"), capacityHandicapMax);
        }else{
            config.setClientConfigValue(capacityHandicapMax);
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
    public static void saveInfo(IbscConfigService configService, String key, String value) throws Exception {
        IbscConfig config = new IbscConfig();
        config.setClientConfigKey(key);
        config.setClientConfigValue(value);
        config.setCreateTime(new Date());
        config.setCreateTimeLong(System.currentTimeMillis());
        config.setUpdateTime(new Date());
        config.setUpdateTimeLong(System.currentTimeMillis());
        config.setState(IbsStateEnum.OPEN.name());
        configService.save(config);
    }
}
