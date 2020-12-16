package c.a.util.core.jdbc.config;

import c.a.util.core.jdbc.bean.sql_custom.SqlCustomBean;

/**
 * SQL自定义查询枚举
 * 
 * @Description:
 * @ClassName: SqlCustomQueryEnum
 * @date 2017年2月20日 下午2:00:31
 * @author cxy
 * @Email: 
 * @Copyright 
 * 
 */
public enum SqlCustomEnum {
	/** in查询 */
	In(SqlCustomBean.TypeOperateIn) {
		public String getType() {
			return " in ";
		}
		public String getTypeCn() {
			return " in查询 ";
		}
	},
	/** in查询 */
	Between(SqlCustomBean.TypeOperateBetween) {
		public String getType() {
			return " between ";
		}
		public String getTypeCn() {
			return " Between查询 ";
		}
	},
	/** like模糊查询 */
	Like(SqlCustomBean.TypeOperateLike) {
		public String getType() {
			return " like ";
		}
		public String getTypeCn() {
			return " 模糊查询 ";
		}
	},
	/** like左模糊查询 */
	LikeLeft(SqlCustomBean.TypeOperateLikeLeft) {
		public String getType() {
			return " like ";
		}
		public String getTypeCn() {
			return " 左模糊查询 ";
		}
	},
	/** like右模糊查询 */
	LikeRight(SqlCustomBean.TypeOperateLikeRight) {
		public String getType() {
			return " like ";
		}
		public String getTypeCn() {
			return " 右模糊查询 ";
		}
	},
	/** equal */
	Equal(SqlCustomBean.TypeOperateEqual) {
		public String getType() {
			return "=";
		}
		public String getTypeCn() {
			return "等于";
		}
	},
	/** > */
	Greate(SqlCustomBean.TypeOperateGreater) {
		public String getType() {
			return ">";
		}
		public String getTypeCn() {
			return "大于";
		}
	},
	/** >= */
	EqualGreate(SqlCustomBean.TypeOperateEqualGreater) {
		public String getType() {
			return ">=";
		}
		public String getTypeCn() {
			return "大于或等于";
		}
	},
	/** < */
	Less(SqlCustomBean.TypeOperateLess) {
		public String getType() {
			return "<";
		}
		public String getTypeCn() {
			return "小于";
		}
	},
	/** <= */
	EqualLess(SqlCustomBean.TypeOperateEqualLess) {
		public String getType() {
			return "<=";
		}
		public String getTypeCn() {
			return "小于或等于";
		}
	},
	/** asc */
	asc(SqlCustomBean.TypeOrderAsc) {
		public String getType() {
			return "asc";
		}
		public String getTypeCn() {
			return "升序";
		}
	},
	/** desc */
	desc(SqlCustomBean.TypeOrderDesc) {
		public String getType() {
			return "desc";
		}
		public String getTypeCn() {
			return "降序";
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	SqlCustomEnum(String codeInput) {
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
	 * 
	 * 类型
	 * 
	 * @Description:
	 * @Title: getType
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public abstract String getType();
	/**
	 * 类型的中文信息
	 * 
	 * @Title: getMsgCn
	 * @Description: 中文信息
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public abstract String getTypeCn();
}
