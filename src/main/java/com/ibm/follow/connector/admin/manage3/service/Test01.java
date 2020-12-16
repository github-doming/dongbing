package com.ibm.follow.connector.admin.manage3.service;

import com.ibm.common.core.CommTest;
import com.ibm.common.tools.GameTool;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds.service.IbmSysBetOddsService;
import com.ibm.follow.servlet.server.core.thread.SettleBetItemThread;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.junit.Test;

import java.util.*;

/**
 * @Description:
 * @Author: wwj
 * @Date: 2020/2/24 21:51
 * @Email: 97085010@qq.com
 * @Version: v1.0
 */
public class Test01  extends CommTest {
    @Test
    public void test99() {
        super.transactionBegin();
        try {
            IbmSysService s = new IbmSysService();

            List<Map<String,Object>> listMap = s.findBetInfo(Arrays.asList("ibm_ha_follow_period","ibm_ha_member_bet_item",
                    "ibm_hm_bet_item","ibm_hm_profit","ibm_hm_profit_item","ibm_hm_profit_period","ibm_hm_profit_period_vr",
                    "ibm_hm_profit_vr"),1l);

            System.out.println(listMap);
        } catch (Exception e) {
            log.error("测试错误", e);
            super.transactionRoll();
        } finally {
            super.transactionEnd();
        }

    }

}
