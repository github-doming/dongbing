package com.ibm.follow.connector.admin.manage3.user.handicap;
import c.a.util.core.json.JsonResultBean;
import c.a.util.core.log.LogThreadLocal;
import com.ibm.common.core.CommAdminAction;
import com.ibm.common.core.JsonResultBeanPlus;
import com.ibm.common.enums.IbmCodeEnum;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.follow.connector.admin.manage3.handicap.service.IbmAdminHandicapUserService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.entity.IbmHaUser;
import com.ibm.follow.servlet.cloud.ibm_hm_user.entity.IbmHmUser;
import org.doming.core.common.servlet.ActionMapping;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.StringTool;

import java.util.Date;
/**
 * @Description: 添加用户盘口
 * @Author: Dongming
 * @Date: 2019-11-11 15:27
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
@ActionMapping(value = "/ibm/admin/manage/user/handicap/add")
public class HandicapAddAction extends CommAdminAction {
	@Override public Object run() throws Exception {
		super.findAdminUser();
		JsonResultBean threadJrb = LogThreadLocal.findLog();
		if (!threadJrb.isSuccess()) {
			return returnJson(threadJrb);
		}
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		String handicapName = request.getParameter("HANDICAP_NAME_");
		String handicapCode = request.getParameter("HANDICAP_CODE_");
		String handicapId = request.getParameter("HANDICAP_ID");
		String userId = request.getParameter("USER_ID_");
		String onlineNumberMaxStr = request.getParameter("ONLINE_NUMBER_MAX_");
		int onlineNumberMax = NumberTool.getInteger(onlineNumberMaxStr, 0);

		if (StringTool.isEmpty(handicapName, handicapCode, handicapId, userId) || onlineNumberMax < 0) {
			bean.putEnum(IbmCodeEnum.IBM_401_DATA);
			bean.putSysEnum(IbmCodeEnum.CODE_401);
			return bean;
		}
		try {
			IbmTypeEnum category = HandicapUtil.category(handicapId);

			String desc = this.getClass().getSimpleName().concat(",").concat(adminUser.getUserName())
					.concat(",添加用户盘口");
			//保存数据
			if (IbmTypeEnum.AGENT.equals(category)){
				IbmHaUser haUser = new IbmHaUser();
				haUser.setAppUserId(userId);
				haUser.setHandicapId(handicapId);
				haUser.setHandicapCode(handicapCode);
				haUser.setHandicapName(handicapName);
				haUser.setOnlineNumberMax(onlineNumberMax);
				haUser.setOnlineAgentsCount(0);
				haUser.setFrequency(0);
				haUser.setCreateTime(new Date());
				haUser.setCreateTimeLong(System.currentTimeMillis());
				haUser.setUpdateTimeLong(System.currentTimeMillis());
				haUser.setState(IbmStateEnum.OPEN.name());
				haUser.setDesc(desc);
				new IbmAdminHandicapUserService().save(haUser);
			}else{
				IbmHmUser hmUser = new IbmHmUser();
				hmUser.setAppUserId(userId);
				hmUser.setHandicapId(handicapId);
				hmUser.setHandicapCode(handicapCode);
				hmUser.setHandicapName(handicapName);
				hmUser.setOnlineNumberMax(onlineNumberMax);
				hmUser.setOnlineMembersCount(0);
				hmUser.setFrequency(0);
				hmUser.setCreateTime(new Date());
				hmUser.setCreateTimeLong(System.currentTimeMillis());
				hmUser.setUpdateTimeLong(System.currentTimeMillis());
				hmUser.setState(IbmStateEnum.OPEN.name());
				hmUser.setDesc(desc);
				new IbmAdminHandicapUserService().save(hmUser);
			}
			bean.success();
		} catch (Exception e) {
			log.error("添加用户盘口错误", e);
			throw e;
		}
		return bean;
	}
}
