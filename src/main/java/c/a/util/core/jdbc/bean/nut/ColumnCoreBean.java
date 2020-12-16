package c.a.util.core.jdbc.bean.nut;

/**
 * 
 * 
 * 描述：通过ResultSetMetaData查找列的信息
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class ColumnCoreBean {

	// Java 编程语言中类的完全限定名称，方法 ResultSet.getObject
	// 将使用该名称获取指定列中的值。此名称为用于自定义映射关系的类名称
	protected String columnClassName;

	// tableCAT String => 表类别（可为 null）
	protected String catalog;
	// tableSCHEM String => 表模式（可为 null）
	protected String schemata;
	// tableNAME String => 表名称
	protected String tableName;

	// 获取指定列的名称。
	protected String columnName;
	// 获取用于打印输出和显示的指定列的建议标题。建议标题通常由 SQL AS 子句来指定。
	// 如果未指定 SQL AS，则从 getColumnLabel
	// 返回的值将和 getColumnName 方法返回的值相同。
	protected String columnLabel;

	// 来自 java.sql.Types 的 SQL 类型
	protected Integer sqlTypeInt;

	// 来自 java.sql.Types 的 SQL 类型
	// 数据库使用的类型名称。如果列类型是用户定义的类型，则返回完全限定的类型名称。
	protected String sqlTypeStr;

	// 允许作为指定列宽度的最大标准字符数
	protected Long columnDisplaySize;
	// p：精度位，precision，是总有效数据位数，取值范围是38，默认是38，可以用字符*表示38。
	// 获取指定列的指定列宽。对于数值型数据，是指最大精度。对于字符型数据，是指字符串长度。对于日期时间的数据类型，是指 String
	// 表示形式的字符串长度（假定为最大允许的小数秒组件）。对于二进制型数据，是指字节长度。对于 ROWID
	// 数据类型，是指字节长度。对于其列大小不可用的数据类型，则返回 0。
	// 最高整数位数＝p-s
	protected Long precision;
	// s：小数位，scale，是小数点右边的位数，取值范围是-84~127，默认值取决于p，
	// 如果没有指定p，那么s是最大范围，如果指定了p，那么s=0。
	// 获取指定列的小数点右边的位数。对于其标度不可用的数据类型，则返回 0。
	protected Long scale;

	// COLUMN_DEF String => 该列的默认值，当值在单引号内时应被解释为一个字符串（可为 null）
	// protected String columnDefalutValue;
	protected String columnDef;

	// REMARKS String => 描述列的注释（可为 null）
	protected String comment;

	// 给定列的状态是否可以为 null 的判断
	private Boolean isNull = true;

	// NULLABLE Integer => 是否允许使用 NULL。
	// columnNoNulls - 可能不允许使用 NULL 值
	// columnNullable - 明确允许使用 NULL 值
	// columnNullableUnknown - 不知道是否可使用 null

	protected Long isNullInt;

	// String => ISO 规则用于确定列是否包括 null。
	// YES --- 如果参数可以包括 NULL
	// NO --- 如果参数不可以包括 NULL
	// 空字符串 --- 如果不知道参数是否可以包括 null
	protected String isNullStr;
	public static String NULL_YES = "YES";
	public static String NULL_NO = "NO";;
	// IS_AUTOINCREMENT String => 指示此列是否自动增加
	// YES --- 如果该列自动增加
	// NO --- 如果该列不自动增加
	// 空字符串 --- 如果不能确定该列是否是自动增加参数

	protected String isAutoIncrememtStr;

	// 是否主键

	// private Boolean isPrimaryKey = false;
	private Boolean isPk = false;

	public String getColumnClassName() {
		return columnClassName;
	}

	public void setColumnClassName(String columnClassName) {
		this.columnClassName = columnClassName;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getSchemata() {
		return schemata;
	}

	public void setSchemata(String schemata) {
		this.schemata = schemata;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public Integer getSqlTypeInt() {
		return sqlTypeInt;
	}

	public void setSqlTypeInt(Integer columnTypeInteger) {
		this.sqlTypeInt = columnTypeInteger;
	}

	public String getSqlTypeStr() {
		return sqlTypeStr;
	}

	public void setSqlTypeStr(String columnTypeStr) {
		this.sqlTypeStr = columnTypeStr;
	}

	public Long getColumnDisplaySize() {
		return columnDisplaySize;
	}

	public void setColumnDisplaySize(Long columnDisplaySize) {
		this.columnDisplaySize = columnDisplaySize;
	}

	public Long getPrecision() {
		return precision;
	}

	public void setPrecision(Long precision) {
		this.precision = precision;
	}

	public Long getScale() {
		return scale;
	}

	public void setScale(Long scale) {
		this.scale = scale;
	}

	public String getColumnDef() {
		return columnDef;
	}

	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getIsNull() {
		return isNull;
	}

	public void setIsNull(Boolean isNull) {
		this.isNull = isNull;
	}

	public Long getIsNullInt() {
		return isNullInt;
	}

	public void setIsNullInt(Long isNullInteger) {
		this.isNullInt = isNullInteger;
	}

	public String getIsNullStr() {
		return isNullStr;
	}

	public void setIsNullStr(String isNullStr) {
		this.isNullStr = isNullStr;
	}

	public String getIsAutoIncrememtStr() {
		return isAutoIncrememtStr;
	}

	public void setIsAutoIncrememtStr(String isAutoIncrememtStr) {
		this.isAutoIncrememtStr = isAutoIncrememtStr;
	}

	public Boolean getIsPk() {
		return isPk;
	}

	public void setIsPk(Boolean isPrimaryKey) {
		this.isPk = isPrimaryKey;
	}

}
