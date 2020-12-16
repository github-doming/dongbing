package com.cloud.lottery.sys_jobs.xyft;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.game.Xyft;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_xyft.t.entity.CloudLotteryXyftT;
import com.cloud.lottery.cloud_lottery_xyft.t.service.CloudLotteryXyftTService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.quartz.JobExecutionContext;

import java.util.Date;

/**
 * @Description: 抓取彩世界接口的XYFT数据
 * @Author: Dongming
 * @Date: 2019年1月23日18:26:39
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryXyftCsjJsonJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.XYFT;

	private int index;
	public static final int CYCLE = 200;

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.XYFT.getLotteryDrawTime() > Xyft.PERIOD) {
			log.info("未到可以开始抓取的时间");
			return;
		}
		String period = PeriodTool.XYFT.findLotteryPeriod();
		log.info(GAME_CODE.getSign() + "期数：" + period + "开始抓取");
		//获取代理ip信息
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
				xyftT.setDesc("彩世界[接口]开奖数据抓取失败");
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
				log.info(GAME_CODE.getSign() + "期数：" + period + "彩世界[接口]开奖数据抓取失败，无需保存抓取数据");
			}
		}

	}
	/**
	 * 抓取Xyft开奖数据
	 *
	 * @param period 期数
	 * @return 抓取结果
	 */
	private CloudLotteryXyftT getLotteryXyftT(String period) throws Exception {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，期数：【" + period + "】，循环次数超过100次");
			return null;
		}
		String url = LotteryTool.getCsjHost().concat("/xyft/getawarddata");
		String param = "r=" + System.currentTimeMillis();
		String html = HttpClientTool.doGet(url, param);

		//循环抓取数据100次，抓到跳出。
		for (; index < CYCLE; index++) {
			if (StringTool.isEmpty(html)) {
				//如果没有抓取到
				if (StringTool.isEmpty(LotteryTool.getXyftLottery(period))) {
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
		if (StringTool.notEmpty(LotteryTool.getXyftLottery(period))) {
			log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
			return null;
		}
		//html为空则继续抓取
		if (StringTool.isEmpty(html)) {
			index++;
			sleep();
			return getLotteryXyftT(period);
		}
		JSONObject jsonObject;
		try {
			jsonObject = JSONObject.parseObject(html);
		} catch (JSONException e) {
			index++;
			sleep();
			log.error(e + " 获取的html是：" + html);
			return getLotteryXyftT(period);
		}
		Object obj = jsonObject.get("current");
		//数据没有读取完
		if (obj == null) {
			if (index < CYCLE / 2) {
				//没有找到元素并且循环次数低于50次
				index++;
				sleep();
				return getLotteryXyftT(period);
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
			return getLotteryXyftT(period);
		}

		CloudLotteryXyftT xyftT = new CloudLotteryXyftT();
		//期数
		xyftT.setPeriod(period);
		xyftT.setHandicapCode("IDC");
		xyftT.setDrawType("CSJ");
		//开奖时间
		xyftT.setDrawTime(DateTool.get(jObj.getString("awardTime")));
		xyftT.setDrawTimeLong(xyftT.getDrawTime().getTime());
		// 号码
		String numberStr = jObj.getString("result");
		String[] numberArray = numberStr.split(",");
		xyftT.setDrawNumber(numberStr);
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
		xyftT.setDesc("彩世界[接口]开奖数据抓取成功");
		return xyftT;
	}

}
