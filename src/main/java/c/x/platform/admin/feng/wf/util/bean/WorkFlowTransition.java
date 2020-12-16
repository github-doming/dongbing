package c.x.platform.admin.feng.wf.util.bean;

public class WorkFlowTransition {

	private String name;
	private String to_node;
	private String from_node;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTo_node() {
		return to_node;
	}

	public void setTo_node(String to_node) {
		this.to_node = to_node;
	}

	public String getFrom_node() {
		return from_node;
	}

	public void setFrom_node(String from_node) {
		this.from_node = from_node;
	}

}
