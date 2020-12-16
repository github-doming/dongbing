package c.a.util.core.jdbc.bean.nut;

import java.util.ArrayList;
import java.util.List;

import c.a.util.core.jdbc.bean.sql_custom.TypeOperateBean;

/**
 * 
 * 
 * 描述：列的信息
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class ColumnBean extends ColumnCoreBean {
	/**
	 * 复合类型=CompositeType
	 */
	public static String dataType_Composite = "CompositeType";
	/**
	 * 复合集合=CompositeCollection
	 */
	public static String dataType_CompositeCollection = "CompositeCollection";
	/**
	 * 数据类型,字符串类型=String
	 */
	public static String dataType_String = "String";
	/**
	 * 大数据字符类型=Clob
	 */
	public static String dataType_Clob = "Clob";
	/**
	 * 大数据二进制类型=Blob
	 */
	public static String dataType_Blob = "Blob";
	/**
	 * 数据类型
	 */
	public static String dataType_Byte = "Byte";
	/**
	 * 数据类型
	 */
	public static String dataType_byteArray = "byte[]";
	/**
	 * 数据类型,数字类型=Number
	 */
	public static String dataType_Number = "Number";
	/**
	 * 数据类型
	 */
	public static String dataType_BigDecimal = "BigDecimal";
	/**
	 * 数据类型
	 */
	public static String dataType_Boolean = "Boolean";
	/**
	 * 数据类型
	 */
	public static String dataType_Short = "Short";
	/**
	 * 数据类型,整数类型=Integer
	 */
	public static String dataType_Integer = "Integer";
	/**
	 * 数据类型
	 */
	public static String dataType_Long = "Long";
	/**
	 * 数据类型
	 */
	public static String dataType_Float = "Float";
	/**
	 * 数据类型
	 */
	public static String dataType_Double = "Double";
	/**
	 * 数据类型,日期类型=Date
	 */
	public static String dataType_Date = "Date";
	// 操作类型
	private String typeOperate;

	// 操作类型
	private List<TypeOperateBean> typeOperateBeanList = null;
	// 实体数据类型
	private String dataType;
	// 自定义的,长度
	protected Long length;
	// 自定义的,列名小写
	private String fieldName;
	// 自定义的,头个字母转换成大写
	private String methodName;
	// 表单的域名
	private String formField;
	// 表单的域名(显示)
	private String formFieldView;
	// 列的值
	private String value;
	// 获取指定列的名称
	private String id;
	// 是否外键
	// private Boolean isForgeignKey = false;
	private Boolean isFk = false;
	// 外键reference table
	private String fkRefTable;
	// 外键reference column
	private String fkRefColumn;
	public String getTypeOperate() {
		return typeOperate;
	}
	public void setTypeOperate(String typeOperate) {
		this.typeOperate = typeOperate;

	}

	public List<TypeOperateBean> getTypeOperateBeanList() {
		if (typeOperateBeanList == null) {
			typeOperateBeanList = new ArrayList<TypeOperateBean>();
		}
		return typeOperateBeanList;
	}
	public void setTypeOperateBeanList(List<TypeOperateBean> typeOperateBeanList) {
		this.typeOperateBeanList = typeOperateBeanList;
	}
	public Long getLength() {
		return length;
	}
	public void setLength(Long length) {
		this.length = length;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getFormField() {
		return formField;
	}
	public void setFormField(String formField) {
		this.formField = formField;
	}
	public String getFormFieldView() {
		return formFieldView;
	}
	public void setFormFieldView(String formFieldView) {
		this.formFieldView = formFieldView;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Boolean getIsFk() {
		return isFk;
	}
	public void setIsFk(Boolean isFk) {
		this.isFk = isFk;
	}
	public String getFkRefTable() {
		return fkRefTable;
	}
	public void setFkRefTable(String fkRefTable) {
		this.fkRefTable = fkRefTable;
	}
	public String getFkRefColumn() {
		return fkRefColumn;
	}
	public void setFkRefColumn(String fkRefColumn) {
		this.fkRefColumn = fkRefColumn;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
