package com.ibm.old.v1.common.doming.test.handicap;
import c.a.util.core.test.CommTest;
import com.ibm.old.v1.common.doming.configs.Ws2Config;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.Ws2Util;
import net.sf.json.JSONObject;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.LogTool;
import org.junit.Test;

import java.util.Date;

/**
 * @Description:
 * @Author: Dongming
 * @Date: 218-12-17 11:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class Ws2Test extends CommTest {

	@Test public void test() {
		String id = "123" ;
		String memberAccount = "hgdb-0001" ;
		String memberPassword = "Aa135135.." ;
		String handicapUrl = "http://sf33.qr68.us/" ;
		String handicapCaptcha = "058yg" ;
		String gameCode = IbmGameEnum.PK10.name();

		String[] betItem = {"第一名|大#1", "第一名|大#2", "第一名|大#10", "第一名|大#21", "第一名|大#8", "第一名|大#2", "第三名|虎#2"};

		Ws2Util ws2Util = Ws2Util.findInstance();

		JsonResultBeanPlus loginBean = ws2Util.login(id, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
		System.out.println("登陆信息 = " + loginBean.toJsonString());
		System.out.println(LogTool.printHashtagM());

		//获取用户是否停押状态
		JsonResultBeanPlus betInfoBean=ws2Util.betInfo(id,Ws2Config.GAME_CODE_MAP.get(gameCode),Ws2Config.getBetCode("PK10","ballNO15"),
				IbmStateEnum.LOGIN);
		//获取到游戏投注信息时判定用户状态是否为停押，获取失败就跳过暂不处理，由投注时进行在次获取
		if(betInfoBean.isSuccess()){
			log.info("WS2盘口会员【"+id + "】游戏投注信息 = " + betInfoBean.toJsonString());
			JSONObject betInfo = (JSONObject)betInfoBean.getData();
			JSONObject dataJson=betInfo.getJSONObject("data");
			if(dataJson.getInt("user_status")==2){

			}
		}

		JsonResultBeanPlus userInfoBean = ws2Util.userInfo(id, Ws2Config.getGameCode(gameCode));
		System.out.println("用户信息 = " + userInfoBean.toJsonString());
		JSONObject userInfo = (JSONObject) userInfoBean.getData();
		JSONObject userObj = userInfo.getJSONObject("data").getJSONObject("user");
		System.out.println("账户名称 = " + userObj.getString("account"));
		System.out.println("账户余额 = " + userObj.getString("re_credit"));
		System.out.println("已下金额 = " + userObj.getString("total_amount"));
		System.out.println(LogTool.printHashtagM());

//		JsonResultBeanPlus betInfoBean = ws2Util.betInfo(id, Ws2Config.getGameCode(gameCode),
//				Ws2Config.BET_INFO_CODE.get(Ws2Config.getGameCode(gameCode)));
//		System.out.println("游戏投注信息 = " + betInfoBean.toJsonString());
//		JSONObject betInfo = (JSONObject) betInfoBean.getData();
//		JSONObject betNotice = betInfo.getJSONObject("data").getJSONObject("betnotice");
//		System.out.println("当前期数=" + betNotice.getString("timesnow"));
//		System.out.println("开奖时间=" + betNotice.getString("timeopen"));
//		System.out.println("封盘时间=" + betNotice.getString("timeclose"));

		System.out.println(LogTool.printHashtagM());

		//		JsonResultBeanPlus betBean = ws2Util
		//				.betting(id, Ws2Config.getGameCode(gameCode), Ws2Config.BET_INFO_CODE.get(Ws2Config.getGameCode(gameCode)), JSONArray.fromObject(betItem));
		//		System.out.println("投注结果 = " + betBean.toJsonString());
		//		System.out.println(LogTool.printHashtagM());

	}
	@Test public void test2() throws Exception {
		String id = "123" ;
		String memberAccount = "hgdb-0015" ;
		String memberPassword = "Aa135135.." ;
		String handicapUrl = "http://sf33.qr68.us/" ;
		String handicapCaptcha = "058yg" ;
		String gameCode = IbmGameEnum.XYFT.name();
		String period = "20190703-180" ;
		String[] betItem = {"第一名|大#1", "第一名|大#2", "第一名|大#10", "第一名|大#21", "第一名|大#8", "第一名|大#2", "第三名|虎#2"};

		Ws2Util ws2Util = Ws2Util.findInstance();

		//获取游戏code
		IbmGameEnum game = IbmGameEnum.valueOf(gameCode);
		//获取日期字符串
		String date;
		switch (game) {
			case PK10:
				date = DateTool.getDate(new Date());
				break;
			case XYFT:
				String[] str = period.split("-");
				StringBuilder stringBuilder = new StringBuilder(str[0]);
				stringBuilder.insert(6, "-");
				stringBuilder.insert(4, "-");
				date = stringBuilder.toString();
				break;
			default:
				throw new Exception("获取日期异常");
		}
		JsonResultBeanPlus loginBean = ws2Util.login(id, handicapUrl, handicapCaptcha, memberAccount, memberPassword);
		ws2Util.firstLogin(id, loginBean);
		System.out.println("登陆信息 = " + loginBean.toJsonString());
		System.out.println(LogTool.printHashtagM());

		JsonResultBeanPlus bean = ws2Util.betProfit(id, Ws2Config.GAME_PLATFORM_MAP.get(gameCode), date, period);
		System.out.println(bean.toJsonString());

		//		JsonResultBeanPlus userInfoBean = ws2Util.userInfo(id, Ws2Config.getGameCode(gameCode));
		//		System.out.println("用户信息 = " + userInfoBean.toJsonString());
		//		JSONObject userInfo = (JSONObject) userInfoBean.getData();
		//		JSONObject userObj = userInfo.getJSONObject("data").getJSONObject("user");
		//		System.out.println("账户名称 = " + userObj.getString("account"));
		//		System.out.println("账户余额 = " + userObj.getString("re_credit"));
		//		System.out.println("已下金额 = " + userObj.getString("total_amount"));
		//		System.out.println(LogTool.printHashtagM());

		//		JsonResultBeanPlus betInfoBean = ws2Util.betInfo(id, Ws2Config.getGameCode(gameCode),
		//				Ws2Config.BET_INFO_CODE.get(Ws2Config.getGameCode(gameCode)));
		//		System.out.println("游戏投注信息 = " + betInfoBean.toJsonString());
		//		JSONObject betInfo = (JSONObject) betInfoBean.getData();
		//		JSONObject betNotice = betInfo.getJSONObject("data").getJSONObject("betnotice");
		//		System.out.println("当前期数=" + betNotice.getString("timesnow"));
		//		System.out.println("开奖时间=" + betNotice.getString("timeopen"));
		//		System.out.println("封盘时间=" + betNotice.getString("timeclose"));

		//		System.out.println(LogTool.printHashtagM());
		//
		//		JsonResultBeanPlus betBean = ws2Util
		//				.betting(id, Ws2Config.getGameCode(gameCode), Ws2Config.BET_INFO_CODE.get(Ws2Config.getGameCode(gameCode)),
		//						userObj.getInt("re_credit"), JSONArray.fromObject(betItem));
		//		System.out.println("投注结果 = " + betBean.toJsonString());
		//		System.out.println(LogTool.printHashtagM());

	}
}
