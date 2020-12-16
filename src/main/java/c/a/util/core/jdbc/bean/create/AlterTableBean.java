package c.a.util.core.jdbc.bean.create;

import java.util.ArrayList;
import java.util.List;

import c.a.util.core.jdbc.bean.nut.ColumnBean;

/**
 * 
 * 修改表的结构
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 * 
 */
public class AlterTableBean {

	private TableBean tableBean = new TableBean();
	// 增加的字段
	private List<ColumnBean> createColumnBeanList = new ArrayList<ColumnBean>();
	// 更新的字段（长度扩大，而且小数位扩大才能更新）
	private List<ColumnBean> alterColumnBeanList = new ArrayList<ColumnBean>();

	public TableBean getTableBean() {
		return tableBean;
	}

	public void setTableBean(TableBean tableBean) {
		this.tableBean = tableBean;
	}

	public List<ColumnBean> getCreateColumnBeanList() {
		return createColumnBeanList;
	}

	public void setCreateColumnBeanList(List<ColumnBean> createColumnBeanList) {
		this.createColumnBeanList = createColumnBeanList;
	}

	public List<ColumnBean> getAlterColumnBeanList() {
		return alterColumnBeanList;
	}

	public void setAlterColumnBeanList(List<ColumnBean> alterColumnBeanList) {
		this.alterColumnBeanList = alterColumnBeanList;
	}

}
