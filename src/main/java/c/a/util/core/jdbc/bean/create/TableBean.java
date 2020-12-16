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
public class TableBean extends TableCoreBean {
	private String id;
	// 表单的域名
	private String formField;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFormField() {
		return formField;
	}

	public void setFormField(String formField) {
		this.formField = formField;
	}

}
