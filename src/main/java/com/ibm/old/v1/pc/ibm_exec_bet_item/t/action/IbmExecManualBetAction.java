package com.ibm.old.v1.pc.ibm_exec_bet_item.t.action;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.old.v1.cloud.core.controller.mq.ManualBetController;
import com.ibm.old.v1.cloud.core.tool.HandicapGameTool;
import com.ibm.old.v1.common.doming.configs.IbmConfig;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.pc.ibm_exec_bet_item.t.service.IbmPcExecBetItemTService;
import com.ibm.old.v1.pc.ibm_handicap_member.t.service.IbmPcHandicapMemberTService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.doming.core.tools.ContainerTool;
import com.ibm.old.v1.common.doming.tools.PeriodTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.*;

/**
 * @Description: 手动投注
 * @date 2019年1月25日 下午2:38:44 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmExecManualBetAction extends BaseAppAction{
	private String[] betModel = {"第一名","第二名","第三名","第四名","第五名","第六名","第七名","第八名","第九名","第十名"};
	private JSONArray betItemArray;
	private String handicapMemberId;
	private Object period;
	private String fundT;

	@Override
	public String run() throws Exception {
		JsonResultBeanPlus jrb = new JsonResultBeanPlus();
		super.findAppUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if(!threadJrb.isSuccess()){
			return returnJson(threadJrb);
		}
		if(appUserT == null){
			jrb.putEnum(IbmCodeEnum.IBM_404_NOT_FIND_USER);
			jrb.putSysEnum(IbmCodeEnum.CODE_404);
			return returnJson(jrb);
		}

		//投注项
		betItemArray = JSONArray.fromObject(dataMap.get("betItem"));
		//盘口会员主键
		handicapMemberId = BeanThreadLocal.find(dataMap.get("HANDICAP_MEMBER_ID_"), "");
		//游戏编码
		String gameCode = BeanThreadLocal.find(dataMap.get("GAME_CODE_"), "");
		//单注金额
		fundT = BeanThreadLocal.find(dataMap.get("FUND_T_"), "");
		
		if(StringTool.isEmpty(handicapMemberId,fundT, gameCode)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(jrb);
		}

		//期数
		period = PeriodTool.findPeriod(gameCode);
		
		if(ContainerTool.isEmpty(betItemArray)){
			jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
			jrb.putSysEnum(IbmCodeEnum.CODE_401);
			return returnJson(jrb);
		}
		
		try {
			//根据盘口会员ID和游戏CODE获取盘口ID和游戏ID
			IbmPcHandicapMemberTService handicapMemberTService = new IbmPcHandicapMemberTService();
			Map<String, Object> idMap = handicapMemberTService.findByHmIdAndGameCode(handicapMemberId, gameCode);
			if(ContainerTool.isEmpty(idMap)){
				jrb.putEnum(IbmCodeEnum.IBM_404_DATA_NOT_FIND);
				jrb.putSysEnum(IbmCodeEnum.CODE_404);
				return returnJson(jrb);
			}
			String tableName= HandicapGameTool.getTableNameByCodeAndId(gameCode,idMap.get("HANDICAP_ID_").toString());
			if(StringTool.isEmpty(tableName)){
				jrb.putEnum(IbmCodeEnum.IBM_401_NOT_FIND_DATA);
				jrb.putSysEnum(IbmCodeEnum.CODE_401);
				return returnJson(jrb);
			}

			List<Map<String, Object>> betItemList = saveBetItem(idMap,tableName);

			jrb = new ManualBetController(gameCode,period,handicapMemberId,betItemList).execute();
			if(!jrb.isSuccess()){
				return this.returnJson(jrb);
			}

			listFormat(betItemList);
		} catch (Exception e) {
			log.error(IbmConfig.LOG_SIGN+"手动投注失败",e);
			throw e;
		}
		return this.returnJson(jrb);
	}

	/**
	 *
	 * @Description: 格式化投注项
	 *
	 * 参数说明
	 * @param list 需要格式化的集合
	 */
	private void listFormat(List<Map<String, Object>> list) {
		Map<String, String> newMap = new HashMap<>();
		StringBuilder builder = new StringBuilder();
		for (Map<String, Object> ibmExecBetItemT : list) {
			String[] bets = ibmExecBetItemT.get("BET_CONTENT_").toString().split("#");
			if (bets.length>0) {
				for (String betItem : bets) {
					String[] bet = betItem.split("\\|");
					if (newMap.containsKey(bet[0])) {
						newMap.put(bet[0], newMap.get(bet[0]) + "," + bet[1]);
					} else {
						newMap.put(bet[0], bet[1]);
					}
				}
				for (Map.Entry<String, String> entry : newMap.entrySet()){
					if(builder.length()>0){
						builder.append("#").append(entry.getKey()).append("-").append(entry.getValue());
					}else{
						builder.append(entry.getKey()).append("-").append(entry.getValue());
					}
				}
				ibmExecBetItemT.put("BET_CONTENT_",builder.toString());
				newMap.clear();
				builder.delete(0,builder.length());
			}
		}
	}

	/**
	 *
	 * @param idMap 	  盘口id和游戏id
	 * @param tableName 表名
	 * @return 投注项信息集合
	 */
	private List<Map<String, Object>> saveBetItem(Map<String, Object> idMap,String tableName) throws SQLException {
		List<Map<String,Object>> list = new ArrayList<>();
		StringBuilder betContent = new StringBuilder();
		for (int i = 0; i < betItemArray.size(); i++) {
			JSONObject bet = betItemArray.getJSONObject(i);
			String betPosition = bet.get("betPosition").toString();
			String[] betOptions = bet.get("betOption").toString().split(",");
			int betLength = 0;
			for (;betLength<betOptions.length;betLength++) {
				if(StringTool.isEmpty(betOptions[betLength])||Integer.parseInt(betPosition)>betModel.length||Integer.parseInt(betPosition)<1){
					continue;
				}
				betContent.append(betModel[Integer.parseInt(betPosition)-1].concat("|").concat(betOptions[betLength]).concat("#"));
			}
			betContent.deleteCharAt(betContent.length()-1);
			Map<String,Object> betItem = new HashMap<>();
			betItem.put("HANDICAP_ID_",idMap.get("HANDICAP_ID_"));
			betItem.put("GAME_ID_",idMap.get("GAME_ID_"));
			betItem.put("HANDICAP_MEMBER_ID_",handicapMemberId);
			betItem.put("PERIOD_",period);
			betItem.put("FUND_T_",fundT);
			betItem.put("BET_CONTENT_",betContent.toString());
			betItem.put("BET_CONTENT_LEN_",betLength);
			list.add(betItem);
			betContent.delete(0,betContent.length());
		}
		//保存投注信息
		new IbmPcExecBetItemTService().saveManualBet(list,tableName);
		return list;
	}
}
