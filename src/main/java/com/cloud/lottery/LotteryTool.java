package com.cloud.lottery;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.tool.RabbitMqTool;
import org.apache.logging.log4j.Logger;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.FileTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpResult;
import org.doming.develop.http.httpclient.HttpClientConfig;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.http.httpclient.HttpClientUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @Description: 爬虫工具类
 * @Author: Dongming
 * @Date: 2019-01-19 15:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryTool {

	public static final Object KEY = new Object();

	//region PK10

	private static int pk10Period;
	private static String pk10Lottery;

	public static String getPk10Lottery(int period) {
		if (period - pk10Period == 0) {
			return pk10Lottery;
		}
		return null;
	}

	public static boolean setPk10Lottery(int period, String lottery) {
		if (period - pk10Period != 0) {
			synchronized (KEY) {
				if (period - pk10Period != 0) {
					pk10Period = period;
					pk10Lottery = lottery;
					return true;
				}
			}
		}
		return false;
	}
	//endregion

	//region XYFT

	private static String xyftPeriod;
	private static String xyftLottery;

	public static String getXyftLottery(String period) {
		if (period.equals(xyftPeriod)) {
			return xyftLottery;
		}
		return null;
	}

	public static boolean setXyftLottery(String period, String lottery) {
		if (!period.equals(xyftPeriod)) {
			synchronized (KEY) {
				if (!period.equals(xyftPeriod)) {
					xyftPeriod = period;
					xyftLottery = lottery;
					return true;
				}
			}
		}
		return false;
	}
	//endregion

	//region CQSSC

	private static String cqsscPeriod;
	private static String cqsscLottery;

	public static String getCqsscLottery(String period) {
		if (period.equals(cqsscPeriod)) {
			return cqsscLottery;
		}
		return null;
	}

	public static boolean setCqsscLottery(String period, String lottery) {
		if (!period.equals(cqsscPeriod)) {
			synchronized (KEY) {
				if (!period.equals(cqsscPeriod)) {
					cqsscPeriod = period;
					cqsscLottery = lottery;
					return true;
				}
			}
		}
		return false;
	}
	//endregion

	//region XYNC

	private static String xyncPeriod;
	private static String xyncLottery;

	public static String getXyncLottery(String period) {
		if (period.equals(xyncPeriod)) {
			return xyncLottery;
		}
		return null;
	}

	public static boolean setXyncLottery(String period, String lottery) {
		if (!period.equals(xyncPeriod)) {
			synchronized (KEY) {
				if (!period.equals(xyncPeriod)) {
					xyncPeriod = period;
					xyncLottery = lottery;
					return true;
				}
			}
		}
		return false;
	}
	//endregion

	// region GDKLC

	private static String gdklcPeriod;
	private static String gdklcLottery;

	public static String getGdklcLottery(String period) {
		if (period.equals(gdklcPeriod)) {
			return gdklcLottery;
		}
		return null;
	}

	public static boolean setGdklcLottery(String period, String lottery) {
		if (!period.equals(gdklcPeriod)) {
			synchronized (KEY) {
				if (!period.equals(gdklcPeriod)) {
					gdklcPeriod = period;
					gdklcLottery = lottery;
					return true;
				}
			}
		}
		return false;
	}
	//endregion

	//region 彩世界主机地址

	private static String csjHost = "https://www.1396r.com";
	private static Long cjsDayTime = DateTool.getDayStart().getTime();

	public static String getCsjHost() throws IOException {
		if (System.currentTimeMillis() - cjsDayTime < 86400000) {
			return csjHost;
		}
		synchronized (LotteryTool.class) {
			if (System.currentTimeMillis() - cjsDayTime < 86400000) {
				return csjHost;
			}
			cjsDayTime = DateTool.getDayStart().getTime();
			String host = getHost(csjHost);
			csjHost = host.substring(0, host.indexOf("/", 10));
			String filePath = LotteryTool.class.getResource("/").getPath().concat("csjHost.txt");
			String context = DateTool.getDate(new Date()).concat(" ").concat(csjHost);
			FileTool.addLines2File(filePath, new String[]{context});

		}
		return csjHost;
	}
	//endregion

	//region HQ主机地址

	private final static String HQ_FINAL_HOST = "https://cc138001.com";
	private static String hqHost = "https://web01.cc138008.com";
	private static Long hqDayTime = DateTool.getDayStart().getTime();

	public static String getHqHost() throws IOException {
		if (System.currentTimeMillis() - hqDayTime < 86400000) {
			return hqHost;
		}
		synchronized (LotteryTool.class) {
			if (System.currentTimeMillis() - hqDayTime < 86400000) {
				return csjHost;
			}
			hqDayTime = DateTool.getDayStart().getTime();
			String host = getHost(HQ_FINAL_HOST);
			if(host.indexOf("/", 10)==-1){
				return hqHost;
			}
			host = host.substring(0, host.indexOf("/", 10));
			if (hqHost.equals(host)) {
				return hqHost;
			} else {
				hqHost = host;
				String filePath = LotteryTool.class.getResource("/").getPath().concat("hqHost.txt");
				String context = DateTool.getDate(new Date()).concat(" ").concat(hqHost);
				FileTool.addLines2File(filePath, new String[]{context});
			}
		}
		return csjHost;
	}

	private static String getHost(String host) {
		HttpClientConfig hcc = HttpClientUtil.getConfig();
		hcc.redirectsEnabled(false);
		HttpClientUtil hcu = HttpClientUtil.findInstance();
		hcc.httpClient(hcu.createHttpClient());
		HttpResult result = hcu.get(hcc.url(host));
		if (StringTool.isEmpty(result.getLocation()) || "/".equals(result.getLocation()) || host
				.equals(result.getLocation())) {
			return host;
		}
		return getHost(result.getLocation());
	}
	//endregion

	/**
	 * 获取开奖元素节点
	 *
	 * @param log         日志
	 * @param sign        日志标签
	 * @param handicapUrl 盘口开奖地址
	 * @param map         盘口开奖数据
	 * @param period      期数
	 * @param cycle       周期
	 * @return 开奖元素节点
	 */
	public static Element getElement(final Logger log, String sign, String handicapUrl, Map<String, Object> map,
			Object period, int index, int cycle) throws InterruptedException {
		if (index > cycle) {
			log.info(sign + "{IDC}开奖数据，期数：【" + period + "】，循环次数超过" + cycle + "次");
			return null;
		}
		String html = HttpClientTool.doPost(handicapUrl, map);
		for (; index < cycle; index++) {
			//没有抓取到
			if (StringTool.isEmpty(html)) {
				Thread.sleep(RandomTool.getInt(500, 1500));
				html = HttpClientTool.doPost(handicapUrl, map);
			} else {
				break;
			}
		}
		if (StringTool.isEmpty(html)) {
			log.info(sign + "{IDC}开奖数据，期数：【" + period + "】，抓取数据为空");
			Thread.sleep(RandomTool.getInt(500, 1500));
			index++;
			return getElement(log, sign, handicapUrl, map, period, index, cycle);
		}
		try {
			if (StringTool.contains(html, "<!DOCTYPE html>","There was an error processing the request","500 Internal Privoxy Error")) {
				log.info(sign + "{IDC}开奖数据，期数：【" + period + "】，获取的页面错误，页面【" + html + "】");
				Thread.sleep(RandomTool.getInt(500, 1500));
				index++;
				return getElement(log, sign, handicapUrl, map, period, index, cycle);
			}
			String d = JSON.parseObject(html).getString("d");
			Document document = Jsoup.parse(JSON.parseArray(d).getString(0));
			Element trElement = document.selectFirst("tbody tr");
			if (trElement == null) {
				log.info(sign + "{IDC}开奖数据，期数：【" + period + "】，解析错误，页面【" + html + "】");
				Thread.sleep(RandomTool.getInt(500, 1500));
				index++;
				return getElement(log, sign, handicapUrl, map, period, index, cycle);
			}
			Elements tds = trElement.getElementsByTag("td");
			String text = tds.get(0).text();
			if (!text.equals(period + "")) {
				log.info(sign + "{IDC}开奖数据，抓取期数为：【" + text + "】，应有期数为：【" + period + "】");
				Thread.sleep(RandomTool.getInt(500, 1500));
				index++;
				return getElement(log, sign, handicapUrl, map, period, index, cycle);
			}
			return trElement;
		} catch (Exception e) {
			log.error(sign + "{IDC}开奖数据，期数：【" + period + "】，解析错误，页面【" + html + "】，错误：", e);
			Thread.sleep(RandomTool.getInt(500, 1500));
			index++;
			return getElement(log, sign, handicapUrl, map, period, index, 100);
		}

	}

	public static void sendMq(final Logger log, Object period, Code gameCode, String handicapCode, String drawNumber,
			String drawItem, long drawTimeLong, String desc, String drawType) throws Exception {
		JSONObject jObj = new JSONObject();
		jObj.put("code", gameCode.name());
		jObj.put("handicapCode", handicapCode);
		jObj.put("period", period);
		jObj.put("drawTimeLong", drawTimeLong);
		jObj.put("drawNumber", drawNumber);
		jObj.put("drawItem", drawItem);
		jObj.put("drawType", drawType);
		jObj.put("desc", desc);
		RabbitMqTool.sendLottery(log, jObj.toString(), gameCode.routingKey);
	}

	public enum Code {
		/**
		 * 号码类
		 */
		PK10("北京赛车", "pk10", "~【PK10】~"),

		XYFT("幸运飞艇", "xyft", "~【XYFT】~"),

		JS10("急速赛车", "js10", "~【JS10】~"),

		SELF_10_2("自有赛车2分彩", "self102", "~【SELF_10_2】~"),

		SELF_10_5("自有赛车5分彩", "self105", "~【SELF_10_5】~"),

		COUNTRY_10("国家赛车", "country10", "~【COUNTRY_10】~"),
		/**
		 * 球类
		 */
		CQSSC("重庆时时彩", "cqssc", "~【CQSSC】~"),

		JSSSC("急速时时彩", "jsssc", "~【JSSSC】~"),

		SELF_SSC_5("自有时时彩5分彩", "selfSsc5", "~【SELF_SSC_5】~"),

		COUNTRY_SSC("国家时时彩", "countrySsc", "~【COUNTRY_SSC】~"),
		/**
		 * 快乐类
		 */
		XYNC("幸运农场", "xync", "~【XYNC】~"),

		GDKLC("广东快乐彩", "gdklc", "~【GDKLC】~"),

		GXKLSF("广西快乐十分", "gxklsf", "~【GXKLSF】~");
		String routingKey;
		String gameName;
		String sign;

		Code(String gameName, String routingKey, String sign) {
			this.gameName = gameName;
			this.routingKey = routingKey;
			this.sign = sign;
		}

		public String getGameName() {
			return gameName;
		}

		public String getRoutingKey() {
			return routingKey;
		}

		public String getSign() {
			return sign;
		}
	}

}
