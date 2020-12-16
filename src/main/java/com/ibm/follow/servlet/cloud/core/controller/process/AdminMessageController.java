package com.ibm.follow.servlet.cloud.core.controller.process;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapGameUtil;
import com.ibm.follow.servlet.cloud.core.controller.CloudExecutor;

/**
 * @Description: 后台消息控制器
 * @Author: null
 * @Date: 2020-03-10 15:51
 * @Version: v1.0
 */
public class AdminMessageController implements CloudExecutor {
    @Override
    public JsonResultBeanPlus execute(JSONObject msgObj, IbmStateEnum requestType) throws Exception {
        JsonResultBeanPlus bean =new JsonResultBeanPlus();
        switch (requestType) {
            case INIT:
                HandicapGameUtil.initInfo();
                break;
            default:
                break;
        }
        bean.success();
        return bean;
    }
}
