package com.cloud.lottery.sys_jobs.self102;

import com.cloud.common.game.idc.Eps2;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodIdcTool;
import com.cloud.lottery.cloud_lottery_self_10_2.entity.CloudLotterySelf102;
import com.cloud.lottery.cloud_lottery_self_10_2.service.CloudLotterySelf102Service;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.DateTool;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * IDC EPS赛马2分彩
 *
 * @Author: Dongming
 * @Date: 2020-04-22 14:57
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotterySelf102IdcJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.SELF_10_2;
	public static final int CYCLE = 100;

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodIdcTool.SELF_10_2.getLotteryDrawTime() > Eps2.PERIOD) {
			log.trace("未到可以开始抓取的时间");
			return;
		}
		String period = PeriodIdcTool.SELF_10_2.findLotteryPeriod();
		CloudLotterySelf102 self102 = getLotterySelf102(period);
		CloudLotterySelf102Service self102Service = new CloudLotterySelf102Service();
		//读取数据库数据
		CloudLotterySelf102 entity = self102Service.findByPeriod(period, "IDC");
		//数据库没有数据
		if (entity == null) {
			if (self102 == null) {
				//全无数据
				self102 = new CloudLotterySelf102();
				self102.setPeriod(period);
				self102.setHandicapCode("IDC");
				self102.setDrawType("IDC");
				self102.setCreateTime(new Date());
				self102.setCreateTimeLong(System.currentTimeMillis());
				self102.setUpdateTimeLong(System.currentTimeMillis());
				self102.setState(StateEnum.CLOSE.name());
				self102.setDesc("{IDC}EPS赛马2分彩开奖数据抓取失败");
				self102Service.save(self102);
				log.info(GAME_CODE.getSign() + "期数：" + period + self102.getDesc());
				return;
			}
			//抓取到数据
			self102Service.save(self102);
			log.info(GAME_CODE.getSign() + "期数：" + period + self102.getDesc());
			// 发送mq
			LotteryTool.sendMq(log, period, GAME_CODE, self102.getHandicapCode(), self102.getDrawNumber(),
					self102.getDrawItem(), self102.getDrawTimeLong(), self102.getDesc(),"IDC");

		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据
			//抓取到数据
			if (self102 != null) {
				self102.setIdx(entity.getIdx());
				self102.setCloudLotterySelf102Id(entity.getCloudLotterySelf102Id());
				self102.setCreateTime(entity.getCreateTime());
				self102.setCreateTimeLong(entity.getCreateTimeLong());
				self102.setUpdateTime(new Date());
				//成功设置抓取信息
				self102Service.update(self102);
				log.info(GAME_CODE.getSign() + "期数：" + period + "，开奖数据：" + self102.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, self102.getHandicapCode(), self102.getDrawNumber(),
						self102.getDrawItem(), self102.getDrawTimeLong(), self102.getDesc(),"IDC");
			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "IDC[EPS赛马2分彩]开奖数据抓取失败，无需保存抓取数据");
			}
		}
	}

	private CloudLotterySelf102 getLotterySelf102(String period) throws InterruptedException, ParseException {
		String handicapUrl = "http://yun.ngk77.com/ui-02/detail.aspx/GetWinningnohistoryList";
		String day;
		Date nowTime = new Date();
		if (nowTime.before(PeriodIdcTool.SELF_10_2.getBeginTime())) {
			day = DateTool.getDate(DateTool.getBeforeDate(nowTime));
		} else {
			day = DateTool.getDate(nowTime);
		}
		String entity = String.format("{gameno:26,gamegroupno:6,pagesize:15,curentsize:1,transdate:\"%s\"}", day);
		Map<String, Object> map = new HashMap<>(1);
		map.put("$ENTITY_JSON$", entity);
		Element element = LotteryTool.getElement(log, GAME_CODE.getSign(), handicapUrl, map, period, 0, CYCLE);
		if (element == null) {
			return null;
		}
		Elements divs = element.getElementsByTag("div");

		Integer[] drawNumbers = new Integer[divs.size()];
		for (int i = 0; i < divs.size(); i++) {
			drawNumbers[i] = Integer.parseInt(divs.get(i).attr("class").split("_")[1]);
		}
		CloudLotterySelf102 self102 = new CloudLotterySelf102();
		long drawTime = PeriodIdcTool.SELF_10_2.getLotteryDrawTime();
		self102.setDrawTime(new Date(drawTime));
		self102.setDrawTimeLong(drawTime);
		self102.setHandicapCode("IDC");
		self102.setDrawType("IDC");
		self102.setPeriod(period);
		self102.setDrawNumber(StringUtils.join(drawNumbers, ","));
		self102.setDrawItem(PeriodIdcTool.SELF_10_2.getDrawItem(self102.getDrawNumber()));
		self102.setP1Number(drawNumbers[0]);
		self102.setP2Number(drawNumbers[1]);
		self102.setP3Number(drawNumbers[2]);
		self102.setP4Number(drawNumbers[3]);
		self102.setP5Number(drawNumbers[4]);
		self102.setP6Number(drawNumbers[5]);
		self102.setP7Number(drawNumbers[6]);
		self102.setP8Number(drawNumbers[7]);
		self102.setP9Number(drawNumbers[8]);
		self102.setP10Number(drawNumbers[9]);
		self102.setCreateTime(new Date());
		self102.setCreateTimeLong(System.currentTimeMillis());
		self102.setUpdateTimeLong(System.currentTimeMillis());
		self102.setState(StateEnum.OPEN.name());
		self102.setDesc("{IDC}EPS赛马2分彩开奖数据抓取成功");
		return self102;

	}
}
