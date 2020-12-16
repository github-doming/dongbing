package c.a.util.core.jdbc.config;
import java.util.ArrayList;
import java.util.List;

import c.a.util.core.jdbc.bean.sql_custom.TypeOperateBean;
import c.a.util.core.jdbc.bean.nut.ColumnBean;
/**
 * 列类型枚举
 * 
 * @Description:
 * @ClassName:
 * @date 2017年2月20日 下午2:00:31
 * @author cxy
 * @Email: 
 * @Copyright 
 * 
 */
public enum ColumnTypeEnum {
	// <code>BIT</code>.
	// public final static int BIT = -7;
	BIT("BIT") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.BIT;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Boolean;
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>TINYINT</code>
	// public final static int TINYINT = -6;
	TINYINT("TINYINT") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.TINYINT;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Byte;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	TINYINT_UNSIGNED("TINYINT UNSIGNED") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.TINYINT;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Byte;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	// <code>SMALLINT</code>.
	// */
	// public final static int SMALLINT = 5;
	SMALLINT("SMALLINT") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.SMALLINT;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Short;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	SMALLINT_UNSIGNED("SMALLINT UNSIGNED") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.SMALLINT;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Short;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	// <code>INTEGER</code>.
	// */
	// public final static int INTEGER = 4;
	INTEGER("INTEGER") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.INTEGER;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Integer;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	INT("INT") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.INTEGER;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Integer;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	INT_UNSIGNED("INT UNSIGNED") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.INTEGER;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Integer;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	// <code>BIGINT</code>.
	// */
	// public final static int BIGINT = -5;
	BIGINT("BIGINT") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.BIGINT;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Long;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	BIGINT_UNSIGNED("BIGINT UNSIGNED") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.BIGINT;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Long;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	// <code>FLOAT</code>.
	// */
	// public final static int FLOAT = 6;
	FLOAT("FLOAT") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.FLOAT;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Float;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	// <code>REAL</code>.
	// */
	// public final static int REAL = 7;
	REAL("REAL") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.REAL;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>DOUBLE</code>.
	// */
	// public final static int DOUBLE = 8;
	DOUBLE("DOUBLE") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.DOUBLE;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Double;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	// <code>NUMERIC</code>.
	// */
	// public final static int NUMERIC = 2;
	NUMERIC("NUMERIC") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.NUMERIC;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	// <code>DECIMAL</code>.
	// */
	// public final static int DECIMAL = 3;
	DECIMAL("DECIMAL") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.DECIMAL;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_BigDecimal;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	// <code>CHAR</code>.
	// */
	// public final static int CHAR = 1;
	CHAR("CHAR") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.CHAR;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_String;
		}
		public String findTypeOperate() {
			return this.findTypeOperateStr();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListStr();
		}
	},
	// <code>VARCHAR</code>.
	// */
	// public final static int VARCHAR = 12;
	VARCHAR("VARCHAR") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.VARCHAR;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_String;
		}
		public String findTypeOperate() {
			return this.findTypeOperateStr();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListStr();
		}
	},
	// <code>LONGVARCHAR</code>.
	// */
	// public final static int LONGVARCHAR = -1;
	LONGVARCHAR("LONGVARCHAR") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.LONGVARCHAR;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return this.findTypeOperateStr();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListStr();
		}
	},
	// <code>DATE</code>.
	// */
	// public final static int DATE = 91;
	DATE("DATE") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.DATE;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Date;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	// <code>TIME</code>.
	// */
	// public final static int TIME = 92;
	TIME("TIME") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.TIME;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Date;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	// <code>TIMESTAMP</code>.
	// */
	// public final static int TIMESTAMP = 93;
	TIMESTAMP("TIMESTAMP") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.TIMESTAMP;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Date;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	DATETIME("DATETIME") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.TIMESTAMP;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_Date;
		}
		public String findTypeOperate() {
			return this.findTypeOperateNumber();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListNumber();
		}
	},
	// <code>BINARY</code>
	// public final static int BINARY = -2;
	BINARY("BINARY") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.BINARY;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>VARBINARY</code>
	// public final static int VARBINARY = -3;
	VARBINARY("VARBINARY") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.VARBINARY;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>LONGVARBINARY</code>
	// public final static int LONGVARBINARY = -4;
	LONGVARBINARY("LONGVARBINARY") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.LONGVARBINARY;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>NULL</code>
	// public final static int NULL = 0;
	NULL("NULL") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.NULL;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>getObject</code> and <code>setObject</code>
	// public final static int OTHER = 1111;
	OTHER("OTHER") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.OTHER;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>JAVA_OBJECT</code>
	// public final static int JAVA_OBJECT = 2000;
	JAVA_OBJECT("JAVA_OBJECT") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.JAVA_OBJECT;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>DISTINCT</code>.
	// *
	// */
	// public final static int DISTINCT = 2001;
	DISTINCT("DISTINCT") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.DISTINCT;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>STRUCT</code>.
	// *
	// */
	// public final static int STRUCT = 2002;
	STRUCT("STRUCT") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.STRUCT;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>ARRAY</code>
	// public final static int ARRAY = 2003;
	ARRAY("ARRAY") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.ARRAY;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>BLOB</code>.
	// *
	// */
	// public final static int BLOB = 2004;
	BLOB("BLOB") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.BLOB;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_byteArray;
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	LONGBLOB("LONGBLOB") {
		@Override
		public Integer getSqlTypeInt() {
			return null;
		}
		@Override
		public String getDataType() {
			return ColumnBean.dataType_byteArray;
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>CLOB</code>.
	// public final static int CLOB = 2005;
	CLOB("CLOB") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.CLOB;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>REF</code>.
	// *
	// */
	// public final static int REF = 2006;
	//
	REF("REF") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.REF;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>DATALINK</code>.
	// *
	// * @since 1.4
	// */
	// public final static int DATALINK = 70;
	DATALINK("DATALINK") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.DATALINK;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>BOOLEAN</code>.
	// *
	// * @since 1.4
	// */
	// public final static int BOOLEAN = 16;
	BOOLEAN("BOOLEAN") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.BOOLEAN;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>ROWID</code>
	// public final static int ROWID = -8;
	ROWID("ROWID") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.ROWID;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>NCHAR</code>
	// public static final int NCHAR = -15;
	NCHAR("NCHAR") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.NCHAR;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return this.findTypeOperateStr();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListStr();
		}
	},
	// <code>NVARCHAR</code>
	// public static final int NVARCHAR = -9;
	NVARCHAR("NVARCHAR") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.NVARCHAR;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return this.findTypeOperateStr();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListStr();
		}
	},
	// <code>LONGNVARCHAR</code>.
	// public static final int LONGNVARCHAR = -16;
	LONGNVARCHAR("LONGNVARCHAR") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.LONGNVARCHAR;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return this.findTypeOperateStr();
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return this.findTypeOperateBeanListStr();
		}
	},
	// <code>NCLOB</code>.
	// public static final int NCLOB = 2011;
	NCLOB("NCLOB") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.NCLOB;
		}
		@Override
		public String getDataType() {
			return "";
		}
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	},
	// <code>XML</code>.
	// public static final int SQLXML = 2009;
	SQLXML("SQLXML") {
		@Override
		public Integer getSqlTypeInt() {
			return java.sql.Types.SQLXML;
		}
		@Override
		public String getDataType() {
			return "";
		}
		@Override
		public String findTypeOperate() {
			return null;
		}
		@Override
		public List<TypeOperateBean> findTypeOperateBeanList() {
			return null;
		}
	};
	private String code;
	// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
	ColumnTypeEnum(Object codeInput) {
		this.code = codeInput.toString();
	}
	public String getCode() {
		return code;
	}
	@Override
	public String toString() {
		return code;
	}
	/**
	 * sql列类型
	 * 
	 * @Description:
	 * @Title: getSqlTypeInt
	 * @return 参数说明
	 * @return Integer 返回类型
	 * @throws
	 */
	public abstract Integer getSqlTypeInt();
	/**
	 * 数据类型
	 * 
	 * @Description:
	 * @Title: getDataType
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public abstract String getDataType();
	/**
	 * 操作类型
	 * 
	 * @Description:
	 * @Title: findTypeOperate
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public abstract String findTypeOperate();
	/**
	 * 
	 * 操作类型
	 * 
	 * @Description:
	 * @Title: getTypeOperate
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public abstract List<TypeOperateBean> findTypeOperateBeanList();
	/**
	 * 数字或日期
	 * 
	 * @Description:
	 * @Title: findTypeOperateNumber
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public String findTypeOperateNumber() {
		StringBuilder sb = new StringBuilder();
		sb.append(SqlCustomEnum.Equal.getCode());
		sb.append(",");
		sb.append(SqlCustomEnum.EqualGreate.getCode());
		sb.append(",");
		sb.append(SqlCustomEnum.Greate.getCode());
		sb.append(",");
		sb.append(SqlCustomEnum.EqualLess.getCode());
		sb.append(",");
		sb.append(SqlCustomEnum.Less.getCode());
		sb.append(",");
		sb.append(SqlCustomEnum.In.getCode());
		return sb.toString();
	}
	/**
	 * 字符串
	 * 
	 * @Description:
	 * @Title: findTypeOperateStr
	 * @return 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public String findTypeOperateStr() {
		StringBuilder sb = new StringBuilder();
		sb.append(SqlCustomEnum.Equal.getCode());
		sb.append(",");
		sb.append(SqlCustomEnum.Like.getCode());
		sb.append(",");
		sb.append(SqlCustomEnum.LikeLeft.getCode());
		sb.append(",");
		sb.append(SqlCustomEnum.LikeRight.getCode());
		sb.append(",");
		sb.append(SqlCustomEnum.In.getCode());
		return sb.toString();
	}
	/**
	 * 数字或日期
	 * 
	 * @Description:
	 * @Title: findTypeOperateBeanListNumber
	 * @return 参数说明
	 * @return List<TypeOperateBean> 返回类型
	 * @throws
	 */
	public List<TypeOperateBean> findTypeOperateBeanListNumber() {
		List<TypeOperateBean> typeOperateBeanList = new ArrayList<TypeOperateBean>();
		TypeOperateBean typeOperateBean = null;
		typeOperateBean = new TypeOperateBean();
		typeOperateBean.setId(SqlCustomEnum.Equal.getCode());
		typeOperateBean.setText(SqlCustomEnum.Equal.getTypeCn());
		typeOperateBeanList.add(typeOperateBean);
		typeOperateBean = new TypeOperateBean();
		typeOperateBean.setId(SqlCustomEnum.EqualGreate.getCode());
		typeOperateBean.setText(SqlCustomEnum.EqualGreate.getTypeCn());
		typeOperateBeanList.add(typeOperateBean);
		typeOperateBean = new TypeOperateBean();
		typeOperateBean.setId(SqlCustomEnum.Greate.getCode());
		typeOperateBean.setText(SqlCustomEnum.Greate.getTypeCn());
		typeOperateBeanList.add(typeOperateBean);
		typeOperateBean = new TypeOperateBean();
		typeOperateBean.setId(SqlCustomEnum.EqualLess.getCode());
		typeOperateBean.setText(SqlCustomEnum.EqualLess.getTypeCn());
		typeOperateBeanList.add(typeOperateBean);
		typeOperateBean = new TypeOperateBean();
		typeOperateBean.setId(SqlCustomEnum.Less.getCode());
		typeOperateBean.setText(SqlCustomEnum.Less.getTypeCn());
		typeOperateBeanList.add(typeOperateBean);
		typeOperateBean = new TypeOperateBean();
		typeOperateBean.setId(SqlCustomEnum.In.getCode());
		typeOperateBean.setText(SqlCustomEnum.In.getTypeCn());
		typeOperateBeanList.add(typeOperateBean);
		typeOperateBean = new TypeOperateBean();
		typeOperateBean.setId(SqlCustomEnum.Between.getCode());
		typeOperateBean.setText(SqlCustomEnum.Between.getTypeCn());
		typeOperateBeanList.add(typeOperateBean);
		return typeOperateBeanList;
	}
	/**
	 * 字符串
	 * 
	 * @Description:
	 * @Title: findTypeOperateBeanListStr
	 * @return 参数说明
	 * @return List<TypeOperateBean> 返回类型
	 * @throws
	 */
	public List<TypeOperateBean> findTypeOperateBeanListStr() {
		List<TypeOperateBean> typeOperateBeanList = new ArrayList<TypeOperateBean>();
		TypeOperateBean typeOperateBean = null;
		typeOperateBean = new TypeOperateBean();
		typeOperateBean.setId(SqlCustomEnum.Equal.getCode());
		typeOperateBean.setText(SqlCustomEnum.Equal.getTypeCn());
		typeOperateBeanList.add(typeOperateBean);
		typeOperateBean = new TypeOperateBean();
		typeOperateBean.setId(SqlCustomEnum.Like.getCode());
		typeOperateBean.setText(SqlCustomEnum.Like.getTypeCn());
		typeOperateBeanList.add(typeOperateBean);
		typeOperateBean = new TypeOperateBean();
		typeOperateBean.setId(SqlCustomEnum.LikeLeft.getCode());
		typeOperateBean.setText(SqlCustomEnum.LikeLeft.getTypeCn());
		typeOperateBeanList.add(typeOperateBean);
		typeOperateBean = new TypeOperateBean();
		typeOperateBean.setId(SqlCustomEnum.LikeRight.getCode());
		typeOperateBean.setText(SqlCustomEnum.LikeRight.getTypeCn());
		typeOperateBeanList.add(typeOperateBean);
		typeOperateBean = new TypeOperateBean();
		typeOperateBean.setId(SqlCustomEnum.Less.getCode());
		typeOperateBean.setText(SqlCustomEnum.Less.getTypeCn());
		typeOperateBeanList.add(typeOperateBean);
		return typeOperateBeanList;
	}
}
