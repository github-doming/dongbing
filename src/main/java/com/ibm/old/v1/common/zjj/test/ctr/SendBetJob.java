package com.ibm.old.v1.common.zjj.test.ctr;

import org.doming.core.common.quartz.BaseCommJob;
import org.quartz.JobExecutionContext;

/**
 *
 *
 * @Description: 发送投注信息
 * @ClassName: SendBetJob
 * @date 2019年1月5日 下午2:20:54
 * @author zjj
 * @Copyright (c) 2014-2016 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class SendBetJob extends BaseCommJob {

	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		/*JobDataMap jobMap = context.getMergedJobDataMap();
		String gameId = jobMap.getString("gameId");
		String handicapId = jobMap.getString("handicapId");

		IbmHmPlanGroupBetItemTService ibmHmPlanGroupBetItemTService = new IbmHmPlanGroupBetItemTService();

		Integer period = PeriodTool.findPK10Period();

		List<Map<String, Object>> betContentList = ibmHmPlanGroupBetItemTService.findBetContent(handicapId, gameId,
				period);

		IbmCloudClientHmTService ibmCloudClientHmTService = new IbmCloudClientHmTService();
		OpenClientController openClientController = new OpenClientController();

		JSONObject jsonObject;
		String loginStr;
		IbmCloudClientHmT ibmCloudClientHmT;

		List<String> handicapMemberIds = ibmHmPlanGroupBetItemTService.findHandicapMemberId();

		Set<List<Object>> set;
		List<Object> list;
		for (String handicapMemberId : handicapMemberIds) {
			jsonObject = new JSONObject();
			set = new HashSet<List<Object>>();

			for (Map<String, Object> map : betContentList) {
				list = new ArrayList<>();

				if (handicapMemberId.equals((String) map.get("HANDICAP_MEMBER_ID_"))) {
					list.add((String) map.get("IBM_HM_PLAN_GROUP_BET_ITEM_ID_"));
					list.add((String) map.get("BET_CONTENT_"));
					list.add(String.valueOf(map.get("FUND_T_")));
					set.add(list);

				}
			}


			ibmCloudClientHmT = ibmCloudClientHmTService.findByHandicapMemberId(handicapMemberId);

			// TODO,无链接客户端处理
			if (ibmCloudClientHmT == null) {
				System.out.println("ibmCloudClientHmT==null--------------");
				loginStr = openClientController.execute(handicapMemberId);

				if (loginStr == null || "".equals(loginStr)) {
					System.out.println("loginStr==null--------------");
				} else {
					System.out.println("loginStr---------" + loginStr);
					ibmCloudClientHmT = new IbmCloudClientHmT();
					ibmCloudClientHmT = ibmCloudClientHmTService.findByHandicapMemberId(handicapMemberId);

				}

			}

			jsonObject.put("clientExistHmId", ibmCloudClientHmT.getClientExistHmId());
			jsonObject.put("handicapId", handicapId);
			jsonObject.put("gameId", gameId);
			// jsonObject.put("handicapMemberId", handicapMemberId);
			jsonObject.put("betItem", set);
			System.out.println("jsonObject-------" + jsonObject.toString());

			// TODO,发送投注消息
			String result = RabbitMqIbmUtil.sendExchange4Bet(handicapMemberId,jsonObject.toString());

			if (!"success".equals(result)) {
				// TODO,处理结果
				System.out.println("---------------" + result);
			}

//			ibmHmPlanExecItemTService.updateExecStateByHandicapMemberId(handicapMemberId);
//			ibmHmPlanGroupBetItemTService.updateExecStateByHandicapMemberId(handicapMemberId);

		}
*/
	}

}
