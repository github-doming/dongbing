package c.a.tools.mvc.dto;
import java.util.ArrayList;
import java.util.List;
public class MvcActionDto {
	private String socket = null;
	private String boot = null;
	private String name = null;
	private String url = null;
	private String actionClass = null;
	private List<MvcResultDto> resultList = new ArrayList<MvcResultDto>();
	// -- 下面的方法不再更新 --//
	public String getSocket() {
		return socket;
	}
	public void setSocket(String socket) {
		this.socket = socket;
	}
	public String getBoot() {
		return boot;
	}
	public void setBoot(String boot) {
		this.boot = boot;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getActionClass() {
		return actionClass;
	}
	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}
	public List<MvcResultDto> getResultList() {
		return resultList;
	}
	public void setResultList(List<MvcResultDto> resultList) {
		this.resultList = resultList;
	}
	// -- 上面的方法不再更新 --//
}
