package com.ibs.plan.module.client.core.mq.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.client.ibsc_config.entity.IbscConfig;
import com.ibs.plan.module.client.ibsc_config.service.IbscConfigService;
import org.doming.core.tools.ContainerTool;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Description: 封盘时间设置控制器
 * @Author: null
 * @Date: 2020-01-19 10:57
 * @Version: v1.0
 */
public class SealTimeSetController{

    public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        JSONObject sealTime=msgObj.getJSONObject("sealTime");
        if(ContainerTool.isEmpty(sealTime)){
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        IbscConfigService configService=new IbscConfigService();
        Map<String, String> sealTimeMap=configService.findAllSealTime();

        //本机存在-修改信息
        for(Object key:sealTimeMap.keySet()){
            if(sealTime.containsKey(key)){
                configService.updateByKeys(sealTime,sealTimeMap);
                break;
            }
        }

        //本机不存在-添加信息
        Set<Object> saveSealTimeKey = new HashSet<>(ContainerTool.getDifferent(sealTime.keySet(), sealTimeMap.keySet()));
        if(ContainerTool.notEmpty(saveSealTimeKey)){
            IbscConfig config=new IbscConfig();
            config.setCreateUser("doming");
            config.setCreateTime(new Date());
            config.setCreateTimeLong(System.currentTimeMillis());
            config.setUpdateTime(new Date());
            config.setUpdateTimeLong(System.currentTimeMillis());
            config.setState(IbsStateEnum.OPEN.name());
            for(Object key:saveSealTimeKey){
                config.setClientConfigKey(key);
                config.setClientConfigValue(sealTime.get(key));
                configService.save(config);
            }
        }
        bean.success();
        return bean;
    }

}
