package c.a.util.ldap;

public enum LdapStatusEnum {
	// 同步时，所有非删除状态的用户先禁用，然后同步，如果数据不更新的激活，数据禁用的删除
	//enable("激活"), disable("禁用"), add("新增"), del("删除"), update("更新");
	/** 激活 */
	enable("enable") {
		public String getMsg() {
			return "enable";
		}
		public String getMsgCn() {
			return "激活";
		}
	},
	/** 禁用 */
	disable("disable") {
		public String getMsg() {
			return "disable";
		}
		public String getMsgCn() {
			return "禁用";
		}
	},
	/** 新增 */
	add("add") {
		public String getMsg() {
			return "add";
		}
		public String getMsgCn() {
			return "新增";
		}
	},
	/** 删除 */
	del("del") {
		public String getMsg() {
			return "del";
		}
		public String getMsgCn() {
			return "删除";
		}
	},
	/** 更新 */
	update("update") {
		public String getMsg() {
			return "update";
		}
		public String getMsgCn() {
			return "更新";
		}
	};
	
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	LdapStatusEnum(String codeInput) {
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
