package com.ibm.common.test.wwj.handicap.hq;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibm.common.test.wwj.handicap.GrabAgent;
import com.ibm.common.test.wwj.handicap.HttpType;
import com.ibm.common.utils.game.GameUtil;
import org.apache.commons.collections.map.HashedMap;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;
import org.doming.develop.http.httpclient.HttpClientConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 环球代理盘口爬虫抓取类
 * @Author: Dongming
 * @Date: 2019-11-22 16:24
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HqAgentGrab extends AbsHqGrab implements GrabAgent {


	@Override public String[] routes(HttpClientConfig httpConfig, String handicapCaptcha, int... index) {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		return routes(httpConfig,httpConfig.url(), handicapCaptcha,"2", index[0]);
	}

	/**
	 * 登录
	 *
	 * @param httpConfig
	 * @param loginUrl   登录地址
	 * @param verifyCode 验证码
	 * @param account    盘口账号
	 * @param password   盘口密码
	 * @return 登录结果
	 */
	@Override
	public String login(HttpClientConfig httpConfig, String loginUrl, String verifyCode, String account, String password) {
		return null;
	}


	/**
	 * 获取会员列表信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @return 会员列表信息
	 */
	public List<Map<String,String>> getMemberListInfo(HttpClientConfig httpConfig, String projectHost, int... index){
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		httpConfig.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
		String url = projectHost.concat("/Member/GetMemberList?");
		String parameters = "Status=0&&lotteryId=4&_="+ System.currentTimeMillis();
		try {
			String html = html(httpConfig.url(url.concat(parameters)).method(HttpConfig.Method.GET), HttpType.MemberList);
			if (StringTool.isEmpty(html)) {
				log.error("获取会员列表信息失败");
				sleep("获取会员列表信息");
				return getMemberListInfo(httpConfig, projectHost, ++index[0]);
			}
			return getMemberInfo(html);
		} catch (Exception e) {
			log.error("获取会员列表信息失败", e);
			sleep("获取会员列表信息");
			return getMemberListInfo(httpConfig, projectHost, ++index[0]);
		}
	}

	/**
	 * 获取未结算摘要信息
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param lotteryId   抓取的游戏
	 * @param day         抓取的天数
	 * @return 未结算摘要信息
	 */
	public Map<String, Map<String, Integer>> getBetSummary(HttpClientConfig httpConfig, String projectHost,
														   String lotteryId,String day, int... index) throws InterruptedException {
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = projectHost.concat("/Report/GetSummary?");
		String parametersFormat = "lotteryId=%s&startDate=%s&endDate=%s&ReportAction=0&periodId=0&moneyType=0&reportType=0&SettleStatus=0&Action=summary&_=%d";
		String parameters = String
				.format(parametersFormat, lotteryId, day, day, System.currentTimeMillis());
		String html=null;
		try {
			html = html(httpConfig.url(url.concat(parameters)).method(HttpConfig.Method.GET),HttpType.BetSummary);
			if (StringTool.isEmpty(html)) {
				log.error("获取未结算摘要信息失败");
				sleep("获取未结算摘要信息");
				return getBetSummary(httpConfig, projectHost, lotteryId, day, ++index[0]);
			}
			if(StringTool.isContains(html,"robot7_session_id")){
				return null;
			}
			return getBetSummary(html);

		} catch (Exception e) {
			log.error("获取未结算摘要信息失败", e);
			sleep("获取未结算摘要信息");
			return getBetSummary(httpConfig, projectHost, lotteryId, day, ++index[0]);
		}
	}

	/**
	 * 获取投注详情
	 *
	 * @param httpConfig  http请求配置类
	 * @param projectHost 主机地址
	 * @param lotteryId   抓取的游戏
	 * @param day         抓取的天
	 * @param memberId    会员ID
	 * @return 投注详情
	 */
	public String getBetDetail(HttpClientConfig httpConfig, String projectHost, String lotteryId,String period,
							   String day, Integer memberId, int... index){
		if (index.length == 0) {
			index = new int[1];
		}
		if (index[0] > MAX_RECURSIVE_SIZE) {
			return null;
		}
		String url = projectHost.concat("/Report/GetBetDetail?");
		String parametersFormat = "startDate=%s&endDate=%s&PreStartDate=%s&PreEndDate=%s"
				+ "&periodId=%s&reportType=0&Action=member&MemberId=%d&lotteryId=%s&PreLotteryId=%s&"
				+ "moneyType=0&SettleStatus=0BetNo=&IsDirectlyMember=0&ReportAction=0&_=%d";
		String parameters = String
				.format(parametersFormat, day, day, day, day, period, memberId, lotteryId, lotteryId,
						System.currentTimeMillis());
		String html=null;
		try {
			html = html(httpConfig.url(url.concat(parameters)).method(HttpConfig.Method.GET),HttpType.BetDetail);
			if (StringTool.isEmpty(html)) {
				log.error("获取未结算摘要信息失败");
				sleep("获取未结算摘要信息");
				return getBetDetail(httpConfig, projectHost, lotteryId, period, day, memberId, ++index[0]);
			}
			if(StringTool.isContains(html,"robot7_session_id","document.cookie")){
				return null;
			}
			return html;
		} catch (Exception e) {
			log.error("获取未结算摘要信息失败", e);
			sleep("获取未结算摘要信息");
			return getBetDetail(httpConfig, projectHost, lotteryId, period, day, memberId, ++index[0]);
		}
	}

	/**
	 * 获取投注详情
	 *
	 * @param betDetail    投注详情
	 * @param gameCode     游戏编码
	 * @param html         投注详情页面
	 * @param subOddNumber 上一次请求的主单号
	 * @return 投注详情
	 */
	public Map<String, Object> getBetDetail(Map<String, Object> betDetail, GameUtil.Code gameCode, String html,
											String subOddNumber) {
		//没有投注项
		if (!StringTool.contains(html, "BetNoFullName")) {
			return null;
		}
		JSONObject data = JSONObject.parseObject(html).getJSONObject("Data");

		JSONArray dataArr = data.getJSONArray("Data");
		JSONObject betDetailInfo;
		List<Map<String, Object>> details;
		if (betDetail == null) {
			betDetail = new HashedMap(2);
			details = new ArrayList<>(data.getInteger("RecordCount") * 3 / 4 + 1);
		} else {
			details = (List<Map<String, Object>>) betDetail.get("details");
		}
		//放入投注项
		String maxOddNumber = betDetail.getOrDefault("maxOddNumber", subOddNumber).toString();
		int compare = maxOddNumber.compareTo(subOddNumber);
		boolean flag = true;
		for (int i = 0; i < dataArr.size(); i++) {
			betDetailInfo = dataArr.getJSONObject(i);
			String oddNumber = betDetailInfo.getString("SerialNo");
			int tmpCompare = oddNumber.compareTo(subOddNumber);
			if (tmpCompare < 0) { // ?
				flag = false;
				break;
			} else {
				if (compare < tmpCompare) {
					compare = tmpCompare;
					maxOddNumber = oddNumber;
				}
			}
			Map<String, Object> info = new HashMap<>(2);
			//放入详情信息
			String betInfo = betDetailInfo.getString("BetNoFullName");
			info.put("betItem", getBetItem(gameCode, betInfo));
			info.put("funds", betDetailInfo.get("BetMoney"));
			details.add(info);
		}
		int pageIndex = data.getInteger("PageIndex");
		if (flag && data.getInteger("PageCount") > pageIndex) {
			betDetail.put("pageIndex", pageIndex + 1);
			betDetail.put("pageSize", data.getInteger("PageSize"));
		} else {
			betDetail.remove("pageIndex");
			betDetail.remove("pageSize");
		}
		betDetail.put("maxOddNumber", maxOddNumber);
		betDetail.put("details", details);
		return betDetail;
	}

}
