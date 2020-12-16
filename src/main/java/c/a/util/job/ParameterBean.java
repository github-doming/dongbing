package c.a.util.job;
/**
 * 
 * <pre>
 * 描述：任务参数对象
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 使用范围：
 * 本源代码受软件著作法保护，请在授权允许范围内使用。
 * </pre>
 */
public class ParameterBean {
	/**
	 * 参数数据类型： int long float boolean
	 */
	private String type = "";
	/**
	 * 参数key
	 */
	private String key = "";
	/**
	 * 参数值
	 */
	private String value = "";

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
