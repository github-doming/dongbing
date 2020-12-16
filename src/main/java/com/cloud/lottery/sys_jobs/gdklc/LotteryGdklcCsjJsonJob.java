package com.cloud.lottery.sys_jobs.gdklc;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.game.Gdklc;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_gdklc.entity.CloudLotteryGdklc;
import com.cloud.lottery.cloud_lottery_gdklc.service.CloudLotteryGdklcService;
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
 * 抓取彩世界接口的GDKLC数据
 * @Author: Dongming
 * @Date: 2020-04-18 17:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryGdklcCsjJsonJob  extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.GDKLC;

	private int index;
	public static final int CYCLE = 500;

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.GDKLC.getLotteryDrawTime() > Gdklc.PERIOD) {
			log.info("未到可以开始抓取的时间");
			super.saveLog = false;
			return;
		}
		String period = PeriodTool.GDKLC.findLotteryPeriod();
		log.info(GAME_CODE.getSign() + "期数：" + period +"开始抓取");
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
				gdklc.setDesc("彩世界[接口]开奖数据抓取失败");
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
				log.info(GAME_CODE.getSign() + "期数：" + period + "彩世界[接口]开奖数据抓取失败，无需保存抓取数据");
			}
		}
	}

	private CloudLotteryGdklc getLotteryGdklc(String period) throws IOException, InterruptedException, ParseException {
		if (index > CYCLE){
			log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，期数：【" + period + "】，循环次数超过100次");
			return null;
		}
		String url =	LotteryTool.getCsjHost().concat("/gdkl10/getawarddata");
		String param = "r=" + System.currentTimeMillis();
		String html = HttpClientTool.doGet(url, param);
		//循环抓取数据100次，抓到跳出。
		for (; index < CYCLE; index++) {
			if (StringTool.isEmpty(html)) {
				//如果没有抓取到
				if (StringTool.isEmpty(LotteryTool.getGdklcLottery(period))) {
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
		if (StringTool.notEmpty(LotteryTool.getGdklcLottery(period))) {
			log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
			return null;
		}
		//html为空则继续抓取
		if(StringTool.isEmpty(html)){
			index++;
			//随机睡眠
			sleep();
			return getLotteryGdklc(period);
		}
		JSONObject jsonObject;
		try {
			jsonObject = JSONObject.parseObject(html);
		} catch (JSONException e){
			index++;
			log.error(e +" 获取的html是："+html);
			sleep();
			return getLotteryGdklc(period);
		}
		Object obj = jsonObject.get("current");
		//数据没有读取完
		if (obj == null) {
			if (index < CYCLE/2) {
				//没有找到元素并且循环次数低于50次
				index ++;
				sleep();
				return getLotteryGdklc(period);
			} else {
				log.info(GAME_CODE.getSign() + "彩世界[接口]开奖数据，期数：【" + period + "】，数据抓取错误");
				return null;
			}
		}
		JSONObject jObj = (JSONObject) obj;
		//期数匹配不上重新抓取
		if (!PeriodTool.equals(period.split("-")[1],jObj.getString("period"))) {
			log.info(
					GAME_CODE.getSign() + "彩世界[接口]开奖数据，抓取期数为：【" + jObj.getString("period") + "】，应有期数为：【"
							+ period + "】");
			index ++;
			sleep();
			return getLotteryGdklc(period);
		}

		CloudLotteryGdklc gdklc = new CloudLotteryGdklc();

		//期数
		gdklc.setPeriod(period);
		gdklc.setHandicapCode("IDC");
		//开奖时间
		gdklc.setDrawTime(DateTool.get(jObj.getString("awardTime")));
		gdklc.setDrawTimeLong(gdklc.getDrawTime().getTime());
		// 号码
		String numberStr = jObj.getString("result");
		String[] numberArray = numberStr.split(",");

        if (numberStr.startsWith("0")){
            numberStr = numberStr.substring(1);
        }
        numberStr = numberStr.replace(",0",",");
		gdklc.setDrawType("CSJ");
		gdklc.setDrawNumber(numberStr);
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
		gdklc.setDesc("彩世界[接口]开奖数据抓取成功");
		return gdklc;
	}
}
