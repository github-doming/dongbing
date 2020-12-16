package com.cloud.lottery.sys_jobs.self105;

import c.a.util.core.string.StringUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
 * 抓取历史开奖数据  FT5
 *
 * @author cxy
 * @Description:
 * @date 2020年6月15日 上午10:36:13
 */
public class LotterySelf105HistoryJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.SELF_10_5;

	private int index = 0;

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		captureData();
	}


	private void captureData() throws Exception {
		String url = "https://www.vegstop1.com/vs/getResult3.do";
		Map<String, Object> param = new HashMap<>(2);
		param.put("date", "");
		param.put("issueNo", "");
		String html = HttpClientTool.doPost(url, param);
		//循环抓取数据50次，抓到跳出。
		for (; index < 15; index++) {
			if (StringTool.isEmpty(html)) {
				Thread.sleep(RandomTool.getInt(500, 1500));
				html = HttpClientTool.doPost(url, param);
			} else {
				break;
			}
		}

		//没有抓取到数据
		if (StringTool.isEmpty(html)) {
			log.info(GAME_CODE.getSign() + "爬取数据失败");
			return;
		}

		//转换为文档处理
		JSONArray results = JSONObject.parseObject(html).getJSONObject("dataObject").getJSONArray("results");
		CloudLotterySelf105Service self105Service = new CloudLotterySelf105Service();
		for (int i = 0; i < results.size(); i++) {
			JSONObject json = results.getJSONObject(i);
			String period = json.getString("issueNo");
			if (StringUtil.isNotBlank(period)) {
				CloudLotterySelf105 entity = self105Service.findByPeriod(period, "NEWWS");
				if (entity == null) {
					Date drawTime = new Date(PeriodNewWsTool.SELF_10_5.getDrawTime(period));
					String drawNumber = json.getString("numbers");
					String[] numberArray = drawNumber.split(",");
					CloudLotterySelf105 lotterySelf105 = new CloudLotterySelf105();
					lotterySelf105.setPeriod(period);
					lotterySelf105.setDrawTime(drawTime);
					lotterySelf105.setDrawTimeLong(drawTime.getTime());
					lotterySelf105.setDrawNumber(drawNumber);
					lotterySelf105.setDrawType("NEWWS");
					lotterySelf105.setHandicapCode("NEWWS");
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
					lotterySelf105.setDesc("[NEWWS_飞艇5分]开奖数据历史整理");
					self105Service.save(lotterySelf105);

					log.info(GAME_CODE.getSign() + "期数：" + period + lotterySelf105.getDesc());
					// 发送mq
					LotteryTool.sendMq(log, period, GAME_CODE, lotterySelf105.getHandicapCode(), lotterySelf105.getDrawNumber(),
							lotterySelf105.getDrawItem(), lotterySelf105.getDrawTimeLong(), lotterySelf105.getDesc(), "NEWWS");

				}
			}
		}

	}

}
