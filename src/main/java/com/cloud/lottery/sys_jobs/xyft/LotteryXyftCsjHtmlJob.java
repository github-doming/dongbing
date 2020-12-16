package com.cloud.lottery.sys_jobs.xyft;

import com.cloud.common.game.Xyft;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_xyft.t.entity.CloudLotteryXyftT;
import com.cloud.lottery.cloud_lottery_xyft.t.service.CloudLotteryXyftTService;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.jsoup.HttpJsoupTool;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.util.Date;
/**
 * @Description: 抓取彩世界页面XYFT的数据
 * @Author: Dongming
 * @Date: 2019-01-19 10:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryXyftCsjHtmlJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.XYFT;

	private int index = 0;
	public static final int CYCLE = 200;

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.XYFT.getLotteryDrawTime() > Xyft.PERIOD) {
			log.info("未到可以开始抓取的时间");
			return;
		}
		String period = PeriodTool.XYFT.findLotteryPeriod();

		CloudLotteryXyftT xyftT = getLotteryXyftT(period);
		CloudLotteryXyftTService xyftTService = new CloudLotteryXyftTService();
		//如果已经抓取到
		if (StringTool.notEmpty(LotteryTool.getXyftLottery(period))) {
			log.info(GAME_CODE.getSign() + "期数：" + period + "已完成抓取，无需保存");
			return;
		}
		//读取数据库数据
		CloudLotteryXyftT entity = xyftTService.findByPeriod(period, "IDC");
		//再次校验
		if (StringTool.notEmpty(LotteryTool.getXyftLottery(period))) {
			log.info(GAME_CODE.getSign() + "期数：" + period + "已完成抓取，无需保存");
			return;
		}
		//数据库没有数据
		if (entity == null) {
			if (xyftT == null) {
				//全无数据
				xyftT = new CloudLotteryXyftT();
				xyftT.setPeriod(period);
				xyftT.setHandicapCode("IDC");
				xyftT.setDrawType("CSJ");
				xyftT.setCreateTime(new Date());
				xyftT.setCreateTimeLong(xyftT.getCreateTime().getTime());
				xyftT.setUpdateTime(new Date());
				xyftT.setUpdateTimeLong(xyftT.getUpdateTime().getTime());
				xyftT.setState(StateEnum.CLOSE.name());
				xyftT.setDesc("彩世界[页面]开奖数据抓取失败");
				xyftTService.save(xyftT);
				log.info(GAME_CODE.getSign() + "期数：" + period + xyftT.getDesc());
				return;
			}
			//抓取到数据
			if (LotteryTool.setXyftLottery(period, xyftT.getDrawNumber())) {
				xyftTService.save(xyftT);
				log.info(GAME_CODE.getSign() + "期数：" + period + xyftT.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, xyftT.getHandicapCode(), xyftT.getDrawNumber(),
						xyftT.getDrawItem(), xyftT.getDrawTimeLong(), xyftT.getDesc(),"CSJ");
			}
		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据

			//抓取到数据
			if (xyftT != null) {
				xyftT.setIdx(entity.getIdx());
				xyftT.setCloudLotteryXyftId(entity.getCloudLotteryXyftId());
				xyftT.setCreateTime(entity.getCreateTime());
				xyftT.setCreateTimeLong(entity.getCreateTimeLong());
				//成功设置抓取信息
				if (LotteryTool.setXyftLottery(period, xyftT.getDrawNumber())) {
					xyftTService.update(xyftT);
					log.info(GAME_CODE.getSign() + "期数：" + period + xyftT.getDesc());
					// 发送mq
					LotteryTool.sendMq(log, period, GAME_CODE, xyftT.getHandicapCode(), xyftT.getDrawNumber(),
							xyftT.getDrawItem(), xyftT.getDrawTimeLong(), xyftT.getDesc(),"CSJ");
				}
			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "彩世界[页面]开奖数据抓取失败，无需保存抓取数据");
			}
		}

	}

	/**
	 * 获取
	 *
	 * @param period 期数
	 * @return 开奖数据
	 */
	private CloudLotteryXyftT getLotteryXyftT(String period) throws InterruptedException, IOException {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，期数：【" + period + "】，循环次数超过100次");
			return null;
		}
		String url = LotteryTool.getCsjHost().concat("/xyft/");
		Document document = HttpJsoupTool.doGet(url);
		//循环抓取数据100次，抓到跳出。
		for (; index < CYCLE; index++) {
			if (document == null || !document.hasText()) {
				//如果没有抓取到
				if (StringTool.isEmpty(LotteryTool.getXyftLottery(period))) {
					Thread.sleep(RandomTool.getInt(500, 1500));
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
		if (StringTool.notEmpty(LotteryTool.getXyftLottery(period))) {
			log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
			return null;
		}
		if (document == null || !document.hasText()) {
			index++;
			sleep();
			return getLotteryXyftT(period);
		}

		Elements trList = document.select("#history>tbody>tr");
		if (trList == null || trList.isEmpty()) {
			if (index < CYCLE / 2) {
				//没有找到元素并且循环次数低于50次
				index++;
				sleep();
				return getLotteryXyftT(period);
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
			return getLotteryXyftT(period);
		}

		CloudLotteryXyftT xyftT = new CloudLotteryXyftT();
		xyftT.setPeriod(text);
		xyftT.setHandicapCode("IDC");
		xyftT.setDrawType("CSJ");
		text = firstEle.getElementsByClass("font_gray999").get(0).text();

		Date drawTime = DateTool.getHm(text);
		xyftT.setDrawTime(drawTime);
		xyftT.setDrawTimeLong(drawTime.getTime());
		// 号码
		text = firstEle.getElementsByClass("number_pk10").get(0).text();
		String[] numberArray = text.split(" ");

		xyftT.setDrawNumber(StringUtils.join(numberArray, ","));
		xyftT.setDrawItem(PeriodTool.XYFT.getDrawItem(xyftT.getDrawNumber()));
		xyftT.setP1Number(numberArray[0].trim());
		xyftT.setP2Number(numberArray[1].trim());
		xyftT.setP3Number(numberArray[2].trim());
		xyftT.setP4Number(numberArray[3].trim());
		xyftT.setP5Number(numberArray[4].trim());
		xyftT.setP6Number(numberArray[5].trim());
		xyftT.setP7Number(numberArray[6].trim());
		xyftT.setP8Number(numberArray[7].trim());
		xyftT.setP9Number(numberArray[8].trim());
		xyftT.setP10Number(numberArray[9].trim());
		xyftT.setCreateTime(new Date());
		xyftT.setCreateTimeLong(xyftT.getCreateTime().getTime());
		xyftT.setUpdateTime(new Date());
		xyftT.setUpdateTimeLong(xyftT.getUpdateTime().getTime());
		xyftT.setState(StateEnum.OPEN.name());
		xyftT.setDesc("彩世界[页面]开奖数据抓取成功");

		return xyftT;
	}

}
