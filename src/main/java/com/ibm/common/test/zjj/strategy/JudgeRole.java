package com.ibm.common.test.zjj.strategy;

import com.ibm.common.test.zjj.factory.RoleOperation;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-03-20 13:58
 * @Version: v1.0
 */
public class JudgeRole {

    public String judge(RoleOperation operation){
        RoleContext roleContext=new RoleContext(operation);
        return roleContext.execute();
    }
}
