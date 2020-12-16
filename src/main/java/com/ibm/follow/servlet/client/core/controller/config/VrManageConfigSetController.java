package com.ibm.follow.servlet.client.core.controller.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibm.common.enums.IbmMethodEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.client.core.controller.ClientExecutor;
import com.ibm.follow.servlet.vrc.vrc_exist_member.entity.VrcExistMember;
import com.ibm.follow.servlet.vrc.vrc_exist_member.service.VrcExistMemberService;
import com.ibm.follow.servlet.vrc.vrc_fm_game_set.entity.VrcFmGameSet;
import com.ibm.follow.servlet.vrc.vrc_fm_game_set.service.VrcFmGameSetService;
import com.ibm.follow.servlet.vrc.vrc_member_bind_info.entity.VrcMemberBindInfo;
import com.ibm.follow.servlet.vrc.vrc_member_bind_info.service.VrcMemberBindInfoService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.sql.SQLException;
import java.util.Date;

/**
 * @Description: 虚拟端管理设置
 * @Author: null
 * @Date: 2020-07-15 17:23
 * @Version: v1.0
 */
public class VrManageConfigSetController implements ClientExecutor {
	private JsonResultBeanPlus bean = new JsonResultBeanPlus();

	@Override
	public JsonResultBeanPlus execute(JSONObject msgObj) throws Exception {

		String existMemberVrId = msgObj.getString("EXIST_MEMBER_VR_ID_");
		String userId = msgObj.getString("USER_ID_");
		//验证内存
		if (StringTool.isEmpty(existMemberVrId,userId)) {
			bean.putEnum(CodeEnum.IBS_404_EXIST);
			bean.putSysEnum(CodeEnum.CODE_404);
			return bean;
		}
		Date nowTime = new Date();

		VrcMemberBindInfoService memberBindInfoService = new VrcMemberBindInfoService();
		String memberBindId = memberBindInfoService.findId(existMemberVrId, userId);
		if (StringTool.isEmpty(memberBindId)) {
			VrcMemberBindInfo memberBindInfo = new VrcMemberBindInfo();
			memberBindInfo.setExistHmVrId(existMemberVrId);
			memberBindInfo.setUserId(userId);
			memberBindInfo.setCreateTime(nowTime);
			memberBindInfo.setCreateTimeLong(nowTime.getTime());
			memberBindInfo.setUpdateTime(nowTime);
			memberBindInfo.setUpdateTimeLong(nowTime.getTime());
			memberBindInfo.setState(IbmStateEnum.OPEN.name());
			memberBindInfoService.save(memberBindInfo);
		}


		switch (IbmMethodEnum.valueOf(msgObj.getString("SET_ITEM_"))) {
			case SET_BET_STATE:
				setBetState(msgObj, existMemberVrId,userId,nowTime);
				break;
			case SET_GAME_INFO:
				setGameInfo(msgObj,existMemberVrId,userId,nowTime);
				break;
			case BIND_VR_CLIENT:
				setBindClient(msgObj,existMemberVrId,userId,nowTime);
				break;
			case DEL_MEMBER_VR:
				delMemberInfo(existMemberVrId);
				break;
			default:
				break;
		}
		return bean;
	}

	private void delMemberInfo( String existMemberVrId) throws SQLException {
		new VrcExistMemberService().delMemberInfo(existMemberVrId);

	}

	private void setBindClient(JSONObject msgObj, String existMemberVrId,String userId,Date nowTime) throws Exception {
		String memberAccount = msgObj.getString("VR_MEMBER_ACCOUNT_");
		String handicapCode = msgObj.getString("HANDICAP_CODE_");
		if (StringTool.isEmpty(memberAccount)||StringTool.isEmpty(handicapCode)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}
		VrcExistMemberService memberService = new VrcExistMemberService();
		VrcExistMember existMember = memberService.find(existMemberVrId);
		if(existMember ==null){
			existMember = new VrcExistMember();
			existMember.setVrcExistMemberId(existMemberVrId);
			existMember.setVrMemberId(userId);
			existMember.setVrMemberAccount(memberAccount);
			existMember.setHandicapCode(handicapCode);
			existMember.setCreateTime(nowTime);
			existMember.setCreateTimeLong(System.currentTimeMillis());
			existMember.setUpdateTime(nowTime);
			existMember.setUpdateTimeLong(System.currentTimeMillis());
			existMember.setState(IbmStateEnum.OPEN.name());
			memberService.save(existMember);
		}else{
			existMember.setVrMemberId(userId);
			existMember.setVrMemberAccount(memberAccount);
			existMember.setHandicapCode(handicapCode);
			existMember.setUpdateTime(nowTime);
			existMember.setUpdateTimeLong(System.currentTimeMillis());
			memberService.update(existMember);
		}
		bean.success();
	}

	private void setGameInfo(JSONObject msgObj, String existMemberVrId,String userId,Date nowTime) throws Exception {
		JSONArray gameInfo = msgObj.getJSONArray("GAME_INFO_");
		if (ContainerTool.isEmpty(gameInfo)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}
		VrcFmGameSetService fmGameSetService = new VrcFmGameSetService();
		VrcFmGameSet fmGameSet;
		for (int i = 0; i < gameInfo.size(); i++) {
			JSONObject jsonObject = gameInfo.getJSONObject(i);
			String gameCode = jsonObject.getString("GAME_CODE_");
			fmGameSet = fmGameSetService.findInfo(existMemberVrId, userId, gameCode);
			if(fmGameSet==null){
				fmGameSet = new VrcFmGameSet();
				fmGameSet.setExistHmVrId(existMemberVrId);
				fmGameSet.setUserId(userId);
				fmGameSet.setCreateTime(nowTime);
				fmGameSet.setCreateTimeLong(nowTime.getTime());
				fmGameSet.setUpdateTime(nowTime);
				fmGameSet.setUpdateTimeLong(nowTime.getTime());
				fmGameSet.setState(IbmStateEnum.OPEN.name());
				fmGameSet.setGameCode(gameCode);
				fmGameSet.setBetState(jsonObject.getString("BET_STATE_"));
				fmGameSet.setBetFollowMultipleT( NumberTool.intValueT(jsonObject.getDouble("BET_FOLLOW_MULTIPLE_")));
				fmGameSet.setBetLeastAmountT(NumberTool.intValueT(jsonObject.getInteger("BET_LEAST_AMOUNT_")));
				fmGameSet.setBetMostAmountT(NumberTool.intValueT(jsonObject.getInteger("BET_MOST_AMOUNT_")));
				fmGameSet.setBetFilterNumber(jsonObject.getString("BET_FILTER_NUMBER_"));
				fmGameSet.setBetFilterTwoSide(jsonObject.getString("BET_FILTER_TWO_SIDE_"));
				fmGameSet.setNumberOpposing(jsonObject.getString("NUMBER_OPPOSING_"));
				fmGameSet.setTwoSideOpposing(jsonObject.getString("TWO_SIDE_OPPOSING_"));

				fmGameSetService.save(fmGameSet);
			}else{
				fmGameSet.setBetState(jsonObject.getString("BET_STATE_"));
				fmGameSet.setBetFollowMultipleT( NumberTool.intValueT(jsonObject.getDouble("BET_FOLLOW_MULTIPLE_")));
				fmGameSet.setBetLeastAmountT(NumberTool.intValueT(jsonObject.getInteger("BET_LEAST_AMOUNT_")));
				fmGameSet.setBetMostAmountT(NumberTool.intValueT(jsonObject.getInteger("BET_MOST_AMOUNT_")));
				fmGameSet.setBetFilterNumber(jsonObject.getString("BET_FILTER_NUMBER_"));
				fmGameSet.setBetFilterTwoSide(jsonObject.getString("BET_FILTER_TWO_SIDE_"));
				fmGameSet.setNumberOpposing(jsonObject.getString("NUMBER_OPPOSING_"));
				fmGameSet.setTwoSideOpposing(jsonObject.getString("TWO_SIDE_OPPOSING_"));
				fmGameSet.setUpdateTime(nowTime);
				fmGameSet.setUpdateTimeLong(nowTime.getTime());
				fmGameSetService.update(fmGameSet);
			}
		}
		bean.success();
	}

	private void setBetState(JSONObject msgObj, String existMemberVrId,String userId,Date nowTime) throws Exception {
		String gameCode = msgObj.getString("GAME_CODE_");
		JSONObject gameInfo = msgObj.getJSONObject("GAME_INFO_");

		if (StringTool.isEmpty(gameCode)||ContainerTool.isEmpty(gameInfo)) {
			bean.putEnum(CodeEnum.IBS_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return;
		}
		VrcFmGameSetService fmGameSetService = new VrcFmGameSetService();
		VrcFmGameSet fmGameSet = fmGameSetService.findInfo(existMemberVrId, userId, gameCode);
		if (fmGameSet == null) {
			fmGameSet = new VrcFmGameSet();
			fmGameSet.setExistHmVrId(existMemberVrId);
			fmGameSet.setUserId(userId);
			fmGameSet.setCreateTime(nowTime);
			fmGameSet.setCreateTimeLong(nowTime.getTime());
			fmGameSet.setUpdateTime(nowTime);
			fmGameSet.setUpdateTimeLong(nowTime.getTime());
			fmGameSet.setState(IbmStateEnum.OPEN.name());
			fmGameSet.setGameCode(gameCode);
			fmGameSet.setBetState(gameInfo.getString("BET_STATE_"));
			fmGameSet.setBetFollowMultipleT(gameInfo.getInteger("BET_FOLLOW_MULTIPLE_T_"));
			fmGameSet.setBetLeastAmountT(gameInfo.getInteger("BET_LEAST_AMOUNT_T_"));
			fmGameSet.setBetMostAmountT(gameInfo.getInteger("BET_MOST_AMOUNT_T_"));
			fmGameSet.setBetFilterNumber(gameInfo.getString("BET_FILTER_NUMBER_"));
			fmGameSet.setBetFilterTwoSide(gameInfo.getString("BET_FILTER_TWO_SIDE_"));
			fmGameSet.setNumberOpposing(gameInfo.getString("NUMBER_OPPOSING_"));
			fmGameSet.setTwoSideOpposing(gameInfo.getString("TWO_SIDE_OPPOSING_"));
			if (gameInfo.containsKey("FILTER_PROJECT_")) {
				fmGameSet.setFilterProject(gameInfo.getString("FILTER_PROJECT_"));
			}
			fmGameSetService.save(fmGameSet);
		}else{
			fmGameSet.setBetState(gameInfo.getString("BET_STATE_"));
			fmGameSet.setUpdateTime(nowTime);
			fmGameSet.setUpdateTimeLong(nowTime.getTime());
			fmGameSetService.update(fmGameSet);
		}
		bean.success();
	}
}
