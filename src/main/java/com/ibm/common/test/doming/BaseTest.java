package com.ibm.common.test.doming;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ibm.common.core.CommTest;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.tools.EncryptTool;
import com.ibm.common.tools.GameTool;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.ThreadExecuteUtil;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.common.utils.game.tools.GameMergeTool;
import com.ibm.connector.service.authority.AuthorityService;
import com.ibm.connector.service.authority.Menu;
import com.ibm.follow.servlet.cloud.ibm_event_login_vali.entity.IbmEventLoginVali;
import com.ibm.follow.servlet.cloud.ibm_event_login_vali.service.IbmEventLoginValiService;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds.service.IbmSysBetOddsService;
import com.ibm.follow.servlet.server.core.thread.SettleBetItemThread;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:
 * @Author: Dongming
 * @Date: 2019-09-06 13:47
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BaseTest extends CommTest {

	@Test public void test01() {
		String[] oddNumbers = {"19647685", "19647684", "19647683", "19647682", "19647681", "19647680", "19647679",
				"19647678", "19647677", "19647676"};
		String subOddNumber = "19647679";
		String maxOddNumber = "29647685";
		int compare = maxOddNumber.compareTo(subOddNumber);

		for (String oddNumber : oddNumbers) {
			int tmpCompare = oddNumber.compareTo(subOddNumber);
			if (tmpCompare <= 0) {
				break;
			} else {
				//19647677-
				if (compare < tmpCompare) {
					compare = tmpCompare;
					maxOddNumber = oddNumber;
				}
			}
		}

		System.out.println(maxOddNumber);

	}

	@Test public void test02() {
		Map<String, Object> map = new HashMap<>();
		map.put("1", 1);
		map.put("2", "2");
		map.put("3", null);
		map.put("4", 'b');
		System.out.println(
				JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue));
	}

	@Test public void test03() throws Exception {

		RabbitMqTool.setMqXmlPath(IbmMainConfig.MQ_XML);

		System.out.println(
				RabbitMqTool.sendAgentInfo("message.toJSONString()", "BCAA7AF324D74065B71AAD82F160183F", "manage"));
	}

	@Test public void test04() throws Exception {
		RabbitMqTool.setMqXmlPath(IbmMainConfig.MQ_XML);
		String result = RabbitMqUtil.findInstance()
				.receiveExchangeById("client_agent", "BCAA7AF324D74065B71AAD82F160183F");

		System.out.println(result);
	}

	@Test public void test99() {
		super.transactionBegin();
		try {
			GameUtil.Code game = GameUtil.Code.PK10;
			String gameId = GameUtil.id(game);
			List<String> handicapIds = new IbmHandicapService().listOpenId();
			if (ContainerTool.isEmpty(handicapIds)) {
				log.info("游戏【" + game.getName() + "】，已开启的盘口列表为空");
				return;
			}

			//获取开奖结果
			String sql = GameTool.findDrawInfoSql(game);
			if (StringTool.isEmpty(sql)) {
				log.error("游戏【" + game.getName() + "】，查找到的开奖sql语句为空");
				return;
			}
			//获取当前期的期数
			Object period = "739972";
			IbmGameService gameService = new IbmGameService();
			Map<String, Object> drawInfo = gameService.findDrawInfo(sql, period, "");
			//60 * 2s = 2min
			if (ContainerTool.isEmpty(drawInfo)) {
				log.error("游戏【" + game.getName() + "】，查找到的开奖信息为空，开奖sql语句为：" + sql + "，期数为：" + period);
				return;
			}
			String drawNumber = drawInfo.get("DRAW_NUMBER_").toString();
			Collection<Object> drawItems = (Collection<Object>) drawInfo.get("DRAW_ITEMS_");

			Map<String, Integer> oddsMap = new IbmSysBetOddsService().mapOddsByGame(gameId);
			String drawItem = StringUtils.join(drawItems, ",");

			String handicapId = "5a72fcadbf1948dfa7efa28aeac925e7";
			new SettleBetItemThread(handicapId, period, drawNumber, drawItem, oddsMap, game).execute(null);
		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}

	}

	@Test public void test05() {
		int[][] arr = {{1, 6}, {2, 7}, {3, 8}, {4, 9}, {5, 10}};
		System.out.println(arr.length);
		System.out.println(arr[0].length);
		for (int[] ints : arr) {
			for (int num : ints) {
				System.out.print(num + "\t");
			}
			System.out.println();
		}
		System.out.println(Arrays.deepToString(arr));
	}

	@Test public void test06() {
		int year = 20;
		double start = 8522;
		double end = 17844;

		System.out.println((Math.pow(end / start, (1D / year)) - 1));
	}

	@Test public void test07() {
		int year = 20;
		double start = 764.603;
		double end = 10801;

		System.out.println((Math.pow(end / start, (1D / year)) - 1));
	}

	@Test public void test08() {
		double A = 17844;
		double C = 10801;
		double a = 0.04;
		double c = 0.06;

		System.out.println(Math.log(A / C) / Math.log((1 + c) / (1 + a)));
	}

	@Test public void test09() {
		String html = "{d:[{\\Rows\\:[{\\rowid\\:1,\\idno\\:912284413,\\gameno\\:21,\\roundno\\:\\20191209033\\,\\wagertime\\:\\15:45:46\\,\\wagerroundno\\:\\A\\,\\wagertypename\\:\\第十名\\,\\wagerobject\\:\\09\\,\\wagerodds\\:\\9.938\\,\\stake\\:160.00,\\winnings\\:1430.08,\\hasdetail\\:0,\\commissionrate\\:0.60},{\\rowid\\:2,\\idno\\:912284412,\\gameno\\:21,\\roundno\\:\\20191209033\\,\\wagertime\\:\\15:45:46\\,\\wagerroundno\\:\\A\\,\\wagertypename\\:\\第十名\\,\\wagerobject\\:\\08\\,\\wagerodds\\:\\9.938\\,\\stake\\:160.00,\\winnings\\:1430.08,\\hasdetail\\:0,\\commissionrate\\:0.60},{\\rowid\\:3,\\idno\\:912284411,\\gameno\\:21,\\roundno\\:\\20191209033\\,\\wagertime\\:\\15:45:46\\,\\wagerroundno\\:\\A\\,\\wagertypename\\:\\第十名\\,\\wagerobject\\:\\07\\,\\wagerodds\\:\\9.938\\,\\stake\\:160.00,\\winnings\\:1430.08,\\hasdetail\\:0,\\commissionrate\\:0.60},{\\rowid\\:4,\\idno\\:912284410,\\gameno\\:21,\\roundno\\:\\20191209033\\,\\wagertime\\:\\15:45:46\\,\\wagerroundno\\:\\A\\,\\wagertypename\\:\\第十名\\,\\wagerobject\\:\\04\\,\\wagerodds\\:\\9.938\\,\\stake\\:160.00,\\winnings\\:1430.08,\\hasdetail\\:0,\\commissionrate\\:0.60},{\\rowid\\:5,\\idno\\:912284409,\\gameno\\:21,\\roundno\\:\\20191209033\\,\\wagertime\\:\\15:45:46\\,\\wagerroundno\\:\\A\\,\\wagertypename\\:\\第十名\\,\\wagerobject\\:\\03\\,\\wagerodds\\:\\9.938\\,\\stake\\:160.00,\\winnings\\:1430.08,\\hasdetail\\:0,\\commissionrate\\:0.60},{\\rowid\\:6,\\idno\\:912284408,\\gameno\\:21,\\roundno\\:\\20191209033\\,\\wagertime\\:\\15:45:46\\,\\wagerroundno\\:\\A\\,\\wagertypename\\:\\第十名\\,\\wagerobject\\:\\02\\,\\wagerodds\\:\\9.938\\,\\stake\\:160.00,\\winnings\\:1430.08,\\hasdetail\\:0,\\commissionrate\\:0.60},{\\rowid\\:7,\\idno\\:912284407,\\gameno\\:21,\\roundno\\:\\20191209033\\,\\wagertime\\:\\15:45:46\\,\\wagerroundno\\:\\A\\,\\wagertypename\\:\\第十名\\,\\wagerobject\\:\\01\\,\\wagerodds\\:\\9.938\\,\\stake\\:160.00,\\winnings\\:1430.08,\\hasdetail\\:0,\\commissionrate\\:0.60},{\\rowid\\:8,\\idno\\:912284406,\\gameno\\:21,\\roundno\\:\\20191209033\\,\\wagertime\\:\\15:45:46\\,\\wagerroundno\\:\\A\\,\\wagertypename\\:\\第九名\\,\\wagerobject\\:\\09\\,\\wagerodds\\:\\9.938\\,\\stake\\:640.00,\\winnings\\:5720.32,\\hasdetail\\:0,\\commissionrate\\:0.60},{\\rowid\\:9,\\idno\\:912284405,\\gameno\\:21,\\roundno\\:\\20191209033\\,\\wagertime\\:\\15:45:46\\,\\wagerroundno\\:\\A\\,\\wagertypename\\:\\第九名\\,\\wagerobject\\:\\08\\,\\wagerodds\\:\\9.938\\,\\stake\\:640.00,\\winnings\\:5720.32,\\hasdetail\\:0,\\commissionrate\\:0.60},{\\rowid\\:10,\\idno\\:912284404,\\gameno\\:21,\\roundno\\:\\20191209033\\,\\wagertime\\:\\15:45:46\\,\\wagerroundno\\:\\A\\,\\wagertypename\\:\\第九名\\,\\wagerobject\\:\\07\\,\\wagerodds\\:\\9.938\\,\\stake\\:640.00,\\winnings\\:5720.32,\\hasdetail\\:0,\\commissionrate\\:0.60}]},{\\Rows\\:[{\\reccount\\:140,\\stake\\:30590.00,\\winnings\\:273413.42}]}]}";
		html = unicodeToString(html);

		html = html.replace("\\", "\"");
		System.out.println(html);

	}

	/**
	 * unicode转字符串
	 *
	 * @param string 字符串
	 * @return
	 */
	public static String unicodeToString(String string) {
		Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Matcher matcher = pattern.matcher(string);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			string = string.replace(matcher.group(1), ch + "");
		}
		return string;
	}

	@Test public void test10() {
		super.transactionBegin();
		try {
			ThreadPoolExecutor executor = ThreadExecuteUtil.findInstance().getMqExecutor();
			IbmEventLoginValiService eventLoginValiService = new IbmEventLoginValiService();
			IbmEventLoginVali eventLoginVali;
			long startTime, endTime, startTime1, endTime1, size = 1;
			Date nowTime = new Date();
			//写入验证登录
			JSONObject content = new JSONObject();
			content.put("HANDICAP_CODE_", "handicapCode");
			content.put("HANDICAP_AGENT_ID_", "handicapAgentId");
			content.put("AGENT_ACCOUNT_", "agentAccount");
			content.put("AGENT_PASSWORD_", "agentPassword");
			content.remove("ROW_NUM");

			startTime = System.currentTimeMillis();
			for (int i = 0; i < size; i++) {
				eventLoginVali = new IbmEventLoginVali();
				eventLoginVali.setCustomerType(IbmTypeEnum.AGENT.name());
				eventLoginVali.setEventContent(content);
				eventLoginVali.setEventState(IbmStateEnum.BEGIN.name());
				eventLoginVali.setExecNumber(0);
				eventLoginVali.setCreateTime(nowTime);
				eventLoginVali.setCreateTimeLong(System.currentTimeMillis());
				eventLoginVali.setUpdateTimeLong(System.currentTimeMillis());
				eventLoginVali.setState(IbmStateEnum.OPEN.name());
				eventLoginVali.setDesc(this.getClass().getName().concat("，验证登录"));
				executor.execute(() -> {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				});
			}
			endTime = System.currentTimeMillis();
			System.out.println("开启线程执行完毕");
			startTime1 = System.currentTimeMillis();
			for (int i = 0; i < size; i++) {
				eventLoginVali = new IbmEventLoginVali();
				eventLoginVali.setCustomerType(IbmTypeEnum.AGENT.name());
				eventLoginVali.setEventContent(content);
				eventLoginVali.setEventState(IbmStateEnum.BEGIN.name());
				eventLoginVali.setExecNumber(0);
				eventLoginVali.setCreateTime(nowTime);
				eventLoginVali.setCreateTimeLong(System.currentTimeMillis());
				eventLoginVali.setUpdateTimeLong(System.currentTimeMillis());
				eventLoginVali.setState(IbmStateEnum.OPEN.name());
				eventLoginVali.setDesc(this.getClass().getName().concat("，验证登录"));
				eventLoginValiService.save(eventLoginVali);

			}
			System.out.println("存储数据库执行完毕");

			System.out.println("开启线程耗时：" + (endTime - startTime));

			endTime1 = System.currentTimeMillis();
			System.out.println("存储数据库耗时：" + (endTime1 - startTime1));

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}

	@Test public void test11() {
		String betItems = "第一名|1|35000#第一名|3|90000#第一名|4|20000#第一名|7|60000#第二名|1|35000#第二名|3|90000#第二名|4|20000#第二名|7|60000#第三名|1|35000#第三名|3|90000#第三名|4|20000#第三名|7|60000#第四名|1|35000#第四名|3|90000#第四名|4|20000#第四名|7|60000#第五名|1|35000#第五名|3|90000#第五名|4|20000#第五名|7|60000#第六名|5|115000#第六名|6|105000#第六名|10|25000#第七名|5|115000#第七名|6|105000#第七名|10|25000#第八名|5|115000#第八名|6|105000#第八名|10|25000#第九名|5|115000#第九名|6|105000#第九名|10|25000#第十名|5|115000#第十名|6|105000#第十名|10|25000#" +
				"第一名|1|15000#第一名|3|95000#第二名|1|15000#第二名|3|95000#第三名|1|15000#第三名|3|95000#第四名|1|15000#第四名|3|95000#第五名|1|15000#第五名|3|95000#第六名|2|15000#第六名|7|10000#第六名|9|15000#第六名|10|90000#第七名|2|15000#第七名|7|10000#第七名|9|15000#第七名|10|90000#第八名|2|15000#第八名|7|10000#第八名|9|15000#第八名|10|90000#第九名|2|15000#第九名|7|10000#第九名|9|15000#第九名|10|90000#第十名|2|15000#第十名|7|10000#第十名|9|15000#第十名|10|90000#第一名|小|50000#第二名|小|50000#第三名|小|50000#第四名|小|50000#第五名|小|50000#" +
				"第一名|1|115000#第一名|2|105000#第一名|3|105000#第一名|4|115000#第一名|5|15000#第一名|6|10000#第一名|7|115000#第一名|8|115000#第一名|9|80000#第二名|1|115000#第二名|2|105000#第二名|3|105000#第二名|4|115000#第二名|5|15000#第二名|6|10000#第二名|7|115000#第二名|8|115000#第二名|9|80000#第三名|1|115000#第三名|2|105000#第三名|3|105000#第三名|4|115000#第三名|5|15000#第三名|6|10000#第三名|7|115000#第三名|8|115000#第三名|9|80000#第四名|1|115000#第四名|2|105000#第四名|3|105000#第四名|4|115000#第四名|5|15000#第四名|6|10000#第四名|7|115000#第四名|8|115000#第四名|9|80000#第五名|1|115000#第五名|2|105000#第五名|3|105000#第五名|4|115000#第五名|5|15000#第五名|6|10000#第五名|7|115000#第五名|8|115000#第五名|9|80000#第六名|1|50000#第六名|2|170000#第六名|3|170000#第六名|4|155000#第六名|5|170000#第六名|6|170000#第六名|8|130000#第六名|9|170000#第六名|10|170000#第七名|1|50000#第七名|2|170000#第七名|3|170000#第七名|4|155000#第七名|5|170000#第七名|6|170000#第七名|8|130000#第七名|9|170000#第七名|10|170000#第八名|1|50000#第八名|2|170000#第八名|3|170000#第八名|4|155000#第八名|5|170000#第八名|6|170000#第八名|8|130000#第八名|9|170000#第八名|10|170000#第九名|1|50000#第九名|2|170000#第九名|3|170000#第九名|4|155000#第九名|5|170000#第九名|6|170000#第九名|8|130000#第九名|9|170000#第九名|10|170000#第十名|1|50000#第十名|2|170000#第十名|3|170000#第十名|4|155000#第十名|5|170000#第十名|6|170000#第十名|8|130000#第十名|9|170000#第十名|10|170000#" +
				"第一名|1|35000#第一名|4|25000#第一名|6|45000#第一名|7|10000#第二名|1|35000#第二名|4|25000#第二名|6|45000#第二名|7|10000#第三名|1|35000#第三名|4|25000#第三名|6|45000#第三名|7|10000#第四名|1|35000#第四名|4|25000#第四名|6|45000#第四名|7|10000#第五名|1|35000#第五名|4|25000#第五名|6|45000#第五名|7|10000#第六名|2|10000#第六名|5|95000#第六名|8|195000#第六名|9|15000#第六名|10|105000#第七名|2|10000#第七名|5|95000#第七名|8|195000#第七名|9|15000#第七名|10|105000#第八名|2|10000#第八名|5|95000#第八名|8|195000#第八名|9|15000#第八名|10|105000#第九名|2|10000#第九名|5|95000#第九名|8|195000#第九名|9|15000#第九名|10|105000#第十名|2|10000#第十名|5|95000#第十名|8|195000#第十名|9|15000#第十名|10|105000#";
		Map<String, int[][]> betInfo = new HashedMap(3);

		GameMergeTool.putBetInfo(GameUtil.Code.PK10, betInfo, betItems.split("#"));

		GameMergeTool.mergeInfo(GameUtil.Code.PK10, betInfo);
		List<Object> info = GameMergeTool.mergeItem(GameUtil.Code.PK10, betInfo, 1.0);
		System.out.println(info);

	}

	@Test public void testPassword() throws Exception {
		String str = "rZlMz5k8eMR+viJDy05vpw==";
		String decode = EncryptTool.decode(EncryptTool.Type.ASE, str);
		System.out.println(decode);

	}

}
