package c.a.util.core.jdbc.bean.create;

import java.util.ArrayList;
import java.util.List;

/**
 * 包括主从表
 * 
 * @author cxy
 * @Email: 
 * @Copyright (c) 2002-2032 使用范围： 本源代码受软件著作法保护，请在授权允许范围内使用。
 */
public class CreateTableListBean {
	private CreateTableBean parentTableBean = new CreateTableBean();
	private List<CreateTableBean> childTableBeanList = new ArrayList<CreateTableBean>();

	public CreateTableBean getParentTableBean() {
		return parentTableBean;
	}

	public void setParentTableBean(CreateTableBean parentTableBean) {
		this.parentTableBean = parentTableBean;
	}

	public List<CreateTableBean> getChildTableBeanList() {
		return childTableBeanList;
	}

	public void setChildTableBeanList(List<CreateTableBean> childTableBeanList) {
		this.childTableBeanList = childTableBeanList;
	}

}
