package com.ibm.old.v1.client.core.job.bet;
import com.ibm.old.v1.client.ibm_client_bet.t.service.IbmClientBetTService;
import com.ibm.old.v1.client.ibm_client_exist_bet.t.service.IbmClientExistBetTService;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.common.doming.configs.Ws2Config;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.core.MessageReceipt;
import com.ibm.old.v1.common.doming.enums.IbmGameEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.enums.IbmHcCodeEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.Ws2Util;
import com.ibm.old.v1.common.doming.utils.job.QuartzIbmUtil;
import net.sf.json.JSONObject;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.NumberTool;
import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Description: 投注结果-定时任务
 * @Author: Dongming
 * @Date: 2019-03-27 11:22
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class BetProfitWs2Job extends BaseCommJob {

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		String existHmId = context.getMergedJobDataMap().getString("existHmId");
		String period = context.getMergedJobDataMap().get("period").toString();
		String gameCode = context.getMergedJobDataMap().get("gameCode").toString();
		log.trace("投注结果WS2盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】触发");
		try {
			//查询盘口会员是否登陆
			IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
			if (!handicapMemberTService.isLogin(existHmId)) {
				log.error("投注结果WS2盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】失败，用户处于登出状态");
				return;
			}
			JsonResultBeanPlus bean = execute(existHmId, gameCode, period);
			if (bean.isSuccess()) {
				QuartzIbmUtil.removeBetProfitJob(existHmId, IbmHandicapEnum.WS2, gameCode, period);
				log.info("投注结果WS2盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】成功:" + bean
						.toJsonString());

			} else {
				log.info("投注结果WS2盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】失败:" + bean
						.toJsonString());
			}
		} catch (Exception e) {
			QuartzIbmUtil.removeBetProfitJob(existHmId, IbmHandicapEnum.WS2, gameCode, period);
			log.error("投注结果WS2盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】异常:" + e.getMessage());
			throw e;
		}
	}

	/**
	 * 执行获取投注赔率的job
	 *
	 * @param existHmId 存在盘口会员id
	 * @param gameCode  游戏code
	 * @param period    期数
	 * @return 处理结果
	 */
	public JsonResultBeanPlus execute(String existHmId, String gameCode, String period) throws Exception {
		CurrentTransaction.transactionEnd();
		try {
			IbmClientBetTService betTService = new IbmClientBetTService();
			//获取游戏code
			IbmGameEnum game = IbmGameEnum.valueOf(gameCode);

			String date;
			//获取日期字符串
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

			//获取投注信息
			Ws2Util util = Ws2Util.findInstance();
			JsonResultBeanPlus bean = util.betProfit(existHmId, Ws2Config.GAME_PLATFORM_MAP.get(gameCode), date, period);

			//获取历史投注失败
			if (!bean.isSuccess()) {
				return bean;
			}

			//结算历史
			Map<String, Map<String, Object>> mapBetProfit = new HashMap<>();
			log.trace("WS2盘口用户【" + existHmId + "】获取的结算历史信息为：" + bean.toJsonString());
			if (ContainerTool.isEmpty( bean.getData())) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				bean.setSuccess(false);
				return bean;
			}
			List<Map<String, String>> resultList = (List<Map<String, String>>) bean.getData();

			for (Map<String, String> result : resultList) {
				//更新结算信息
				Map<String, Object> settlementInfo = betTService.updateSettlement(existHmId, gameCode, period, result);
				if (ContainerTool.isEmpty(settlementInfo)) {
					continue;
				}

				//准备将回传信息处理完成
				String existBetId = settlementInfo.get("existBetId").toString();
				if (mapBetProfit.containsKey(existBetId)) {
					settlementInfo.remove("existBetId");
					Map<String, Object> betProfit = mapBetProfit.get(existBetId);
					betProfit.put("ODDS_",
							betProfit.get("ODDS_").toString().concat(",").concat(settlementInfo.get("ODDS_").toString()));
					betProfit.put("PROFIT_T_", NumberTool.addInt(betProfit.get("PROFIT_T_"), settlementInfo.get("PROFIT_T_")));
				} else {
					settlementInfo.remove("betInfoExistId");
					mapBetProfit.put(existBetId, settlementInfo);
				}
			}

			if (ContainerTool.isEmpty(mapBetProfit)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_PROFIT_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				bean.setSuccess(false);
				return bean;
			}

			//更新到已存投注信息表
			IbmClientExistBetTService existBetTService = new IbmClientExistBetTService();
			boolean status = existBetTService.updateSettlement(mapBetProfit);
			if (!status) {
				log.info("WS2盘口用户【" + existHmId + "】批量更新投注信息失败");
				bean.putEnum(IbmHcCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			// 回传数据
			Map<String, List<Map<String, Object>>> finishBetItem = existBetTService
					.mapFinishBetItem(existHmId, gameCode, period);
			if (ContainerTool.isEmpty(finishBetItem)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_RESULT_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				bean.setSuccess(false);
				return bean;
			}
			bean.setSuccess(false);
			JSONObject userInfo=getUserInfo(existHmId,gameCode);

			bean.setData(MessageReceipt.messageReceiptBetFinish(userInfo, finishBetItem, gameCode, period,IbmHandicapEnum.WS2.name()));

			bean.success();
			return bean;
		} finally {
			CurrentTransaction.transactionBegin();
		}
	}
	/**
	 * 获取用户信息
	 *
	 * @return 用户信息
	 */
	private JSONObject getUserInfo(String existHmId,String game) {
		JSONObject info = new JSONObject();
		Ws2Util ws2Util = Ws2Util.findInstance();
		JsonResultBeanPlus userBean = ws2Util.userInfo(existHmId, Ws2Config.GAME_CODE_MAP.get(game));
		//获取信息失败
		if (!userBean.isSuccess()) {
			log.info("WS2盘口用户【" + existHmId + "】获取用户信息失败" + userBean.toJsonString());
		} else {
			log.info("WS2盘口用户【" + existHmId + "】获取用户信息成功" + userBean.toJsonString());
			JSONObject userInfo = (JSONObject) userBean.getData();
			JSONObject userObj = userInfo.getJSONObject("data").getJSONObject("user");
			info.put("existHmId", existHmId);
			info.put("NICKNAME", userObj.get("memberAccount"));
			info.put("BALANCE", userObj.getDouble("usedQuota"));
			info.put("PROFIT",  userObj.getDouble("profitAmount"));
		}
		return info;
	}

}
