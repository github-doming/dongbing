package com.cloud.lottery.sys_jobs.jsssc;
import com.cloud.common.game.idc.JssscIdc;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodIdcTool;
import com.cloud.lottery.cloud_lottery_jsssc.entity.CloudLotteryJsssc;
import com.cloud.lottery.cloud_lottery_jsssc.service.CloudLotteryJssscService;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.CurrentTransaction;
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
 * @Description: IDC盘口开奖抓取
 * @Author: Dongming
 * @Date: 2019-10-08 17:16
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryJssscIdcJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.JSSSC;
	public static final int CYCLE = 100;
	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodIdcTool.JSSSC.getLotteryDrawTime() > JssscIdc.PERIOD) {
			saveLog = false;
			log.info("未到可以开始抓取的时间");
			return;
		}
		long period = PeriodIdcTool.JSSSC.findLotteryPeriod();
		CloudLotteryJsssc jsssc = getLotteryJsssc(period);
		CloudLotteryJssscService jssscService = new CloudLotteryJssscService();
		//读取数据库数据
		CurrentTransaction.transactionCommit();
		CloudLotteryJsssc entity = jssscService.findByPeriod(period,"IDC");
		//数据库没有数据
		if (entity == null) {
			if (jsssc == null) {
				//全无数据
				jsssc = new CloudLotteryJsssc();
				jsssc.setPeriod(period);
				jsssc.setHandicapCode("IDC");
				jsssc.setDrawType("CSJ");
				jsssc.setCreateTime(new Date());
				jsssc.setCreateTimeLong(System.currentTimeMillis());
				jsssc.setUpdateTimeLong(System.currentTimeMillis());
				jsssc.setState(StateEnum.CLOSE.name());
				jsssc.setDesc("{IDC}急速时时彩开奖数据抓取失败");
				jssscService.save(jsssc);
				log.info(GAME_CODE.getSign() + "期数：" + period + jsssc.getDesc());
				return;
			}
			//抓取到数据
			jssscService.save(jsssc);
			log.info(GAME_CODE.getSign() + "期数：" + period + jsssc.getDesc());
			// 发送mq
			LotteryTool.sendMq(log, period, GAME_CODE, jsssc.getHandicapCode(), jsssc.getDrawNumber(), jsssc.getDrawItem(),
					jsssc.getDrawTimeLong(), jsssc.getDesc(),"CSJ");

		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据
			//抓取到数据
			if (jsssc != null) {
				jsssc.setIdx(entity.getIdx());
				jsssc.setCloudLotteryJssscId(entity.getCloudLotteryJssscId());
				jsssc.setCreateTime(entity.getCreateTime());
				jsssc.setCreateTimeLong(entity.getCreateTimeLong());
				jsssc.setUpdateTime(new Date());
				//成功设置抓取信息
				jssscService.update(jsssc);
				log.info(GAME_CODE.getSign() + "期数：" + period + "，开奖数据：" + jsssc.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, jsssc.getHandicapCode(), jsssc.getDrawNumber(), jsssc.getDrawItem(),
						jsssc.getDrawTimeLong(), jsssc.getDesc(),"CSJ");
			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "彩世界[页面]开奖数据抓取失败，无需保存抓取数据");
			}
		}
	}

	/**
	 * 获取急速赛车开奖实体
	 *
	 * @param period 期数
	 * @return 急速赛车开奖实体
	 */
	private CloudLotteryJsssc getLotteryJsssc(long period) throws InterruptedException, ParseException {
		String handicapUrl = "http://yun.ngk77.com/ui-02/detail.aspx/GetWinningnohistoryList";
		String day;
		Date nowTime = new Date();
		if (nowTime.before(PeriodIdcTool.JSSSC.getStartTime())) {
			day = DateTool.getDate(DateTool.getBeforeDate(nowTime));
		} else {
			day = DateTool.getDate(nowTime);
		}
		String entity = String.format("{gameno:23,gamegroupno:3,pagesize:15,curentsize:1,transdate:\"%s\"}", day);
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
		CloudLotteryJsssc jsssc = new CloudLotteryJsssc();
		long drawTime = PeriodIdcTool.JSSSC.getLotteryDrawTime();
		jsssc.setDrawTime(new Date(drawTime));
		jsssc.setDrawTimeLong(drawTime);
		jsssc.setHandicapCode("IDC");
		jsssc.setDrawType("CSJ");
		jsssc.setPeriod(period);
		jsssc.setDrawNumber(StringUtils.join(drawNumbers, ","));
		jsssc.setDrawItem(PeriodIdcTool.JSSSC.getDrawItem(jsssc.getDrawNumber()));
		jsssc.setP1Number(drawNumbers[0]);
		jsssc.setP2Number(drawNumbers[1]);
		jsssc.setP3Number(drawNumbers[2]);
		jsssc.setP4Number(drawNumbers[3]);
		jsssc.setP5Number(drawNumbers[4]);
		jsssc.setCreateTime(new Date());
		jsssc.setCreateTimeLong(System.currentTimeMillis());
		jsssc.setUpdateTimeLong(System.currentTimeMillis());
		jsssc.setState(StateEnum.OPEN.name());
		jsssc.setDesc("{IDC}急速时时彩开奖数据抓取成功");
		return jsssc;

	}

}
