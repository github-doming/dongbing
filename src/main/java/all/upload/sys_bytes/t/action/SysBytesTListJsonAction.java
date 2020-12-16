package all.upload.sys_bytes.t.action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import all.upload.sys_bytes.t.service.SysBytesTService;
import c.x.platform.root.common.action.BaseAction;
public class SysBytesTListJsonAction extends BaseAction {
	public SysBytesTListJsonAction() {
		this.menuAllow = false;
	}
	@Override
	public String execute() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 分页
	
		String sortField=BeanThreadLocal.findThreadLocal().get().find(request,"sortField", "");
		String sortOrder=BeanThreadLocal.findThreadLocal().get().find(request,"sortOrder", "");
		request.setAttribute("sortField", sortField);
		request.setAttribute("sortOrder",sortOrder);
		
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get().find(request,"page", 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get().find(request,"rows", 10);

		List<Object> parameterList = new ArrayList<Object>();
		SysBytesTService service = new SysBytesTService();
		PageCoreBean<Map<String, Object>> basePage = service.find(sortField,sortOrder,
				pageIndex, pageSize);

		List<Map<String, Object>> listMap = basePage.getList();
		//map.put("data", listMap);
		map.put("rows", listMap);
		map.put("total", basePage.getTotalCount());
		return this.returnJson(map);
	}
	
}
