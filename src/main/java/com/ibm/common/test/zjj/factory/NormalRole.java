package com.ibm.common.test.zjj.factory;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-03-20 13:36
 * @Version: v1.0
 */
public class NormalRole implements RoleOperation {
    private String roleName;


    public NormalRole(String roleName){
        this.roleName=roleName;
    }

    @Override
    public String op(){
        return roleName+"has CCC permission";
    }
}
