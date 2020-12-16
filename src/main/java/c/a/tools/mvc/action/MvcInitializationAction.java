package c.a.tools.mvc.action;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.json.JsonTransactionBase;
/**
 * 
 * 自定义MVC框架BaseAction
 * 
 * 
 * 
 */
public class MvcInitializationAction extends JsonTransactionBase {
	protected Logger log = LogManager.getLogger(MvcInitializationAction.class);
	private String jsonTcp = null;
	protected JsonTcpBean jsonTcpBean = null;
	protected ServletContext servletContext;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	// -- set/get --//
	public String getJsonTcp() {
		return jsonTcp;
	}
	public void setJsonTcp(String jsonTcp) {
		this.jsonTcp = jsonTcp;
	}
	public JsonTcpBean getJsonTcpBean() {
		return jsonTcpBean;
	}
	public void setJsonTcpBean(JsonTcpBean jsonTcpBean) {
		this.jsonTcpBean = jsonTcpBean;
	}
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public ServletContext getServletContext() {
		return servletContext;
	}
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	// -- 上面的方法不再更新 --/
	// -- 下面添加新的方法 --/
}
