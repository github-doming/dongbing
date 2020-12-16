package c.a.util.ldap;

public enum LdapConfigEnum {

	// connLdap("连接成功"), syncLdap("同步到中间表成功"), syncOrg("同步到组织架构成功");

	/** 连接成功 */
	connLdap("connLdap") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "连接成功";
		}
	},
	/** 同步到中间表成功 */
	syncLdap("syncLdap") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "同步到中间表成功";
		}
	},
	/** 同步到组织架构成功 */
	syncOrg("syncOrg") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "同步到组织架构成功";
		}
	}

	;
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	LdapConfigEnum(String codeInput) {
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
