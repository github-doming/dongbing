package com.cloud.lottery.sys_jobs.cqssj;

import com.cloud.common.game.Cqssc;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_cqssc.t.entity.CloudLotteryCqsscT;
import com.cloud.lottery.cloud_lottery_cqssc.t.service.CloudLotteryCqsscTService;
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
 * @Description: 抓取彩世界页面Cqssj的数据
 * @Author: Dongming
 * @Date: 2019-01-19 10:20
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryCqsscCsjHtmlJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.CQSSC;

	private int index = 0;
	public static final int CYCLE = 500;

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {

		if (System.currentTimeMillis() - PeriodTool.CQSSC.getLotteryDrawTime() > Cqssc.PERIOD) {
			log.info("未到可以开始抓取的时间");
			super.saveLog = false;
			return;
		}
		String period = PeriodTool.CQSSC.findLotteryPeriod();

		CloudLotteryCqsscT cqsscT = getLotteryCqsscT(period);
		CloudLotteryCqsscTService cqsscTService = new CloudLotteryCqsscTService();
		//如果已经抓取到
		if (StringTool.notEmpty(LotteryTool.getCqsscLottery(period))) {
			return;
		}
		//读取数据库数据
		CloudLotteryCqsscT entity = cqsscTService.findByPeriod(period);
		//再次校验
		if (StringTool.notEmpty(LotteryTool.getCqsscLottery(period))) {
			return;
		}
		//数据库没有数据
		if (entity == null) {
			if (cqsscT == null) {
				//全无数据
				cqsscT = new CloudLotteryCqsscT();
				cqsscT.setPeriod(period);
				cqsscT.setCreateTime(new Date());
				cqsscT.setCreateTimeLong(cqsscT.getCreateTime().getTime());
				cqsscT.setUpdateTime(new Date());
				cqsscT.setUpdateTimeLong(cqsscT.getUpdateTime().getTime());
				cqsscT.setState(StateEnum.CLOSE.name());
				cqsscT.setDesc("彩世界[页面]开奖数据抓取失败");
				cqsscTService.save(cqsscT);
				log.info(GAME_CODE.getSign()+ "期数：" + period + cqsscT.getDesc());
				return;
			}
			//抓取到数据
			if (LotteryTool.setCqsscLottery(period, cqsscT.getDrawNumber())) {
				cqsscTService.save(cqsscT);
				log.info(GAME_CODE.getSign() + "期数：" + period + cqsscT.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, "IDC", cqsscT.getDrawNumber(), cqsscT.getDrawItem(),
						cqsscT.getDrawTimeLong(), cqsscT.getDesc(),"CSJ");
			}
		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据

			//抓取到数据
			if (cqsscT != null) {
				cqsscT.setIdx(entity.getIdx());
				cqsscT.setCloudLotteryCqsscId(entity.getCloudLotteryCqsscId());
				cqsscT.setCreateTime(entity.getCreateTime());
				cqsscT.setCreateTimeLong(entity.getCreateTimeLong());
				//成功设置抓取信息
				if (LotteryTool.setCqsscLottery(period, cqsscT.getDrawNumber())) {
					cqsscTService.update(cqsscT);
					log.info(GAME_CODE.getSign() + "期数：" + period + cqsscT.getDesc());
					// 发送mq
					LotteryTool.sendMq(log, period, GAME_CODE, "IDC", cqsscT.getDrawNumber(), cqsscT.getDrawItem(),
							cqsscT.getDrawTimeLong(), cqsscT.getDesc(),"CSJ");
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
	private CloudLotteryCqsscT getLotteryCqsscT(String period) throws InterruptedException, IOException {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，期数：【" + period + "】，循环次数超过100次");
			return null;
		}
		String url = LotteryTool.getCsjHost().concat("/shishicai/");
		Document document = HttpJsoupTool.doGet(url);

		//循环抓取数据20次，抓到跳出
		for (int i = 0; i < CYCLE; i++) {
			if (document == null || !document.hasText()) {
				//如果没有抓取到
				if (StringTool.isEmpty(LotteryTool.getCqsscLottery(period))) {
					Thread.sleep(RandomTool.getInt(500, 1500));
					document = HttpJsoupTool.doGet(url);
				} else {
					log.info(GAME_CODE.getSign()+ "彩世界[页面]开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
					return null;
				}
			} else {
				break;
			}
		}

		//其他方法以及抓取到数据
		if (StringTool.notEmpty(LotteryTool.getCqsscLottery(period))) {
			log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
			return null;
		}

		//html为空则 睡眠后 继续抓取
		if (document == null || !document.hasText()) {
			Thread.sleep(RandomTool.getInt(500 + 5 * index, 1500 + 5 * index));
			index++;
			return getLotteryCqsscT(period);
		}
		Elements trList = document.select("#tbHistory>tbody>tr");

		//没有找到元素并且循环次数低于   可循环次数/5
		if (trList == null || trList.isEmpty()) {
			if (index < CYCLE / 5) {
				Thread.sleep(RandomTool.getInt(500 + 5 * index, 1500 + 5 * index));
				index++;
				return getLotteryCqsscT(period);
			} else {
				log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，期数：【" + period + "】，数据抓取错误");
				return null;
			}
		}

		//记录字符串
		String text;
		//只分析之后一行数据
		Element firstEle = trList.get(1);

		//期数
		text = firstEle.getElementsByClass("font_gray666").get(0).text();

		//期数匹配不上重新抓取
		if (!text.equals(period)) {
			log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，抓取期数为：【" + text + "】，应有期数为：【" + period + "】");
			Thread.sleep(RandomTool.getInt(500 + 5 * index, 1500 + 5 * index));
			index++;
			return getLotteryCqsscT(period);
		}


		CloudLotteryCqsscT cqsscT = new CloudLotteryCqsscT();
		cqsscT.setPeriod(text);
		//开奖时间
		text = firstEle.getElementsByClass("font_gray999").get(0).text();

		cqsscT.setDrawTime(DateTool.getHm(text));
		cqsscT.setDrawTimeLong(cqsscT.getDrawTime().getTime());

		// 号码
		text = firstEle.getElementsByClass("number_redAndBlue").get(0).text();
		String[] numberArray = text.split(" ");
		cqsscT.setDrawType("CSJ");
		cqsscT.setDrawNumber(StringUtils.join(numberArray, ","));
		cqsscT.setDrawItem(PeriodTool.CQSSC.getDrawItem(cqsscT.getDrawNumber()));
		cqsscT.setP1Number(numberArray[0].trim());
		cqsscT.setP2Number(numberArray[1].trim());
		cqsscT.setP3Number(numberArray[2].trim());
		cqsscT.setP4Number(numberArray[3].trim());
		cqsscT.setP5Number(numberArray[4].trim());
		cqsscT.setCreateTime(new Date());
		cqsscT.setCreateTimeLong(cqsscT.getCreateTime().getTime());
		cqsscT.setUpdateTime(new Date());
		cqsscT.setUpdateTimeLong(cqsscT.getUpdateTime().getTime());
		cqsscT.setState(StateEnum.OPEN.name());
		cqsscT.setDesc("彩世界[页面]开奖数据抓取成功");
		return cqsscT;
	}

}
