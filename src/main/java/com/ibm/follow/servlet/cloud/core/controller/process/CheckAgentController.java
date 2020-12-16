package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_client_ha.service.IbmClientHaService;
import com.ibm.follow.servlet.cloud.ibm_ha_info.service.IbmHaInfoService;
import com.ibm.follow.servlet.cloud.ibm_ha_set.service.IbmHaSetService;
import org.doming.core.tools.StringTool;

/**
 * @Description: 代理检验控制器
 * @Author: null
 * @Date: 2020-04-10 19:18
 * @Version: v1.0
 */
public class CheckAgentController {

    public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        IbmClientHaService clientHaService=new IbmClientHaService();
        String handicapAgentId=clientHaService.findHaId(msgObj.getString("EXIST_HA_ID_"));
        if (StringTool.isEmpty(handicapAgentId, msgObj.getString("requestType"))) {
            bean.putEnum(CodeEnum.IBS_401_DATA);
            bean.putSysEnum(CodeEnum.CODE_401);
            return bean;
        }
        IbmStateEnum requestType = IbmStateEnum.valueOf(msgObj.getString("requestType"));
        switch (requestType) {
            case SUCCESS:
                new IbmHaInfoService().updateMemberList(handicapAgentId,msgObj.get("memberList"));
                new IbmHaSetService().updateMemberList(handicapAgentId,msgObj.get("memberList"));
                bean.success();
                break;
            case FAIL:
                //TODO 保存错误信息
                break;
            default:
                bean.putEnum(CodeEnum.IBS_404_METHOD);
                bean.putSysEnum(CodeEnum.CODE_404);
                break;
        }
        return bean;

    }
}
