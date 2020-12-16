package com.cloud.lottery.sys_jobs.countryssc;
import com.cloud.common.game.idc.AustraliaLucky5;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodIdcTool;
import com.cloud.lottery.cloud_lottery_country_ssc.entity.CloudLotteryCountrySsc;
import com.cloud.lottery.cloud_lottery_country_ssc.service.CloudLotteryCountrySscService;
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
 * IDC 澳洲幸运5 国家时时彩
 *
 * @Author: Dongming
 * @Date: 2020-04-30 14:54
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryCountrySscIdcJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.COUNTRY_SSC;
	public static final int CYCLE = 100;
	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodIdcTool.COUNTRY_SSC.getLotteryDrawTime() > AustraliaLucky5.PERIOD) {
			log.trace("未到可以开始抓取的时间");
			super.saveLog = false;
			return;
		}
		Integer period = PeriodIdcTool.COUNTRY_SSC.findLotteryPeriod();
		CloudLotteryCountrySsc countrySsc = getLotteryCountrySsc(period);
		CloudLotteryCountrySscService countrySscService = new CloudLotteryCountrySscService();
		//读取数据库数据
		CloudLotteryCountrySsc entity = countrySscService.findByPeriod(period);
		//数据库没有数据
		if (entity == null) {
			if (countrySsc == null) {
				//全无数据
				countrySsc = new CloudLotteryCountrySsc();
				countrySsc.setPeriod(period);
				countrySsc.setHandicapCode("IDC");
				countrySsc.setDrawType("IDC");
				countrySsc.setCreateTime(new Date());
				countrySsc.setCreateTimeLong(System.currentTimeMillis());
				countrySsc.setUpdateTimeLong(System.currentTimeMillis());
				countrySsc.setState(StateEnum.CLOSE.name());
				countrySsc.setDesc("{IDC}" + GAME_CODE.getGameName() + "开奖数据抓取失败");
				countrySscService.save(countrySsc);
				log.info(GAME_CODE.getSign() + "期数：" + period + countrySsc.getDesc());
				return;
			}
			//抓取到数据
			countrySscService.save(countrySsc);
			log.info(GAME_CODE.getSign() + "期数：" + period + countrySsc.getDesc());
			// 发送mq
			LotteryTool.sendMq(log, period, GAME_CODE, countrySsc.getHandicapCode(), countrySsc.getDrawNumber(),
					countrySsc.getDrawItem(), countrySsc.getDrawTimeLong(), countrySsc.getDesc(),"IDC");
		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据
			//抓取到数据
			if (countrySsc != null) {
				countrySsc.setIdx(entity.getIdx());
				countrySsc.setCloudLotteryCountrySscId(entity.getCloudLotteryCountrySscId());
				countrySsc.setCreateTime(entity.getCreateTime());
				countrySsc.setCreateTimeLong(entity.getCreateTimeLong());
				countrySsc.setUpdateTime(new Date());
				//成功设置抓取信息
				countrySscService.update(countrySsc);
				log.info(GAME_CODE.getSign() + "期数：" + period + "，开奖数据：" + countrySsc.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, countrySsc.getHandicapCode(), countrySsc.getDrawNumber(),
						countrySsc.getDrawItem(), countrySsc.getDrawTimeLong(), countrySsc.getDesc(),"IDC");

			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "IDC开奖数据抓取失败，无需保存抓取数据");
			}
		}
	}

	private CloudLotteryCountrySsc getLotteryCountrySsc(Integer period) throws InterruptedException, ParseException {
		String handicapUrl = "http://yun.ngk77.com/ui-02/detail.aspx/GetWinningnohistoryList";
		String day;
		Date nowTime = new Date();
		if (nowTime.before(PeriodIdcTool.COUNTRY_SSC.getStartTime())) {
			day = DateTool.getDate(DateTool.getBeforeDate(nowTime));
		} else {
			day = DateTool.getDate(nowTime);
		}
		String entity = String.format("{gameno:38,gamegroupno:3,pagesize:15,curentsize:1,transdate:\"%s\"}", day);
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
		CloudLotteryCountrySsc countrySsc = new CloudLotteryCountrySsc();
		long drawTime = PeriodIdcTool.COUNTRY_SSC.getLotteryDrawTime();
		countrySsc.setDrawTime(new Date(drawTime));
		countrySsc.setDrawTimeLong(drawTime);
		countrySsc.setHandicapCode("IDC");
		countrySsc.setDrawType("IDC");
		countrySsc.setPeriod(period);
		countrySsc.setDrawNumber(StringUtils.join(drawNumbers, ","));
		countrySsc.setDrawItem(PeriodIdcTool.COUNTRY_SSC.getDrawItem(countrySsc.getDrawNumber()));
		countrySsc.setP1Number(drawNumbers[0]);
		countrySsc.setP2Number(drawNumbers[1]);
		countrySsc.setP3Number(drawNumbers[2]);
		countrySsc.setP4Number(drawNumbers[3]);
		countrySsc.setP5Number(drawNumbers[4]);
		countrySsc.setCreateTime(new Date());
		countrySsc.setCreateTimeLong(System.currentTimeMillis());
		countrySsc.setUpdateTimeLong(System.currentTimeMillis());
		countrySsc.setState(StateEnum.OPEN.name());
		countrySsc.setDesc("{IDC}" + GAME_CODE.getGameName() + "开奖数据抓取成功");
		return countrySsc;
	}
}
