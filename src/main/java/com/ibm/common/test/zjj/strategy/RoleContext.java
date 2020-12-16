package com.ibm.common.test.zjj.strategy;

import com.ibm.common.test.zjj.factory.NormalRole;
import com.ibm.common.test.zjj.factory.OrderAdminRole;
import com.ibm.common.test.zjj.factory.RoleOperation;
import com.ibm.common.test.zjj.factory.RootAdminRole;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-03-20 13:56
 * @Version: v1.0
 */
public class RoleContext {
    private RoleOperation operation;

    public RoleContext(RoleOperation operation){
        this.operation=operation;
    }
    public String execute(){
        return operation.op();
    }




    public static void main(String[] args) {
        JudgeRole judgeRole=new JudgeRole();
        String result1=judgeRole.judge(new RootAdminRole("ROLE_ROOT_ADMIN"));

        System.out.println(result1);

        String result2=judgeRole.judge(new OrderAdminRole("ROLE_ORDER_ADMIN"));

        System.out.println(result2);

        String result3=judgeRole.judge(new NormalRole("ROLE_NORMAL"));

        System.out.println(result3);
    }
}
