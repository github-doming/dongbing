package com.ibs.plan.connector.admin.manage.base.handicap;

import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.common.core.JsonResultBeanPlus;
import com.common.enums.CodeEnum;
import com.ibs.common.core.CommAdminAction;
import com.ibs.common.enums.IbsStateEnum;
import com.ibs.plan.module.cloud.ibsp_game.service.IbspGameService;
import com.ibs.plan.module.cloud.ibsp_handicap.entity.IbspHandicap;
import com.ibs.plan.module.cloud.ibsp_handicap.service.IbspHandicapService;
import com.ibs.plan.module.cloud.ibsp_handicap_game.entity.IbspHandicapGame;
import com.ibs.plan.module.cloud.ibsp_handicap_game.service.IbspHandicapGameService;
import com.ibs.plan.module.cloud.ibsp_handicap_item.entity.IbspHandicapItem;
import com.ibs.plan.module.cloud.ibsp_handicap_item.service.IbspHandicapItemService;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;
import org.doming.develop.http.HttpConfig;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Description: 新增盘口
 * @Author: null
 * @Date: 2020-03-21 17:41
 * @Version: v1.0
 */
@ActionMapping(value = "/ibs/sys/manage/handicap/save", method = HttpConfig.Method.POST)
public class HandicapSaveAction extends CommAdminAction {
	private String handicapCode, handicapType, handicapIcon, handicapGame, handicapUrl, handicapCaptcha, desc;
	private double handicapWorth;
	private int sn;
	private Object version;

	@Override
	public Object run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		if (valiParameters()) {
			bean.putEnum(CodeEnum.IBS_401_DATA);
			bean.putSysEnum(CodeEnum.CODE_401);
			return super.returnJson(bean);
		}
		try {
			IbspHandicapService handicapService = new IbspHandicapService();
			if (handicapService.isExist(handicapCode)) {
				bean.putEnum(CodeEnum.IBS_403_EXIST);
				bean.putSysEnum(CodeEnum.CODE_403);
				return bean;
			}
			desc = this.getClass().getSimpleName().concat(",").concat(appUserId).concat(",新增盘口");
			Date nowTime = new Date();
			//新增盘口
			String handicapId = saveHandicap(handicapService, nowTime);

			//初始化盘口游戏
			if (StringTool.notEmpty(handicapGame)) {
				saveHandicapGame(handicapId, nowTime);
			}
			//新增盘口详情数据
			if (StringTool.notEmpty(handicapUrl) && StringTool.notEmpty(handicapCaptcha)) {
				saveHandicapItem(handicapId, nowTime);
			}

			bean.success();
		} catch (Exception e) {
			log.error("新增盘口错误", e);
			throw e;
		}
		return bean;
	}

	private void saveHandicapItem(String handicapId, Date nowTime) throws Exception {
		IbspHandicapItem handicapItem = new IbspHandicapItem();
		handicapItem.setHandicapId(handicapId);
		handicapItem.setHandicapCode(handicapCode);
		handicapItem.setHandicapName(handicapCode);
		handicapItem.setHandicapUrl(handicapUrl);
		handicapItem.setHandicapCaptcha(handicapCaptcha);
		handicapItem.setCreateTime(nowTime);
		handicapItem.setCreateTimeLong(nowTime.getTime());
		handicapItem.setUpdateTime(nowTime);
		handicapItem.setUpdateTimeLong(nowTime.getTime());
		new IbspHandicapItemService().save(handicapItem);
	}

	private void saveHandicapGame(String handicapId, Date nowTime) throws Exception {
		String[] gameCodes = handicapGame.split(",");
		IbspGameService gameService = new IbspGameService();
		IbspHandicapGameService handicapGameService = new IbspHandicapGameService();
		IbspHandicapGame handicapGame;
		List<Map<String, Object>> gameInfos = gameService.listByGameCodes(gameCodes);
		for (Map<String, Object> gameInfo : gameInfos) {

			handicapGame = new IbspHandicapGame();
			handicapGame.setHandicapId(handicapId);
			handicapGame.setGameCode(gameInfo.get("GAME_CODE_"));
			handicapGame.setGameId(gameInfo.get("GAME_ID_"));
			handicapGame.setHandicapGameName(gameInfo.get("GAME_NAME_"));
			handicapGame.setCreateTime(nowTime);
			handicapGame.setCreateTimeLong(nowTime.getTime());
			handicapGame.setUpdateTimeLong(nowTime.getTime());
			handicapGame.setDesc(desc);
			handicapGame.setState(IbsStateEnum.OPEN.name());

			handicapGameService.save(handicapGame);
		}
	}


	private String saveHandicap(IbspHandicapService handicapService, Date nowTime) throws Exception {

		IbspHandicap handicap = new IbspHandicap();
		handicap.setHandicapName(handicapCode);
		handicap.setHandicapCode(handicapCode);
		handicap.setHandicapType(handicapType);
		handicap.setHandicapWorthT(NumberTool.intValueT(handicapWorth));
		handicap.setHandicapVersion(version);
		handicap.setHandicapIcon(handicapIcon);
		handicap.setDesc(desc);
		handicap.setSn(sn);
		handicap.setCreateUser(appUserId);
		handicap.setCreateTime(nowTime);
		handicap.setCreateTimeLong(System.currentTimeMillis());
		handicap.setUpdateTimeLong(System.currentTimeMillis());
		handicap.setState(IbsStateEnum.OPEN.name());
		return handicapService.save(handicap);
	}

	private boolean valiParameters() {

		//盘口code或名称
		handicapCode = StringTool.getString(dataMap, "handicapCode", "");
		//盘口类型
		handicapType = StringTool.getString(dataMap, "handicapType", "");
		//可用游戏
		handicapGame = StringTool.getString(dataMap, "handicapGame", "");
		//导航地址
		handicapUrl = StringTool.getString(dataMap, "handicapUrl", "");
		//验证码
		handicapCaptcha = StringTool.getString(dataMap, "handicapCaptcha", "");
		//盘口图标
		handicapIcon = StringTool.getString(dataMap, "handicapIcon", "");
		//盘口价值
		handicapWorth = NumberTool.getDouble(dataMap.get("handicapWorth"), 0);
		//盘口次序
		sn = NumberTool.getInteger(dataMap, "sn", 0);
		//盘口版本HANDICAP_VERSION_
		version = StringTool.getString(dataMap, "version", "");

		return StringTool.isEmpty(handicapCode, handicapType, handicapWorth);
	}
}
