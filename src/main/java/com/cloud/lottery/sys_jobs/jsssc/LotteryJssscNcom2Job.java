package com.cloud.lottery.sys_jobs.jsssc;

import com.alibaba.fastjson.JSONObject;
import com.cloud.lottery.LotteryTool;
import com.cloud.lottery.PeriodNcom2Tool;
import com.cloud.lottery.cloud_lottery_jsssc.entity.CloudLotteryJsssc;
import com.cloud.lottery.cloud_lottery_jsssc.service.CloudLotteryJssscService;
import org.apache.commons.lang.StringUtils;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.httpclient.HttpClientTool;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.util.Date;

/**
 * @Description: HQ盘口开奖抓取
 * @Author: wwj
 * @Date: 2019-11-19 09:40
 * @Version: v1.0
 */
@Deprecated
public class LotteryJssscNcom2Job extends BaseCommJob {
    private int index = 0;
    public static final int CYCLE = 100;
    public static final LotteryTool.Code GAME_CODE = LotteryTool.Code.JSSSC;

    @Override
    public void executeJob(JobExecutionContext context) throws Exception {

        if (System.currentTimeMillis() - PeriodNcom2Tool.getLotteryJssscDrawTime() >= 25000L) {
            log.info("跳过循环");
            saveLog = false;
            return;
        }
        long period = Long.valueOf(PeriodNcom2Tool.findLotteryJssscPeriod());
        CloudLotteryJsssc jsssc = null;
        try {
            jsssc = getLotteryJsssc(period);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        CloudLotteryJssscService jssscService = new CloudLotteryJssscService();

        //读取数据库数据
        CloudLotteryJsssc entity = jssscService.findByPeriod(period,"NCOM2");
        //数据库没有数据
        if (entity == null) {
            if (jsssc == null) {
                //全无数据
                jsssc = new CloudLotteryJsssc();
                jsssc.setPeriod(period);
                jsssc.setCreateTime(new Date());
                jsssc.setCreateTimeLong(System.currentTimeMillis());
                jsssc.setState(StateEnum.CLOSE.name());
                jsssc.setDesc("{NCOM2}急速时时彩开奖数据抓取失败");
                jssscService.save(jsssc);
                log.info(GAME_CODE.getSign() + "期数：" + period + jsssc.getDesc());
                return;
            }
            //抓取到数据
            jssscService.save(jsssc);
            log.info(GAME_CODE.getSign() + "期数：" + period + jsssc.getDesc());
            // 发送mq
            LotteryTool.sendMq(log, period, GAME_CODE, "NCOM2", jsssc.getDrawNumber(), jsssc.getDrawItem(),
                    jsssc.getDrawTimeLong(), jsssc.getDesc(),"NCOM2");

        } else if (!entity.getState().equals(StateEnum.OPEN.name())) {
            //数据库存放的是失败的数据
            //抓取到数据
            if (jsssc != null) {
                jsssc.setIdx(entity.getIdx());
                jsssc.setCloudLotteryJssscId(entity.getCloudLotteryJssscId());
                jsssc.setCreateTime(entity.getCreateTime());
                jsssc.setCreateTimeLong(entity.getCreateTimeLong());
                jsssc.setUpdateTime(new Date());
                jsssc.setUpdateTimeLong(System.currentTimeMillis());
                //成功设置抓取信息
                jssscService.update(jsssc);
                log.info(GAME_CODE.getSign() + "期数：" + period + "，开奖数据：" + jsssc.getDesc());
                // 发送mq
                LotteryTool.sendMq(log, period, GAME_CODE, "NCOM2", jsssc.getDrawNumber(), jsssc.getDrawItem(),
                        jsssc.getDrawTimeLong(), jsssc.getDesc(),"NCOM2");
            } else {
                log.info(GAME_CODE.getSign() + "期数：" + period + "时时资讯[JSON]开奖数据抓取失败，无需保存抓取数据");
            }
        }
    }

    /**
     * 获取急速赛车开奖实体
     * @param period
     * @return
     */
    private CloudLotteryJsssc getLotteryJsssc(long period) throws IOException, InterruptedException {
        String handicapUrl = "http://vip.hahha320.com:99/index.php?s=/Home/Heat/newopen&id=12";
        JSONObject jsonObject = getJsonData(handicapUrl,period);
        if(jsonObject == null){
            return null;
        }
        String[] codes = jsonObject.getString("opencode").split(",");
        Integer[] drawNumbers = new Integer[codes.length];
        for (int i = 0; i < codes.length; i++) {
            drawNumbers[i] = Integer.parseInt(codes[i]);
        }
        CloudLotteryJsssc jsssc = new CloudLotteryJsssc();
        long drawTime = PeriodNcom2Tool.getLotteryJssscDrawTime();
        jsssc.setDrawTime(new Date(drawTime));
        jsssc.setDrawTimeLong(drawTime);
        jsssc.setHandicapCode("NCOM2");
        jsssc.setPeriod(period);
        jsssc.setDrawNumber(StringUtils.join(drawNumbers, ","));
        jsssc.setP1Number(drawNumbers[0]);
        jsssc.setP2Number(drawNumbers[1]);
        jsssc.setP3Number(drawNumbers[2]);
        jsssc.setP4Number(drawNumbers[3]);
        jsssc.setP5Number(drawNumbers[4]);

        jsssc.setCreateTime(new Date());
        jsssc.setCreateTimeLong(System.currentTimeMillis());
        jsssc.setState(StateEnum.OPEN.name());
        jsssc.setDesc("{NCOM2}急速时时彩开奖数据抓取成功");
        return jsssc;
    }

    /**
     * 获取开奖JSON
     * @param handicapUrl
     * @param period
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    private JSONObject getJsonData(String handicapUrl,long period) throws IOException, InterruptedException {
        if (index > CYCLE) {
            log.info(GAME_CODE.getSign() + "{NCOM2}急速时时彩开奖数据，期数：【" + period + "】，循环次数超过20次");
            return null;
        }
        String html = HttpClientTool.doGet(handicapUrl);
        for (; index < CYCLE; index++) {
            //没有抓取到
            if (StringTool.isEmpty(html)) {
                Thread.sleep(RandomTool.getInt(500, 1500));
                html = HttpClientTool.doGet(handicapUrl);
            } else {
                break;
            }
        }
        if (StringTool.isEmpty(html)) {
            log.info(GAME_CODE.getSign() + "{NCOM2}急速时时彩开奖数据，期数：【" + period + "】，抓取数据为空");
            index++;
            sleep();
            return getJsonData(handicapUrl, period);
        }
        try {
            JSONObject jsonObject = JSONObject.parseObject(html);
            JSONObject openDate = jsonObject.getJSONObject("open_data");
            String crawlPeriod = openDate.getString("expect");
            if(!crawlPeriod.equals(period+"")){
                log.info(GAME_CODE.getSign() + "{NCOM2}急速时时彩开奖数据，抓取期数为：【" + crawlPeriod + "】，应有期数为：【" + period + "】");
                index++;
                sleep();
                return getJsonData(handicapUrl,period);
            }
            return openDate;
        }catch (Exception e){
            log.error(GAME_CODE.getSign() + "{NCOM2}急速时时彩开奖数据，期数：【" + period + "】，解析错误，页面【" + html + "】，错误：", e);
            index++;
            return getJsonData(handicapUrl, period);
        }

    }
}
