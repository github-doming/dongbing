package com.ibm.common.test.wwj;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ibm.common.core.CommTest;
import com.ibm.common.core.configs.IbmMainConfig;
import com.ibm.common.tools.GameTool;
import com.ibm.common.tools.RabbitMqTool;
import com.ibm.common.utils.game.GameUtil;
import com.ibm.follow.servlet.cloud.ibm_game.service.IbmGameService;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_sys_bet_odds.service.IbmSysBetOddsService;
import com.ibm.follow.servlet.server.core.thread.SettleBetItemThread;
import org.apache.commons.lang.StringUtils;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.util.*;
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
			Map<String, Object> drawInfo = gameService.findDrawInfo(sql, period,"");
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

	@Test public void test() {
		String html =
				"\n"
						+ "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
						+ "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" + "<head>\n"
						+ "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
						+ "<title>注单明细</title>\n"
						+ "<link href=\"/js/jquery/new/jquery-ui.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
						+ "<link href=\"/default/css/agent/red/master.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
						+ "<link href=\"/default/css/agent/red/layout.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
						+ "<link href=\"/default/css/sweetalert.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
						+ "<link href=\"/default/css/loading.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
						+ "<script type=\"text/javascript\" src=\"/js/jquery.js\"></script>\n"
						+ "<script type=\"text/javascript\" src=\"/js/jquery-ui.js\"></script>\n"
						+ "<script type=\"text/javascript\" src=\"/js/jquery.ui.datepicker-zh-CN.js\"></script>\n"
						+ "<script type=\"text/javascript\" src=\"/js/libs.js\"></script>\n"
						+ "<script type=\"text/javascript\" src=\"/js/json2.js\"></script>\n"
						+ "<script type=\"text/javascript\" src=\"/default/js/sweetalert.min.js\"></script>\n"
						+ "<script>\n" + "  $(document).ready(function(){\n"
						+ "    $(\".page-jump input\").on(\"keydown\", function(e){\n" + "      if(e.keyCode === 13){\n"
						+ "        e.stopPropagation();\n" + "        $(this).blur();\n"
						+ "        if($(this).val() <= 0 || $(this).parent().data(\"total-page\") < $(this).val()){\n"
						+ "          alert(\"页面不存在\");\n" + "          return;\n" + "        } else{\n"
						+ "          var navigatePage = $(this).parent().data(\"page-url\").replace(\"page=1\", \"page=\" + $(this).val());\n"
						+ "          location.href = location.origin + location.pathname + navigatePage;\n"
						+ "        }\n" + "      }\n" + "    })\n" + "  })\n" + "\n"
						+ "</script><link href=\"/default/css/agent/bets.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
						+ "<link href=\"/default/css/betExtra.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
						+ "<script type=\"text/javascript\" src=\"/default/js/bets.js\"></script>\n"
						+ "<script type=\"text/javascript\" src=\"/default/js/betExtra.js\"></script>\n" + "</head>\n"
						+ "<body>\n" + "<div class=\"main\">\n" + "<div class=\"top_info\">\n"
						+ "<span class=\"title\">注单明细</span>\n" + "<span class=\"right\">\n"
						+ "<a class=\"back\" href=\"javascript:history.go(-1)\">返回</a>\n" + "</span>\n" + "</div>\n"
						+ "<div class=\"contents\">\n" + "<table class=\"data_table list\">\n" + "<thead>\n" + "<tr>\n"
						+ "<th class=\"th-box\">注单号\n" + "</th>\n" + "<th>投注时间</th>\n"
						+ "<th class=\"type th-box\">投注种类\n" + "</th>\n" + "<th>账号</th>\n" + "<th>投注内容</th>\n"
						+ "<th class=\"range th-box\" style=\"width:250px\">\n" + "<div class=\"display_amount\">\n"
						+ "下注金额\n" + "</div>\n" + "<div class=\"input_amount\" style=\"display:none\">\n"
						+ "<input  type\"search\" placeholder=\"最小下注金额\" name=\"minAmount1\" value=\"\" style=\"width:80px\" />&nbsp;\n"
						+ "<input type\"search\" placeholder=\"最大下注金额\" name=\"maxAmount1\" value=\"\" style=\"width:80px\"/>\n"
						+ "</div>\n" + "<button class=\"th_icon1\"></button>\n" + "</th>\n" + "</th>\n"
						+ "<th>退水</th>\n" + "<th>下注结果</th>\n" + "<th>本级占成</th>\n" + "<th class=\"result\">本级结果</th>\n"
						+ "<th>占成明细</th>\n" + "</tr>\n" + "</thead>\n" + "<tbody>\n" + "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914433251941348580004\", \"CQHLSX\")'>2019110914433251941348580004#</a> </span></td>\n"
						+ "<td>2019-11-09 14:43:32 星期六</td>\n"
						+ "<td class=\"period\">重庆欢乐生肖<div class=\"drawNumber1\">20191109032期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber1\">第五球『单』</span> @ <span class=\"odds\">1.9826</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914433251941348580004\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914433251941348580003\", \"CQHLSX\")'>2019110914433251941348580003#</a> </span></td>\n"
						+ "<td>2019-11-09 14:43:32 星期六</td>\n"
						+ "<td class=\"period\">重庆欢乐生肖<div class=\"drawNumber1\">20191109032期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber1\">第四球『8』</span> @ <span class=\"odds\">9.91</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914433251941348580003\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914433251941348580002\", \"CQHLSX\")'>2019110914433251941348580002#</a> </span></td>\n"
						+ "<td>2019-11-09 14:43:32 星期六</td>\n"
						+ "<td class=\"period\">重庆欢乐生肖<div class=\"drawNumber1\">20191109032期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber1\">第二球『2』</span> @ <span class=\"odds\">9.91</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914433251941348580002\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914433251941348580001\", \"CQHLSX\")'>2019110914433251941348580001#</a> </span></td>\n"
						+ "<td>2019-11-09 14:43:32 星期六</td>\n"
						+ "<td class=\"period\">重庆欢乐生肖<div class=\"drawNumber1\">20191109032期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber1\">第一球『小』</span> @ <span class=\"odds\">1.9826</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914433251941348580001\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914431951941343470008\", \"CQHLSX\")'>2019110914431951941343470008#</a> </span></td>\n"
						+ "<td>2019-11-09 14:43:19 星期六</td>\n"
						+ "<td class=\"period\">重庆欢乐生肖<div class=\"drawNumber1\">20191109032期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber1\">后三『对子』</span> @ <span class=\"odds\">3</span>\n" + "</td>\n"
						+ "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914431951941343470008\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914431951941343470007\", \"CQHLSX\")'>2019110914431951941343470007#</a> </span></td>\n"
						+ "<td>2019-11-09 14:43:19 星期六</td>\n"
						+ "<td class=\"period\">重庆欢乐生肖<div class=\"drawNumber1\">20191109032期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber1\">中三『杂六』</span> @ <span class=\"odds\">2.93</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914431951941343470007\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914431951941343470006\", \"CQHLSX\")'>2019110914431951941343470006#</a> </span></td>\n"
						+ "<td>2019-11-09 14:43:19 星期六</td>\n"
						+ "<td class=\"period\">重庆欢乐生肖<div class=\"drawNumber1\">20191109032期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber1\">前三『顺子』</span> @ <span class=\"odds\">14.02</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914431951941343470006\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914431951941343470005\", \"CQHLSX\")'>2019110914431951941343470005#</a> </span></td>\n"
						+ "<td>2019-11-09 14:43:19 星期六</td>\n"
						+ "<td class=\"period\">重庆欢乐生肖<div class=\"drawNumber1\">20191109032期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber1\">第三球『单』</span> @ <span class=\"odds\">1.9826</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914431951941343470005\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914431951941343470004\", \"CQHLSX\")'>2019110914431951941343470004#</a> </span></td>\n"
						+ "<td>2019-11-09 14:43:19 星期六</td>\n"
						+ "<td class=\"period\">重庆欢乐生肖<div class=\"drawNumber1\">20191109032期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber1\">第一球『小』</span> @ <span class=\"odds\">1.9826</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914431951941343470004\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914431951941343470003\", \"CQHLSX\")'>2019110914431951941343470003#</a> </span></td>\n"
						+ "<td>2019-11-09 14:43:19 星期六</td>\n"
						+ "<td class=\"period\">重庆欢乐生肖<div class=\"drawNumber1\">20191109032期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber1\">和</span> @ <span class=\"odds\">9.35</span>\n" + "</td>\n"
						+ "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914431951941343470003\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914431951941343470002\", \"CQHLSX\")'>2019110914431951941343470002#</a> </span></td>\n"
						+ "<td>2019-11-09 14:43:19 星期六</td>\n"
						+ "<td class=\"period\">重庆欢乐生肖<div class=\"drawNumber1\">20191109032期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber1\">龙</span> @ <span class=\"odds\">1.9826</span>\n" + "</td>\n"
						+ "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914431951941343470002\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914431951941343470001\", \"CQHLSX\")'>2019110914431951941343470001#</a> </span></td>\n"
						+ "<td>2019-11-09 14:43:19 星期六</td>\n"
						+ "<td class=\"period\">重庆欢乐生肖<div class=\"drawNumber1\">20191109032期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber1\">总和小</span> @ <span class=\"odds\">1.9826</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914431951941343470001\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914422351941315090004\", \"BJPK10\")'>2019110914422351941315090004#</a> </span></td>\n"
						+ "<td>2019-11-09 14:42:23 星期六</td>\n"
						+ "<td class=\"period\">北京赛车(PK10)<div class=\"drawNumber2\">741024期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber2\">第七名『单』</span> @ <span class=\"odds\">1.9826</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914422351941315090004\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914422351941315090003\", \"BJPK10\")'>2019110914422351941315090003#</a> </span></td>\n"
						+ "<td>2019-11-09 14:42:23 星期六</td>\n"
						+ "<td class=\"period\">北京赛车(PK10)<div class=\"drawNumber2\">741024期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber2\">第五名『虎』</span> @ <span class=\"odds\">1.9826</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914422351941315090003\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914422351941315090002\", \"BJPK10\")'>2019110914422351941315090002#</a> </span></td>\n"
						+ "<td>2019-11-09 14:42:23 星期六</td>\n"
						+ "<td class=\"period\">北京赛车(PK10)<div class=\"drawNumber2\">741024期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber2\">第五名『双』</span> @ <span class=\"odds\">1.9826</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914422351941315090002\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914422351941315090001\", \"BJPK10\")'>2019110914422351941315090001#</a> </span></td>\n"
						+ "<td>2019-11-09 14:42:23 星期六</td>\n"
						+ "<td class=\"period\">北京赛车(PK10)<div class=\"drawNumber2\">741024期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber2\">冠军『小』</span> @ <span class=\"odds\">1.9826</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914422351941315090001\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914421051941304160006\", \"BJPK10\")'>2019110914421051941304160006#</a> </span></td>\n"
						+ "<td>2019-11-09 14:42:10 星期六</td>\n"
						+ "<td class=\"period\">北京赛车(PK10)<div class=\"drawNumber2\">741024期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber2\">第十名『6』</span> @ <span class=\"odds\">9.91</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914421051941304160006\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914421051941304160005\", \"BJPK10\")'>2019110914421051941304160005#</a> </span></td>\n"
						+ "<td>2019-11-09 14:42:10 星期六</td>\n"
						+ "<td class=\"period\">北京赛车(PK10)<div class=\"drawNumber2\">741024期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber2\">第七名『7』</span> @ <span class=\"odds\">9.91</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914421051941304160005\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914421051941304160004\", \"BJPK10\")'>2019110914421051941304160004#</a> </span></td>\n"
						+ "<td>2019-11-09 14:42:10 星期六</td>\n"
						+ "<td class=\"period\">北京赛车(PK10)<div class=\"drawNumber2\">741024期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber2\">第五名『2』</span> @ <span class=\"odds\">9.91</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914421051941304160004\">明细</a></td>\n" + "</tr>\n"
						+ "<tr>\n"
						+ "<td><a href='javascript:queryBetExtraDetail(\"qq22301\", \"2019110914421051941304160003\", \"BJPK10\")'>2019110914421051941304160003#</a> </span></td>\n"
						+ "<td>2019-11-09 14:42:10 星期六</td>\n"
						+ "<td class=\"period\">北京赛车(PK10)<div class=\"drawNumber2\">741024期</div></td>\n"
						+ "<td>qq22301<div>A盘</div></td>\n"
						+ "<td><span class=\"drawNumber2\">第四名『7』</span> @ <span class=\"odds\">9.91</span>\n"
						+ "</td>\n" + "<td class=\"money\">2</td>\n" + "<td class=\"commission\">0%</td>\n"
						+ "<td class=\"money dividend color\"><span>未结算</span></td>\n" + "<td class=\"share\">0%</td>\n"
						+ "<td class=\"money color\"></td>\n"
						+ "<td class=\"detail\"><a bid=\"2019110914421051941304160003\">明细</a></td>\n" + "</tr>\n"
						+ "</tbody>\n" + "<tfoot>\n"
						+ "<tr><th colspan=\"5\">总计：28笔</th><td class=\"money\">56</td><td></td><td class=\"money color\"></td><td></td>\n"
						+ "<td class=\"money color\"></td><td></td></tr>\n" + "</tfoot>\n" + "</table>\n" + "</div>\n"
						+ "<div id=\"betExtraDialog\"></div>\n" + "<div class=\"page\"><div class=\"page_info\">\n"
						+ "<span class=\"record\">共 28 笔注单</span>\n" + "<span class=\"page_count\">共 2 页</span>\n"
						+ "<span class=\"page_control\">\n" + "  <a class=\"previous\">首页</a> |\n"
						+ "<span class=\"page-jump\" data-total-page=\"2\" data-page-url=\"?\n" + "username=qq22301&\n"
						+ "\n" + "begin=2019-11-09&\n" + "\n" + "end=2019-11-09&\n" + "\n" + "settle=false&\n"
						+ "page=1\n" + "\">跳转至<input type=\"number\" style=\"width: 50px\" maxlength=\"3\"/>页 </span>\n"
						+ "<a class=\"previous\">前一页</a>『\n" + "<span class=\"current\">1</span>\n" + "<a href=\"?\n"
						+ "username=qq22301&\n" + "\n" + "begin=2019-11-09&\n" + "\n" + "end=2019-11-09&\n" + "\n"
						+ "settle=false&\n" + "page=2\n" + "\">2</a>\n" + "』<a class=\"next\" href=\"?\n"
						+ "username=qq22301&\n" + "\n" + "begin=2019-11-09&\n" + "\n" + "end=2019-11-09&\n" + "\n"
						+ "settle=false&\n" + "page=2\n" + "\">后一页</a> |\n" + "  <a class=\"next\" href=\"?\n"
						+ "username=qq22301&\n" + "\n" + "begin=2019-11-09&\n" + "\n" + "end=2019-11-09&\n" + "\n"
						+ "settle=false&\n" + "page=2\n" + "\">末页</a>\n" + "</span>\n" + "</div>\n" + "</div></div>\n"
						+ "</body>\n" + "</html>";
		Document document = Jsoup.parse(html);
		Elements trElements = document.select("div.contents>table.data_table>tbody>tr");
		if (trElements.isEmpty()) {
			System.out.println("123");
			return;
		}
		Elements pageElements = document.select("div.page>div.page_info>span");
		String total = StringUtils.substringBetween(pageElements.get(0).text(), "共 ", " 笔注单");
		String pages = StringUtils.substringBetween(pageElements.get(1).text(), "共 ", " 页");
		System.out.println(total);
		System.out.println(pages);

		for (Element trElement : trElements) {
			String oddNumber = trElement.child(0).text();
			String betInfo = trElement.child(4).selectFirst("span").text();
			betInfo = betInfo.replace("』","");
			String funds = trElement.child(5).text();
			System.out.print(oddNumber + "\t");
			System.out.print(betInfo + "\t");
			System.out.print(funds+ "\t");
			String betItem =getBetItem(betInfo);
			System.out.println(betItem);

		}
	}
	private static String getBetItem( String betInfo) {
		String[] infos = betInfo.split("『");
		if (infos.length == 1){
			return	betInfo.substring(0,2).concat("|").concat(betInfo.substring(2));
		}
		switch (infos[0]) {
			case "冠军":
				return "第一名|".concat(infos[1]);
			case "亚军":
				return "第二名|".concat(infos[1]);
			case "冠亚军和":
				return "冠亚|".concat(infos[1]);
			default:
				return infos[0].concat("|").concat(infos[1]);
		}
	}
}
