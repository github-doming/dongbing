package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.client.ibmc_config.service.IbmcConfigService;
import com.ibm.follow.servlet.client.ibmc_exist_ha.service.IbmcExistHaService;
import com.ibm.follow.servlet.client.ibmc_exist_hm.service.IbmcExistHmService;
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
public class HandicapCapacityDelController implements ClientExecutor {
    protected static final Logger log = LogManager.getLogger(HandicapCapacityDelController.class);

    @Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
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
        IbmcExistHmService existHmService=new IbmcExistHmService();
        List<String> existIds= existHmService.findByHandicapCode(handicapCode);
        if(ContainerTool.notEmpty(existIds)){
            HandicapMigrateController.clearHmInfo(existIds,handicapCode);
        }

        //清除代理信息
        IbmcExistHaService existHaService=new IbmcExistHaService();
        existIds=existHaService.findByHandicapCode(handicapCode);
        if(ContainerTool.notEmpty(existIds)){
            HandicapMigrateController.clearHaInfo(existIds,handicapCode);
        }
        //删除该盘口容量信息
        IbmcConfigService configService=new IbmcConfigService();
        configService.updateByHandicapCode(handicapCode);

        bean.setData(handicapCode.name());
        bean.success();
        return bean;
    }


}
