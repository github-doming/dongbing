package com.cloud.lottery.sys_jobs.js10;

import com.cloud.common.game.idc.Js10Idc;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodIdcTool;
import com.cloud.lottery.cloud_lottery_js10.entity.CloudLotteryJs10;
import com.cloud.lottery.cloud_lottery_js10.service.CloudLotteryJs10Service;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;

import java.text.ParseException;
import java.util.Date;

/**
 * @Description: IDC盘口开奖抓取 备用线路
 * @Author: Dongming
 * @Date: 2019-10-08 17:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryJs10IdcSpareJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.JS10;
	public static final int CYCLE = 100;

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodIdcTool.JS10.getLotteryDrawTime() > Js10Idc.PERIOD) {
			saveLog = false;
			log.info("未到可以开始抓取的时间");
			return;
		}
		if (System.currentTimeMillis() - PeriodIdcTool.JS10.getLotteryDrawTime() >= 25000L) {
			log.info("跳过循环");
			saveLog = false;
			return;
		}
		long period = PeriodIdcTool.JS10.findLotteryPeriod();
		CloudLotteryJs10 js10 = getLotteryJs10(period, 0);
		CloudLotteryJs10Service js10Service = new CloudLotteryJs10Service();
		//读取数据库数据
		CurrentTransaction.transactionCommit();
		CloudLotteryJs10 entity = js10Service.findByPeriod(period, "IDC");
		//数据库没有数据
		if (entity == null) {
			if (js10 == null) {
				//全无数据
				js10 = new CloudLotteryJs10();
				js10.setPeriod(period);
				js10.setCreateTime(new Date());
				js10.setCreateTimeLong(System.currentTimeMillis());
				js10.setUpdateTimeLong(System.currentTimeMillis());
				js10.setState(StateEnum.CLOSE.name());
				js10.setDesc("{IDC}急速赛车开奖数据抓取失败");
				js10Service.save(js10);
				log.info(GAME_CODE.getSign() + "期数：" + period + js10.getDesc());
				return;
			}
			//抓取到数据
			js10Service.save(js10);
			log.info(GAME_CODE.getSign() + "期数：" + period + js10.getDesc());
			// 发送mq
			LotteryTool.sendMq(log, period, GAME_CODE, js10.getHandicapCode(), js10.getDrawNumber(), js10.getDrawItem(),
					js10.getDrawTimeLong(), js10.getDesc(), "CSJ");

		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据
			//抓取到数据
			if (js10 != null) {
				js10.setIdx(entity.getIdx());
				js10.setCloudLotteryJs10Id(entity.getCloudLotteryJs10Id());
				js10.setCreateTime(entity.getCreateTime());
				js10.setCreateTimeLong(entity.getCreateTimeLong());
				js10.setUpdateTime(new Date());
				//成功设置抓取信息
				js10Service.update(js10);
				log.info(GAME_CODE.getSign() + "期数：" + period + "，开奖数据：" + js10.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, js10.getHandicapCode(), js10.getDrawNumber(),
						js10.getDrawItem(), js10.getDrawTimeLong(), js10.getDesc(), "CSJ");
			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "彩世界[页面]开奖数据抓取失败，无需保存抓取数据");
			}
		}
	}

	/**
	 * 获取急速赛车开奖实体
	 *
	 * @param period 期数
	 * @return 急速赛车开奖实体
	 */
	private CloudLotteryJs10 getLotteryJs10(long period, int index) throws InterruptedException, ParseException {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "{IDC}开奖数据，期数：【" + period + "】，循环次数超过" + CYCLE + "次");
			return null;
		}

		String handicapUrl = "https://speedylottos.com/speedy10-result.php";
		String html = HttpClientTool.doGet(handicapUrl);
		//循环抓取数据100次，抓到跳出。
		for (; index < CYCLE; index++) {
			if (StringTool.isEmpty(html)) {
				++index;
				//如果没有抓取到
				if (StringTool.isEmpty(getLotteryJs10(period, index))) {
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
			return getLotteryJs10(period, index);
		}
		Document document = Jsoup.parse(html);

		Element el = document.getElementsByClass("txtbold").first();
		int no = NumberTool.getInteger(el.text().split(",")[0]);
		//期数匹配不上重新抓取
		if (no - period != 0) {
			log.info(GAME_CODE.getSign() + "{IDC}开奖数据，抓取期数为：【" + no + "】，应有期数为：【" + period
					+ "】");
			index++;
			sleep();
			return getLotteryJs10(period, index);
		}
		Elements divs = document.getElementsByClass("resultnum2");

		Integer[] drawNumbers = new Integer[divs.size()];
		for (int i = 0; i < divs.size(); i++) {
			drawNumbers[i] = Integer.parseInt(divs.get(i).text());
		}
		// 号码
		CloudLotteryJs10 js10 = new CloudLotteryJs10();
		long drawTime = PeriodIdcTool.JS10.getLotteryDrawTime();
		js10.setDrawTime(new Date(drawTime));
		js10.setDrawTimeLong(drawTime);
		js10.setHandicapCode("IDC");
		js10.setDrawType("CSJ");
		js10.setPeriod(period);
		js10.setDrawNumber(StringUtils.join(drawNumbers, ","));
		js10.setDrawItem(PeriodIdcTool.JS10.getDrawItem(js10.getDrawNumber()));
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
		js10.setUpdateTimeLong(System.currentTimeMillis());
		js10.setState(StateEnum.OPEN.name());
		js10.setDesc("{IDC}急速赛车开奖数据抓取成功");
		return js10;

	}

}
