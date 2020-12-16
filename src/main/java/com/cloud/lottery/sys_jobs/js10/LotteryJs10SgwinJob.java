package com.cloud.lottery.sys_jobs.js10;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.game.GameOperation;
import com.cloud.common.game.PeriodOperation;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodSgWinTool;
import com.cloud.lottery.cloud_lottery_js10.entity.CloudLotteryJs10;
import com.cloud.lottery.cloud_lottery_js10.service.CloudLotteryJs10Service;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * @Description: SGWIN盘口开奖抓取
 * @Author: wwj
 * @Date: 2019-11-20 09:40
 * @Version: v1.0
 */
public class LotteryJs10SgwinJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.JS10;
	PeriodOperation<Integer> periodOperation =  PeriodSgWinTool.JS10;
	GameOperation gameOperation = PeriodSgWinTool.JS10;
	private int index = 0;
	public static final int CYCLE = 100;
	@Override public void executeJob(JobExecutionContext context) throws Exception {


		if (System.currentTimeMillis() - periodOperation.getLotteryDrawTime() > periodOperation.period()) {
			saveLog = false;
			log.info("未到可以开始抓取的时间");
			return;
		}
		if (System.currentTimeMillis() - periodOperation.getLotteryDrawTime() >= 25000L) {
			log.info("跳过循环");
			saveLog = false;
			return;
		}
		//已开奖期数
		long period = periodOperation.findLotteryPeriod();
		CloudLotteryJs10 js10 = getLotteryJs10(period);
		CloudLotteryJs10Service js10Service = new CloudLotteryJs10Service();

		//读取数据库数据
		CloudLotteryJs10 entity = js10Service.findByPeriod(period,"SGWIN");
		//数据库没有数据
		if (entity == null) {
			if (js10 == null) {
				//全无数据
				js10 = new CloudLotteryJs10();
				js10.setPeriod(period);
				js10.setCreateTime(new Date());
				js10.setCreateTimeLong(System.currentTimeMillis());
				js10.setState(StateEnum.CLOSE.name());
				js10.setDesc("{SGWIN}急速赛车开奖数据抓取失败");
				js10Service.save(js10);
				log.info(GAME_CODE.getSign() + "期数：" + period + js10.getDesc());
				return;
			}
			//抓取到数据
			js10Service.save(js10);
			log.info(GAME_CODE.getSign() + "期数：" + period + js10.getDesc());
			// 发送mq
			LotteryTool.sendMq(log, period, GAME_CODE, js10.getHandicapCode(), js10.getDrawNumber(), js10.getDrawItem(),
					js10.getDrawTimeLong(), js10.getDesc(),"168");

		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据
			//抓取到数据
			if (js10 != null) {
				js10.setIdx(entity.getIdx());
				js10.setCloudLotteryJs10Id(entity.getCloudLotteryJs10Id());
				js10.setCreateTime(entity.getCreateTime());
				js10.setCreateTimeLong(entity.getCreateTimeLong());
				js10.setUpdateTime(new Date());
				js10.setUpdateTimeLong(System.currentTimeMillis());
				//成功设置抓取信息
				js10Service.update(js10);
				log.info(GAME_CODE.getSign() + "期数：" + period + "，开奖数据：" + js10.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, js10.getHandicapCode(), js10.getDrawNumber(),
						js10.getDrawItem(), js10.getDrawTimeLong(), js10.getDesc(),"168");
			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "168开奖网[JSON]开奖数据抓取失败，无需保存抓取数据");
			}
		}
	}

	/**
	 * 获取急速赛车开奖实体
	 *
	 * @param period
	 * @return
	 */
	private CloudLotteryJs10 getLotteryJs10(long period) throws IOException, InterruptedException, ParseException {
		String handicapUrl = "https://api.api861861.com/pks/getLotteryPksInfo.do";
		String param = "issue=" + period + "&lotCode=10037";
		JSONObject data = getJsonData(handicapUrl, param, period);
		if (data == null) {
			return null;
		}
		String[] codes = data.getString("preDrawCode").split(",");
		Integer[] drawNumbers = new Integer[codes.length];
		for (int i = 0; i < codes.length; i++) {
			drawNumbers[i] = Integer.parseInt(codes[i]);
		}
		CloudLotteryJs10 js10 = new CloudLotteryJs10();
		long drawTime = periodOperation.getLotteryDrawTime();
		js10.setDrawTime(new Date(drawTime));
		js10.setDrawTimeLong(drawTime);
		js10.setHandicapCode("SGWIN");
		js10.setDrawType("168");
		js10.setPeriod(period);
		js10.setDrawNumber(StringUtils.join(drawNumbers, ","));
		js10.setDrawItem(gameOperation.getDrawItem(js10.getDrawNumber()));
		js10.setP1Number(drawNumbers[0]);
		js10.setP2Number(drawNumbers[1]);
		js10.setP3Number(drawNumbers[2]);
		js10.setP4Number(drawNumbers[3]);
		js10.setP5Number(drawNumbers[4]);
		js10.setP6Number(drawNumbers[5]);
		js10.setP7Number(drawNumbers[6]);
		js10.setP8Number(drawNumbers[7]);
		js10.setP9Number(drawNumbers[8]);
		js10.setP10Number(drawNumbers[9]);
		js10.setCreateTime(new Date());
		js10.setCreateTimeLong(System.currentTimeMillis());
		js10.setState(StateEnum.OPEN.name());
		js10.setDesc("{SGWIN}急速赛车开奖数据抓取成功");
		return js10;
	}

	/**
	 * 获取开奖JSON
	 *
	 * @param handicapUrl
	 * @param param
	 * @param period
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private JSONObject getJsonData(String handicapUrl, String param, long period)
			throws IOException, InterruptedException {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "{SGWIN}急速赛车开奖数据，期数：【" + period + "】，循环次数超过20次");
			return null;
		}
		String html = HttpClientTool.doGet(handicapUrl, param);
		for (; index < CYCLE; index++) {
			//没有抓取到
			if (StringTool.isEmpty(html)) {
				Thread.sleep(RandomTool.getInt(500, 1500));
				html = HttpClientTool.doGet(handicapUrl, param);
			} else {
				break;
			}
		}
		if (StringTool.isEmpty(html)) {
			log.info(GAME_CODE.getSign() + "{SGWIN}急速赛车开奖数据，期数：【" + period + "】，抓取数据为空");
			index++;
			Thread.sleep(RandomTool.getInt(500, 1500));
			return getJsonData(handicapUrl, param, period);
		}
		if (StringTool.contains(html, "HTTP Status 500", "502 Bad Gateway","Access Denied")) {
			log.info(GAME_CODE.getSign() + "{SGWIN}急速时时彩开奖数据，期数：【" + period + "】，获取的页面错误{" + html + "}");
			index++;
			Thread.sleep(RandomTool.getInt(500, 1500));
			return getJsonData(handicapUrl, param, period);
		}
		try {
			JSONObject jsonObject = JSONObject.parseObject(html).getJSONObject("result").getJSONObject("data");
			String crawlPeriod = jsonObject.getString("preDrawIssue");
			if (!crawlPeriod.equals(period + "")) {
				log.info(GAME_CODE.getSign() + "{SGWIN}急速赛车开奖数据，抓取期数为：【" + crawlPeriod + "】，应有期数为：【" + period + "】");
				index++;
				Thread.sleep(RandomTool.getInt(500, 1500));
				return getJsonData(handicapUrl, param, period);
			}
			return jsonObject;
		} catch (Exception e) {
			log.error(GAME_CODE.getSign() + "{SGWIN}急速赛车开奖数据，期数：【" + period + "】，解析错误，页面【" + html + "】，错误：", e);
			index++;
			sleep();
			return getJsonData(handicapUrl, param, period);
		}

	}
}
