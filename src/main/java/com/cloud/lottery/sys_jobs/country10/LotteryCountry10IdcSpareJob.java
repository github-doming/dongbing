package com.cloud.lottery.sys_jobs.country10;

import com.cloud.common.game.idc.AustraliaLucky10;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodIdcTool;
import com.cloud.lottery.cloud_lottery_country_10.entity.CloudLotteryCountry10;
import com.cloud.lottery.cloud_lottery_country_10.service.CloudLotteryCountry10Service;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.quartz.JobExecutionContext;

import java.text.ParseException;
import java.util.Date;

/**
 * IDC 澳洲幸运10 国家赛车
 * 备用线路
 *
 * @Author: Dongming
 * @Date: 2020-04-30 14:17
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryCountry10IdcSpareJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.COUNTRY_10;

	public static final int CYCLE = 100;

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodIdcTool.COUNTRY_10.getLotteryDrawTime()
				> AustraliaLucky10.PERIOD) {
			super.saveLog = false;
			log.trace("未到可以开始抓取的时间");
			return;
		}
		Integer period = PeriodIdcTool.COUNTRY_10.findLotteryPeriod();
		CloudLotteryCountry10 country10 = getLotteryCountry10(period, 0);
		CloudLotteryCountry10Service country10Service = new CloudLotteryCountry10Service();
		//读取数据库数据
		CloudLotteryCountry10 entity = country10Service.findByPeriod(period);
		//数据库没有数据
		if (entity == null) {
			if (country10 == null) {
				//全无数据
				country10 = new CloudLotteryCountry10();
				country10.setPeriod(period);
				country10.setHandicapCode("IDC");
				country10.setDrawType("IDC");
				country10.setCreateTime(new Date());
				country10.setCreateTimeLong(System.currentTimeMillis());
				country10.setUpdateTimeLong(System.currentTimeMillis());
				country10.setState(StateEnum.CLOSE.name());
				country10.setDesc("{IDC}" + GAME_CODE.getGameName() + "开奖数据抓取失败");
				country10Service.save(country10);
				log.info(GAME_CODE.getSign() + "期数：" + period + country10.getDesc());
				return;
			}
			//抓取到数据
			country10Service.save(country10);
			log.info(GAME_CODE.getSign() + "期数：" + period + country10.getDesc());
			// 发送mq
			LotteryTool.sendMq(log, period, GAME_CODE, country10.getHandicapCode(), country10.getDrawNumber(),
					country10.getDrawItem(), country10.getDrawTimeLong(), country10.getDesc(), "IDC");
		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据
			//抓取到数据
			if (country10 != null) {
				country10.setIdx(entity.getIdx());
				country10.setCloudLotteryCountry10Id(entity.getCloudLotteryCountry10Id());
				country10.setCreateTime(entity.getCreateTime());
				country10.setCreateTimeLong(entity.getCreateTimeLong());
				country10.setUpdateTime(new Date());
				//成功设置抓取信息
				country10Service.update(country10);
				log.info(GAME_CODE.getSign() + "期数：" + period + "，开奖数据：" + country10.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, country10.getHandicapCode(), country10.getDrawNumber(),
						country10.getDrawItem(), country10.getDrawTimeLong(), country10.getDesc(), "IDC");

			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "IDC开奖数据抓取失败，无需保存抓取数据");
			}
		}

	}

	private CloudLotteryCountry10 getLotteryCountry10(Integer period, int index) throws InterruptedException, ParseException {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "{IDC}开奖数据，期数：【" + period + "】，循环次数超过" + CYCLE + "次");
			return null;
		}
		String handicapUrl = "https://auluckylottery.com/results/lucky-ball-10";
		String html = HttpClientTool.doGet(handicapUrl);
		//循环抓取数据100次，抓到跳出。
		for (; index < CYCLE; index++) {
			if (StringTool.isEmpty(html)) {
				++index;
				//如果没有抓取到
				if (StringTool.isEmpty(getLotteryCountry10(period, index))) {
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
			return getLotteryCountry10(period, index);
		}
		Document document;
		try {
			document = Jsoup.parse(html);
		} catch (Exception e) {
			index++;
			sleep();
			log.error(e + " 获取的html是：" + html);
			return getLotteryCountry10(period, index);
		}
		Element el = document.getElementsByClass("brt2f_2").first();
		int no = NumberTool.getInteger(el.text().split(" ")[1]);
		//期数匹配不上重新抓取
		if (no - period != 0) {
			log.info(GAME_CODE.getSign() + "{IDC}开奖数据，抓取期数为：【" + no + "】，应有期数为：【" + period
					+ "】");
			index++;
			sleep();
			return getLotteryCountry10(period, index);
		}

		el = document.getElementsByClass("brt2_ball").first();
		String[] drawNumbers = el.text().split(" ");
		// 号码
		CloudLotteryCountry10 country10 = new CloudLotteryCountry10();
		long drawTime = PeriodIdcTool.COUNTRY_10.getLotteryDrawTime();
		country10.setDrawTime(new Date(drawTime));
		country10.setDrawTimeLong(drawTime);
		country10.setHandicapCode("IDC");
		country10.setDrawType("IDC");
		country10.setPeriod(period);
		country10.setDrawNumber(StringUtils.join(drawNumbers, ","));
		country10.setDrawItem(PeriodIdcTool.COUNTRY_10.getDrawItem(country10.getDrawNumber()));
		country10.setP1Number(drawNumbers[0]);
		country10.setP2Number(drawNumbers[1]);
		country10.setP3Number(drawNumbers[2]);
		country10.setP4Number(drawNumbers[3]);
		country10.setP5Number(drawNumbers[4]);
		country10.setP6Number(drawNumbers[5]);
		country10.setP7Number(drawNumbers[6]);
		country10.setP8Number(drawNumbers[7]);
		country10.setP9Number(drawNumbers[8]);
		country10.setP10Number(drawNumbers[9]);
		country10.setCreateTime(new Date());
		country10.setCreateTimeLong(System.currentTimeMillis());
		country10.setUpdateTimeLong(System.currentTimeMillis());
		country10.setState(StateEnum.OPEN.name());
		country10.setDesc("{IDC}" + GAME_CODE.getGameName() + "开奖数据抓取成功");
		return country10;

	}
}
