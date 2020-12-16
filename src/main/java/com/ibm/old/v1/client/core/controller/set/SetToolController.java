package com.ibm.old.v1.client.core.controller.set;
import com.ibm.old.v1.client.core.job.login.LoginHandicapIDCJob;
import com.ibm.old.v1.client.core.job.login.LoginHandicapSgWinJob;
import com.ibm.old.v1.client.core.job.login.LoginHandicapWs2Job;
import com.ibm.old.v1.client.ibm_client_exist_hm.t.entity.IbmClientExistHmT;
import com.ibm.old.v1.client.ibm_client_hm.t.entity.IbmClientHmT;
import com.ibm.old.v1.client.ibm_client_hm.t.service.IbmClientHmTService;
import com.ibm.old.v1.client.ibm_client_hm_game_set.t.entity.IbmClientHmGameSetT;
import com.ibm.old.v1.client.ibm_client_hm_game_set.t.service.IbmClientHmGameSetTService;
import com.ibm.old.v1.common.doming.configs.SgWinConfig;
import com.ibm.old.v1.common.doming.configs.Ws2Config;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.common.doming.enums.IbmCodeEnum;
import com.ibm.old.v1.common.doming.enums.IbmHandicapEnum;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import com.ibm.old.v1.common.doming.enums.IbmTypeEnum;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.SgWinUtil;
import com.ibm.old.v1.common.doming.utils.http.httpclient.util.Ws2Util;
import com.ibm.old.v1.common.doming.utils.job.QuartzIbmUtil;
import net.sf.json.JSONObject;

import java.util.Date;
/**
 * @Description: 设置工具类
 * @Author: Dongming
 * @Date: 2019-03-09 16:42
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class SetToolController {

	/**
	 * 开始登陆
	 *
	 * @param existHmId     存在盘口会员id
	 * @param handicapCode  盘口code
	 * @param memberAccount 会员账户
	 * @return 登陆结果
	 */
	protected static JsonResultBeanPlus startLogin(String existHmId, String handicapCode, String memberAccount)
			throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();

		IbmHandicapEnum handicapEnum = IbmHandicapEnum.valueOf(handicapCode);
		switch (handicapEnum) {
			case WS2:
				LoginHandicapWs2Job loginWs2 = new LoginHandicapWs2Job();
				bean = loginWs2.execute(existHmId, memberAccount);
				break;
			case IDC:
				LoginHandicapIDCJob loginIdc = new LoginHandicapIDCJob();
				bean = loginIdc.execute(existHmId, memberAccount);
				break;
			case SGWIN:
				LoginHandicapSgWinJob loginSgWin=new LoginHandicapSgWinJob();
				bean =loginSgWin.execute(existHmId, memberAccount);
				break;
			default:
				bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
				bean.putSysEnum(IbmCodeEnum.CODE_404);
				return bean;
		}

		//判断登录结果，
		if (bean.isSuccess()) {
			QuartzIbmUtil.checkUser(existHmId, handicapCode, bean);
			QuartzIbmUtil.removeLoginJob(existHmId,handicapEnum);
		}
		return bean;
	}

	/**
	 * 更新盘口会员信息
	 *
	 * @param msgObj                 消息内容
	 * @param existHmT               存在盘口会员实体类
	 * @param memberAccount          会员账号
	 * @param handicapMemberT        盘口会员实体类
	 * @param handicapMemberTService 盘口会员服务类
	 */
	protected static void updateHandicapMember(JSONObject msgObj, IbmClientExistHmT existHmT, String memberAccount,
			IbmClientHmTService handicapMemberTService, IbmClientHmT handicapMemberT) throws Exception {
		//盘口会员已存在历史信息
		if (handicapMemberT != null) {
			//用户已存在与该服务中	<id匹配，状态open>
			handicapMemberT.setMemberPassword(msgObj.get("memberPassword"));
			handicapMemberT.setHandicapUrl(msgObj.get("handicapUrl"));
			handicapMemberT.setHandicapCaptcha(msgObj.get("handicapCaptcha"));
			handicapMemberT.setUpdateTime(new Date());
			handicapMemberT.setUpdateTimeLong(handicapMemberT.getUpdateTime().getTime());
			handicapMemberTService.update(handicapMemberT);
		} else {
			//不存在历史盘口会员信息
			handicapMemberT = new IbmClientHmT();
			handicapMemberT.setClientExistHmId(existHmT.getIbmClientExistHmId());
			handicapMemberT.setHandicapMemberId(existHmT.getHandicapMemberId());
			handicapMemberT.setHandicapId(existHmT.getHandicapId());
			handicapMemberT.setMemberAccount(memberAccount);
			handicapMemberT.setMemberPassword(msgObj.get("memberPassword"));
			handicapMemberT.setHandicapUrl(msgObj.get("handicapUrl"));
			handicapMemberT.setHandicapCaptcha(msgObj.get("handicapCaptcha"));
			handicapMemberT.setCreateTime(new Date());
			handicapMemberT.setCreateTimeLong(handicapMemberT.getCreateTime().getTime());
			handicapMemberT.setUpdateTime(new Date());
			handicapMemberT.setCreateTimeLong(handicapMemberT.getUpdateTime().getTime());
			handicapMemberT.setState(IbmStateEnum.OPEN.name());
			handicapMemberTService.save(handicapMemberT);
		}
	}

	/**
	 * 配置游戏限制
	 *
	 * @param existHmT          存在盘口会员实体类
	 * @param msgObj            消息内容
	 * @param gameId            游戏id
	 * @param hmGameSetTService 盘口会员游戏服务类
	 * @return 游戏限制结果
	 */
	public static JsonResultBeanPlus gameLimit(IbmClientExistHmT existHmT, JSONObject msgObj, String gameId,
			IbmClientHmGameSetTService hmGameSetTService) throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		if (IbmTypeEnum.TRUE.name().equals(msgObj.get("betState"))) {
			IbmHandicapEnum handicap = existHmT.getHandicapCodeEnum();
			switch (handicap) {
				case WS2:
					Ws2Util util = Ws2Util.findInstance();
					bean = util.gameLimit(existHmT.getIbmClientExistHmId(),
							Ws2Config.getGameCode(msgObj.getString("gameCode")));
					if (!bean.isSuccess()) {
						return bean;
					}
				case IDC:
					//TODO IDC没有做游戏限额信息处理
					break;
				case SGWIN:
					SgWinUtil sgWinUtil=SgWinUtil.findInstance();
					bean=sgWinUtil.gameLimit(existHmT.getIbmClientExistHmId(), SgWinConfig.GAME_CODE_MAP.get(msgObj.getString("gameCode")));
					if (!bean.isSuccess()) {
						return bean;
					}
					break;
				default:
					bean.putEnum(IbmCodeEnum.IBM_404_HANDICAP_CODE);
					bean.putSysEnum(IbmCodeEnum.CODE_404);
					return bean;
			}
			if (bean.getData() != null) {
				IbmClientHmGameSetT hmGameSetT = hmGameSetTService.findExist(existHmT.getIbmClientExistHmId(), gameId);
				hmGameSetT.setBetLimit(bean.getData().toString());
				hmGameSetTService.update(hmGameSetT);
			}
		}
		bean.success();
		return bean;
	}

}
