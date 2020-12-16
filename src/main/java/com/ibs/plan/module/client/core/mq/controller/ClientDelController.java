package com.ibs.plan.module.client.core.mq.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibs.common.utils.HandicapUtil;
import com.ibs.plan.common.tools.QuartzTool;
import com.ibs.plan.module.client.core.CustomerCache;
import com.ibs.plan.module.client.ibsc_exist_hm.service.IbscExistHmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.ContainerTool;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 客户端删除
 * @Author: null
 * @Date: 2020-04-13 11:02
 * @Version: v1.0
 */
public class ClientDelController {
    protected static final Logger log = LogManager.getLogger(ClientDelController.class);

    public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        IbscExistHmService existHmService=new IbscExistHmService();
        List<Map<String,Object>> existHmInfos=existHmService.findAll();
        if(ContainerTool.isEmpty(existHmInfos)){
            List<String> existIds=new ArrayList<>();
            for(Map<String,Object> map:existHmInfos){
                String existHmId =map.get("existHmId").toString();
                existIds.add(existHmId);
                HandicapUtil.Code handicapCode = HandicapUtil.Code.valueOf(map.get("handicapCode").toString());
                //清除内存
                CustomerCache.clearUp(existHmId);
                //移除定时器，新加游戏需处理
                QuartzTool.removeCheckJob(existHmId,handicapCode.name());
                //清除客户盘口存在信息，新加盘口需处理
                handicapCode.getMemberFactory().removeCrawler(existHmId);
            }
            //清除客户客户机上的信息
            existHmService.removeExistHmInfo(existIds);
        }
        QuartzTool.shutdownClient();

        bean.success();
        return bean;
    }
}
