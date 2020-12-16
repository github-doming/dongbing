package c.a.util.core.enums.bean;
/**
 * 用户类型
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum UserTypeEnum {
	/** 没有账户的游客 */
	GUEST("GUEST") {
		public String getMsg() {
			return "GUEST";
		}
		public String getMsgCn() {
			return "没有账户的游客";
		}
	},
	/** 平台会员,房间游客,会员(没有带入积分) */
	TOURIST("TOURIST") {
		public String getMsg() {
			return "TOURIST";
		}
		public String getMsgCn() {
			return "平台会员,房间游客,会员";
		}
	},
	/** 会员 */
	MEMBER("MEMBER") {
		public String getMsg() {
			return "MEMBER";
		}
		public String getMsgCn() {
			return "会员";
		}
	},
	/** VIP */
	VIP("VIP") {
		public String getMsg() {
			return "VIP";
		}
		public String getMsgCn() {
			return "VIP";
		}
	},
	/** 加入房间, 玩家(有带入积分) */
	PLAYER("PLAYER") {
		public String getMsg() {
			return "PLAYER";
		}
		public String getMsgCn() {
			return "加入房间, 玩家";
		}
	},
	/** 房间代理 */
	ROOMAGENT("ROOMAGENT") {
		public String getMsg() {
			return "ROOMAGENT";
		}
		public String getMsgCn() {
			return "房间代理";
		}
	},
	/** 房主 */
	OWNER("OWNER") {
		public String getMsg() {
			return "OWNER";
		}
		public String getMsgCn() {
			return "房主";
		}
	},
	/** 总代理 */
	GENERALAGENT("GENERALAGENT") {
		public String getMsg() {
			return "GENERALAGENT";
		}
		public String getMsgCn() {
			return "总代理";
		}
	},
	/** 业务平台管理员 */
	ADMIN("ADMIN") {
		public String getMsg() {
			return "ADMIN";
		}
		public String getMsgCn() {
			return "业务平台管理员";
		}
	},
	/** 技术平台管理员或超级管理员 */
	SUPERADMIN("SUPERADMIN") {
		public String getMsg() {
			return "SUPERADMIN";
		}
		public String getMsgCn() {
			return "技术平台管理员或超级管理员";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	UserTypeEnum(String codeInput) {
		this.code = codeInput;
	}
	public String getCode() {
		return code;
	}
	@Override
	public String toString() {
		return code;
	}
	/**
	 * 英文信息
	 * 
	 * @Title: getMsg
	 * @Description:
	 *
	 * 				参数说明
	 * @return 返回类型 String
	 */
	public abstract String getMsg();
	/**
	 * 中文信息
	 * 
	 * @Title: getMsgCn
	 * @Description:
	 *
	 * 				参数说明
	 * @return 返回类型 String
	 */
	public abstract String getMsgCn();
}
