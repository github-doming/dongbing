package com.cloud.lottery.sys_jobs.gxklsf;

import com.cloud.common.game.GameOperation;
import com.cloud.common.game.idc.Gxklsf;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodIdcTool;
import com.cloud.lottery.cloud_lottery_gxklsf.entity.CloudLotteryGxklsf;
import com.cloud.lottery.cloud_lottery_gxklsf.service.CloudLotteryGxklsfService;
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
 * IDC盘口开奖抓取
 *
 * @Author: Dongming
 * @Date: 2020-04-20 15:08
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryGxklsfIdcJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.GXKLSF;
	GameOperation gameOperation = PeriodIdcTool.GXKLSF;
	public static final int CYCLE = 500;

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodIdcTool.GXKLSF.getLotteryDrawTime() > Gxklsf.PERIOD) {
			log.info("未到可以开始抓取的时间");
			super.saveLog = false;
			return;
		}
		String period = PeriodIdcTool.GXKLSF.findLotteryPeriod();
		CloudLotteryGxklsf gxklsf = getLotteryGxklsf(period);
		CloudLotteryGxklsfService gxklsfService = new CloudLotteryGxklsfService();
		//读取数据库数据
		CloudLotteryGxklsf entity = gxklsfService.findByPeriod(period);
		//数据库没有数据
		if (entity == null) {
			if (gxklsf == null) {
				//全无数据
				gxklsf = new CloudLotteryGxklsf();
				gxklsf.setPeriod(period);
				gxklsf.setHandicapCode("IDC");
				gxklsf.setDrawType("CSJ");
				gxklsf.setCreateTime(new Date());
				gxklsf.setCreateTimeLong(System.currentTimeMillis());
				gxklsf.setUpdateTimeLong(System.currentTimeMillis());
				gxklsf.setState(StateEnum.CLOSE.name());
				gxklsf.setDesc("{IDC}广西快乐十分开奖数据抓取失败");
				gxklsfService.save(gxklsf);
				log.info(GAME_CODE.getSign() + "期数：" + period + gxklsf.getDesc());
				return;
			}
			//抓取到数据
			gxklsfService.save(gxklsf);
			log.info(GAME_CODE.getSign() + "期数：" + period + gxklsf.getDesc());
			// 发送mq
			LotteryTool.sendMq(log, period, GAME_CODE, gxklsf.getHandicapCode(), gxklsf.getDrawNumber(),
					gxklsf.getDrawItem(), gxklsf.getDrawTimeLong(), gxklsf.getDesc(),"CSJ");

		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据
			//抓取到数据
			if (gxklsf != null) {
				gxklsf.setIdx(entity.getIdx());
				gxklsf.setCloudLotteryGxklsfId(entity.getCloudLotteryGxklsfId());
				gxklsf.setCreateTime(entity.getCreateTime());
				gxklsf.setCreateTimeLong(entity.getCreateTimeLong());
				gxklsf.setUpdateTime(new Date());
				//成功设置抓取信息
				gxklsfService.update(gxklsf);
				log.info(GAME_CODE.getSign() + "期数：" + period + "，开奖数据：" + gxklsf.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, gxklsf.getHandicapCode(), gxklsf.getDrawNumber(),
						gxklsf.getDrawItem(), gxklsf.getDrawTimeLong(), gxklsf.getDesc(),"CSJ");
			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "彩世界[页面]开奖数据抓取失败，无需保存抓取数据");
			}
		}

	}

	private CloudLotteryGxklsf getLotteryGxklsf(String period) throws InterruptedException, ParseException {
		String handicapUrl = "http://yun.ngk77.com/ui-02/detail.aspx/GetWinningnohistoryList";
		String entity = String.format("{gameno:9,gamegroupno:5,pagesize:15,curentsize:1,transdate:\"%s\"}",
				DateTool.getDate(new Date()));
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
		// 号码
		CloudLotteryGxklsf gxklsf = new CloudLotteryGxklsf();
		long drawTime = PeriodIdcTool.GXKLSF.getLotteryDrawTime();
		gxklsf.setDrawTime(new Date(drawTime));
		gxklsf.setDrawTimeLong(drawTime);
		gxklsf.setHandicapCode("IDC");
		gxklsf.setDrawType("CSJ");
		gxklsf.setPeriod(period);
		gxklsf.setDrawNumber(StringUtils.join(drawNumbers, ","));
		gxklsf.setDrawItem(gameOperation.getDrawItem(gxklsf.getDrawNumber()));
		gxklsf.setP1Number(drawNumbers[0]);
		gxklsf.setP2Number(drawNumbers[1]);
		gxklsf.setP3Number(drawNumbers[2]);
		gxklsf.setP4Number(drawNumbers[3]);
		gxklsf.setP5Number(drawNumbers[4]);
		gxklsf.setCreateTime(new Date());
		gxklsf.setCreateTimeLong(System.currentTimeMillis());
		gxklsf.setUpdateTimeLong(System.currentTimeMillis());
		gxklsf.setState(StateEnum.OPEN.name());
		gxklsf.setDesc("{IDC}广西快乐十分开奖数据抓取成功");
		return gxklsf;
	}

}
