package all.sys_admin.sys.sys_dict_tree.t.action;
import c.a.util.core.enums.bean.CommViewEnum;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import c.x.platform.root.common.action.BaseAction;
import all.sys_admin.sys.sys_dict_tree.t.entity.SysDictTreeT;
import all.sys_admin.sys.sys_dict_tree.t.service.SysDictTreeTService;
import all.sys_admin.sys.sys_dict_tree.t.vo.SysDictTreeTVo;
public class SysDictTreeTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysDictTreeTService service = new SysDictTreeTService();
		String parent_id = request.getParameter("sys_dict_tree.parent");
		String id = request.getParameter("sys_dict_tree.sysDictTreeId");
		SysDictTreeT entity = (SysDictTreeT) RequestThreadLocal.doRequest2Entity(
		SysDictTreeTVo.class, SysDictTreeT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
