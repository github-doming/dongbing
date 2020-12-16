package com.ibm.common.test.wwj;

import com.common.core.JsonResultBeanPlus;
import com.ibm.common.core.CommTest;
import com.ibm.common.test.wwj.handicap.CrawlerAgent;
import com.ibm.common.test.wwj.handicap.CrawlerMember;
import com.ibm.common.test.wwj.handicap.HandicapException;
import com.ibm.common.test.wwj.handicap.HttpType;
import com.ibm.common.test.wwj.handicap.logger.OptionLogger;
import com.ibm.common.test.wwj.handicap.logger.SaveLogService;
import com.ibm.common.utils.HandicapUtil;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 盘口测试类
 * @Author: Dongming
 * @Date: 2019-11-27 17:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HandicapTest extends CommTest {
	Map<String, CrawlerAgent> agentMap = new HashMap<>();
	Map<String, CrawlerMember> memberMap = new HashMap<>();

	/*
		1.新用户想要验证登录
	 */
	@Test public void test01() throws HandicapException {
		String existId = verifyLogin(HandicapUtil.Code.HQ, "w8.sc3588.co", "hzz828", "aa1237890", "Aa123123.");
		if (StringTool.isEmpty(existId)) {
			return;
		}
		System.out.println("验证登录成功：" + existId);
		List<Map<String, String>> list = memberList(existId);
		System.out.println("会员列表：" + list);
	}
	private List<Map<String, String>> memberList(String existId) throws HandicapException {
		return agentMap.get(existId).memberList(true);
	}

	private String verifyLogin(HandicapUtil.Code handicap, String handicapUrl, String handicapCaptcha, String account,
			String password) throws HandicapException {
		// 创建盘口爬虫
//		CrawlerAgent agent = FactoryDirector.getCrawlerAgent(handicap);
//		CrawlerAgent agent = factory.getCrawlerAgent();
		// 验证登录
//		JsonResultBeanPlus bean = agent.verifyLogin(handicapUrl, handicapCaptcha, account, password);
//		System.out.println("登录结果：" + bean.toJsonString());
//		// 记录该信息
//		if (bean.isSuccess()) {
//			String existId = bean.getData().toString();
//			agentMap.put(existId, agent);
//			return existId;
//		}

		return null;
	}

	/*
		2.登录
	 */
	@Test public void test02() throws HandicapException {
		String existId = RandomTool.getNumLetter32();
		JsonResultBeanPlus bean = login(HandicapUtil.Code.HQ, existId, "w8.sc3588.co", "hzz828", "aa1237890",
				"Aa123123.");
		if (bean.isSuccess()) {
			return;
		}
		System.out.println("登录成功：" + existId);
		Map<String, String> list = userInfo(existId);
		System.out.println("会员信息：" + list);

	}
	private Map<String, String> userInfo(String existId) throws HandicapException {
		return memberMap.get(existId).userInfo(true);
	}

	private JsonResultBeanPlus login(HandicapUtil.Code handicap, String existId, String handicapUrl,
			String handicapCaptcha, String account, String password) throws HandicapException {
		// 创建盘口爬虫
//		CrawlerMember member  = FactoryDirector.getCrawlerMember(handicap);
////		CrawlerMember member = factory.getCrawlerMember();
//
//		//绑定爬虫
//		while (!member.putExistId(existId)) {
//			System.out.println("该爬虫已绑定客户信息，无法绑定其他客户,创建其他爬虫");
//			member = FactoryDirector.getCrawlerMember(handicap);
//		}
//		memberMap.put(existId, member);
//
//		// 登录
//		JsonResultBeanPlus bean = member.login(handicapUrl, handicapCaptcha, account, password);
//		System.out.println("登录结果：" + bean.toJsonString());
		return null;

	}

	@Test public void test() {
		super.transactionBegin();
		try {
			long start, end;
			SaveLogService service = new SaveLogService();
			List<String> existIds = RandomTool.listLetter32(100);
			start = System.currentTimeMillis();

			for (int i = 0; i < 100000; i++) {
				int index = RandomTool.getInt(100);
				OptionLogger optionLogger = new OptionLogger("url", "parameter", "html", existIds.get(index),
						HandicapUtil.codes()[index / 25], HttpType.Normal);
				service.save(optionLogger);
			}
			end = System.currentTimeMillis();
			System.out.println("保存耗时=" + (end - start));
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}

	}
}
