package c.a.util.core.enums.bean.cnt;
/**
 * 维度类型
 * 
 * @ClassName:
 * @Description:
 * @author
 * @date 2016年9月16日 下午6:10:50
 * 
 */
public enum CntDimensionTypeEnum {
	/** 指标值 */
	QUOTA_VALUE("QUOTA_VALUE") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "指标值";
		}
	},
	/** 时间 */
	TIME("TIME") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "时间";
		}
	},
	/** 行政区划 */
	AREA("AREA") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "行政区划";
		}
	},

	/** 计量单位 */
	UNIT("UNIT") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "计量单位";
		}
	},

	/** 行业分类 */
	PROFESSION("PROFESSION") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "行业分类 ";
		}
	},

	/** 经济类型 */
	ECONOMIC_TYPE("ECONOMIC_TYPE") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "经济类型";
		}
	},

	/** 企业规模 */
	ENTERPRISE_SIZE("ENTERPRISE_SIZE") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "企业规模";
		}
	},
	/** 企业属性 */
	ENTERPRISE_ATT("ENTERPRISE_ATT") {
		public String getMsg() {
			return "en";
		}
		public String getMsgCn() {
			return "企业属性";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	CntDimensionTypeEnum(String codeInput) {
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
