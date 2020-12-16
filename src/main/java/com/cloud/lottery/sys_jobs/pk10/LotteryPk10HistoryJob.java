package com.cloud.lottery.sys_jobs.pk10;

import c.a.util.core.string.StringUtil;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_pk10.t.entity.CloudLotteryPk10T;
import com.cloud.lottery.cloud_lottery_pk10.t.service.CloudLotteryPk10TService;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.DateTool;
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
 * @Description: 抓取历史开奖数据  pk10
 * @Author: Dongming
 * @Date: 2018-10-24 15:56
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryPk10HistoryJob  extends BaseCommJob{

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
	 * 返回类型 List<GaRepDrawPk10T>
	 */
	private String captureData() throws Exception {

		String url =	LotteryTool.getCsjHost().concat("/pk10/kaijiang");

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


		if (trList == null || trList.isEmpty()) {
			if (index < 5) {
				//没有找到元素并且循环次数低于5次
				index = 3;
				captureData();
			} else {
				return null;
			}
		}

		CloudLotteryPk10TService pk10TService = new CloudLotteryPk10TService();
		for (Element tdElement : trList) {
			String period = tdElement.getElementsByClass("font_gray666").get(0).text();

			if(StringUtil.isNotBlank(period)){
				
				
				//读取数据库数据
				CloudLotteryPk10T entity = pk10TService.findByPeriod(Integer.parseInt(period));
				
				Date drawTime = DateTool.getMdHmDate(tdElement.getElementsByClass("font_gray999").get(0).text());
				String drawNumber = tdElement.getElementsByClass("number_pk10").get(0).text();
				String[] numberArray = drawNumber.split(" ");
				//数据库没有数据
				if (entity == null) {
					CloudLotteryPk10T pk10T = new CloudLotteryPk10T();
					pk10T.setPeriod(period);
					pk10T.setDrawType("CSJ");
					pk10T.setDrawTime(drawTime);
					pk10T.setDrawTimeLong(drawTime.getTime());
					pk10T.setDrawNumber(StringUtils.join(numberArray, ","));
					pk10T.setDrawItem(PeriodTool.PK10.getDrawItem(pk10T.getDrawNumber()));
					pk10T.setP1Number(numberArray[0].trim());
					pk10T.setP2Number(numberArray[1].trim());
					pk10T.setP3Number(numberArray[2].trim());
					pk10T.setP4Number(numberArray[3].trim());
					pk10T.setP5Number(numberArray[4].trim());
					pk10T.setP6Number(numberArray[5].trim());
					pk10T.setP7Number(numberArray[6].trim());
					pk10T.setP8Number(numberArray[7].trim());
					pk10T.setP9Number(numberArray[8].trim());
					pk10T.setP10Number(numberArray[9].trim());
					pk10T.setCreateTime(new Date());
					pk10T.setCreateTimeLong(pk10T.getCreateTime().getTime());
					pk10T.setUpdateTime(new Date());
					pk10T.setUpdateTimeLong(pk10T.getUpdateTime().getTime());
					pk10T.setState(StateEnum.OPEN.name());
					pk10T.setDesc("彩世界[页面]开奖数据历史整理");
					pk10TService.save(pk10T);
					
					log.info(GAME_CODE.getSign() + "期数：" + period + pk10T.getDesc());
					// 发送mq
					LotteryTool.sendMq(log, period, GAME_CODE, "IDC", pk10T.getDrawNumber(), pk10T.getDrawItem(),
							pk10T.getDrawTimeLong(), pk10T.getDesc(),"CSJ");
				}
			}

		}
		return null;
	}
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.PK10;
}
