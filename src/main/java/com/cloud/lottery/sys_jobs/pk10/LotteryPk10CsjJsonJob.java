package com.cloud.lottery.sys_jobs.pk10;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.game.Pk10;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_pk10.t.entity.CloudLotteryPk10T;
import com.cloud.lottery.cloud_lottery_pk10.t.service.CloudLotteryPk10TService;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.quartz.JobExecutionContext;

import java.util.Date;

/**
 * @Description: 抓取彩世界接口的PK10数据
 * @Author: Dongming
 * @Date: 2019年1月19日16:05:21
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryPk10CsjJsonJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.PK10;

	private int index;
	public static final int CYCLE = 500;

	@Override public void executeJob(JobExecutionContext context) throws Exception {

		if (System.currentTimeMillis() - PeriodTool.PK10.getLotteryDrawTime() > Pk10.PERIOD) {
			log.info("未到可以开始抓取的时间");
			super.saveLog = false;
			return;
		}
		int period = PeriodTool.PK10.findLotteryPeriod();

		//获取代理ip信息

		CloudLotteryPk10T pk10T = getLotteryPk10T(period);
		CloudLotteryPk10TService pk10TService = new CloudLotteryPk10TService();

		//如果已经抓取到
		if (StringTool.notEmpty(LotteryTool.getPk10Lottery(period))) {
			return;
		}
		//读取数据库数据
		CloudLotteryPk10T entity = pk10TService.findByPeriod(period);
		//再次校验
		if (StringTool.notEmpty(LotteryTool.getPk10Lottery(period))) {
			return;
		}
		//数据库没有数据
		if (entity == null) {
			if (pk10T == null) {
				//全无数据
				pk10T = new CloudLotteryPk10T();
				pk10T.setPeriod(period);
				pk10T.setCreateTime(new Date());
				pk10T.setCreateTimeLong(pk10T.getCreateTime().getTime());
				pk10T.setUpdateTime(new Date());
				pk10T.setUpdateTimeLong(pk10T.getUpdateTime().getTime());
				pk10T.setState(StateEnum.CLOSE.name());
				pk10T.setDesc("彩世界[接口]开奖数据抓取失败");
				pk10TService.save(pk10T);
				log.info(GAME_CODE.getSign() + "期数：" + period + pk10T.getDesc());
				return;
			}
			//抓取到数据
			if (LotteryTool.setPk10Lottery(period, pk10T.getDrawNumber())) {
				pk10TService.save(pk10T);
				log.info(GAME_CODE.getSign() + "期数：" + period + pk10T.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, "IDC", pk10T.getDrawNumber(), pk10T.getDrawItem(),
						pk10T.getDrawTimeLong(), pk10T.getDesc(),"CSJ");
			}
		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据

			//抓取到数据
			if (pk10T != null) {
				pk10T.setIdx(entity.getIdx());
				pk10T.setCloudLotteryPk10Id(entity.getCloudLotteryPk10Id());
				pk10T.setCreateTime(entity.getCreateTime());
				pk10T.setCreateTimeLong(entity.getCreateTimeLong());
				//成功设置抓取信息
				if (LotteryTool.setPk10Lottery(period, pk10T.getDrawNumber())) {
					pk10TService.update(pk10T);
					log.info(GAME_CODE.getSign() + "期数：" + period + pk10T.getDesc());
					// 发送mq
					LotteryTool.sendMq(log, period, GAME_CODE, "IDC", pk10T.getDrawNumber(), pk10T.getDrawItem(),
							pk10T.getDrawTimeLong(), pk10T.getDesc(),"CSJ");
				}
			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "彩世界[接口]开奖数据抓取失败，无需保存抓取数据");
			}
		}

	}

	/**
	 * 抓取pk10开奖数据
	 *
	 * @param period 期数
	 * @return 抓取结果
	 */
	private CloudLotteryPk10T getLotteryPk10T(Integer period) throws Exception {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，期数：【" + period + "】，循环次数超过100次");
			return null;
		}
		String url = LotteryTool.getCsjHost().concat("/pk10/getawarddata");
		String param = "r=" + System.currentTimeMillis();
		String html = HttpClientTool.doGet(url, param);

		//循环抓取数据100次，抓到跳出。
		for (; index < CYCLE; index++) {
			if (StringTool.isEmpty(html)) {
				//如果没有抓取到
				if (StringTool.isEmpty(LotteryTool.getPk10Lottery(period))) {
					Thread.sleep(RandomTool.getInt(500, 1500));
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
		if (StringTool.notEmpty(LotteryTool.getPk10Lottery(period))) {
			log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
			return null;
		}
		//html为空则继续抓取
		if (StringTool.isEmpty(html)) {
			index++;
			sleep();
			return getLotteryPk10T(period);
		}

		JSONObject jsonObject;
		try {
			jsonObject = JSONObject.parseObject(html);
		} catch (JSONException e) {
			index++;
			sleep();
			log.error(e + " 获取的html是：" + html);
			return getLotteryPk10T(period);
		}
		Object obj = jsonObject.get("current");

		//数据没有读取完
		if (obj == null) {
			if (index < CYCLE / 2) {
				//没有找到元素并且循环次数低于50次
				index++;
				sleep();
				return getLotteryPk10T(period);
			} else {
				log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，期数：【" + period + "】，数据抓取错误");
				return null;
			}
		}
		JSONObject jObj = (JSONObject) obj;
		//期数匹配不上重新抓取
		if (!jObj.getString("period").equals(period.toString())) {
			log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，抓取期数为：【" + jObj.getString("period") + "】，应有期数为：【" + period
					+ "】");
			index++;
			sleep();
			return getLotteryPk10T(period);
		}

		CloudLotteryPk10T pk10T = new CloudLotteryPk10T();
		//期数
		pk10T.setPeriod(period);
		//开奖时间
		pk10T.setDrawTime(DateTool.get(jObj.getString("awardTime")));
		pk10T.setDrawTimeLong(pk10T.getDrawTime().getTime());
		pk10T.setDrawType("CSJ");
		// 号码
		String numberStr = jObj.getString("result");
		String[] numberArray = numberStr.split(",");
		pk10T.setDrawNumber(numberStr);
		pk10T.setDrawItem(PeriodTool.PK10.getDrawItem(pk10T.getDrawNumber()));
		pk10T.setP1Number(numberArray[0].trim());
		pk10T.setP2Number(numberArray[1].trim());
		pk10T.setP3Number(numberArray[2].trim());
		pk10T.setP4Number(numberArray[3].trim());
		pk10T.setP5Number(numberArray[4].trim());
		pk10T.setP6Number(numberArray[5].trim());
		pk10T.setP7Number(numberArray[6].trim());
		pk10T.setP8Number(numberArray[7].trim());
		pk10T.setP9Number(numberArray[8].trim());
		pk10T.setP10Number(numberArray[9].trim());
		pk10T.setCreateTime(new Date());
		pk10T.setCreateTimeLong(pk10T.getCreateTime().getTime());
		pk10T.setUpdateTime(new Date());
		pk10T.setUpdateTimeLong(pk10T.getUpdateTime().getTime());
		pk10T.setState(StateEnum.OPEN.name());
		pk10T.setDesc("彩世界[接口]开奖数据抓取成功");
		return pk10T;
	}
}
