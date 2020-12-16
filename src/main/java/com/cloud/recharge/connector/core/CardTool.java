package com.cloud.recharge.connector.core;

import c.a.config.SysConfig;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.core.CodeEnum;
import com.cloud.common.core.DefaultConfig;
import com.cloud.common.core.JsonResultBeanPlus;
import com.cloud.user.app_user_point.service.AppUserPointService;
import com.cloud.user.app_user_point_rep.service.AppUserPointRepService;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.DateTool;
import org.doming.core.tools.Md5Tool;
import org.doming.core.tools.NumberTool;
import org.doming.develop.http.HttpTool;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 开卡工具类
 *
 * @Author: Dongming
 * @Date: 2020-06-17 17:59
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CardTool {

	private static String dailyTime;

	private static long dailyTimeEndLong = 0;

	public static String dailyTime() {
		long currentTime = System.currentTimeMillis();
		if (dailyTimeEndLong < currentTime) {
			synchronized (CardTool.class) {
				if (dailyTimeEndLong < currentTime) {
					Date nowTime = new Date(currentTime);
					dailyTime = DateTool.getDay(nowTime);
					dailyTimeEndLong = DateTool.getDayEnd(nowTime).getTime();
				}
			}
		}
		return dailyTime;
	}

	/**
	 * 查询用户类型是否能够添加卡种的充值卡
	 *
	 * @param userType     用户类型
	 * @param cardTreeType 卡种类型
	 * @return 能添加 true
	 */
	public static boolean checkCreateCard(String userType, String cardTreeType) {
		Type type = Type.valueOf(cardTreeType);
		switch (type) {
			case AGENT:
				return false;
			case PARTNER:
				return Type.AGENT.getUserType().equals(userType);
			case ADMIN:
				return !Type.ADMIN.getUserType().equals(userType);
			default:
				return true;
		}
	}

	/**
	 * 是否属于创建状态
	 *
	 * @param cardState 充值卡状态
	 * @return 属于创建状态 true
	 */
	public static boolean isCreateState(String cardState) {
		return "OPEN".equals(cardState) || "ALLOT".equals(cardState);
	}

	/**
	 * 激活卡密
	 *
	 * @param cardPassword 卡密
	 * @param useUserId    使用者
	 * @param point        积分
	 * @return 激活结果
	 */
	public static double activateCardPassword(String cardPassword, String useUserId, int point)
			throws Exception {
		Date nowTime = new Date();

		AppUserPointService userPointService = new AppUserPointService();
		AppUserPointRepService userPointRepService = new AppUserPointRepService();
		Map<String, Object> lastRepInfo = userPointService.findLastRepInfo(useUserId);
		String title = String.format("使用充值卡：%s", cardPassword);
		if (ContainerTool.notEmpty(lastRepInfo)) {
			String repId = userPointRepService.save(lastRepInfo, useUserId, point, title, nowTime);
			userPointService.update(lastRepInfo.get("APP_USER_POINT_ID_").toString(), point, repId, nowTime);
		} else {
			String repId = userPointRepService.save(new HashMap<>(), useUserId, point, title, nowTime);
			userPointService.save(useUserId, repId, point, nowTime);
		}
		return NumberTool.doubleT(lastRepInfo.get("BALANCE_T_")) + point;
	}

	/**
	 * 验证token
	 *
	 * @param name        验证名称
	 * @param token       验证token
	 * @param channelType 通道
	 * @return 验证结果
	 */
	public static JSONObject verifyToken(String name, String token, String channelType)
			throws Exception {
		JSONObject param = new JSONObject();
		param.put("name", name);
		param.put("token", token);
		param.put("channelType", channelType);
		String time = System.currentTimeMillis() + "";
		param.put("time", time);
		param.put("valiDate", Md5Tool.generate(time));
		Object url = SysConfig.findInstance().findMap().getOrDefault("cloud_url", DefaultConfig.CLOUD_URL);
		String cloudUrl = url + "/cloud/user/api/verifyToken";
		return JSON.parseObject(HttpTool.doGet(cloudUrl, HttpTool.paramJson(param)));

	}

	/**
	 * 检查用户是否有权限
	 *
	 * @param bean         检查结果
	 * @param cardTreeInfo 卡种信息
	 * @param userType     用户类型
	 * @return 没有 true
	 */
	public static boolean checkCard(JsonResultBeanPlus bean, Map<String, Object> cardTreeInfo, String userType) {
		if (ContainerTool.isEmpty(cardTreeInfo)) {
			bean.putEnum(CodeEnum.CLOUD_404_DATA);
			bean.putSysEnum(CodeEnum.CODE_404);
			return true;
		}
		if (CardTool.checkCreateCard(userType, cardTreeInfo.get("CARD_TREE_TYPE_").toString())) {
			bean.putEnum(CodeEnum.CLOUD_403_PERMISSION);
			bean.putSysEnum(CodeEnum.CODE_403);
			return true;
		}
		return false;
	}

	public enum State {
		/**
		 * 充值卡状态
		 */
		OPEN("未分配"), ALLOT("已分配"), ACTIVATE("激活");
		String name;

		State(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

	}

	public enum Type {
		/**
		 * 开卡管理员类型
		 */
		ADMIN("CARD_ADMIN", "管理员"), PARTNER("CARD_PARTNER", "股东"), AGENT("CARD_AGENT", "代理");
		String userType, name;

		Type(String userType, String name) {
			this.userType = userType;
			this.name = name;
		}

		public String getUserType() {
			return userType;
		}

		public String getName() {
			return name;
		}

		/**
		 * 返回类型
		 *
		 * @param userType 用户类型
		 * @return 类型
		 */
		public static Type getType(String userType) {
			switch (userType) {
				case "CARD_ADMIN":
				case "ADMIN":
				case "POWER_USER":
					return ADMIN;
				case "CARD_PARTNER":
				case "PARTNER":
					return PARTNER;
				case "CARD_AGENT":
				case "AGENT":
					return AGENT;
				default:
					return null;
			}
		}

		/**
		 * 返回类型
		 *
		 * @param treeType 卡种类型
		 * @return 类型
		 */
		public static Type getTreeType(String treeType) {
			switch (treeType) {
				case "ADMIN":
					return ADMIN;
				case "PARTNER":
					return PARTNER;
				case "AGENT":
					return AGENT;
				default:
					return null;
			}
		}

		/**
		 * 是否属于管理员
		 *
		 * @param userType 用户类型
		 * @return 管理员 true
		 */
		public static boolean isAdmin(String userType) {
			Type type = Type.getType(userType);
			return type == Type.ADMIN;
		}
	}
}
