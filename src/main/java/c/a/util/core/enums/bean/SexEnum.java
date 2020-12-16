package c.a.util.core.enums.bean;
/**
 * 性别
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum SexEnum {
	
	/** 女*/
	FEMALE("FEMALE") {
		public String getMsg() {
			return "female";
		}
		public String getMsgCn() {
			return "女";
		}
	},
	/** 男 */
	MALE("MALE") {
		public String getMsg() {
			return "male";
		}
		public String getMsgCn() {
			return "男";
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
	SexEnum(String codeInput) {
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
