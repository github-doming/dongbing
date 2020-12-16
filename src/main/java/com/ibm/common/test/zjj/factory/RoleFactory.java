package com.ibm.common.test.zjj.factory;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 工厂类
 * @Author: null
 * @Date: 2020-03-20 13:39
 * @Version: v1.0
 */
public class RoleFactory {
    private static Map<String,RoleOperation> roleOperationMap=new HashMap<>();

    // 在静态块中先把初始化工作全部做完
    static {
        roleOperationMap.put("ROLE_ROOT_ADMIN",new RootAdminRole("ROLE_ROOT_ADMIN"));
        roleOperationMap.put("ROLE_ORDER_ADMIN",new RootAdminRole("ROLE_ORDER_ADMIN"));
        roleOperationMap.put("ROLE_NORMAL",new RootAdminRole("ROLE_NORMAL"));
    }

    public static RoleOperation getOp(String roleName){
        return roleOperationMap.get(roleName);
    }


    public String judgeRole(String roleName){
        return RoleFactory.getOp(roleName).op();
    }


    @Test
    public void test02() throws Exception {
        System.out.println(judgeRole("ROLE_ROOT_ADMIN"));
    }

}

