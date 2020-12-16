package com.ibm.old.v1.admin.cms_topic.w.action;

import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.root.common.action.BaseRoleAction;
import com.ibm.old.v1.pc.ibm_cms_topic.t.service.IbmPcCmsTopicTService;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @Description: 查询所有消息
 * @date 2019年2月20日 上午10:12:17 
 * @author wck
 * @Email: 810160078@qq.com
 * @Version v1.0
 *
 */
public class IbmCmsTopicWListAction extends BaseRoleAction{

	@Override
	public String execute() throws Exception {
		// 排序

		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);

		// 分页
		Integer pageNo = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request.getParameter(SysConfig.pageSizeName),
				10);

		IbmPcCmsTopicTService cmsTopicTService = new IbmPcCmsTopicTService();
		PageCoreBean pageCore = cmsTopicTService.listByUserId(pageNo, pageSize);

		List<Map<String, Object>> listMap = pageCore.getList();
		
		request.setAttribute("cPage", pageCore);
		request.setAttribute("list", listMap);

		return CommViewEnum.Default.toString();
	}
	
	
}
