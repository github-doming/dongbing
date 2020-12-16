package com.cloud.lottery.sys_jobs.selfssc5;
import com.cloud.common.game.idc.SuperFast5;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodIdcTool;
import com.cloud.lottery.cloud_lottery_self_ssc_5.entity.CloudLotterySelfSsc5;
import com.cloud.lottery.cloud_lottery_self_ssc_5.service.CloudLotterySelfSsc5Service;
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
 * IDC 超级快5 5分彩
 *
 * @Author: Dongming
 * @Date: 2020-04-30 11:44
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotterySelfSsc5IdcJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.SELF_SSC_5;

	public static final int CYCLE = 100;

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		if (System.currentTimeMillis() - PeriodIdcTool.SELF_SSC_5.getLotteryDrawTime() > SuperFast5.PERIOD) {
			log.trace("未到可以开始抓取的时间");
			return;
		}
		String period = PeriodIdcTool.SELF_SSC_5.findLotteryPeriod();
		CloudLotterySelfSsc5 selfSsc5 = getLotterySelfSsc5(period);
		CloudLotterySelfSsc5Service selfSsc5Service = new CloudLotterySelfSsc5Service();
		//读取数据库数据
		CloudLotterySelfSsc5 entity = selfSsc5Service.findByPeriod(period);
		//数据库没有数据
		if (entity == null) {
			if (selfSsc5 == null) {
				//全无数据
				selfSsc5 = new CloudLotterySelfSsc5();
				selfSsc5.setPeriod(period);
				selfSsc5.setHandicapCode("IDC");
				selfSsc5.setDrawType("IDC");
				selfSsc5.setCreateTime(new Date());
				selfSsc5.setCreateTimeLong(System.currentTimeMillis());
				selfSsc5.setUpdateTimeLong(System.currentTimeMillis());
				selfSsc5.setState(StateEnum.CLOSE.name());
				selfSsc5.setDesc("{IDC}" + GAME_CODE.getGameName() + "开奖数据抓取失败");
				selfSsc5Service.save(selfSsc5);
				log.info(GAME_CODE.getSign() + "期数：" + period + selfSsc5.getDesc());
				return;
			}
			//抓取到数据
			selfSsc5Service.save(selfSsc5);
			log.info(GAME_CODE.getSign() + "期数：" + period + selfSsc5.getDesc());
			// 发送mq
			LotteryTool.sendMq(log, period, GAME_CODE, selfSsc5.getHandicapCode(), selfSsc5.getDrawNumber(),
					selfSsc5.getDrawItem(), selfSsc5.getDrawTimeLong(), selfSsc5.getDesc(),"IDC");
		} else if (!entity.getState().equals(StateEnum.OPEN.name())) {
			//数据库存放的是失败的数据
			//抓取到数据
			if (selfSsc5 != null) {
				selfSsc5.setIdx(entity.getIdx());
				selfSsc5.setCloudLotterySelfSsc5Id(entity.getCloudLotterySelfSsc5Id());
				selfSsc5.setCreateTime(entity.getCreateTime());
				selfSsc5.setCreateTimeLong(entity.getCreateTimeLong());
				selfSsc5.setUpdateTime(new Date());
				//成功设置抓取信息
				selfSsc5Service.update(selfSsc5);
				log.info(GAME_CODE.getSign() + "期数：" + period + "，开奖数据：" + selfSsc5.getDesc());
				// 发送mq
				LotteryTool.sendMq(log, period, GAME_CODE, selfSsc5.getHandicapCode(), selfSsc5.getDrawNumber(),
						selfSsc5.getDrawItem(), selfSsc5.getDrawTimeLong(), selfSsc5.getDesc(),"IDC");

			} else {
				log.info(GAME_CODE.getSign() + "期数：" + period + "IDC开奖数据抓取失败，无需保存抓取数据");
			}
		}

	}

	private CloudLotterySelfSsc5 getLotterySelfSsc5(String period) throws InterruptedException, ParseException {
		String handicapUrl = "http://yun.ngk77.com/ui-02/detail.aspx/GetWinningnohistoryList";
		String day;
		Date nowTime = new Date();
		if (nowTime.before(PeriodIdcTool.SELF_SSC_5.getBeginTime())) {
			day = DateTool.getDate(DateTool.getBeforeDate(nowTime));
		} else {
			day = DateTool.getDate(nowTime);
		}
		String entity = String.format("{gameno:37,gamegroupno:3,pagesize:15,curentsize:1,transdate:\"%s\"}", day);
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
		CloudLotterySelfSsc5 selfSsc5 = new CloudLotterySelfSsc5();
		long drawTime = PeriodIdcTool.SELF_SSC_5.getLotteryDrawTime();
		selfSsc5.setDrawTime(new Date(drawTime));
		selfSsc5.setDrawTimeLong(drawTime);
		selfSsc5.setHandicapCode("IDC");
		selfSsc5.setDrawType("IDC");
		selfSsc5.setPeriod(period);
		selfSsc5.setDrawNumber(StringUtils.join(drawNumbers, ","));
		selfSsc5.setDrawItem(PeriodIdcTool.SELF_SSC_5.getDrawItem(selfSsc5.getDrawNumber()));
		selfSsc5.setP1Number(drawNumbers[0]);
		selfSsc5.setP2Number(drawNumbers[1]);
		selfSsc5.setP3Number(drawNumbers[2]);
		selfSsc5.setP4Number(drawNumbers[3]);
		selfSsc5.setP5Number(drawNumbers[4]);
		selfSsc5.setCreateTime(new Date());
		selfSsc5.setCreateTimeLong(System.currentTimeMillis());
		selfSsc5.setUpdateTimeLong(System.currentTimeMillis());
		selfSsc5.setState(StateEnum.OPEN.name());
		selfSsc5.setDesc("{IDC}" + GAME_CODE.getGameName() + "开奖数据抓取成功");
		return selfSsc5;
	}
}
