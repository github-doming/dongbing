package c.a.util.core.enums.bean;
/**
 * 
 * 请求类型
 * @Description: 
 * @date 2019年6月25日 上午11:09:10 
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public enum  RequestTypeEnum {
	/** USER*/
	USER("USER") {
		public String getMsg() {
			return "user";
		}
		public String getMsgCn() {
			return "USER请求";
		}
	},
	/** ADMIN*/
	ADMIN("ADMIN") {
		public String getMsg() {
			return "admin";
		}
		public String getMsgCn() {
			return "ADMIN请求";
		}
	},
	/**APP*/
	APP("APP") {
		public String getMsg() {
			return "app";
		}
		public String getMsgCn() {
			return "APP请求";
		}
	},
	/** SYSTEM*/
	SYSTEM("SYSTEM") {
		public String getMsg() {
			return "system";
		}
		public String getMsgCn() {
			return "SYSTEM请求";
		}
	},
	/** 缺省 */
	DEFAULT("DEFAULT") {
		public String getMsg() {
			return "default";
		}
		public String getMsgCn() {
			return "缺省";
		}
	},
	/** 已删除 */
	DEL("DEL") {
		public String getMsg() {
			return "del";
		}
		public String getMsgCn() {
			return "已删除";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	RequestTypeEnum(String codeInput) {
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
