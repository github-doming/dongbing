package c.a.util.core.enums.bean;
/**
 * 用户状态
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum UserStateEnum {
	
	/** 登录 */
	LOGIN("LOGIN") {
		public String getMsg() {
			return "LOGIN";
		}
		public String getMsgCn() {
			return "登录";
		}
	},
	/** 退出 */
	LOGOUT("LOGOUT") {
		public String getMsg() {
			return "LOGOUT";
		}
		public String getMsgCn() {
			return "退出";
		}
	},
	/** 在线 */
	ON_LINE("ON_LINE") {
		public String getMsg() {
			return "ON_LINE";
		}
		public String getMsgCn() {
			return "在线";
		}
	},
	/** 离线 */
	OFF_LINE("OFF_LINE") {
		public String getMsg() {
			return "OFF_LINE";
		}
		public String getMsgCn() {
			return "离线";
		}
	},
	/** 已打开 */
	OPEN("OPEN") {
		public String getMsg() {
			return "OPEN";
		}
		public String getMsgCn() {
			return "打开";
		}
	},
	/** 已关闭 */
	CLOSE("CLOSE") {
		public String getMsg() {
			return "CLOSE";
		}
		public String getMsgCn() {
			return "关闭";
		}
	},
	/** 已删除 */
	DEL("DEL") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "已删除";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	UserStateEnum(String codeInput) {
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
