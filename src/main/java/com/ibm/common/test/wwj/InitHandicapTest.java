package com.ibm.common.test.wwj;
import com.ibm.common.core.CommTest;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.common.enums.IbmTypeEnum;
import com.ibm.common.utils.HandicapUtil;
import com.ibm.connector.service.user.AppUserService;
import com.ibm.follow.servlet.cloud.ibm_ha_user.entity.IbmHaUser;
import com.ibm.follow.servlet.cloud.ibm_ha_user.service.IbmHaUserService;
import com.ibm.follow.servlet.cloud.ibm_handicap.entity.IbmHandicap;
import com.ibm.follow.servlet.cloud.ibm_handicap.service.IbmHandicapService;
import com.ibm.follow.servlet.cloud.ibm_hm_user.entity.IbmHmUser;
import com.ibm.follow.servlet.cloud.ibm_hm_user.service.IbmHmUserService;
import org.doming.core.tools.ContainerTool;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 * @Description: 初始化盘口信息测试类
 * @Author: Dongming
 * @Date: 2019-08-30 11:31
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class InitHandicapTest extends CommTest {

	@Test public void test() {
		super.transactionBegin();
		try {
			initHandicap(HandicapUtil.Code.IDC);

		} catch (Exception e) {
			log.error("测试错误", e);
			super.transactionRoll();
		} finally {
			super.transactionEnd();
		}
	}
	/**
	 * 初始化盘口
	 * @param code 盘口编码
	 */
	private void initHandicap(HandicapUtil.Code code) throws Exception {
		String handicapId =initHandicap(code, IbmTypeEnum.MEMBER);
		initUserMemberHandicap(code,handicapId);

		handicapId =initHandicap(code, IbmTypeEnum.AGENT);
		initUserAgentHandicap(code,handicapId);
	}

	/**
	 * 初始化盘口
	 * @param code 盘口编码
	 * @param category 盘口列表
	 * @return 盘口ID
	 */
	private String initHandicap(HandicapUtil.Code code, IbmTypeEnum category) throws Exception {
		IbmHandicap handicap = new IbmHandicap();
		handicap.setHandicapName(code.getName());
		handicap.setHandicapCode(code.name());
		handicap.setHandicapExplain("手动初始化");
		handicap.setHandicapCategory(category.name());
		handicap.setHandicapType(IbmTypeEnum.FREE.name());
		handicap.setHandicapWorthT(0);
		handicap.setHandicapVersion("0.0.1");
		handicap.setSn(0);
		handicap.setCreateUser("Doming");
		handicap.setCreateTime(new Date());
		handicap.setCreateTimeLong(System.currentTimeMillis());
		handicap.setUpdateTimeLong(System.currentTimeMillis());
		handicap.setState(IbmStateEnum.OPEN.name());
		return new IbmHandicapService().save(handicap);
	}

	/**
	 * 初始化会员盘口
	 * @param code 盘口编码
	 * @param handicapId 盘口ID
	 */
	private void initUserMemberHandicap(HandicapUtil.Code code, String handicapId) throws Exception {
		AppUserService userService = new AppUserService();
		List<Map<String,Object>>  userInfos = userService.listInfo2InitHandicap(IbmTypeEnum.FREE);
		if (ContainerTool.isEmpty(userInfos)){
			return;
		}
		IbmHmUserService hmUserService = new IbmHmUserService();
		Date nowTime = new Date();
		for (Map<String,Object> userInfo :userInfos){
			IbmHmUser hmUser = new IbmHmUser();
			hmUser.setAppUserId(userInfo.get("APP_USER_ID_"));
			hmUser.setHandicapId(handicapId);
			hmUser.setHandicapCode(code.name());
			hmUser.setHandicapName(code.getName());
			hmUser.setOnlineMembersCount(0);
			hmUser.setFrequency(0);
			hmUser.setCreateTime(nowTime);
			hmUser.setCreateTimeLong(System.currentTimeMillis());
			hmUser.setUpdateTimeLong(System.currentTimeMillis());
			hmUser.setState(IbmStateEnum.OPEN.name());
			hmUserService.save(hmUser);
		}
	}

	/**
	 * 初始化代理盘口
	 * @param code 盘口编码
	 * @param handicapId 盘口ID
	 */
	private void initUserAgentHandicap(HandicapUtil.Code code, String handicapId) throws Exception {
		AppUserService userService = new AppUserService();
		List<Map<String,Object>>  userInfos = userService.listInfo2InitHandicap(IbmTypeEnum.FREE);
		if (ContainerTool.isEmpty(userInfos)){
			return;
		}
		IbmHaUserService haUserService = new IbmHaUserService();
		Date nowTime = new Date();
		for (Map<String,Object> userInfo :userInfos){
			IbmHaUser haUser = new IbmHaUser();
			haUser.setAppUserId(userInfo.get("APP_USER_ID_"));
			haUser.setHandicapId(handicapId);
			haUser.setHandicapCode(code.name());
			haUser.setHandicapName(code.getName());
			haUser.setOnlineAgentsCount(0);
			haUser.setFrequency(0);
			haUser.setCreateTime(nowTime);
			haUser.setCreateTimeLong(System.currentTimeMillis());
			haUser.setUpdateTimeLong(System.currentTimeMillis());
			haUser.setState(IbmStateEnum.OPEN.name());
			haUserService.save(haUser);
		}
	}
}
