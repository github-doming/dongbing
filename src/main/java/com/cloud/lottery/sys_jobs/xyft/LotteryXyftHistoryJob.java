package com.cloud.lottery.sys_jobs.xyft;

import c.a.util.core.string.StringUtil;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_xyft.t.entity.CloudLotteryXyftT;
import com.cloud.lottery.cloud_lottery_xyft.t.service.CloudLotteryXyftTService;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
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
 * 抓取历史开奖数据  XYFT
 * 
 * @Description: 
 * @ClassName: LotteryXyftHistoryJob 
 * @date 2019年6月1日 上午10:36:13 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2014-2016 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class LotteryXyftHistoryJob extends BaseCommJob{

	private int index = 0;

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
	private Element captureData() throws Exception {
		String url =	LotteryTool.getCsjHost().concat("/xyft/kaijiang/");
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
			log.info(GAME_CODE.getSign() + "爬取数据失败");
			return null;
		}

		//转换为文档处理
		Document document = Jsoup.parse(html);
		Elements trList = document.select("#history>tbody>tr");


        if(ContainerTool.isEmpty(trList)){
			if (index < 5) {
				//没有找到元素并且循环次数低于5次
				index = 3;
				captureData();
			} else {
				return null;
			}
		}
		CloudLotteryXyftTService xyftTService = new CloudLotteryXyftTService();

        for (Element tdElement : trList) {
			String period = tdElement.getElementsByClass("font_gray666").get(0).text();
			if(StringUtil.isNotBlank(period)){
				//读取数据库数据
				CloudLotteryXyftT entity = xyftTService.findByPeriod(period, "IDC");

				Date drawTime = new Date( PeriodTool.XYFT.getDrawTime(period));
				String drawNumber = tdElement.getElementsByClass("number_pk10").get(0).text();
				String[] numberArray = drawNumber.split(" ");
				if(entity == null){
					CloudLotteryXyftT xyftT = new CloudLotteryXyftT();
					xyftT.setPeriod(period);
					xyftT.setHandicapCode("IDC");
					xyftT.setDrawType("CSJ");
					xyftT.setDrawTime(drawTime);
					xyftT.setDrawTimeLong(drawTime.getTime());
					xyftT.setDrawNumber(StringUtils.join(numberArray, ","));
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
					xyftT.setDesc("彩世界[页面]开奖数据历史整理");
					xyftTService.save(xyftT);
					
					log.info(GAME_CODE.getSign() + "期数：" + period + xyftT.getDesc());
					// 发送mq
					LotteryTool.sendMq(log, period, GAME_CODE, "IDC", xyftT.getDrawNumber(), xyftT.getDrawItem(),
							xyftT.getDrawTimeLong(), xyftT.getDesc(),"CSJ");
				}
			}

		}
		return null;
	}
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.XYFT;

}
