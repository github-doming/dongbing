package c.a.util.core.jdbc.bean.data_row;

import java.util.ArrayList;

/**
 * 表记录;
 * 
 * @Description:
 * @ClassName: SqlDataAyBean
 * @date 2017年2月17日 下午2:37:58
 * @author cxy
 * @Email: 
 * @Copyright 
 * 
 */
public class JdbcDataDto {
	/**
	 * 列
	 */
	private ArrayList<String> fieldList;
	/**
	 * 一行记录;
	 */
	private ArrayList<JdbcRowDto> rowList;
	public ArrayList<String> getFieldList() {
		return fieldList;
	}
	public void setFieldList(ArrayList<String> fieldList) {
		this.fieldList = fieldList;
	}
	public ArrayList<JdbcRowDto> getRowList() {
		return rowList;
	}
	public void setRowList(ArrayList<JdbcRowDto> rowList) {
		this.rowList = rowList;
	}

}
