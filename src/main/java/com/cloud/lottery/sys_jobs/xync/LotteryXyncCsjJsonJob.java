package com.cloud.lottery.sys_jobs.xync;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.game.Xync;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_xync.entity.CloudLotteryXync;
import com.cloud.lottery.cloud_lottery_xync.service.CloudLotteryXyncService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * 抓取彩世界接口的XYNC数据
 *
 * @Author: Dongming
 * @Date: 2020-04-18 14:03
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryXyncCsjJsonJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.XYNC;

	private int index;
	public static final int CYCLE = 500;

	@Override public void executeJob(JobExecutionContext context) throws Exception {
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
				xync.setDesc("彩世界[接口]开奖数据抓取失败");
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
				log.info(GAME_CODE.getSign() + "期数：" + period + "彩世界[接口]开奖数据抓取失败，无需保存抓取数据");
			}
		}
	}
	private CloudLotteryXync getLotteryXync(String period) throws IOException, InterruptedException, ParseException {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，期数：【" + period + "】，循环次数超过100次");
			return null;
		}
		String url = LotteryTool.getCsjHost().concat("/xync/getawarddata");
		String param = "r=" + System.currentTimeMillis();
		String html = HttpClientTool.doGet(url, param);

		//循环抓取数据100次，抓到跳出。
		for (; index < CYCLE; index++) {
			if (StringTool.isEmpty(html)) {
				//如果没有抓取到
				if (StringTool.isEmpty(LotteryTool.getXyncLottery(period))) {
					//随机睡眠
					sleep();
					html = HttpClientTool.doGet(url, param);
				} else {
					log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
					return null;
				}
			} else {
				break;
			}
		}
		//其他方法以及抓取到数据
		if (StringTool.notEmpty(LotteryTool.getXyncLottery(period))) {
			log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
			return null;
		}
		//html为空则继续抓取
		if (StringTool.isEmpty(html)) {
			index++;
			//随机睡眠
			sleep();
			return getLotteryXync(period);
		}
		JSONObject jsonObject;
		try {
			jsonObject = JSONObject.parseObject(html);
		} catch (JSONException e) {
			index++;
			log.error(e + " 获取的html是：" + html);
			sleep();
			return getLotteryXync(period);
		}
		Object obj = jsonObject.get("current");
		//数据没有读取完
		if (obj == null) {
			if (index < CYCLE / 2) {
				//没有找到元素并且循环次数低于50次
				index++;
				sleep();
				return getLotteryXync(period);
			} else {
				log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，期数：【" + period + "】，数据抓取错误");
				return null;
			}
		}
		JSONObject jObj = (JSONObject) obj;
		//期数匹配不上重新抓取
		if (!PeriodTool.equals(period.split("-")[1], jObj.getString("period"))) {
			log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，抓取期数为：【" + jObj.getString("period") + "】，应有期数为：【" + period
					+ "】");
			index++;
			sleep();
			return getLotteryXync(period);
		}

		CloudLotteryXync xync = new CloudLotteryXync();
		//期数
		xync.setPeriod(period);
		//开奖时间
		xync.setDrawTime(DateTool.get(jObj.getString("awardTime")));
		xync.setDrawTimeLong(xync.getDrawTime().getTime());
		// 号码
		String numberStr = jObj.getString("result");
		if (numberStr.startsWith("0")) {
			numberStr = numberStr.substring(1);
		}
		numberStr = numberStr.replace(",0", ",");

		String[] numberArray = numberStr.split(",");
		xync.setDrawNumber(numberStr);
		xync.setDrawItem(PeriodTool.XYNC.getDrawItem(xync.getDrawNumber()));
		xync.setHandicapCode("IDC");
		xync.setDrawType("CSJ");
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
		xync.setDesc("彩世界[接口]开奖数据抓取成功");
		return xync;

	}
}
