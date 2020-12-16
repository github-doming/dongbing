package com.ibm.old.v1.common.zjj.init;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.cloud.ibm_sys_handicap.t.entity.IbmSysHandicapT;
import com.ibm.old.v1.cloud.ibm_sys_handicap.t.service.IbmSysHandicapTService;
import org.junit.Test;

import java.util.Date;
/**
 * @Description:
 * @Author: zjj
 * @Date: 2019-04-13 15:36
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmSysHandicapInit  extends CommTest {
	@Test
	public void demoTest() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);


			init();

			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}

	}
	private void init() throws Exception {
		IbmSysHandicapTService handicapTService=new IbmSysHandicapTService();
		IbmSysHandicapT handicapT=new IbmSysHandicapT();
		handicapT.setHandicapId("5b0bf47af4e249a2b2fe56ba37f1d0d8");
		handicapT.setHandicapCode("IDC");
		handicapT.setHandicapDetectionTime(60);
		handicapT.setCreateTime(new Date());
		handicapT.setCreateTimeLong(handicapT.getCreateTime().getTime());
		handicapT.setState("OPEN");
		handicapTService.save(handicapT);
	}
}
