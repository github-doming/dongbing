package com.ibm.old.v1.common.doming.init;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.client.ibm_client_config.t.entity.IbmClientConfigT;
import com.ibm.old.v1.client.ibm_client_config.t.service.IbmClientConfigTService;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.junit.Test;

import java.util.Date;
/**
 * @Description: 初始化配置类
 * @Author: Dongming
 * @Date: 2018-12-04 13:57
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class InitClientConfigTest extends CommTest {

	@Test public void createConfig() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
			IbmClientConfigTService configTService = new IbmClientConfigTService();
			createCapacityMax(configTService);
			createHandicapMax(configTService, "WS2");
			createHandicapMax(configTService, "WS");
			createHandicapMax(configTService, "AS", 5);

			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}
		System.out.println("end");
	}

	/**
	 * 设置容器最大容量
	 *
	 * @param configTService 客户端配置丰富
	 * @throws Exception 保存失败
	 */
	private void createCapacityMax(IbmClientConfigTService configTService) throws Exception {
		IbmClientConfigT configT = new IbmClientConfigT();
		configT.setClientConfigKey("CAPACITY_MAX");
		configT.setClientConfigValue("10");
		configT.setCreateUser("doming");
		configT.setCreateTime(new Date());
		configT.setCreateTimeLong(configT.getCreateTime().getTime());
		configT.setUpdateTime(new Date());
		configT.setUpdateTimeLong(configT.getUpdateTime().getTime());
		configT.setState(IbmStateEnum.OPEN.name());
		configTService.save(configT);
	}
	/**
	 * 设置盘口最大容量
	 * 默认为3
	 *
	 * @param configTService 客户端配置丰富
	 * @param handicapCode   盘口code
	 * @throws Exception 保存失败
	 */
	private void createHandicapMax(IbmClientConfigTService configTService, String handicapCode) throws Exception {
		createHandicapMax(configTService, handicapCode, 3);
	}

	/**
	 * 设置盘口最大容量
	 *
	 * @param configTService 客户端配置丰富
	 * @param handicapCode   盘口code
	 * @param capacity       容量
	 * @throws Exception 保存失败
	 */
	private void createHandicapMax(IbmClientConfigTService configTService, String handicapCode, int capacity)
			throws Exception {
		IbmClientConfigT configT = new IbmClientConfigT();
		configT.setClientConfigKey(handicapCode + "_CAPACITY_MAX");
		configT.setClientConfigValue(String.valueOf(capacity));
		configT.setCreateUser("doming");
		configT.setCreateTime(new Date());
		configT.setCreateTimeLong(configT.getCreateTime().getTime());
		configT.setUpdateTime(new Date());
		configT.setUpdateTimeLong(configT.getUpdateTime().getTime());
		configT.setState(IbmStateEnum.OPEN.name());
		configTService.save(configT);
	}
}
