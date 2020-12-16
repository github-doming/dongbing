package all.ui.easyui.bean;
import java.util.List;
import java.util.Map;
public class EasyuiMenuBean {
	private String id;
	private String pid;
	private String text;
	private String url;
	private String iconCls;
	private String state;
	private List<EasyuiMenuBean> children;
	private Map<String, Object> attributes;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPid() {
		return pid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<EasyuiMenuBean> getChildren() {
		return children;
	}
	public void setChildren(List<EasyuiMenuBean> children) {
		this.children = children;
	}
	public Map<String, Object> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	
}
