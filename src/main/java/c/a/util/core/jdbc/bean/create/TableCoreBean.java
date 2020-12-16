package c.a.util.core.jdbc.bean.create;

/**
 * 
 * <pre>
 * 描述：表的信息
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 使用范围：
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 * </pre>
 */
public class TableCoreBean {
	// tableTYPE String => 表类型。典型的类型是
	// "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS"
	// 和 "SYNONYM"。
	public static String tableType_TABLE = "TABLE";
	// 表类型
	public static String tableType_VIEW = "VIEW";
	// 表类型
	public static String tableType_SYSTEM_TABLE = "SYSTEM TABLE";
	// 表类型
	public static String tableType_GLOBAL_TEMPORARY = "GLOBAL TEMPORARY";
	// 表类型
	public static String tableType_LOCAL_TEMPORARY = "LOCAL TEMPORARY";
	// 表类型
	public static String tableType_LOCAL_ALIAS = "ALIAS";
	// 表类型
	public static String tableType_SYNONYM = "SYNONYM";
	/**
	 * 通常希望在每次插入新记录时，自动地创建主键字段的值
	 */
	private Long autoIncrement;

	// tableCAT String => 表类别（可为 null）
	private String catalog;
	// tableSCHEM String => 表模式（可为 null）
	private String schemata;
	// tableNAME String => 表名称
	private String tableName;
	// tableTYPE String => 表类型。典型的类型是
	// "TABLE"、"VIEW"、"SYSTEM TABLE"、"GLOBAL TEMPORARY"、"LOCAL TEMPORARY"、"ALIAS"
	// 和 "SYNONYM"。
	private String tableType;

	// REMARKS String => 表的解释性注释
	private String comment;
	// type_CAT String => 类型的类别（可为 null）
	private String typeCat;
	// type_SCHEM String => 类型模式（可为 null）
	private String typeSchem;
	// type_NAME String => 类型名称（可为 null）
	private String typeName;
	// SELF_REFERENCING_COL_NAME String => 有类型表的指定 "identifier" 列的名称（可为 null）
	private String selftReferencingColName;
	// REF_GENERATION String => 指定在 SELF_REFERENCING_COL_NAME 中创建值的方式。这些值为
	// "SYSTEM"、"USER" 和 "DERIVED"。（可能为 null）
	private String refGeneration;
	public Long getAutoIncrement() {
		return autoIncrement;
	}
	public void setAutoIncrement(Long autoIncrement) {
		this.autoIncrement = autoIncrement;
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
	public String getTableType() {
		return tableType;
	}
	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getTypeCat() {
		return typeCat;
	}
	public void setTypeCat(String typeCat) {
		this.typeCat = typeCat;
	}
	public String getTypeSchem() {
		return typeSchem;
	}
	public void setTypeSchem(String typeSchem) {
		this.typeSchem = typeSchem;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getSelftReferencingColName() {
		return selftReferencingColName;
	}
	public void setSelftReferencingColName(String selftReferencingColName) {
		this.selftReferencingColName = selftReferencingColName;
	}
	public String getRefGeneration() {
		return refGeneration;
	}
	public void setRefGeneration(String refGeneration) {
		this.refGeneration = refGeneration;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

}
