package c.a.util.core.enums.bean.cms;
/**
 * 消息类型
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum CmsMsgTypeEnum {
	/** 私人消息 */
	PRIVATE("PRIVATE") {
		public String getMsg() {
			return "PRIVATE";
		}
		public String getMsgCn() {
			return "私人消息";
		}
	},
	/** APP系统消息 */
	APP("APP") {
		public String getMsg() {
			return "APP";
		}
		public String getMsgCn() {
			return "APP系统消息";
		}
	},
	/** 平台系统消息 */
	SYS("SYS") {
		public String getMsg() {
			return "SYS";
		}
		public String getMsgCn() {
			return "平台系统消息";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	CmsMsgTypeEnum(String codeInput) {
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
