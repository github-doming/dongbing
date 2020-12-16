package c.x.platform.root.tag.example;
import c.x.platform.root.tag.core.BaseBodyTagSupport;
public class TagExample extends BaseBodyTagSupport {
	private String name;
	private String code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String toHtml_bodyEmptyNot(String strBody) throws Exception {
		String str = "name=" + name;
		str = str + ";code=" + code;
		str = str + ";body=" + strBody;
		return str + ";不为空1";
	}
	public String toHtml_bodyEmpty() throws Exception {
		String str = "name=" + name;
		str = str + ";code=" + code;
		return str + ";为空2";
	}
}
