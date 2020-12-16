package c.a.util.job;
public enum QuartzEnum {
	/** 是 */
	TRUE("TRUE") {
		public String getMsg() {
			return "TRUE";
		}
		public String getMsgCn() {
			return "是";
		}
	},
	/** 否 */
	FALSE("FALSE") {
		public String getMsg() {
			return "FALSE";
		}
		public String getMsgCn() {
			return "否";
		}
	}
	
	;
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	QuartzEnum(String codeInput) {
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
	 * @Title: getMsg @Description: 英文信息 @return 参数说明 @return String
	 *         返回类型 @throws
	 */
	public abstract String getMsg();
	/**
	 * 中文信息
	 * 
	 * @Title: getMsgCn @Description: 中文信息 @return 参数说明 @return String
	 *         返回类型 @throws
	 */
	public abstract String getMsgCn();
}
