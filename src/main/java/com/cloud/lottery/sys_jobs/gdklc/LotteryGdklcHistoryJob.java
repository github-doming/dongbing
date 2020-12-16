package com.cloud.lottery.sys_jobs.gdklc;

import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_gdklc.entity.CloudLotteryGdklc;
import com.cloud.lottery.cloud_lottery_gdklc.service.CloudLotteryGdklcService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.doming.develop.mq.rabbitmq.RabbitMqUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;

import java.util.Date;
/**
 * 抓取广东快乐彩历史开奖数据
 *
 * @Author: Dongming
 * @Date: 2020-04-18 18:10
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryGdklcHistoryJob extends BaseCommJob {

	private int index = 0;
	@Override public void executeJob(JobExecutionContext context) throws Exception {
		captureData();

	}
	private void captureData() throws Exception {
		if (index > 50) {
			log.info(GAME_CODE.getSign() + "彩世界[历史]开奖数据，循环次数超过100次");
			return;
		}

		String url = LotteryTool.getCsjHost().concat("/gdkl10/kaijiang/");
		String html = HttpClientTool.doGet(url);
		//循环抓取数据50次，抓到跳出。
		for (; index < 50; index++) {
			if (StringTool.isEmpty(html)) {
				sleep();
				html = HttpClientTool.doGet(url);
			} else {
				break;
			}
		}
		//没有抓取到数据
		if (StringTool.isEmpty(html)) {
			log.info(GAME_CODE.getSign() + "彩世界[历史]开奖数据，爬取数据失败");
			return;
		}
		//转换为文档处理
		Document document = Jsoup.parse(html);
		Elements trList = document.select("#dataContainer>tbody>tr");
		if (ContainerTool.isEmpty(trList) || trList.isEmpty()) {
			if (index < 25) {
				//没有找到元素并且循环次数低于25次
				index++;
				captureData();
			} else {
				log.info(GAME_CODE.getSign() + "彩世界[历史]开奖数据，数据抓取错误");
				return;
			}
		}
		CloudLotteryGdklcService gdklcService = new CloudLotteryGdklcService();

		JSONObject message = new JSONObject();
		message.put("code", "GDKLC");
		message.put("handicapCode", "IDC");
		message.put("desc", "云平台历史整理彩世界数据-广东快乐彩");
		RabbitMqUtil util = RabbitMqUtil.findInstance();
		String text = "";
		for (Element tdElement : trList) {
			String period = tdElement.getElementsByClass("font_gray666").get(0).text();
			if (StringTool.notEmpty(period)) {
				CloudLotteryGdklc entity = gdklcService.findByPeriod(period);
				//数据库中没有该数据
				if (entity == null) {
					Date drawTime = new Date(PeriodTool.GDKLC.getDrawTime(period));
					text = tdElement.getElementsByClass("number_redAndBlue").get(0).text();
					String[] numberArray = text.split(" ");
                    String numbers= StringUtils.join(numberArray, ",");
                    if (numbers.startsWith("0")){
                        numbers = numbers.substring(1);
                    }
                    numbers = numbers.replace(",0",",");

					CloudLotteryGdklc gdklc = new CloudLotteryGdklc();
					gdklc.setPeriod(period);
					gdklc.setHandicapCode("IDC");
					gdklc.setDrawType("CSJ");
					gdklc.setDrawTime(drawTime);
					gdklc.setDrawTimeLong(drawTime.getTime());
					gdklc.setDrawNumber(numbers);
					gdklc.setDrawItem(PeriodTool.GDKLC.getDrawItem(gdklc.getDrawNumber()));
					gdklc.setP1Number(numberArray[0].trim());
					gdklc.setP2Number(numberArray[1].trim());
					gdklc.setP3Number(numberArray[2].trim());
					gdklc.setP4Number(numberArray[3].trim());
					gdklc.setP5Number(numberArray[4].trim());
					gdklc.setP6Number(numberArray[5].trim());
					gdklc.setP7Number(numberArray[6].trim());
					gdklc.setP8Number(numberArray[7].trim());
					gdklc.setCreateTime(new Date());
					gdklc.setCreateTimeLong(System.currentTimeMillis());
					gdklc.setUpdateTime(new Date());
					gdklc.setUpdateTimeLong(System.currentTimeMillis());
					gdklc.setState(StateEnum.OPEN.name());
					gdklc.setDesc("彩世界[页面]开奖数据历史整理");
					gdklcService.save(gdklc);

					log.info(GAME_CODE.getSign() + "期数：" + period + gdklc.getDesc());
					// 发送mq
					LotteryTool.sendMq(log, period, GAME_CODE, "IDC", gdklc.getDrawNumber(), gdklc.getDrawItem(),
							gdklc.getDrawTimeLong(), gdklc.getDesc(),"CSJ");
				}
			}

		}
	}
	public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.GDKLC;
}
