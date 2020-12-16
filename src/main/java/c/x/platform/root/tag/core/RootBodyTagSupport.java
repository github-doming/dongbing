package c.x.platform.root.tag.core;
import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
public abstract class RootBodyTagSupport extends BodyTagSupport {
	private static final long serialVersionUID = 1L;
	public int doEndTag() throws JspException {
		JspWriter jspWriter = this.pageContext.getOut();
		try {
			jspWriter.println(this.toHtml());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	public String toHtml() throws Exception {
		BodyContent bodyContent = this.getBodyContent();
		if (bodyContent == null) {
			return this.toHtml_bodyEmpty();
		} else {
			String strBody = bodyContent.getString();
			return this.toHtml_bodyEmptyNot(strBody);
		}
	}
	public abstract String toHtml_bodyEmptyNot(String strBody)
			throws Exception;
	public abstract String toHtml_bodyEmpty() throws Exception;
}
