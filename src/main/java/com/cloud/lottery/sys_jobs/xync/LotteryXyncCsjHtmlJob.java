package com.cloud.lottery.sys_jobs.xync;

import com.cloud.common.game.Xync;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_xync.entity.CloudLotteryXync;
import com.cloud.lottery.cloud_lottery_xync.service.CloudLotteryXyncService;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.jsoup.HttpJsoupTool;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.util.Date;

/**
 * 抓取彩世界页面XYNC的数据
 *
 * @Author: Dongming
 * @Date: 2020-04-18 14:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryXyncCsjHtmlJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.XYNC;

	private int index = 0;

	public static final int CYCLE = 500;

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.XYNC.getLotteryDrawTime() > Xync.PERIOD) {
			log.info("未到可以开始抓取的时间");
			return;
		}
		String period = PeriodTool.XYNC.findLotteryPeriod();
		log.info(GAME_CODE.getSign() + "期数：" + period + "开始抓取");
		CloudLotteryXync xync = getLotteryXync(period);
		CloudLotteryXyncService xyncService = new CloudLotteryXyncService();
		//如果已经抓取到
		if (StringTool.notEmpty(LotteryTool.getXyncLottery(period))) {
			log.info(GAME_CODE.getSign() + "期数：" + period + "已完成抓取，无需保存");
			return;
		}
		//读取数据库数据
		CloudLotteryXync entity = xyncService.findByPeriod(period);
		//再次校验
		if (StringTool.notEmpty(LotteryTool.getXyncLottery(period))) {
			log.info(GAME_CODE.getSign() + "期数：" + period + "已完成抓取，无需保存");
			return;
		}
		//数据库没有数据
		if (entity == null) {
			if (xync == null) {
				//全无数据
				xync = new CloudLotteryXync();
				xync.setPeriod(period);
				xync.setHandicapCode("IDC");
				xync.setDrawType("CSJ");
				xync.setCreateTime(new Date());
				xync.setCreateTimeLong(System.currentTimeMillis());
				xync.setUpdateTime(new Date());
				xync.setUpdateTimeLong(System.currentTimeMillis());
				xync.setState(StateEnum.CLOSE.name());
				xync.setDesc("彩世界[页面]开奖数据抓取失败");
				xyncService.save(xync);
				log.info(GAME_CODE.getSign() + "期数：" + period + xync.getDesc());
				return;
			}
			//抓取到数据
			if (LotteryTool.setXyncLottery(period, xync.getDrawNumber())) {
				xyncService.save(xync);
				log.info(GAME_CODE.getSign() + "期数：" + period + xync.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, xync.getHandicapCode(), xync.getDrawNumber(),
						xync.getDrawItem(), xync.getDrawTimeLong(), xync.getDesc(),"CSJ");
			}
		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据

			//抓取到数据
			if (xync != null) {
				xync.setIdx(entity.getIdx());
				xync.setCloudLotteryXyncId(entity.getCloudLotteryXyncId());
				xync.setCreateTime(entity.getCreateTime());
				xync.setCreateTimeLong(entity.getCreateTimeLong());
				//成功设置抓取信息
				if (LotteryTool.setXyncLottery(period, xync.getDrawNumber())) {
					xyncService.update(xync);
					log.info(GAME_CODE.getSign() + "期数：" + period + xync.getDesc());
					// 发送mq
					LotteryTool.sendMq(log, period, GAME_CODE, xync.getHandicapCode(), xync.getDrawNumber(),
							xync.getDrawItem(), xync.getDrawTimeLong(), xync.getDesc(),"CSJ");
				}
			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "彩世界[页面]开奖数据抓取失败，无需保存抓取数据");
			}
		}

	}


	private CloudLotteryXync getLotteryXync(String period) throws IOException, InterruptedException {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，期数：【" + period + "】，循环次数超过100次");
			return null;
		}
		String url = LotteryTool.getCsjHost().concat("/xync/");
		Document document = HttpJsoupTool.doGet(url);
		//循环抓取数据100次，抓到跳出。
		for (; index < CYCLE; index++) {
			if (document == null || !document.hasText()) {
				//如果没有抓取到
				if (StringTool.isEmpty(LotteryTool.getXyncLottery(period))) {
					//随机睡眠
					sleep();
					document = HttpJsoupTool.doGet(url);
				} else {
					log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
					return null;
				}
			} else {
				break;
			}
		}
		//其他方法以及抓取到数据
		if (StringTool.notEmpty(LotteryTool.getXyncLottery(period))) {
			log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
			return null;
		}
		if (document == null || !document.hasText()) {
			index++;
			sleep();
			return getLotteryXync(period);
		}
		Elements trList = document.select("#dataContainer>tbody>tr");
		if (trList == null || trList.isEmpty()) {
			if (index < CYCLE / 2) {
				//没有找到元素并且循环次数低于50次
				index++;
				sleep();
				return getLotteryXync(period);
			} else {
				log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，期数：【" + period + "】，数据抓取错误");
				return null;
			}
		}
		//记录字符串
		String text;
		//只分析之后一行数据
		Element firstEle = trList.get(0);

		//期数
		text = firstEle.getElementsByClass("font_gray666").get(0).text();
		//期数匹配不上重新抓取
		if (!text.equals(period)) {
			log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，抓取期数为：【" + text + "】，应有期数为：【" + period + "】");
			index++;
			sleep();
			return getLotteryXync(period);
		}

		CloudLotteryXync xync = new CloudLotteryXync();
		xync.setPeriod(text);
		xync.setHandicapCode("IDC");
		xync.setDrawType("CSJ");
		text = firstEle.getElementsByClass("font_gray999").get(0).text();
		Date drawTime = DateTool.getHm(text);
		xync.setDrawTime(drawTime);
		xync.setDrawTimeLong(drawTime.getTime());
		xync.setHandicapCode("IDC");
		// 号码
	    StringBuilder 	numberText = new StringBuilder();
		Elements numberXync = firstEle.select("div.number_xync>span");
		for (Element element : numberXync) {
            numberText.append(NumberTool.getInteger(element.attr("class").substring(3))).append(",");
		}
		String[] numberArray = numberText.toString().split(",");
		xync.setDrawNumber(StringUtils.join(numberArray, ","));
		xync.setDrawItem(PeriodTool.XYNC.getDrawItem(xync.getDrawNumber()));
		xync.setP1Number(numberArray[0].trim());
		xync.setP2Number(numberArray[1].trim());
		xync.setP3Number(numberArray[2].trim());
		xync.setP4Number(numberArray[3].trim());
		xync.setP5Number(numberArray[4].trim());
		xync.setP6Number(numberArray[5].trim());
		xync.setP7Number(numberArray[6].trim());
		xync.setP8Number(numberArray[7].trim());
		xync.setCreateTime(new Date());
		xync.setCreateTimeLong(System.currentTimeMillis());
		xync.setUpdateTime(new Date());
		xync.setUpdateTimeLong(System.currentTimeMillis());
		xync.setState(StateEnum.OPEN.name());
		xync.setDesc("彩世界[页面]开奖数据抓取成功");
		return xync;
	}
}
