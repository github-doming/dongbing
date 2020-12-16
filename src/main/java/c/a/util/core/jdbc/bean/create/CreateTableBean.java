package c.a.util.core.jdbc.bean.create;

import java.util.ArrayList;
import java.util.List;
/**
 * 多行记录
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class CreateTableBean {
	private TableBean tableBean = new TableBean();
	private List<RowBean> rowBeanList = new ArrayList<RowBean>();

	public List<RowBean> getRowBeanList() {
		return rowBeanList;
	}

	public void setRowBeanList(List<RowBean> rowBeanList) {
		this.rowBeanList = rowBeanList;
	}

	public TableBean getTableBean() {
		return tableBean;
	}

	public void setTableBean(TableBean tableBean) {
		this.tableBean = tableBean;
	}

}
