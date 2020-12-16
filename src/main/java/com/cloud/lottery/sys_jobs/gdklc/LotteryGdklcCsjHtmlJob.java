package com.cloud.lottery.sys_jobs.gdklc;

import com.cloud.common.game.Gdklc;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_gdklc.entity.CloudLotteryGdklc;
import com.cloud.lottery.cloud_lottery_gdklc.service.CloudLotteryGdklcService;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.jsoup.HttpJsoupTool;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.util.Date;

/**
 * 抓取彩世界页面的GDKLC数据
 *
 * @Author: Dongming
 * @Date: 2020-04-18 17:57
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryGdklcCsjHtmlJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.GDKLC;

	private int index;
	public static final int CYCLE = 500;

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.GDKLC.getLotteryDrawTime() > Gdklc.PERIOD) {
			log.info("未到可以开始抓取的时间");
			super.saveLog = false;
			return;
		}
		String period = PeriodTool.GDKLC.findLotteryPeriod();
		log.info(GAME_CODE.getSign() + "期数：" + period + "开始抓取");
		CloudLotteryGdklc gdklc = getLotteryGdklc(period);
		CloudLotteryGdklcService gdklcService = new CloudLotteryGdklcService();
		//如果已经抓取到
		if (StringTool.notEmpty(LotteryTool.getGdklcLottery(period))) {
			log.info(GAME_CODE.getSign() + "期数：" + period + "已完成抓取，无需保存");
			return;
		}
		//读取数据库数据
		CloudLotteryGdklc entity = gdklcService.findByPeriod(period);
		//再次校验
		if (StringTool.notEmpty(LotteryTool.getGdklcLottery(period))) {
			log.info(GAME_CODE.getSign() + "期数：" + period + "已完成抓取，无需保存");
			return;
		}
		//数据库没有数据
		if (entity == null) {
			if (gdklc == null) {
				//全无数据
				gdklc = new CloudLotteryGdklc();
				gdklc.setPeriod(period);
				gdklc.setHandicapCode("IDC");
				gdklc.setDrawType("CSJ");
				gdklc.setCreateTime(new Date());
				gdklc.setCreateTimeLong(System.currentTimeMillis());
				gdklc.setUpdateTime(new Date());
				gdklc.setUpdateTimeLong(System.currentTimeMillis());
				gdklc.setState(StateEnum.CLOSE.name());
				gdklc.setDesc("彩世界[页面]开奖数据抓取失败");
				gdklcService.save(gdklc);
				log.info(GAME_CODE.getSign() + "期数：" + period + gdklc.getDesc());
				return;
			}
			//抓取到数据
			if (LotteryTool.setGdklcLottery(period, gdklc.getDrawNumber())) {
				gdklcService.save(gdklc);
				log.info(GAME_CODE.getSign() + "期数：" + period + gdklc.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, gdklc.getHandicapCode(), gdklc.getDrawNumber(),
						gdklc.getDrawItem(), gdklc.getDrawTimeLong(), gdklc.getDesc(),"CSJ");
			}
		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据

			//抓取到数据
			if (gdklc != null) {
				gdklc.setIdx(entity.getIdx());
				gdklc.setCloudLotteryGdklcId(entity.getCloudLotteryGdklcId());
				gdklc.setCreateTime(entity.getCreateTime());
				gdklc.setCreateTimeLong(entity.getCreateTimeLong());
				//成功设置抓取信息
				if (LotteryTool.setGdklcLottery(period, gdklc.getDrawNumber())) {
					gdklcService.update(gdklc);
					log.info(GAME_CODE.getSign() + "期数：" + period + gdklc.getDesc());
					// 发送mq
					LotteryTool.sendMq(log, period, GAME_CODE, gdklc.getHandicapCode(), gdklc.getDrawNumber(),
							gdklc.getDrawItem(), gdklc.getDrawTimeLong(), gdklc.getDesc(),"CSJ");
				}
			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "彩世界[页面]开奖数据抓取失败，无需保存抓取数据");
			}
		}
	}

	private CloudLotteryGdklc getLotteryGdklc(String period) throws IOException, InterruptedException {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，期数：【" + period + "】，循环次数超过100次");
			return null;
		}
		String url = LotteryTool.getCsjHost().concat("/gdkl10/");
		Document document = HttpJsoupTool.doGet(url);
		//循环抓取数据100次，抓到跳出。
		for (; index < CYCLE; index++) {
			if (document == null || !document.hasText()) {
				//如果没有抓取到
				if (StringTool.isEmpty(LotteryTool.getGdklcLottery(period))) {
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
		if (StringTool.notEmpty(LotteryTool.getGdklcLottery(period))) {
			log.info(GAME_CODE.getSign() + "彩世界[页面]开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
			return null;
		}
		if (document == null || !document.hasText()) {
			index++;
			sleep();
			return getLotteryGdklc(period);
		}
		Elements trList = document.select("#dataContainer>tbody>tr");
		if (trList == null || trList.isEmpty()) {
			if (index < CYCLE/2) {
				//没有找到元素并且循环次数低于50次
				index++;
				sleep();
				return getLotteryGdklc(period);
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
			return getLotteryGdklc(period);
		}
		CloudLotteryGdklc gdklc = new CloudLotteryGdklc();
		gdklc.setPeriod(text);
		text = firstEle.getElementsByClass("font_gray999").get(0).text();
		Date drawTime = DateTool.getHm(text);
		gdklc.setDrawTime(drawTime);
		gdklc.setDrawTimeLong(drawTime.getTime());
		text = firstEle.getElementsByClass("number_redAndBlue").get(0).text();
		String[] numberArray = text.split(" ");
        String numbers= StringUtils.join(numberArray, ",");
        if (numbers.startsWith("0")){
            numbers = numbers.substring(1);
        }
        numbers = numbers.replace(",0",",");

		gdklc.setHandicapCode("IDC");
		gdklc.setDrawType("CSJ");
		gdklc.setDrawNumber(numbers);
		gdklc.setDrawItem(PeriodTool.GDKLC.getDrawItem(gdklc.getDrawNumber()));
		gdklc.setP1Number(numberArray[0].trim());
		gdklc.setP2Number(numberArray[1].trim());
		gdklc.setP3Number(numberArray[2].trim());
		gdklc.setP4Number(numberArray[3].trim());
		gdklc.setP5Number(numberArray[4].trim());
		gdklc.setP6Number(numberArray[5].trim());
		gdklc.setP7Number(numberArray[6].trim());
		gdklc.setP8Number(numberArray[7].trim());
		gdklc.setCreateTime(new Date());
		gdklc.setCreateTimeLong(System.currentTimeMillis());
		gdklc.setUpdateTime(new Date());
		gdklc.setUpdateTimeLong(System.currentTimeMillis());
		gdklc.setState(StateEnum.OPEN.name());
		gdklc.setDesc("彩世界[页面]开奖数据抓取成功");

		return gdklc;
	}
}
