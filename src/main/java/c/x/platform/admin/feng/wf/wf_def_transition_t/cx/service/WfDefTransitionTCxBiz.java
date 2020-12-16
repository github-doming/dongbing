package c.x.platform.admin.feng.wf.wf_def_transition_t.cx.service;

import java.util.ArrayList;
import java.util.List;

import c.x.platform.admin.feng.wf.wf_def_transition_t.cx.entity.WfDefTransitionTCx;
import c.x.platform.root.common.service.BaseService;

public class WfDefTransitionTCxBiz extends BaseService {
	/**
	 * 通过from_node_name查询
	 * 
	 * @Description
	 * @Title findByFromNodeName
	 * @param from_node_name
	 * @param def_process_id
	 * @return
	 * @throws Exception
	 *             参数说明
	 * @return WfDefTransitionTCx 返回类型
	 * @throws
	 */
	public WfDefTransitionTCx findByFromNodeName(String from_node_name,
			String def_process_id) throws Exception {
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(from_node_name);
		parameterList.add(def_process_id);
		String sql = "SELECT * FROM wf_def_transition_t where from_node_name=? and def_process_id=?";
		WfDefTransitionTCx bean = (WfDefTransitionTCx) this.dao.findObject(
				WfDefTransitionTCx.class, sql, parameterList);
		return bean;
	}
}
