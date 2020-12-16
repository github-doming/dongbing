package c.a.tools.mvc.xml;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import c.a.util.core.xml.XmlUtil;
import c.a.tools.mvc.dto.MvcActionDto;
import c.a.tools.mvc.dto.MvcResultDto;
public class MvcXmlUtil extends XmlUtil {
	protected Logger log = LogManager.getLogger(MvcXmlUtil.class);
	/**
	 * 
	 * @param url
	 * @return
	 * @throws DocumentException
	 */
	public MvcActionDto findActionVo(String url) throws DocumentException {
		MvcActionDto action = null;
		List<Element> actionElementList = this.findRoot().elements();
		for (Element actionElement : actionElementList) {
			if ("action".equals(actionElement.getName())) {
				if (url.equals(actionElement.attributeValue("url"))) {
					action = new MvcActionDto();
					// 添加属性name
					action.setName(actionElement.attributeValue("name"));
					// 添加属性url
					action.setUrl(actionElement.attributeValue("url"));
					// 子元素
					List<Element> resultElementList = actionElement.elements();
					// 添加属性class
					// log.trace("class=" +
					// element_action.getTextTrim());
					action.setActionClass(actionElement.getTextTrim());
					for (Element resultElement : resultElementList) {
						MvcResultDto result = new MvcResultDto();
						if ("result".equals(resultElement.getName())) {
							// 添加属性name
							result.setName(resultElement.attributeValue("name"));
							// 添加属性type
							// log.trace("type="
							// + element_child.attributeValue("type"));
							result.setType(resultElement.attributeValue("type"));
							// 添加属性url
							// log.trace("url result="
							// + element_child.getTextTrim());
							result.setUrl(resultElement.getTextTrim());
							// 添加result
							action.getResultList().add(result);
						}
					}
					break;
				}
			}
		}
		return action;
	}
	/**
	 * 
	 * @return
	 * @throws DocumentException
	 */
	public List<MvcActionDto> findMvcActionDtoList() throws DocumentException {
		List<MvcActionDto> mvcActionDtoList = new ArrayList<MvcActionDto>();
		List<Element> actionElementList = this.findRoot().elements();
		for (Element actionElement : actionElementList) {
			if ("action".equals(actionElement.getName())) {	
				MvcActionDto action = new MvcActionDto();
				// 添加属性socket
				action.setSocket(actionElement.attributeValue("socket"));
				// 添加属性boot
				action.setBoot(actionElement.attributeValue("boot"));
				// 添加属性name
				action.setName(actionElement.attributeValue("name"));
				// 添加属性url
				//log.trace("action url=" + actionElement.attributeValue("url"));
				action.setUrl(actionElement.attributeValue("url"));
				// 子元素
				List<Element> resultElementList = actionElement.elements();
				// 添加属性class
				//log.trace("class=" + actionElement.getTextTrim());
				action.setActionClass(actionElement.getTextTrim());
				for (Element resultElement : resultElementList) {
					MvcResultDto result = new MvcResultDto();
					if ("result".equals(resultElement.getName())) {
						// 添加属性name
						result.setName(resultElement.attributeValue("name"));
						// 添加属性type
						//log.trace("type=" + childElement.attributeValue("type"));
						result.setType(resultElement.attributeValue("type"));
						// 添加属性url
						//log.trace("url result=" + childElement.getTextTrim());
						result.setUrl(resultElement.getTextTrim());
						// 添加result
						action.getResultList().add(result);
					}
				}
				// 添加url_action
				mvcActionDtoList.add(action);
			}
		}
		return mvcActionDtoList;
	}
}
