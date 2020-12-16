package c.x.platform.admin.feng.wf.util.nut.ins;

import java.util.Date;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.x.platform.admin.feng.wf.wf_def_transition_t.cx.entity.WfDefTransitionTCx;
import c.x.platform.admin.feng.wf.wf_def_transition_t.cx.service.WfDefTransitionTCxBiz;
import c.x.platform.admin.feng.wf.wf_def_transition_t.cx.service.WfDefTransitionTCxService;
import c.x.platform.admin.feng.wf.wf_ins_node_t.cx.entity.WfInsNodeTCx;
import c.x.platform.admin.feng.wf.wf_ins_node_t.cx.service.WfInsNodeTCxService;
import c.x.platform.admin.feng.wf.wf_ins_process_t.cx.entity.WfInsProcessTCx;
import c.x.platform.admin.feng.wf.wf_ins_process_t.cx.service.WfInsProcessTCxService;

public class WorkFlowInstanceTools {
	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 
	 * @Title: insProcess_create
	 * @Description: 流程实例创建
	 * @param DefProcessId
	 * @return
	 * @throws Exception 参数说明
	 * @return String 返回类型
	 * @throws
	 */
	public String insProcess_create(Long DefProcessId) throws Exception {
		WfInsProcessTCxService service = new WfInsProcessTCxService();
		WfInsProcessTCx info = new WfInsProcessTCx();
		info.setDefProcessId(DefProcessId);
		String ins_process_id = service.save(info);
		return ins_process_id;
	}

	/**
	 * 
	 * @Title: insProcess_start
	 * @Description: 执行开始节点
	 * @param ins_process_id
	 * @throws Exception 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void insProcess_start(String ins_process_id) throws Exception {
		WfInsProcessTCxService ins = new WfInsProcessTCxService();
		WfInsProcessTCx info = ins.find(ins_process_id);
		this.insProcess_signal("start", info.getDefProcessId().toString(),
				ins_process_id);
	}

	/**
	 * 
	 * @Title: insProcess_signal
	 * @Description: 发送信号
	 * @param from_node_name
	 * @param DefProcessId
	 * @param ins_process_id
	 * @throws Exception 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void insProcess_signal(String from_node_name, String DefProcessId,
			String ins_process_id) throws Exception {
		// 查找下一个节点
		WfDefTransitionTCxService wfDefTransitionInfoService = new WfDefTransitionTCxService();
		WfDefTransitionTCxBiz cxWfDefTransitionInfoBiz = new WfDefTransitionTCxBiz();
		WfDefTransitionTCx wfDefTransitionInfo = cxWfDefTransitionInfoBiz
				.findByFromNodeName(from_node_name, DefProcessId);
		log.trace("to node=" + wfDefTransitionInfo.getToNodeName());
		// 跳转到下一个节点
		Date date = new Date();
		WfInsNodeTCxService wfInsNodeInfoService = new WfInsNodeTCxService();
		WfInsNodeTCx info = new WfInsNodeTCx();
		info.setNodeName(wfDefTransitionInfo.getToNodeName());
		info.setStatus("start");
		info.setStartTime(date.getTime());
		info.setStartTimeDt(date);
		info.setInsProcessId(Long.parseLong(ins_process_id));
		wfInsNodeInfoService.save(info);
	}
}
