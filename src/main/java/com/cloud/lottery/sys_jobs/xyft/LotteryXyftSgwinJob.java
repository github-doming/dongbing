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
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.quartz.JobExecutionContext;

import java.text.ParseException;
import java.util.Date;
/**
 * SGWIN盘口 XYFT开奖抓取
 *
 * @Author: Dongming
 * @Date: 2020-03-06 16:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryXyftSgwinJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.XYFT;

	private int index = 0;
	public static final int CYCLE = 200;
	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodTool.XYFT.getLotteryDrawTime() > Xyft.PERIOD) {
			log.trace("未到可以开始抓取的时间");
			return;
		}
		String period = PeriodTool.XYFT.findLotteryPeriod();

		CloudLotteryXyftT xyftT = getLotteryXyftT(period);
		CloudLotteryXyftTService xyftTService = new CloudLotteryXyftTService();
		//读取数据库数据
		CloudLotteryXyftT entity = xyftTService.findByPeriod(period, "SGWIN");
		//数据库没有数据
		if (entity == null) {
			if (xyftT == null) {
				//全无数据
				xyftT = new CloudLotteryXyftT();
				xyftT.setPeriod(period);
				xyftT.setHandicapCode("SGWIN");
				xyftT.setDrawType("168");
				xyftT.setCreateTime(new Date());
				xyftT.setCreateTimeLong(xyftT.getCreateTime().getTime());
				xyftT.setUpdateTime(new Date());
				xyftT.setUpdateTimeLong(xyftT.getUpdateTime().getTime());
				xyftT.setState(StateEnum.CLOSE.name());
				xyftT.setDesc("168开奖网[JSON]开奖数据抓取失败");
				xyftTService.save(xyftT);
				log.info(GAME_CODE.getSign()+ "期数：" + period + "168开奖网[JSON]开奖数据抓取失败，保存抓取数据");
			} else {
				xyftTService.save(xyftT);
				log.info(GAME_CODE.getSign()+ "期数：" + period + xyftT.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, xyftT.getHandicapCode(), xyftT.getDrawNumber(),
						xyftT.getDrawItem(), xyftT.getDrawTimeLong(), xyftT.getDesc(),"168");
			}
		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//抓取到数据,数据库存放的是失败的数据
			if (xyftT != null) {
				xyftT.setIdx(entity.getIdx());
				xyftT.setCloudLotteryXyftId(entity.getCloudLotteryXyftId());
				xyftT.setCreateTime(entity.getCreateTime());
				xyftT.setCreateTimeLong(entity.getCreateTimeLong());
				//成功设置抓取信息
				xyftTService.update(xyftT);
				log.info(GAME_CODE.getSign()+ "期数：" + period + xyftT.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, xyftT.getHandicapCode(), xyftT.getDrawNumber(),
						xyftT.getDrawItem(), xyftT.getDrawTimeLong(), xyftT.getDesc(),"168");
			}
		} else {
			log.info(GAME_CODE.getSign()+ "期数：" + period + "168开奖网[JSON]开奖数据抓取失败，已有数据无需保存抓取数据");
		}
	}

	private CloudLotteryXyftT getLotteryXyftT(String period) throws ParseException, InterruptedException {
		if (index > CYCLE) {
			log.info(GAME_CODE.getSign()+ "168开奖网[JSON]开奖数据，期数：【" + period + "】，循环次数超过100次");
			return null;
		}
		String url = "https://api.api861861.com/pks/getLotteryPksInfo.do";
		String param = "lotCode=10057";
		String html;
		try {
			html = HttpClientTool.doGet(url, param);
			//循环抓取数据100次，抓到跳出。
			for (; index < CYCLE; index++) {
				if (StringTool.isEmpty(html)) {
					//随机睡眠
					sleep();
					Thread.sleep(RandomTool.getInt(500, 1500));
					html = HttpClientTool.doGet(url, param);
				} else {
					break;
				}
			}
		} catch (Exception ignore) {
			index++;
			sleep();
			return getLotteryXyftT(period);
		}
		//html为空则继续抓取 或者没有 全部包含，则错误
		if (StringTool.isEmpty(html) || !StringTool.isContains(html, "result", "data", "preDrawCode")) {
			index++;
			log.info(GAME_CODE.getSign()+ "168开奖网[JSON]开奖数据，期数：【" + period + "】，数据抓取页面错误{}", html);
			sleep();
			return getLotteryXyftT(period);
		}
		JSONObject jsonObject;
		try {
			jsonObject = JSONObject.parseObject(html);
		} catch (JSONException e) {
			index++;
			log.error(e + " 获取的html是：" + html);
		sleep();
		return getLotteryXyftT(period);
	}
		Object obj = jsonObject.getJSONObject("result").get("data");
		//数据没有读取完
		if (obj == null) {
			if (index < CYCLE / 2) {
				//没有找到元素并且循环次数低于50次
				index++;
				sleep();
				return getLotteryXyftT(period);
			} else {
				log.info(GAME_CODE.getSign()+ "168开奖网[JSON]开奖数据，期数：【" + period + "】，数据抓取错误");
				return null;
			}
		}
		JSONObject jObj = (JSONObject) obj;
		//期数匹配不上重新抓取
		if (!PeriodTool.equals(period.split("-")[1], jObj.getString("preDrawIssue").substring(8))) {
			log.info(GAME_CODE.getSign()+ "168开奖网[JSON]开奖数据，抓取期数为：【" + jObj.getString("preDrawIssue") + "】，应有期数为：【"
					+ period + "】");
			index++;
			sleep();
			return getLotteryXyftT(period);
		}
		CloudLotteryXyftT xyftT = new CloudLotteryXyftT();
		//期数
		xyftT.setPeriod(period);
		xyftT.setHandicapCode("SGWIN");
		xyftT.setDrawType("168");
		//开奖时间
		xyftT.setDrawTime(DateTool.get(jObj.getString("preDrawTime")));
		xyftT.setDrawTimeLong(xyftT.getDrawTime().getTime());
		// 号码
		String numberStr = jObj.getString("preDrawCode");
		numberStr = ("," + numberStr).replace(",0", ",").substring(1);
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
		xyftT.setDesc("168开奖网[JSON]开奖数据抓取成功");
		return xyftT;
	}
}
