package c.x.platform.admin.feng.wf.util.bean;
import java.util.ArrayList;
import java.util.List;
public class WorkFlowXmlBean {
	// 流程名称
	private String process_name = null;
	public String getProcess_name() {
		return process_name;
	}
	public void setProcess_name(String process_name) {
		this.process_name = process_name;
	}
	private List<WorkFlowNode> listNode = new ArrayList<WorkFlowNode>();
	public List<WorkFlowNode> getListNode() {
		return listNode;
	}
	public void setListNode(List<WorkFlowNode> listNode) {
		this.listNode = listNode;
	}
}
