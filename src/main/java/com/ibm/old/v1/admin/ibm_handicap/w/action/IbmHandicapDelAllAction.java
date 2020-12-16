package com.ibm.old.v1.admin.ibm_handicap.w.action;
import c.a.util.core.enums.bean.CommViewEnum;
import com.ibm.old.v1.cloud.ibm_handicap.t.service.IbmHandicapTService;
import com.ibm.old.v1.cloud.ibm_handicap_game.t.service.IbmHandicapGameTService;
import com.ibm.old.v1.cloud.ibm_handicap_item.t.service.IbmHandicapItemTService;
import com.ibm.old.v1.cloud.ibm_role.w.service.IbmRoleWService;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.wck.init.InitUserDataInfo;
import com.ibm.old.v1.pc.app_user.t.service.AppUserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/**
 * @Description: 删除选择盘口
 * @Author: zjj
 * @Date: 2019-08-13 11:18
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class IbmHandicapDelAllAction extends BaseAppAction {

	@Override public String run() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		IbmHandicapTService service = new IbmHandicapTService();
		service.delAll(ids,this.getClass().getName());
		IbmHandicapItemTService handicapItemTService = new IbmHandicapItemTService();
		handicapItemTService.delAllByHandicapId(ids);

		//删除盘口相关游戏
		IbmHandicapGameTService handicapGameTService = new IbmHandicapGameTService();
		handicapGameTService.delAllByHandicapId(ids);


		//获取所有角色的盘口权限
		IbmRoleWService roleWService = new IbmRoleWService();
		List<Map<String, Object>> list = roleWService.findAllHandicapId();
		for (Map<String, Object> roleInfo : list) {
			List<String> handicapIdList = new ArrayList<>(Arrays.asList(roleInfo.get("HANDICAP_ID_").toString().split(",")));
			for (String id : ids) {
				handicapIdList.remove(id);
			}
			//更新角色盘口权限
			roleWService.updateHandicap(roleInfo.get("IBM_ROLE_ID_").toString(), handicapIdList);
		}
		//获取所有用户信息
		AppUserService appUserService = new AppUserService();
		List<String> userIdList = appUserService.findAllId();
		InitUserDataInfo dataInfo = new InitUserDataInfo();
		for (String userId : userIdList) {
			//初始化盘口用户信息
			dataInfo.initHandicap(userId);
		}
		return CommViewEnum.Default.toString();
	}
}
