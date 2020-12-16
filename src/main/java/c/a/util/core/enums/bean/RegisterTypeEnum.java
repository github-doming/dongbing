package c.a.util.core.enums.bean;
/**
 * 注册类型或来源(APP或PC或管理员或程序)
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum RegisterTypeEnum {
	/** 房间代理注册 */
	ROOMAGENT("ROOMAGENT") {
		public String getMsg() {
			return "ROOMAGENT";
		}
		public String getMsgCn() {
			return "房间代理注册";
		}
	},
	/** 房主注册 */
	OWNER("OWNER") {
		public String getMsg() {
			return "OWNER";
		}
		public String getMsgCn() {
			return "房主注册";
		}
	},
	/** 通过APP注册 */
	APP("APP") {
		public String getMsg() {
			return "APP";
		}
		public String getMsgCn() {
			return "通过APP注册";
		}
	},
	/** 通过PC注册 */
	PC("PC") {
		public String getMsg() {
			return "PC";
		}
		public String getMsgCn() {
			return "通过PC注册";
		}
	},
	/** 管理员在后台手动添加注册 */
	ADMIN("ADMIN") {
		public String getMsg() {
			return "ADMIN";
		}
		public String getMsgCn() {
			return "管理员在后台手动添加注册";
		}
	},
	/** 程序员通过程序初始化注册 */
	INIT("INIT") {
		public String getMsg() {
			return "INIT";
		}
		public String getMsgCn() {
			return "程序员通过程序初始化注册";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	RegisterTypeEnum(String codeInput) {
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
