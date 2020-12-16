package c.x.platform.admin.feng.wf.util.nut.def;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import c.x.platform.admin.feng.wf.util.bean.WorkFlowNode;
import c.x.platform.admin.feng.wf.util.bean.WorkFlowTransition;
import c.x.platform.admin.feng.wf.util.bean.WorkFlowXmlBean;
import c.x.platform.admin.feng.wf.wf_def_node_t.cx.entity.WfDefNodeTCx;
import c.x.platform.admin.feng.wf.wf_def_node_t.cx.service.WfDefNodeTCxService;
import c.x.platform.admin.feng.wf.wf_def_process_t.cx.entity.WfDefProcessTCx;
import c.x.platform.admin.feng.wf.wf_def_process_t.cx.service.WfDefProcessTCxService;
import c.x.platform.admin.feng.wf.wf_def_transition_t.cx.entity.WfDefTransitionTCx;
import c.x.platform.admin.feng.wf.wf_def_transition_t.cx.service.WfDefTransitionTCxService;
import c.a.util.core.file.FileThreadLocal;
import c.a.util.core.file.FileUtil;
import c.a.util.core.path.PathThreadLocal;
import c.a.util.core.path.PathUtil;

public class WorkFlowDefineTools {
	protected Logger log = LogManager.getLogger(this.getClass());
	/**
	 * 
	 * @Title: defProcess_saveBean
	 * @Description: 流程定义保存到数据库
	 * @param bean
	 * @throws Exception 参数说明
	 * @return void 返回类型
	 * @throws
	 */
	public void defProcess_saveBean(WorkFlowXmlBean bean) throws Exception {
		// 流程定义
		WfDefProcessTCxService wfDefProcessInfoService = new WfDefProcessTCxService();
		// 流程节点
		WfDefNodeTCxService wfDefNodeInfoService = new WfDefNodeTCxService();
		// 流程变迁
		WfDefTransitionTCxService service = new WfDefTransitionTCxService();
		// 流程定义
		WfDefProcessTCx wfDefProcessInfo = new WfDefProcessTCx();
		wfDefProcessInfo.setProcessKey(bean.getProcess_name());
		wfDefProcessInfo.setProcessName(bean.getProcess_name());
		String def_process_id = wfDefProcessInfoService.save(wfDefProcessInfo);
		// 流程节点
		for (WorkFlowNode node : bean.getListNode()) {
			WfDefNodeTCx wfDefNodeInfo = new WfDefNodeTCx();
			wfDefNodeInfo.setDefProcessId(Long.parseLong(def_process_id));
			wfDefNodeInfo.setNodeName(node.getName());
			wfDefNodeInfoService.save(wfDefNodeInfo);
			// 流程变迁
			for (WorkFlowTransition trTo : node.getList_transition_to()) {
				WfDefTransitionTCx wfDefTransitionInfo = new WfDefTransitionTCx();
				wfDefTransitionInfo.setDefProcessId(Long
						.parseLong(def_process_id));
				wfDefTransitionInfo.setFromNodeName(trTo.getFrom_node());
				wfDefTransitionInfo.setToNodeName(trTo.getTo_node());
				wfDefTransitionInfo.setTransitionName(trTo.getName());
				service.save(wfDefTransitionInfo);
			}
		}
	}

	/**
	 * 
	 * @Title: processParse
	 * @Description: 流程解析
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException 参数说明
	 * @return WorkFlowXmlBean 返回类型
	 * @throws
	 */
	public WorkFlowXmlBean defProcess_parse() throws IOException,
			ParserConfigurationException, SAXException {
		PathUtil pathUtil =PathThreadLocal.findThreadLocal().get();
		String filePath_in = pathUtil
				.findPath("/config/co/chen/simple/business/wf/example/i1/jbpm.xml");
		// log.trace("path=" + filePath_in);
		String filePath_out = "d://gen//b.txt";
		String string$file_content = "";
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
		StringBuilder sb;
		// log.trace("文件是否存在=" + fileUtil.isExistsFile(filePath_in));
		sb = fileUtil.read(filePath_in);
		WorkFlowXmlBean bean = strXml2bean(sb.toString());
		log.trace("Process_name=" + bean.getProcess_name());
		List<WorkFlowNode> listNode = bean.getListNode();
		for (WorkFlowNode wfNode : listNode) {
			log.trace("node name=" + wfNode.getName());
			List<WorkFlowTransition> list_transition_to = wfNode
					.getList_transition_to();
			for (WorkFlowTransition wfTransition : list_transition_to) {
				log.trace("tr name=" + wfTransition.getName());
				log.trace("tr from=" + wfTransition.getFrom_node());
				log.trace("tr to=" + wfTransition.getTo_node());
			}
		}
		return bean;
	}

	public WorkFlowXmlBean strXml2bean(String xml)
			throws ParserConfigurationException, SAXException, IOException {
		WorkFlowXmlBean bean = new WorkFlowXmlBean();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory
				.newDocumentBuilder();
		StringReader stringReader = new StringReader(xml);
		InputSource inputSource = new InputSource(stringReader);
		Document document = documentBuilder.parse(inputSource);
		Element rootElement = document.getDocumentElement();
		log.trace("rootElement=" + rootElement.getNodeName());
		NamedNodeMap mapRoot = rootElement.getAttributes();
		bean.setProcess_name(mapRoot.getNamedItem("name").getTextContent());
		NodeList nodeList = rootElement.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			WorkFlowNode wfNode = new WorkFlowNode();
			Node node = nodeList.item(i);
			if ("start".equals(node.getNodeName())
					|| "end".equals(node.getNodeName())
					|| "task".equals(node.getNodeName())) {
				// log.trace("node=" + node.getNodeName());
				wfNode.setName(node.getNodeName());
				NodeList nodeList_Child = node.getChildNodes();
				for (int k = 0; k < nodeList_Child.getLength(); k++) {
					Node nodeChild = nodeList_Child.item(k);
					if ("transition".equals(nodeChild.getNodeName())) {
						WorkFlowTransition wfTransition = new WorkFlowTransition();
						// log.trace("child=" +
						// nodeChild.getNodeName());
						NamedNodeMap map = nodeChild.getAttributes();
						//
						// log.trace("name="
						// + map.getNamedItem("name").getTextContent());
						// log.trace("to="
						// + map.getNamedItem("to").getTextContent());
						wfTransition.setName(map.getNamedItem("name")
								.getTextContent());
						wfTransition.setTo_node(map.getNamedItem("to")
								.getTextContent());
						wfTransition.setFrom_node(wfNode.getName());
						wfNode.getList_transition_to().add(wfTransition);
					}
				}
				bean.getListNode().add(wfNode);
			}
		}
		return bean;
	}
}
