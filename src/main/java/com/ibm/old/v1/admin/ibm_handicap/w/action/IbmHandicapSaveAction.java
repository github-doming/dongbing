package com.ibm.old.v1.admin.ibm_handicap.w.action;
import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_game.t.service.IbmGameTService;
import com.ibm.old.v1.cloud.ibm_handicap.t.entity.IbmHandicapT;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.entity.IbmHandicapGameT;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.service.IbmHandicapGameTService;
import com.ibm.old.v1.cloud.ibm_handicap_item.t.entity.IbmHandicapItemT;
import com.ibm.old.v1.cloud.ibm_handicap_item.t.service.IbmHandicapItemTService;
import com.ibm.old.v1.cloud.ibm_sys_handicap.t.entity.IbmSysHandicapT;
import com.ibm.old.v1.cloud.ibm_sys_handicap.t.service.IbmSysHandicapTService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.enums.IbmStateEnum;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 新增盘口
 * @Author: zjj
 * @Date: 2019-08-13 11:19
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmHandicapSaveAction extends BaseAppAction {

	@Override public String run() throws Exception {

		//盘口ID
		String id = request.getParameter("IBM_HANDICAP_ID_");
		//盘口详情ID
		String handicapItemid = request.getParameter("IBM_HANDICAP_ITEM_ID_");
		//盘口名称
		String handicapName = request.getParameter("HANDICAP_NAME_");
		//盘口CODE
		String handicapCode = request.getParameter("HANDICAP_CODE_");
		//盘口地址
		String handicapUrl = request.getParameter("HANDICAP_URL_");
		//校验码
		String handicapCaptcha = request.getParameter("HANDICAP_CAPTCHA_");
		//盘口说明
		String handicapExplain = request.getParameter("HANDICAP_EXPLAIN_");
		//盘口类型
		String handicapType = request.getParameter("HANDICAP_TYPE_");
		//盘口价值
		String handicapWorth = request.getParameter("HANDICAP_WORTH_T_");
		//盘口版本
		String handicapVersion = request.getParameter("HANDICAP_VERSION_");
		//排序
		String sn = request.getParameter("SN_");
		//游戏id
		String gameIds = request.getParameter("GAME_ID_");

		Date nowTime = new Date();
		IbmHandicapT handicapT = new IbmHandicapT();
		IbmHandicapItemT handicapItemT = new IbmHandicapItemT();

		handicapT.setIbmHandicapId(id);
		handicapT.setHandicapName(handicapName);
		handicapT.setHandicapCode(handicapCode);
		handicapT.setHandicapExplain(handicapExplain);
		handicapT.setHandicapType(handicapType);
		handicapT.setHandicapWorthT(NumberTool.longValueT(handicapWorth));
		handicapT.setHandicapVersion(handicapVersion);
		handicapT.setSn(Integer.parseInt(sn));
		handicapT.setState("OPEN");
		handicapT.setUpdateTime(nowTime);
		handicapT.setUpdateTimeLong(nowTime.getTime());

		handicapItemT.setHandicapName(handicapName);
		handicapItemT.setHandicapUrl(handicapUrl);
		handicapItemT.setHandicapCaptcha(handicapCaptcha);
		handicapItemT.setState("OPEN");
		handicapItemT.setUpdateTime(nowTime);
		handicapItemT.setUpdateTimeLong(nowTime.getTime());

		IbmHandicapTService handicapTService = new IbmHandicapTService();
		IbmHandicapItemTService handicapItemTService = new IbmHandicapItemTService();
		if (StringTool.isEmpty(id)) {
			handicapT.setCreateTime(nowTime);
			handicapT.setCreateTimeLong(nowTime.getTime());
			id = handicapTService.save(handicapT);
			handicapT.setIbmHandicapId(id);
			handicapItemT.setHandicapId(id);
			handicapItemT.setCreateTime(nowTime);
			handicapItemT.setCreateTimeLong(nowTime.getTime());
			handicapItemTService.save(handicapItemT);
			//添加系统盘口
			saveSysHandicap(handicapT);
		} else {
			IbmHandicapT ibmHandicap = handicapTService.find(handicapT.getIbmHandicapId());
			handicapT.setCreateTime(ibmHandicap.getCreateTime());
			handicapT.setCreateTimeLong(ibmHandicap.getCreateTimeLong());
			handicapTService.update(handicapT);
			handicapItemT.setIbmHandicapItemId(handicapItemid);
			handicapItemT.setHandicapId(id);
			handicapItemT.setCreateTime(ibmHandicap.getCreateTime());
			handicapItemT.setCreateTimeLong(ibmHandicap.getCreateTimeLong());
			handicapItemTService.update(handicapItemT);
		}

		initHandicapGame(handicapT, gameIds.split(","));
		return CommViewEnum.Default.toString();
	}
	private void saveSysHandicap(IbmHandicapT handicapT) throws Exception {
		Date nowTime=new Date();
		IbmSysHandicapTService sysHandicapTService=new IbmSysHandicapTService();
		IbmSysHandicapT sysHandicapT=new IbmSysHandicapT();
		sysHandicapT.setHandicapId(handicapT.getIbmHandicapId());
		sysHandicapT.setHandicapCode(handicapT.getHandicapCode());
		sysHandicapT.setHandicapDetectionTime(60);
		sysHandicapT.setCreateTime(nowTime);
		sysHandicapT.setCreateTimeLong(sysHandicapT.getCreateTime().getTime());
		sysHandicapT.setUpdateTime(nowTime);
		sysHandicapT.setUpdateTimeLong(sysHandicapT.getUpdateTime().getTime());
		sysHandicapT.setState(IbmStateEnum.OPEN.name());
		sysHandicapTService.save(sysHandicapT);
	}
	/**
	 * 初始化盘口游戏
	 *
	 * @param handicap 盘口信息
	 * @param gameIds  游戏ids
	 * @throws Exception
	 */
	private void initHandicapGame(IbmHandicapT handicap, String[] gameIds) throws Exception {
		Date nowTime = new Date();
		List<Map<String, Object>> list = new IbmGameTService().listByIds(gameIds);
		IbmHandicapGameTService handicapGameService = new IbmHandicapGameTService();
		for (Map<String, Object> map : list) {
			IbmHandicapGameT handicapGame = new IbmHandicapGameT();
			handicapGame.setHandicapId(handicap.getIbmHandicapId());
			handicapGame.setGameId(map.get("IBM_GAME_ID_"));
			handicapGame.setHandicapName(handicap.getHandicapName());
			handicapGame.setHandicapCode(handicap.getHandicapCode());
			handicapGame.setGameName(map.get("GAME_NAME_"));
			handicapGame.setGameCode(map.get("GAME_CODE_"));
			handicapGame.setSubIebiTableName("SUB_IEBI_"+handicap.getHandicapCode()+"_"+map.get("GAME_CODE_"));
			handicapGame.setIcon(map.get("ICON_"));
			handicapGame.setSn(7);
			handicapGame.setCreateUser("doming");
			handicapGame.setCreateTime(nowTime);
			handicapGame.setCreateTimeLong(nowTime.getTime());
			handicapGame.setUpdateUser("doming");
			handicapGame.setUpdateTime(nowTime);
			handicapGame.setUpdateTimeLong(nowTime.getTime());
			handicapGame.setState("OPEN");
			handicapGameService.save(handicapGame);
		}
	}
}
