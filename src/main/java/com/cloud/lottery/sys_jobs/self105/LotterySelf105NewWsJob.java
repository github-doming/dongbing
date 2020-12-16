package com.cloud.lottery.sys_jobs.self105;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.game.newws.Ft5;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodNewWsTool;
import com.cloud.lottery.cloud_lottery_self_10_5.entity.CloudLotterySelf105;
import com.cloud.lottery.cloud_lottery_self_10_5.service.CloudLotterySelf105Service;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * NEWWS 飞艇5分
 *
 * @Author: null
 * @Date: 2020-04-22 14:57
 * @Version: v1.0  5/15 * * * * ? *
 */
public class LotterySelf105NewWsJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.SELF_10_5;
	public static final int CYCLE = 100;

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodNewWsTool.SELF_10_5.getDrawTime() > Ft5.PERIOD) {
			log.trace("未到可以开始抓取的时间");
			return;
		}
		String period = PeriodNewWsTool.SELF_10_5.findPeriod();
		CloudLotterySelf105 self105 = getLotterySelf105(period, 0);
		CloudLotterySelf105Service self105Service = new CloudLotterySelf105Service();
		//读取数据库数据
		CloudLotterySelf105 entity = self105Service.findByPeriod(period, "NEWWS");
		//数据库没有数据
		if (entity == null) {
			if (self105 == null) {
				//全无数据
				self105 = new CloudLotterySelf105();
				self105.setPeriod(period);
				self105.setHandicapCode("NEWWS");
				self105.setCreateTime(new Date());
				self105.setCreateTimeLong(System.currentTimeMillis());
				self105.setUpdateTimeLong(System.currentTimeMillis());
				self105.setState(StateEnum.CLOSE.name());
				self105.setDesc("{NEWWS}飞艇5分彩开奖数据抓取失败");
				self105Service.save(self105);
				log.info(GAME_CODE.getSign() + "期数：" + period + self105.getDesc());
				return;
			}
			//抓取到数据
			self105Service.save(self105);
			log.info(GAME_CODE.getSign() + "期数：" + period + self105.getDesc());
			// 发送mq
			LotteryTool.sendMq(log, period, GAME_CODE, self105.getHandicapCode(), self105.getDrawNumber(),
					self105.getDrawItem(), self105.getDrawTimeLong(), self105.getDesc(), "NEWWS");

		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据
			//抓取到数据
			if (self105 != null) {
				self105.setIdx(entity.getIdx());
				self105.setCloudLotterySelf105Id(entity.getCloudLotterySelf105Id());
				self105.setCreateTime(entity.getCreateTime());
				self105.setCreateTimeLong(entity.getCreateTimeLong());
				self105.setUpdateTime(new Date());
				//成功设置抓取信息
				self105Service.update(self105);
				log.info(GAME_CODE.getSign() + "期数：" + period + "，开奖数据：" + self105.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, self105.getHandicapCode(), self105.getDrawNumber(),
						self105.getDrawItem(), self105.getDrawTimeLong(), self105.getDesc(), "NEWWS");
			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "NEWWS[飞艇5分]开奖数据抓取失败，无需保存抓取数据");
			}
		}
	}

	private CloudLotterySelf105 getLotterySelf105(String period, int index) throws Exception {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign() + "{NEWWS}开奖数据，期数：【" + period + "】，循环次数超过" + CYCLE + "次");
			return null;
		}
		String url = "https://www.vegstop1.com/vs/getResult3.do";

		Map<String, Object> param = new HashMap<>(2);
		param.put("date", "");
		param.put("issueNo", "");
		String html = HttpClientTool.doPost(url, param);

		//循环抓取数据100次，抓到跳出。
		for (; index < CYCLE; index++) {
			if (StringTool.isEmpty(html)) {
				++index;
				//如果没有抓取到
				if (StringTool.isEmpty(getLotterySelf105(period, index))) {
					Thread.sleep(RandomTool.getInt(500, 1500));
					html = HttpClientTool.doPost(url, param);
				} else {
					log.info(GAME_CODE.getSign() + "{NEWWS}开奖数据，期数：【" + period + "】，其他地方以及抓取到数据");
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
			return getLotterySelf105(period, index);
		}

		JSONObject jsonObject;
		try {
			jsonObject = JSONObject.parseObject(html);
		} catch (JSONException e) {
			index++;
			sleep();
			log.error(e + " 获取的html是：" + html);
			return getLotterySelf105(period, index);
		}
		JSONObject obj = jsonObject.getJSONObject("dataObject");

		//数据没有读取完
		if (obj == null) {
			if (index < CYCLE / 2) {
				//没有找到元素并且循环次数低于50次
				index++;
				sleep();
				return getLotterySelf105(period, index);
			} else {
				log.info(GAME_CODE.getSign() + "{NEWWS}开奖数据，期数：【" + period + "】，数据抓取错误");
				return null;
			}
		}

		//期数匹配不上重新抓取
		if (!obj.getString("issueNo").equals(period)) {
			log.info(GAME_CODE.getSign() + "{NEWWS}开奖数据，抓取期数为：【" + obj.getString("issueNo") + "】，应有期数为：【" + period
					+ "】");
			index++;
			sleep();
			return getLotterySelf105(period, index);
		}

		CloudLotterySelf105 lotterySelf105 = new CloudLotterySelf105();
		//期数
		lotterySelf105.setPeriod(period);
		//开奖时间
		lotterySelf105.setDrawTime(new Date(PeriodNewWsTool.SELF_10_5.getDrawTime(period)));
		lotterySelf105.setDrawTimeLong(PeriodNewWsTool.SELF_10_5.getDrawTime(period));

		// 号码
		String numberStr = obj.getString("numbers");
		String[] numberArray = numberStr.split(",");
		lotterySelf105.setDrawNumber(numberStr);
		lotterySelf105.setHandicapCode("NEWWS");
		lotterySelf105.setDrawType("NEWWS");
		lotterySelf105.setDrawItem(PeriodNewWsTool.SELF_10_5.getDrawItem(lotterySelf105.getDrawNumber()));
		lotterySelf105.setP1Number(numberArray[0].trim());
		lotterySelf105.setP2Number(numberArray[1].trim());
		lotterySelf105.setP3Number(numberArray[2].trim());
		lotterySelf105.setP4Number(numberArray[3].trim());
		lotterySelf105.setP5Number(numberArray[4].trim());
		lotterySelf105.setP6Number(numberArray[5].trim());
		lotterySelf105.setP7Number(numberArray[6].trim());
		lotterySelf105.setP8Number(numberArray[7].trim());
		lotterySelf105.setP9Number(numberArray[8].trim());
		lotterySelf105.setP10Number(numberArray[9].trim());
		lotterySelf105.setCreateTime(new Date());
		lotterySelf105.setCreateTimeLong(lotterySelf105.getCreateTime().getTime());
		lotterySelf105.setUpdateTime(new Date());
		lotterySelf105.setUpdateTimeLong(lotterySelf105.getUpdateTime().getTime());
		lotterySelf105.setState(StateEnum.OPEN.name());
		lotterySelf105.setDesc("{NEWWS}开奖数据抓取成功");
		return lotterySelf105;
	}


}
