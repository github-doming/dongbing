package all.gen.wx_p_config.t.action;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import all.gen.wx_p_config.t.service.WxPConfigTService;
import c.x.platform.root.common.action.BaseRoleAction;
import c.a.config.SysConfig;
import c.a.util.core.enums.bean.CommViewEnum;
public class WxPConfigTListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		WxPConfigTService service = new WxPConfigTService();
		// 排序
		String sortFieldName = request.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);
		// 分页
		Integer pageIndex = BeanThreadLocal.find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.find(request.getParameter(SysConfig.pageSizeName), 10);
		PageCoreBean<Map<String, Object>> basePage = service.find(sortFieldName, sortOrderName, pageIndex, pageSize);
		List<Map<String, Object>> mapList = basePage.getList();
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", mapList);
		return CommViewEnum.Default.toString();
	}
}
