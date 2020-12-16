package c.a.util.core.jdbc.bean.create;

import java.util.ArrayList;
import java.util.List;

import c.a.util.core.jdbc.bean.nut.ColumnBean;
/**
 * 行记录
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class RowBean {

	private List<ColumnBean> columnBeanList = new ArrayList<ColumnBean>();

	public List<ColumnBean> getColumnBeanList() {
		return columnBeanList;
	}

	public void setColumnBeanList(List<ColumnBean> columnBeanList) {
		this.columnBeanList = columnBeanList;
	}

}
