package com.cloud.lottery.sys_jobs.countryssc;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.game.idc.AustraliaLucky5;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodIdcTool;
import com.cloud.lottery.cloud_lottery_country_ssc.entity.CloudLotteryCountrySsc;
import com.cloud.lottery.cloud_lottery_country_ssc.service.CloudLotteryCountrySscService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.quartz.JobExecutionContext;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 澳洲幸运5 国家时时彩 858kai.com
 *
 * @Author: Dongming
 * @Date: 2020-04-30 14:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryCountrySscKjwJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.COUNTRY_SSC;
	public static final int CYCLE = 100;

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodIdcTool.COUNTRY_SSC.getLotteryDrawTime() > AustraliaLucky5.PERIOD) {
			log.trace("未到可以开始抓取的时间");
			super.saveLog = false;
			return;
		}
		Integer period = PeriodIdcTool.COUNTRY_SSC.findLotteryPeriod();
		CloudLotteryCountrySsc countrySsc = getLotteryCountrySsc(period, 0);
		CloudLotteryCountrySscService countrySscService = new CloudLotteryCountrySscService();
		//读取数据库数据
		CloudLotteryCountrySsc entity = countrySscService.findByPeriod(period);
		//数据库没有数据
		if (entity == null) {
			if (countrySsc == null) {
				//全无数据
				countrySsc = new CloudLotteryCountrySsc();
				countrySsc.setPeriod(period);
				countrySsc.setHandicapCode("IDC");
				countrySsc.setDrawType("IDC");
				countrySsc.setCreateTime(new Date());
				countrySsc.setCreateTimeLong(System.currentTimeMillis());
				countrySsc.setUpdateTimeLong(System.currentTimeMillis());
				countrySsc.setState(StateEnum.CLOSE.name());
				countrySsc.setDesc("{IDC}" + GAME_CODE.getGameName() + "开奖数据抓取失败");
				countrySscService.save(countrySsc);
				log.info(GAME_CODE.getSign() + "期数：" + period + countrySsc.getDesc());
				return;
			}
			//抓取到数据
			countrySscService.save(countrySsc);
			log.info(GAME_CODE.getSign() + "期数：" + period + countrySsc.getDesc());
			// 发送mq
			LotteryTool.sendMq(log, period, GAME_CODE, countrySsc.getHandicapCode(), countrySsc.getDrawNumber(),
					countrySsc.getDrawItem(), countrySsc.getDrawTimeLong(), countrySsc.getDesc(), "IDC");
		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据
			//抓取到数据
			if (countrySsc != null) {
				countrySsc.setIdx(entity.getIdx());
				countrySsc.setCloudLotteryCountrySscId(entity.getCloudLotteryCountrySscId());
				countrySsc.setCreateTime(entity.getCreateTime());
				countrySsc.setCreateTimeLong(entity.getCreateTimeLong());
				countrySsc.setUpdateTime(new Date());
				//成功设置抓取信息
				countrySscService.update(countrySsc);
				log.info(GAME_CODE.getSign() + "期数：" + period + "，开奖数据：" + countrySsc.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, countrySsc.getHandicapCode(), countrySsc.getDrawNumber(),
						countrySsc.getDrawItem(), countrySsc.getDrawTimeLong(), countrySsc.getDesc(), "IDC");

			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "IDC开奖数据抓取失败，无需保存抓取数据");
			}
		}
	}

	private CloudLotteryCountrySsc getLotteryCountrySsc(Integer period, int index) throws InterruptedException, ParseException {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "{IDC}开奖数据，期数：【" + period + "】，循环次数超过" + CYCLE + "次");
			return null;
		}
		String handicapUrl = "https://www.858kai.com/gamePeriod/gamePeriodLastQuery.asp";
		Map<String, Object> map = new HashMap<>(1);
		map.put("$ENTITY_JSON$","{\"gameId\":\""+77+"\"}");
		String html = HttpClientTool.doPost(handicapUrl, map);
		//循环抓取数据100次，抓到跳出。
		for (; index < CYCLE; index++) {
			if (StringTool.isEmpty(html)) {
				++index;
				//如果没有抓取到
				if (StringTool.isEmpty(getLotteryCountrySsc(period, index))) {
					Thread.sleep(RandomTool.getInt(500, 1500));
					html = HttpClientTool.doGet(handicapUrl);
				} else {
					log.info(GAME_CODE.getSign() + "{IDC}开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
					return null;
				}
			} else {
				break;
			}
		}
		//html为空则继续抓取
		if (StringTool.isEmpty(html)) {
			index++;
			sleep();
			return getLotteryCountrySsc(period, index);
		}
		JSONObject result = JSONObject.parseObject(html);
		if(StringTool.isEmpty(result.getString("openNum"))){
			//开奖数据为空
			index++;
			sleep();
			return getLotteryCountrySsc(period, index);
		}
		int no = result.getInteger("gamePeriod");
		//期数匹配不上重新抓取
		if (no - period != 0) {
			log.info(GAME_CODE.getSign() + "{IDC}开奖数据，抓取期数为：【" + no + "】，应有期数为：【" + period
					+ "】");
			index++;
			sleep();
			return getLotteryCountrySsc(period, index);
		}


		// 号码
		CloudLotteryCountrySsc countrySsc = new CloudLotteryCountrySsc();
		long drawTime = PeriodIdcTool.COUNTRY_SSC.getLotteryDrawTime();
		countrySsc.setDrawTime(new Date(drawTime));
		countrySsc.setDrawTimeLong(drawTime);
		countrySsc.setHandicapCode("IDC");
		countrySsc.setDrawType("IDC");
		countrySsc.setPeriod(period);
		countrySsc.setDrawNumber(result.getString("openNum").replace("|", ","));
		countrySsc.setDrawItem(PeriodIdcTool.COUNTRY_SSC.getDrawItem(countrySsc.getDrawNumber()));
		countrySsc.setP1Number(result.getInteger("openNum1"));
		countrySsc.setP2Number(result.getInteger("openNum2"));
		countrySsc.setP3Number(result.getInteger("openNum3"));
		countrySsc.setP4Number(result.getInteger("openNum4"));
		countrySsc.setP5Number(result.getInteger("openNum5"));
		countrySsc.setCreateTime(new Date());
		countrySsc.setCreateTimeLong(System.currentTimeMillis());
		countrySsc.setUpdateTimeLong(System.currentTimeMillis());
		countrySsc.setState(StateEnum.OPEN.name());
		countrySsc.setDesc("{IDC}" + GAME_CODE.getGameName() + "开奖数据抓取成功");
		return countrySsc;
	}
}
