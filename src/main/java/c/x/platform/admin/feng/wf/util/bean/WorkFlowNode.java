package c.x.platform.admin.feng.wf.util.bean;

import java.util.ArrayList;
import java.util.List;

public class WorkFlowNode {
	private String name;
	private List<WorkFlowTransition> list_transition_from = new ArrayList<WorkFlowTransition>();
	private List<WorkFlowTransition> list_transition_to = new ArrayList<WorkFlowTransition>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<WorkFlowTransition> getList_transition_from() {
		return list_transition_from;
	}

	public void setList_transition_from(
			List<WorkFlowTransition> list_transition_from) {
		this.list_transition_from = list_transition_from;
	}

	public List<WorkFlowTransition> getList_transition_to() {
		return list_transition_to;
	}

	public void setList_transition_to(
			List<WorkFlowTransition> list_transition_to) {
		this.list_transition_to = list_transition_to;
	}

}
