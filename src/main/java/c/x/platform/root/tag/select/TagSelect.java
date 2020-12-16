package c.x.platform.root.tag.select;
import javax.servlet.jsp.tagext.BodyContent;
import org.apache.velocity.VelocityContext;
import c.x.platform.root.tag.core.BaseBodyTagSupport;
import c.a.util.core.file.FileUtil;
import c.a.config.SysConfig;
import c.a.tools.velocity.VelocityUtil;
/**
 * 
 * 
 * <B>
 * <P>
 * 描述:
 * 
 * 可编辑的Select的自定义标签
 * 
 * 
 * </P>
 * </B>
 * 
 * 
 * 
 */
public class TagSelect extends BaseBodyTagSupport {
	private String id;
	private String name;
	private String key;
	private String onchange;
	private String selected;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getOnchange() {
		return onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public String toHtml() throws Exception {
		String strBody = "";
		BodyContent bc = this.getBodyContent();
		if (bc != null) {
			strBody = bc.getString();
		}
		return toHtml(strBody);
	}
	public String toHtml(String strBody) throws Exception {
		String servletContextPath = this.pageContext.getServletContext().getRealPath("/");
		String templateFile = "/config/example/tag/select.html";
		// StringBuilder sb = new StringBuilder();
		// 1;读取模板文本;
		FileUtil fileUtil = new FileUtil();
		String templateFileContent = fileUtil.read(templateFile).toString();
		// 2;上下文;
		VelocityUtil velocityUtil = new VelocityUtil();
		VelocityContext velocityContext = new VelocityContext();
		velocityContext.put("id", id);
		velocityContext.put("name", name);
		velocityContext.put("key", key);
		if (this.onchange == null) {
			this.onchange = "";
		}
		velocityContext.put("onchange", this.onchange);
		if (this.selected == null) {
			this.selected = "";
		}
		velocityContext.put("selected", this.selected);
		velocityContext.put("option", strBody);
		String commLocalLog=SysConfig.findInstance().findMap().get(SysConfig.commLocalLog).toString();
		String html = velocityUtil.doTemplate2html(commLocalLog, velocityContext, templateFileContent);
		// log.trace("html=" + html, this);
		// sb.append(html);
		// return sb.toString();
		return html;
	}
	@Override
	public String toHtml_bodyEmptyNot(String strBody) throws Exception {
		return toHtml(strBody);
	}
	@Override
	public String toHtml_bodyEmpty() throws Exception {
		return toHtml("");
	}
}
