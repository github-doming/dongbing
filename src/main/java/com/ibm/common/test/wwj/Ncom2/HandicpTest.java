package com.ibm.common.test.wwj.Ncom2;

import com.alibaba.fastjson.JSONArray;
import com.common.core.JsonResultBeanPlus;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.http.utils.member2.Ncom2MemberUtil2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2019/12/27 16:32
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class HandicpTest {

    public static void main(String[] args) {
        String account = "a122701";
        String password = "Aa1231231";
        String handicapUrl = "http://xxt6699.com";
        String handicapCaptcha = "190018";
        Ncom2MemberUtil2 util = Ncom2MemberUtil2.findInstance();
        JsonResultBeanPlus bean = util.valiLogin(handicapUrl,handicapCaptcha,account,password);
        String exID = (String) bean.getData();
        System.out.println("exID=="+exID);
        bean = util.userInfo(exID,true);
        Map<String, String> userInfo = (Map<String, String>) bean.getData();
        System.out.println("userInfo=="+userInfo);
        bean = util.getQuotaList(exID, GameUtil.Code.PK10);
        JSONArray gameLimit = (JSONArray) bean.getData();
        System.out.println("gameLimit=="+gameLimit);

        try {
            util.getOddsInfo(exID,GameUtil.Code.PK10, "ballNO");
            List<String> betItems = new ArrayList<>();
            betItems.add("第二名|2|2000");
            betItems.add("第二名|5|3000");
            util.betting(exID,GameUtil.Code.PK10,betItems,"ballNO");

        } catch (Exception e) {
            e.printStackTrace();
        }


    }




}
