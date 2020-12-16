package all.upload.sys_bytes.t.action;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import all.upload.sys_bytes.t.service.SysBytesTService;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.root.common.action.BaseRoleAction;
public class SysBytesTListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		SysBytesTService service = new SysBytesTService();
			// 排序
			String sortFieldName = request.getParameter(SysConfig.sortFieldName);
			String sortOrderName = request
					.getParameter(SysConfig.sortOrderName);
			request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
			request.setAttribute(SysConfig.sortOrderValue, sortOrderName);
			// 分页
			Integer pageIndex = BeanThreadLocal.findThreadLocal().get().find(
					request.getParameter(SysConfig.pageIndexName), 1);
			Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(
					request.getParameter(SysConfig.pageSizeName), 10);
			PageCoreBean<Map<String, Object>> basePage = service.find(sortFieldName,
					sortOrderName, pageIndex, pageSize);
			List<Map<String, Object>> mapList = basePage.getList();
			for(Map<String, Object> map:mapList){
				Object descObject=map.get("DESC_");
				if(descObject==null){
					map.put("DESC_", "");
				}
			}
			request.setAttribute("cPage", basePage);
			request.setAttribute("list", mapList);
			return CommViewEnum.Default.toString();	
	}
}
