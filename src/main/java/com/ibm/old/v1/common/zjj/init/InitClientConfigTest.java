package com.ibm.old.v1.common.zjj.init;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.client.ibm_client_config.t.entity.IbmClientConfigT;
import com.ibm.old.v1.client.ibm_client_config.t.service.IbmClientConfigTService;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 初始化客户端配置
 * @Author: zjj
 * @Date: 2019-03-15 13:46
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class InitClientConfigTest extends CommTest {
	@Test
	public void demoTest() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			IbmClientConfigTService configTService=new IbmClientConfigTService();

//			createHandicapSeal(configTService,"IDC");
			testSql(configTService);

			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}

	}
	private void testSql(IbmClientConfigTService configTService) throws SQLException {
		Map<Object,Object> map=configTService.findMaxCapacity("WS2");
		System.out.println(map);
	}

	private void createHandicapSeal(IbmClientConfigTService configTService, String handicapCode) throws Exception {
		IbmGameTService gameTService = new IbmGameTService();
		List<Map<String, Object>> gameId = gameTService.listInfo();

		IbmClientConfigT configT;

		for(Map<String ,Object> map:gameId){
			configT=new IbmClientConfigT();
			configT.setClientConfigKey(handicapCode.concat("_").concat(map.get("GAME_CODE_").toString()).concat("_SEAL"));
			configT.setClientConfigValue("100");
			configT.setCreateUser("zjj");
			configT.setCreateTime(new Date());
			configT.setCreateTimeLong(configT.getCreateTime().getTime());
			configT.setUpdateTime(new Date());
			configT.setUpdateTimeLong(configT.getUpdateTime().getTime());
			configT.setState("OPEN");
			configTService.save(configT);
		}


	}
}
