package com.ibm.old.v1.common.cwy.TestDate;

import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.ibm_hm_game_set.t.service.IbmHmGameSetTService;
import org.junit.Test;

import java.util.Map;

/**
 * @Description: 测试方案详情修改记录日志
 * @Author: cwy
 * @Date: 2019-08-01 14:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class TestDate extends CommTest {

    @Test
    public void demo() {
        try {
            jdbcTool = this.findJdbcToolLocal();
            this.transactionStart(jdbcTool);
            test01();
            this.transactionEnd(jdbcTool);
        } catch (Exception e) {
            e.printStackTrace();
            this.transactionRoll(jdbcTool);
        } finally {
            this.transactionClose(jdbcTool);
        }

    }

    private void test01() throws Exception {
        String hmGameSetId="003a39479aec41dc8c92175a90a7eeda";
        IbmHmGameSetTService ibmHmGameSetTService=new IbmHmGameSetTService();
        Map<String, Object> showData = ibmHmGameSetTService.findShow(hmGameSetId);
        System.out.println(showData);
    }




}
