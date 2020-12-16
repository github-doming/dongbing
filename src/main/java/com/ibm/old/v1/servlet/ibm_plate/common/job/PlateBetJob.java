package com.ibm.old.v1.servlet.ibm_plate.common.job;

import com.ibm.old.v1.client.ibm_client_exist_bet.t.entity.IbmClientExistBetT;
import com.ibm.old.v1.client.ibm_client_exist_bet.t.service.IbmClientExistBetTService;
import net.sf.json.JSONObject;
import org.doming.core.common.quartz.BaseCommJob;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.Date;
import java.util.List;
/**
 * 
 * @ClassName: PlateBetJob
 * @Description: 挡板投注定时任务
 * @author zjj
 * @date 2019年2月18日 下午6:29:12
 *
 */
public class PlateBetJob extends BaseCommJob {

	@SuppressWarnings("unchecked")
	@Override
	public void executeJob(JobExecutionContext context) throws Exception {
		
		try {
			
			JobDataMap map = context.getMergedJobDataMap();
			String clientBetInfoExistId = map.getString("clientBetInfoExistId");
			List<String> betItemList = (List<String>) map.get("betItemList");

			JSONObject jsonObject = PlateGetOddsAndBet.getBetting(betItemList, PlateGetOddsAndBet.getOdds());

			IbmClientExistBetTService existBetTService = new IbmClientExistBetTService();
			IbmClientExistBetT existBetT = existBetTService.find(clientBetInfoExistId);
			existBetT.setBetInfo(jsonObject.toString());
			existBetT.setUpdateTime(new Date());
			existBetT.setUpdateTimeLong(existBetT.getUpdateTime().getTime());

			existBetTService.update(existBetT);
		} catch (Exception e) {
			log.error("投注进挡板中-定时任务" , e);
			
		}

	}

}
