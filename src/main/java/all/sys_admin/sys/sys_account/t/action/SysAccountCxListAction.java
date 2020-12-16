package all.sys_admin.sys.sys_account.t.action;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.sys_admin.sys.sys_account.t.service.SysAccountService;
import c.a.config.SysConfig;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.x.platform.root.common.action.BaseRoleAction;
import c.x.platform.root.login_not.current.CurrentSysUser;
public class SysAccountCxListAction extends BaseRoleAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		// 排序
		String sortFieldName = request
				.getParameter(SysConfig.sortFieldName);
		String sortOrderName = request
				.getParameter(SysConfig.sortOrderName);
		request.setAttribute(SysConfig.sortFieldValue, sortFieldName);
		request.setAttribute(SysConfig.sortOrderValue, sortOrderName);
		// 分页
		Integer pageIndex = BeanThreadLocal.findThreadLocal().get()
				.find(request.getParameter(SysConfig.pageIndexName), 1);
		Integer pageSize = BeanThreadLocal.findThreadLocal().get()
				.find(request.getParameter(SysConfig.pageSizeName), 10);
		SysAccountService service = new SysAccountService();
		 CurrentSysUser  currentUser=this.findCurrentSysUser();
		PageCoreBean<Map<String, Object>> basePage = service.find( currentUser ,sortFieldName,
				sortOrderName, pageIndex, pageSize);
		List<Map<String, Object>> mapList = basePage.getList();
		request.setAttribute("cPage", basePage);
		request.setAttribute("list", mapList);
		return CommViewEnum.Default.toString();
	}
}
