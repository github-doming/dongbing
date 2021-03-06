package com.cloud.lottery.sys_jobs.jsssc;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.game.GameOperation;
import com.cloud.common.game.PeriodOperation;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodSgWinTool;
import com.cloud.lottery.cloud_lottery_jsssc.entity.CloudLotteryJsssc;
import com.cloud.lottery.cloud_lottery_jsssc.service.CloudLotteryJssscService;
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
public class LotteryJssscSgwinJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.JSSSC;
	PeriodOperation<Integer> periodOperation =  PeriodSgWinTool.JSSSC;
	GameOperation gameOperation = PeriodSgWinTool.JSSSC;
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
		long period = periodOperation.findLotteryPeriod();

		CloudLotteryJsssc jsssc = getLotteryJsssc(period);
		CloudLotteryJssscService jssscService = new CloudLotteryJssscService();

		//读取数据库数据
		CloudLotteryJsssc entity = jssscService.findByPeriod(period,"SGWIN");
		//数据库没有数据
		if (entity == null) {
			if (jsssc == null) {
				//全无数据
				jsssc = new CloudLotteryJsssc();
				jsssc.setPeriod(period);
				jsssc.setHandicapCode("SGWIN");
				jsssc.setDrawType("168");
				jsssc.setCreateTime(new Date());
				jsssc.setCreateTimeLong(System.currentTimeMillis());
				jsssc.setState(StateEnum.CLOSE.name());
				jsssc.setDesc("{SGWIN}急速时时彩开奖数据抓取失败");
				jssscService.save(jsssc);
				log.info(GAME_CODE.getSign() + "期数：" + period + jsssc.getDesc());
				return;
			}
			//抓取到数据
			jssscService.save(jsssc);
			log.info(GAME_CODE.getSign() + "期数：" + period + jsssc.getDesc());
			// 发送mq
			LotteryTool.sendMq(log, period, GAME_CODE, jsssc.getHandicapCode(), jsssc.getDrawNumber(), jsssc.getDrawItem(),
					jsssc.getDrawTimeLong(), jsssc.getDesc(),"168");

		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据
			//抓取到数据
			if (jsssc != null) {
				jsssc.setIdx(entity.getIdx());
				jsssc.setCloudLotteryJssscId(entity.getCloudLotteryJssscId());
				jsssc.setCreateTime(entity.getCreateTime());
				jsssc.setCreateTimeLong(entity.getCreateTimeLong());
				jsssc.setUpdateTime(new Date());
				jsssc.setUpdateTimeLong(System.currentTimeMillis());
				//成功设置抓取信息
				jssscService.update(jsssc);
				log.info(GAME_CODE.getSign() + "期数：" + period + "，开奖数据：" + jsssc.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, jsssc.getHandicapCode(), jsssc.getDrawNumber(), jsssc.getDrawItem(),
						jsssc.getDrawTimeLong(), jsssc.getDesc(),"168");
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
	private CloudLotteryJsssc getLotteryJsssc(long period) throws IOException, InterruptedException, ParseException {
		String handicapUrl = "https://api.api861861.com/CQShiCai/getBaseCQShiCai.do";
		String param = "issue=" + period + "&lotCode=10036";
		JSONObject data = getJsonData(handicapUrl, param, period);
		if (data == null) {
			return null;
		}

		String[] codes = data.getString("preDrawCode").split(",");
		Integer[] drawNumbers = new Integer[codes.length];
		for (int i = 0; i < codes.length; i++) {
			drawNumbers[i] = Integer.parseInt(codes[i]);
		}

		CloudLotteryJsssc jsssc = new CloudLotteryJsssc();
		long drawTime = periodOperation.getLotteryDrawTime();
		jsssc.setDrawTime(new Date(drawTime));
		jsssc.setDrawTimeLong(drawTime);
		jsssc.setHandicapCode("SGWIN");
		jsssc.setDrawType("168");
		jsssc.setPeriod(period);
		jsssc.setDrawNumber(StringUtils.join(drawNumbers, ","));
		jsssc.setDrawItem(gameOperation.getDrawItem(jsssc.getDrawNumber()));
		jsssc.setP1Number(drawNumbers[0]);
		jsssc.setP2Number(drawNumbers[1]);
		jsssc.setP3Number(drawNumbers[2]);
		jsssc.setP4Number(drawNumbers[3]);
		jsssc.setP5Number(drawNumbers[4]);
		jsssc.setCreateTime(new Date());
		jsssc.setCreateTimeLong(System.currentTimeMillis());
		jsssc.setState(StateEnum.OPEN.name());
		jsssc.setDesc("{SGWIN}急速时时彩开奖数据抓取成功");
		return jsssc;
	}

	/**
	 * 获取开奖JSON
	 *
	 * @param handicapUrl 请求地址
	 * @param param       请求参数
	 * @param period      期数
	 * @return 开奖JSON
	 */
	private JSONObject getJsonData(String handicapUrl, String param, long period) throws InterruptedException {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "{SGWIN}急速时时彩开奖数据，期数：【" + period + "】，循环次数超过" + CYCLE + "次");
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
			log.info(GAME_CODE.getSign() + "{SGWIN}急速时时彩开奖数据，期数：【" + period + "】，抓取数据为空");
			index++;
			Thread.sleep(RandomTool.getInt(500, 1500));
			return getJsonData(handicapUrl, param, period);
		}
		if (StringTool.contains(html, "HTTP Status 500","502 Bad Gateway","Access Denied")) {
			log.info(GAME_CODE.getSign() + "{SGWIN}急速时时彩开奖数据，期数：【" + period + "】，获取的页面错误{" + html + "}");
			index++;
			Thread.sleep(RandomTool.getInt(500, 1500));
			return getJsonData(handicapUrl, param, period);
		}
		try {
			JSONObject data = JSONObject.parseObject(html).getJSONObject("result").getJSONObject("data");
			String crawlPeriod = data.getString("preDrawIssue");
			if (!crawlPeriod.equals(period + "")) {
				log.info(GAME_CODE.getSign() + "{SGWIN}急速时时彩开奖数据，抓取期数为：【" + crawlPeriod + "】，应有期数为：【" + period
						+ "】");
				index++;
				Thread.sleep(RandomTool.getInt(500, 1500));
				return getJsonData(handicapUrl, param, period);
			}
			return data;
		} catch (Exception e) {
			log.error(GAME_CODE.getSign() + "{SGWIN}急速时时彩开奖数据，期数：【" + period + "】，解析错误，页面【" + html + "】，错误：", e);
			index++;
			sleep();
			return getJsonData(handicapUrl, param, period);
		}

	}
}
