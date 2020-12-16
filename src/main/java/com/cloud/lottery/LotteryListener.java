package com.cloud.lottery;
import com.alibaba.fastjson.JSONObject;
import org.doming.core.common.mq.BaseCommMq;
import org.doming.core.enums.TypeEnum;
import org.doming.core.tools.DateTool;
/**
 * @Description: 开奖结果监听实例
 * @Author: Dongming
 * @Date: 2019-01-23 15:41
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class LotteryListener extends BaseCommMq {

	@Override public String execute(String message) throws Exception {
		JSONObject jObj = JSONObject.parseObject(message);
		log.info("游戏为：" + jObj.get("code"));
		log.info("期数为：" + jObj.get("period"));
		log.info("抓取时间为：" + DateTool.getCnStr(jObj.getLong("drawTimeLong")));
		log.info("抓取号码为：" + jObj.get("drawNumber"));
		return TypeEnum.TRUE.name();
	}
}
