package com.cloud.lottery.sys_jobs.xync;

import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodTool;
import com.cloud.lottery.cloud_lottery_xync.entity.CloudLotteryXync;
import com.cloud.lottery.cloud_lottery_xync.service.CloudLotteryXyncService;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.JobExecutionContext;

import java.util.Date;
/**
 * 抓取幸运农场历史开奖数据
 *
 * @Author: Dongming
 * @Date: 2020-04-18 14:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryXyncHistoryJob extends BaseCommJob {

    private int index = 0;

    @Override public void executeJob(JobExecutionContext context) throws Exception {
        captureData();
    }

    private void captureData() throws Exception {
        if (index > 50) {
            log.info(GAME_CODE.getSign() + "彩世界[历史]开奖数据，循环次数超过100次");
            return;
        }

        String url = LotteryTool.getCsjHost().concat("/xync/kaijiang/");
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
            return ;
        }
        //转换为文档处理
        Document document = Jsoup.parse(html);
        Elements trList = document.select("#dataContainer>tbody>tr");
        if(ContainerTool.isEmpty(trList) || trList.isEmpty()){
            if (index < 25) {
                //没有找到元素并且循环次数低于25次
                index ++;
                captureData();
            } else {
                log.info(GAME_CODE.getSign() + "彩世界[历史]开奖数据，数据抓取错误");
                return;
            }
        }
        CloudLotteryXyncService xyncService = new CloudLotteryXyncService();

        for (Element tdElement : trList) {
            String period = tdElement.getElementsByClass("font_gray666").get(0).text();
            if(StringTool.notEmpty(period)){
                CloudLotteryXync entity = xyncService.findByPeriod(period);
                //数据库中没有该数据
                if(entity == null){
                    Date drawTime = new Date(PeriodTool.XYNC.getDrawTime(period));
                    Elements numberXync = tdElement.select("div.number_xync>span");
                    StringBuilder 	numberText = new StringBuilder();
                    for (Element element : numberXync) {
                        numberText.append(NumberTool.getInteger(element.attr("class").substring(3))).append(",");
                    }
                    String[] numberArray = numberText.toString().split(",");
                    CloudLotteryXync xync = new CloudLotteryXync();
                    xync.setPeriod(period);
                    xync.setHandicapCode("IDC");
					xync.setDrawType("CSJ");
                    xync.setDrawTime(drawTime);
                    xync.setDrawTimeLong(drawTime.getTime());
                    xync.setDrawNumber(StringUtils.join(numberArray, ","));
                    xync.setDrawItem(PeriodTool.XYNC.getDrawItem(xync.getDrawNumber()));
                    xync.setP1Number(numberArray[0].trim());
                    xync.setP2Number(numberArray[1].trim());
                    xync.setP3Number(numberArray[2].trim());
                    xync.setP4Number(numberArray[3].trim());
                    xync.setP5Number(numberArray[4].trim());
                    xync.setP6Number(numberArray[5].trim());
                    xync.setP7Number(numberArray[6].trim());
                    xync.setP8Number(numberArray[7].trim());
                    xync.setCreateTime(new Date());
                    xync.setCreateTimeLong(System.currentTimeMillis());
                    xync.setUpdateTime(new Date());
                    xync.setUpdateTimeLong(System.currentTimeMillis());
                    xync.setState(StateEnum.OPEN.name());
                    xync.setDesc("彩世界[页面]开奖数据历史整理");
                    xyncService.save(xync);

                    log.info(GAME_CODE.getSign() + "期数：" + period + xync.getDesc());
                    // 发送mq
                    LotteryTool.sendMq(log, period, GAME_CODE, "IDC", xync.getDrawNumber(), xync.getDrawItem(),
                            xync.getDrawTimeLong(), xync.getDesc(),"CSJ");
                }
            }

        }
    }
    public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.XYNC;
}
