package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.core.servlet.boot.IbmModuleListener;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.tools.QuartzTool;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import org.doming.core.tools.StringTool;

import static com.ibm.common.core.configs.QuartzConfig.GROUP_HEARTBEAT;
import static com.ibm.common.core.configs.QuartzConfig.NAME_HEARTBEAT;

/**
 * @Description: 停止心跳控制器
 * @Author: null
 * @Date: 2020-03-03 13:33
 * @Version: v1.0
 */
public class StopHeartbeatController implements ClientExecutor {

    @Override
    public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();
        String methodType = msgObj.getString("methodType");
        if (StringTool.isEmpty(methodType)) {
            bean.putEnum(CodeEnum.IBS_404_DATA);
            bean.putSysEnum(CodeEnum.CODE_404);
            return bean;
        }
        IbmMethodEnum method = IbmMethodEnum.valueOf(methodType);

        String name = String.format(NAME_HEARTBEAT, IbmModuleListener.servletCode()).concat("Job");
        String groupName = String.format(GROUP_HEARTBEAT, IbmModuleListener.servletCode());
        switch (method) {
            case OPEN:
                QuartzTool.resumeJob(name, groupName);
                break;
            case CLOSE:
                //暂停心跳job
                QuartzTool.pauseJob(name, groupName);
                break;
            default:
                bean.putEnum(CodeEnum.IBS_404_DATA);
                bean.putSysEnum(CodeEnum.CODE_404);
                return bean;
        }
        bean.success();
        return bean;
    }

}
