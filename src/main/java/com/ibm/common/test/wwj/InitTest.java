package com.ibm.common.test.wwj;
import com.ibm.common.core.CommTest;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.client.ibmc_config.entity.IbmcConfig;
import com.ibm.follow.servlet.client.ibmc_config.service.IbmcConfigService;
import com.ibm.follow.servlet.cloud.ibm_config.entity.IbmConfig;
import com.ibm.follow.servlet.cloud.ibm_config.service.IbmConfigService;
import com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.entity.IbmSysServletIp;
import com.ibm.follow.servlet.cloud.ibm_sys_servlet_ip.service.IbmSysServletIpService;
import org.doming.core.tools.DateTool;
import org.junit.Test;

import java.net.InetAddress;
import java.util.Date;
/**
 * @Description: 初始化测试类
 * @Author: Dongming
 * @Date: 2019-09-04 16:53
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class InitTest extends CommTest {
	@Test public void test() {
		try {
			InetAddress ia = InetAddress.getLocalHost();

			String localname = ia.getHostName();
			String localip = ia.getHostAddress();
			System.out.println("本机名称是：" + localname);
			System.out.println("本机的ip是 ：" + localip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test public void testInitClientIp() {
		super.transactionBegin();
		try {
			Date endTime = DateTool.getDay("20991231");
			Date nowTime = new Date();
			IbmSysServletIp servletIp = new IbmSysServletIp();
			servletIp.setServletIp("127.0.0.1");
			servletIp.setStartTime(nowTime);
			servletIp.setStartTimeLong(System.currentTimeMillis());
			servletIp.setEndTime(endTime);
			servletIp.setEndTimeLong(endTime.getTime());
			servletIp.setCreateUser("Doming");
			servletIp.setCreateTime(nowTime);
			servletIp.setCreateTimeLong(System.currentTimeMillis());
			servletIp.setUpdateTimeLong(System.currentTimeMillis());
			servletIp.setState(IbmStateEnum.OPEN.name());
			new IbmSysServletIpService().save(servletIp);
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
	@Test public void testInitConfig() {
		super.transactionBegin();
		try {
			Date nowTime = new Date();
			IbmConfigService configService = new IbmConfigService();
			String key;
			String value = "60";
			for (HandicapUtil.Code handicap : HandicapUtil.codes()) {
				for (GameUtil.Code gameCode : GameUtil.codes()) {
					key = handicap.name().concat("#").concat(gameCode.name()).concat("#SEAL_TIME");
					createConfig(nowTime, configService, key, value);
				}
			}
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
	private void createConfig(Date nowTime, IbmConfigService configService, String key, String value) throws Exception {
		IbmConfig config = new IbmConfig();
		config.setConfigKey(key);
		config.setConfigValue(value);
		config.setCreateUser("doming");
		config.setCreateTime(nowTime);
		config.setCreateTimeLong(System.currentTimeMillis());
		config.setUpdateTimeLong(System.currentTimeMillis());
		config.setState(IbmStateEnum.OPEN.name());
		configService.save(config);
	}

	@Test public void testInitClientConfig() {
		super.transactionBegin();
		try {
			Date nowTime = new Date();
			IbmcConfigService configService = new IbmcConfigService();
			String key;
			String value = "60";
			for (HandicapUtil.Code handicap : HandicapUtil.codes()) {
				for (GameUtil.Code gameCode : GameUtil.codes()) {
					key = handicap.name().concat("#").concat(gameCode.name()).concat("#SEAL_TIME");
					createClientConfig(nowTime, configService, key, value);
				}
			}
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}

	}
	private void createClientConfig(Date nowTime, IbmcConfigService configService, String key, String value)
			throws Exception {
		IbmcConfig config = new IbmcConfig();
		config.setClientConfigKey(key);
		config.setClientConfigValue(value);
		config.setCreateUser("doming");
		config.setCreateTime(nowTime);
		config.setCreateTimeLong(System.currentTimeMillis());
		config.setUpdateTimeLong(System.currentTimeMillis());
		config.setState(IbmStateEnum.OPEN.name());
		configService.save(config);
	}

	@Test public void createGame() {
		super.transactionBegin();
		try {
			Date nowTime = new Date();





		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}

	}

}
