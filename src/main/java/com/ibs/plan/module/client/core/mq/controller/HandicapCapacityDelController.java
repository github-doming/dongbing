package com.ibs.plan.module.client.core.mq.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.tools.QuartzTool;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.ibsc_config.service.IbscConfigService;
import com.ibs.plan.module.client.ibsc_exist_hm.service.IbscExistHmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;

import java.util.List;

/**
 * @Description: 盘口容量删除
 * @Author: null
 * @Date: 2020-04-11 18:09
 * @Version: v1.0
 */
public class HandicapCapacityDelController {
    protected static final Logger log = LogManager.getLogger(HandicapCapacityDelController.class);

     public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        //盘口容量设置
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        String code=msgObj.getString("handicapCode");
        if (StringTool.isEmpty(code)) {
            bean.putEnum(CodeEnum.IBS_401_DATA);
            bean.putSysEnum(CodeEnum.CODE_401);
            return bean;
        }
        HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(code);
        //清除会员信息
        IbscExistHmService existHmService=new IbscExistHmService();
        List<String> existIds= existHmService.findByHandicapCode(handicapCode);
        if(ContainerTool.notEmpty(existIds)){
            for(String existHmId:existIds){
                //清除内存
                CustomerCache.clearUp(existHmId);
                //移除定时器，新加游戏需处理
                QuartzTool.removeCheckJob(existHmId, handicapCode.name());
                //清除客户盘口存在信息，新加盘口需处理
                handicapCode.getMemberFactory().removeCrawler(existHmId);
                //定时任务处理
                existHmService.removeQuertzInfo(existHmId);
            }
            //清除客户客户机上的信息
            existHmService.removeExistHmInfo(existIds);
        }
        //删除该盘口容量信息
         new IbscConfigService().updateByHandicapCode(handicapCode);

        bean.setData(handicapCode.name());
        bean.success();
        return bean;
    }


}
