package com.ibm.old.v1.client.core.job.bet;
import com.ibm.old.v1.client.ibm_client_bet.t.entity.IbmClientBetT;
import com.ibm.old.v1.client.ibm_client_bet.t.service.IbmClientBetTService;
import com.ibm.old.v1.client.ibm_client_exist_bet.t.service.IbmClientExistBetTService;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.common.doming.configs.IdcConfig;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.core.MessageReceipt;
import com.ibm.old.v1.common.doming.enums.*;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.IdcUtil;
import com.ibm.old.v1.common.doming.utils.job.QuartzIbmUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.common.CurrentTransaction;
import org.doming.core.common.quartz.BaseCommJob;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.quartz.JobExecutionContext;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: IDC获取盈亏信息定时器
 * @Author: zjj
 * @Date: 2019-03-29 14:47
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class BetProfitIdcJob extends BaseCommJob {

	@Override public void executeJob(JobExecutionContext context) throws Exception {
		String gameCode = context.getMergedJobDataMap().getString("gameCode");
		String existHmId = context.getMergedJobDataMap().getString("existHmId");
		String period = context.getMergedJobDataMap().get("period").toString();
		log.trace("投注结果IDC盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】触发");
		try {
			//查询盘口会员是否登陆
			IbmClientHmTService handicapMemberTService = new IbmClientHmTService();
			if (!handicapMemberTService.isLogin(existHmId)) {
				log.error("投注结果IDC盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】失败，用户处于登出状态");
				return;
			}
			JsonResultBeanPlus bean = execute(existHmId, period, gameCode);
			if (bean.isSuccess()) {
				QuartzIbmUtil.removeBetProfitJob(existHmId, IbmHandicapEnum.IDC, gameCode, period);
				log.info("投注结果IDC盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】成功:" + bean
						.toJsonString());
			} else {
				log.info("投注结果IDC盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】失败:" + bean
						.toJsonString());
			}
		} catch (Exception e) {
			QuartzIbmUtil.removeBetProfitJob(existHmId, IbmHandicapEnum.IDC, gameCode, period);
			log.error("投注结果IDC盘口用户【" + existHmId + "】游戏【" + gameCode + "】期数【" + period + "】异常:" + e.getMessage());
			throw e;
		}
	}

	private JsonResultBeanPlus execute(String existHmId, String period, String gameCode) throws Exception {
		JsonResultBeanPlus bean=null;
		CurrentTransaction.endTransaction();
		try {
		bean = new JsonResultBeanPlus();
		IbmClientBetTService betTService = new IbmClientBetTService();

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
		//是否结算
		String isJs = "";

			//条数
			int pageSize = 10;
			//页数
			int curPage = 1;
			//判断是否有投注成功项
			List<IbmClientBetT> ibmClientBetTList = betTService.findList(existHmId, period, gameCode);
			if (ContainerTool.isEmpty(ibmClientBetTList)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				bean.setSuccess(false);
				return bean;
			}

			IdcUtil idcUtil = IdcUtil.findInstance();
			//获取未结算数据
			bean = idcUtil.profitLoss(existHmId, IdcConfig.GAME_BET_CODE.get(gameCode), date, isJs, curPage, pageSize);

			if (!bean.isSuccess()) {
				log.info("IDC盘口用户【" + existHmId + "】获取盈亏信息失败=" + bean.toJsonString());
				return bean;
			}
			JSONArray rowsArray = getRowsArray(bean);
			log.info("IDC盘口用户【" + existHmId + "】获取未结算数据" + rowsArray);
			bean.setSuccess(false);
			//判断未结算数据是否为空
			if (!ContainerTool.isEmpty(rowsArray)) {
				//判断未结算数据第一条盈亏信息的期数是否相同
				String lotteryPeriod = ibmClientBetTList.get(0).getPeriod();
				if (rowsArray.getJSONObject(0).get("roundno").equals(lotteryPeriod.replaceAll("-", ""))) {
					log.info("IDC盘口用户【" + existHmId + "】，gameCode=【" + gameCode + "】，period=【" + period + "】尚未开奖");
					bean.setSuccess(false);
					return bean;
				}
				rowsArray.clear();
			}
			//获取页数
			if (ibmClientBetTList.size() % pageSize == 0) {
				curPage = ibmClientBetTList.size() / pageSize;
			} else {
				curPage = ibmClientBetTList.size() / pageSize + 1;
			}
			bean.setSuccess(false);
			isJs = "1";
			for (int i = 1; i <= curPage; i++) {
				//获取盈亏信息
				bean = idcUtil.profitLoss(existHmId, IdcConfig.GAME_BET_CODE.get(gameCode), date, isJs, i, pageSize);
				//错误处理
				if (!bean.isSuccess() || bean.getData() == null) {
					return bean;
				}
				rowsArray = getRowsArray(bean);
				//盈亏信息长度等于0
				if (ContainerTool.isEmpty(rowsArray)) {
					log.info("IDC盘口用户【" + existHmId + "】盈亏信息为空" + rowsArray);
					bean.setSuccess(false);
					return bean;
				}
				JSONObject jsonObject;
				//循环匹对注单号
				for (IbmClientBetT ibmClientBetT : ibmClientBetTList) {
					for (Object json : rowsArray) {
						jsonObject = JSONObject.fromObject(json);
						//注单号匹配成功，保存盈亏信息
						if (jsonObject.getInt("idno") == Integer.parseInt(ibmClientBetT.getBetNumber())) {
							if (jsonObject.getDouble("netprofitandloss") > 0) {
								ibmClientBetT.setBetResult(IbmTypeEnum.TRUE.name());
							} else {
								ibmClientBetT.setBetResult(IbmTypeEnum.FALSE.name());
							}
							ibmClientBetT.setProfitT((long) (jsonObject.getDouble("netprofitandloss") * 1000));
							ibmClientBetT.setUpdateTime(new Date());
							ibmClientBetT.setUpdateTimeLong(ibmClientBetT.getUpdateTime().getTime());
							ibmClientBetT.setState(IbmStateEnum.FINISH.name());
							betTService.update(ibmClientBetT);
						}
					}
				}
			}
			bean.setSuccess(false);
			//拼接盈利结果
			//获取投注结果
			Map<String, Map<String, Object>> mapBetProfit = betTService.findBetResult(existHmId, period, gameCode);
			if (ContainerTool.isEmpty(mapBetProfit)) {
				log.info("IDC盘口用户【" + existHmId + "】未获取到已投注信息");
				bean.putEnum(IbmHcCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}
			IbmClientExistBetTService existBetTService = new IbmClientExistBetTService();
			boolean status = existBetTService.updateSettlement(mapBetProfit);
			if (!status) {
				log.trace("IDC盘口用户【" + existHmId + "】批量更新投注信息失败");
				bean.putEnum(IbmHcCodeEnum.IBM_404_DATA);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				return bean;
			}

			Map<String, List<Map<String, Object>>> finishBetItem = existBetTService
					.mapFinishBetItem(existHmId, gameCode, period);
			if (ContainerTool.isEmpty(finishBetItem)) {
				bean.putEnum(IbmHcCodeEnum.IBM_404_RESULT_INFO);
				bean.putSysEnum(IbmHcCodeEnum.CODE_404);
				bean.setSuccess(false);
				return bean;
			}
			//获取用户信息
			JSONObject userInfo = getUserInfo(existHmId);

			if (ContainerTool.isEmpty(userInfo)) {
				bean.setData(MessageReceipt
						.messageReceiptBetFinish(null, finishBetItem, gameCode, period, IbmHandicapEnum.IDC.name()));
			} else {
				log.trace("IDC盘口用户【" + existHmId + "】用户信息：" + userInfo);
				bean.setData(MessageReceipt.messageReceiptBetFinish(userInfo, finishBetItem, gameCode, period,
						IbmHandicapEnum.IDC.name()));
			}

			//判断是否还有投注项未获取到盈利信息
			ibmClientBetTList = betTService.findList(existHmId, period, gameCode);
			if (!ContainerTool.isEmpty(ibmClientBetTList)) {
				log.info("IDC盘口用户【" + existHmId + "】获取盈亏信息-定时任务未完成");
				bean.setSuccess(false);
				return bean;
			}
			bean.success();
		} catch (IOException e) {
			bean.setSuccess(false);
			log.error("IDC盘口用户【" + existHmId + "】获取盈亏信息-定时任务失败:", e);
		} finally {
			CurrentTransaction.transactionBegin();
		}

		return bean;
	}
	/**
	 * 获取用户信息
	 *
	 * @param existHmId 已存在盘口会员id
	 * @return
	 */
	private JSONObject getUserInfo(String existHmId) {
		JSONObject userInfo = new JSONObject();

		IdcUtil idcUtil = IdcUtil.findInstance();
		JsonResultBeanPlus userBean = idcUtil.userInfo(existHmId);

		if (!userBean.isSuccess()) {
			log.info("IDC盘口用户【" + existHmId + "】获取用户信息失败" + userBean.toJsonString());
		} else {
			log.info("IDC盘口用户【" + existHmId + "】获取用户信息成功" + userBean.toJsonString());
			Map<String,Object>  user = (JSONObject) userBean.getData();
			userInfo.put("existHmId", existHmId);
			userInfo.put("NICKNAME", user.get("memberAccount"));
			userInfo.put("BALANCE", user.get("usedQuota") );
			userInfo.put("PROFIT",  user.get("profitAmount") );
		}
		return userInfo;
	}
	/**
	 * 盈亏信息array
	 *
	 * @param bean 盈亏信息bean
	 * @return 盈亏信息array
	 */
	private JSONArray getRowsArray(JsonResultBeanPlus bean) {
		JSONArray jsonArray = JSONArray.fromObject(JSONObject.fromObject(bean.getData()).get("d"));
		return jsonArray.getJSONObject(0).getJSONArray("Rows");
	}
}

