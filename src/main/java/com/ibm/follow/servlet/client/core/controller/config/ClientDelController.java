package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.tools.QuartzTool;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Description: 客户端删除
 * @Author: null
 * @Date: 2020-04-13 11:02
 * @Version: v1.0
 */
public class ClientDelController implements ClientExecutor {
    protected static final Logger log = LogManager.getLogger(ClientDelController.class);

    @Override public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {
        JsonResultBeanPlus bean = new JsonResultBeanPlus();

        //清除会员信息
        ClientReplaceController.clearMemberInfo();

        //清除代理信息
        ClientReplaceController.clearAgentInfo();

        QuartzTool.destroyClient();

        bean.success();
        return bean;
    }
}
