package com.ibm.common.test.zjj.factory;

/**
 * @Description:
 * @Author: null
 * @Date: 2020-03-20 13:35
 * @Version: v1.0
 */
public class RootAdminRole implements RoleOperation {
    private String roleName;


    public RootAdminRole(String roleName){
        this.roleName=roleName;
    }

    @Override
    public String op(){
        return roleName+"has AAA permission";
    }
}
