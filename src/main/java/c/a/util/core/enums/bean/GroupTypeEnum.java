package c.a.util.core.enums.bean;
/**
 * 用户组类型
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum GroupTypeEnum {
	/** 角色*/
	ROLE("ROLE") {
		public String getMsg() {
			return "ROLE";
		}
		public String getMsgCn() {
			return "角色";
		}
	},
	/** 权限组 */
	PERMISSION_GROUP("PERMISSION_GROUP") {
		public String getMsg() {
			return "PERMISSION_GROUP";
		}
		public String getMsgCn() {
			return "权限组";
		}
	},
	/** 讨论小组 */
	DISCUSSION_GROUP("DISCUSSION_GROUP") {
		public String getMsg() {
			return "DISCUSSION_GROUP";
		}
		public String getMsgCn() {
			return "讨论小组";
		}
	},
	/** 兴趣小组 */
	INTEREST_GROUP("INTEREST_GROUP") {
		public String getMsg() {
			return "INTEREST_GROUP";
		}
		public String getMsgCn() {
			return "兴趣小组";
		}
	},
	/** 机构 */
	ORG("ORG") {
		public String getMsg() {
			return "ORG";
		}
		public String getMsgCn() {
			return "机构 ";
		}
	},
	/** 公司 */
	COMPANY("COMPANY") {
		public String getMsg() {
			return "COMPANY";
		}
		public String getMsgCn() {
			return "公司";
		}
	},
	/** 子公司 */
	SUBCOMPANY("SUBCOMPANY") {
		public String getMsg() {
			return "SUBCOMPANY";
		}
		public String getMsgCn() {
			return "子公司";
		}
	},
	/** 部门 */
	DEPT("DEPT") {
		public String getMsg() {
			return "DEPT";
		}
		public String getMsgCn() {
			return "部门";
		}
	},
	/** 岗位 */
	POSITION("POSITION") {
		public String getMsg() {
			return "POSITION";
		}
		public String getMsgCn() {
			return "岗位";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	GroupTypeEnum(String codeInput) {
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
