package c.a.util.core.enums.bean;
/**
 * 数据权限等级
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum PermissionGradeEnum {
	
	/** 程序员*/
	PERMISSION_GRADE_CODER("0") {
		public String getMsg() {
			return "0";
		}
		public String getMsgCn() {
			return "程序员";
		}
	},
	/** 管理员*/
	PERMISSION_GRADE_ADMIN("10") {
		public String getMsg() {
			return "10";
		}
		public String getMsgCn() {
			return "管理员";
		}
	},
	
	/** 普通用户*/
	PERMISSION_GRADE_USER("20") {
		public String getMsg() {
			return "20";
		}
		public String getMsgCn() {
			return "普通用户";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	PermissionGradeEnum(String codeInput) {
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
