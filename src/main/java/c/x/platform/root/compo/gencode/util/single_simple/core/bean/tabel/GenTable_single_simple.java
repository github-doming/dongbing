package c.x.platform.root.compo.gencode.util.single_simple.core.bean.tabel;

import java.util.List;

import c.a.util.core.jdbc.bean.nut.ColumnBean;
import c.a.util.core.string.StringUtil;

public class GenTable_single_simple {
	private String tableNameCustom;
	private String tableName;
	private String tableComment;
	private String className;
	private String packageName;
	private String objectName;

	// 自定义的,列名小写(主键)
	private String fieldNamePk;
	// 自定义的,头个字母转换成大写(主键)
	private String methodNamePk;

	// 获取指定列的名称(主键)
	protected String columnNamePk;

	private List<ColumnBean> columnBeanList;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<ColumnBean> getColumnBeanList() {
		return columnBeanList;
	}

	public void setColumnBeanList(List<ColumnBean> columnBeanList) {
		this.columnBeanList = columnBeanList;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = StringUtil.findClassName(className);
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = StringUtil.findPackageName(packageName);
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objName) {
		this.objectName = StringUtil.findFieldName(objName);
	}

	public String getTableNameCustom() {
		return tableNameCustom;
	}

	public void setTableNameCustom(String tableNameCustom) {
		this.tableNameCustom = tableNameCustom;
	}

	public String getFieldNamePk() {
		return fieldNamePk;
	}

	public void setFieldNamePk(String fieldNamePk) {
		this.fieldNamePk = fieldNamePk;
	}

	public String getMethodNamePk() {
		return methodNamePk;
	}

	public void setMethodNamePk(String methodNamePk) {
		this.methodNamePk = methodNamePk;
	}

	public String getColumnNamePk() {
		return columnNamePk;
	}

	public void setColumnNamePk(String columnNamePk) {
		this.columnNamePk = columnNamePk;
	}

	public String getTableComment() {
		return tableComment;
	}

	public void setTableComment(String tableComment) {
		this.tableComment = tableComment;
	}

}
