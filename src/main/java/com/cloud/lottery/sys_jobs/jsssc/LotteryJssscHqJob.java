package com.cloud.lottery.sys_jobs.jsssc;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.game.hq.JssscHq;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodHqTool;
import com.cloud.lottery.cloud_lottery_jsssc.entity.CloudLotteryJsssc;
import com.cloud.lottery.cloud_lottery_jsssc.service.CloudLotteryJssscService;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.CurrentTransaction;
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
 * @Description: HQ盘口开奖抓取
 * @Author: wwj
 * @Date: 2019-11-19 09:40
 * @Version: v1.0
 */
public class LotteryJssscHqJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.JSSSC;

	private int index = 0;
	public static final int CYCLE = 100;
	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodHqTool.JSSSC.getLotteryDrawTime() > JssscHq.PERIOD) {
			saveLog = false;
			log.info("未到可以开始抓取的时间");
			return;
		}
		if (System.currentTimeMillis() - PeriodHqTool.JSSSC.getLotteryDrawTime() >= 25000L) {
			log.info("跳过循环");
			saveLog = false;
			return;
		}
		String period = PeriodHqTool.JSSSC.findLotteryPeriod();

		CloudLotteryJsssc jsssc = getLotteryJsssc(period);
		CloudLotteryJssscService jssscService = new CloudLotteryJssscService();

		//读取数据库数据
		CurrentTransaction.transactionCommit();
		CloudLotteryJsssc entity = jssscService.findByPeriod(period);
		//数据库没有数据
		if (entity == null) {
			if (jsssc == null) {
				//全无数据
				jsssc = new CloudLotteryJsssc();
				jsssc.setPeriod(period);
				jsssc.setHandicapCode("HQ");
				jsssc.setDrawType("HQ");
				jsssc.setCreateTime(new Date());
				jsssc.setCreateTimeLong(System.currentTimeMillis());
				jsssc.setState(StateEnum.CLOSE.name());
				jsssc.setDesc("{HQ}急速时时彩开奖数据抓取失败");
				jssscService.save(jsssc);
				log.info(GAME_CODE.getSign() + "期数：" + period + jsssc.getDesc());
				return;
			}
			//抓取到数据
			jssscService.save(jsssc);
			log.info(GAME_CODE.getSign() + "期数：" + period + jsssc.getDesc());
			// 发送mq
			LotteryTool
					.sendMq(log, period, GAME_CODE, jsssc.getHandicapCode(), jsssc.getDrawNumber(), jsssc.getDrawItem(),
							jsssc.getDrawTimeLong(), jsssc.getDesc(),"HQ");

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
				LotteryTool.sendMq(log, period, GAME_CODE, jsssc.getHandicapCode(), jsssc.getDrawNumber(),
						jsssc.getDrawItem(), jsssc.getDrawTimeLong(), jsssc.getDesc(),"HQ");
			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "时时资讯[JSON]开奖数据抓取失败，无需保存抓取数据");
			}
		}
	}

	/**
	 * 获取急速赛车开奖实体
	 *
	 * @param period
	 * @return
	 */
	private CloudLotteryJsssc getLotteryJsssc(String period) throws IOException, InterruptedException, ParseException {
		String handicapUrl = LotteryTool.getHqHost().concat("/kaijiang/jsssc.json");
		String param = "v=" + System.currentTimeMillis();
		JSONObject jsonObject = getJsonData(handicapUrl, param, period);
		if (jsonObject == null) {
			return null;
		}
		JSONArray codes = jsonObject.getJSONArray("code");
		Integer[] drawNumbers = new Integer[codes.size()];
		for (int i = 0; i < codes.size(); i++) {
			drawNumbers[i] = Integer.parseInt((String) codes.get(i));
		}
		CloudLotteryJsssc jsssc = new CloudLotteryJsssc();
		long drawTime = PeriodHqTool.JSSSC.getLotteryDrawTime();
		jsssc.setDrawTime(new Date(drawTime));
		jsssc.setDrawTimeLong(drawTime);
		jsssc.setHandicapCode("HQ");
		jsssc.setDrawType("HQ");
		jsssc.setPeriod(period);
		jsssc.setDrawNumber(StringUtils.join(drawNumbers, ","));
		jsssc.setDrawItem(PeriodHqTool.JSSSC.getDrawItem(jsssc.getDrawNumber()));
		jsssc.setP1Number(drawNumbers[0]);
		jsssc.setP2Number(drawNumbers[1]);
		jsssc.setP3Number(drawNumbers[2]);
		jsssc.setP4Number(drawNumbers[3]);
		jsssc.setP5Number(drawNumbers[4]);

		jsssc.setCreateTime(new Date());
		jsssc.setCreateTimeLong(System.currentTimeMillis());
		jsssc.setState(StateEnum.OPEN.name());
		jsssc.setDesc("{HQ}急速时时彩开奖数据抓取成功");
		return jsssc;
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
	private JSONObject getJsonData(String handicapUrl, String param, String period) throws InterruptedException {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "{HQ}急速时时彩开奖数据，期数：【" + period + "】，循环次数超过20次");
			return null;
		}
		String html = HttpClientTool.doGet(handicapUrl, param);
		for (; index < CYCLE; index++) {
			//没有抓取到
			if (StringTool.isEmpty(html)) {
				Thread.sleep(RandomTool.getInt(500, 1500));
				param = "v=" + System.currentTimeMillis();
				html = HttpClientTool.doGet(handicapUrl, param);
			} else {
				break;
			}
		}
		if (StringTool.isEmpty(html)) {
			log.info(GAME_CODE.getSign() + "{HQ}急速时时彩开奖数据，期数：【" + period + "】，抓取数据为空");
			index++;
			sleep();
			return getJsonData(handicapUrl, param, period);
		}
		try {
			JSONObject jsonObject = JSONObject.parseObject(html);
			String crawlPeriod = jsonObject.getString("issue");
			if (!crawlPeriod.equals(period + "")) {
				log.info(GAME_CODE.getSign() + "{HQ}急速时时彩开奖数据，抓取期数为：【" + crawlPeriod + "】，应有期数为：【" + period + "】");
				index++;
				sleep();
				param = "v=" + System.currentTimeMillis();
				return getJsonData(handicapUrl, param, period);
			}
			return jsonObject;
		} catch (Exception e) {
			log.error(GAME_CODE.getSign() + "{HQ}急速时时彩开奖数据，期数：【" + period + "】，解析错误，页面【" + html + "】，错误：", e);
			index++;
			sleep();
			param = "v=" + System.currentTimeMillis();
			return getJsonData(handicapUrl, param, period);
		}

	}
}
