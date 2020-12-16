package com.cloud.lottery.sys_jobs.cqssj;

import c.a.util.core.string.StringUtil;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_cqssc.t.entity.CloudLotteryCqsscT;
import com.cloud.lottery.cloud_lottery_cqssc.t.service.CloudLotteryCqsscTService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;

import java.text.ParseException;
import java.util.Date;

/**
 * @Description: 抓取历史开奖数据  CQSSC
 * @Author: Dongming
 * @Date: 2018-10-24 15:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryCqsscHistoryJob extends BaseCommJob {
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.CQSSC;

	private int index = 0;

	private Logger captureLog = Logger.getLogger("capture");

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {

		captureData();
		return;
	}


	/**
	 * 好彩网
	 * @Title: captureData
	 * @Description:
	 *
	 * 参数说明
	 * @return
	 * @throws InterruptedException
	 * @throws ParseException
	 * 返回类型 String
	 */
	private String captureData() throws Exception {

		String url =	LotteryTool.getCsjHost().concat("/shishicai/kaijiang/");

		String html = HttpClientTool.doGet(url);
		//循环抓取数据50次，抓到跳出。
		for (; index < 15; index++) {
			if (StringTool.isEmpty(html)) {
				Thread.sleep(RandomTool.getInt(500,1500));
				html = HttpClientTool.doGet(url);
			} else {
				break;
			}
		}

		//没有抓取到数据
		if (StringTool.isEmpty(html)) {
			captureLog.info(GAME_CODE.getSign()+ "爬取数据失败");
			return null;
		}

		//转换为文档处理
		Document document = Jsoup.parse(html);
		Elements trList = document.select("#tbHistory>tbody>tr");


		if (trList == null || trList.isEmpty()) {
			if (index < 5) {
				//没有找到元素并且循环次数低于5次
				index = 3;
				captureData();
			} else {
				return null;
			}
		}
		
		CloudLotteryCqsscTService cqsscTService = new CloudLotteryCqsscTService();
		
		for (int i = 1; i < trList.size(); i++) {
			Element tdElement = trList.get(i);
			String period = tdElement.getElementsByClass("font_gray666").get(0).text();

			if(StringUtil.isNotBlank(period)){
				
				//读取数据库数据
				CloudLotteryCqsscT entity = cqsscTService.findByPeriod(period);

				Date drawTime = new Date(PeriodTool.CQSSC.getDrawTime(period));
				String drawNumber = tdElement.getElementsByClass("number_redAndBlue").get(0).text();
				String[] numberArray = drawNumber.split(" ");
				
				if(entity==null){
					CloudLotteryCqsscT cqsscT = new CloudLotteryCqsscT();
					cqsscT.setPeriod(period);
					cqsscT.setDrawType("CSJ");
					cqsscT.setDrawTime(drawTime);
					cqsscT.setDrawTimeLong(drawTime.getTime());
					cqsscT.setDrawNumber(StringUtils.join(numberArray, ","));
					cqsscT.setDrawItem(PeriodTool.CQSSC.getDrawItem(cqsscT.getDrawNumber()));
					cqsscT.setP1Number(numberArray[0].trim());
					cqsscT.setP2Number(numberArray[1].trim());
					cqsscT.setP3Number(numberArray[2].trim());
					cqsscT.setP4Number(numberArray[3].trim());
					cqsscT.setP5Number(numberArray[4].trim());
					cqsscT.setCreateTime(new Date());
					cqsscT.setCreateTimeLong(cqsscT.getCreateTime().getTime());
					cqsscT.setUpdateTime(new Date());
					cqsscT.setUpdateTimeLong(cqsscT.getUpdateTime().getTime());
					cqsscT.setState(StateEnum.OPEN.name());
					cqsscT.setDesc("彩世界[页面]开奖数据历史整理");
					cqsscTService.save(cqsscT);
					
					log.info(GAME_CODE.getSign() + "期数：" + period + cqsscT.getDesc());
					// 发送mq
					LotteryTool.sendMq(log, period, GAME_CODE, "IDC", cqsscT.getDrawNumber(), cqsscT.getDrawItem(),
							cqsscT.getDrawTimeLong(), cqsscT.getDesc(),"CSJ");
				}
			}

		}
		return null;
	}
}
