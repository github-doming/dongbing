package all.sys_admin.sys.dict.sys_dict.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import  c.x.platform.root.common.action.BaseAction;
import c.a.util.core.enums.bean.CommViewEnum;
public class SysDictTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("sys_dict.sysDictId");
		all.sys_admin.sys.dict.sys_dict.entity.SysDictT entity = (all.sys_admin.sys.dict.sys_dict.entity.SysDictT) RequestThreadLocal.doRequest2Entity(
				all.sys_admin.sys.dict.sys_dict.vo.SysDictTVo.class, all.sys_admin.sys.dict.sys_dict.entity.SysDictT.class, request);
		all.sys_admin.sys.dict.sys_dict.service.SysDictTService service = new all.sys_admin.sys.dict.sys_dict.service.SysDictTService();
		// 第3表
		//{
		all.sys_admin.sys.dict.sys_dict_ref.service.SysDictRefTService tSysDictRefTService = new all.sys_admin.sys.dict.sys_dict_ref.service.SysDictRefTService();
		all.sys_admin.sys.dict.sys_dict_ref.entity.SysDictRefT tSysDictRefT = new all.sys_admin.sys.dict.sys_dict_ref.entity.SysDictRefT();
		//树的老的ID
		String name_first$tree$id = request
				.getParameter("name_first$tree$id");
		//树的新的ID
		String sys_dict_tree$parent = request
				.getParameter("sys_dict_tree.parent");
		request.setAttribute("first$tree$id", sys_dict_tree$parent  );
		//}
		// 第3表
		if (StringUtil.isBlank(id)) {
			id = service.save(entity);
			// 第3表保存
			tSysDictRefT.setSysDictTreeId(sys_dict_tree$parent);
			tSysDictRefT.setSysDictId(id);
			tSysDictRefTService.save(tSysDictRefT);
		} else {
			service.update(entity);
			// 第3表删除
			tSysDictRefTService.del(name_first$tree$id, id);
			// 第3表保存
			tSysDictRefT.setSysDictTreeId( sys_dict_tree$parent);
			tSysDictRefT.setSysDictId(id);
			tSysDictRefTService.save(tSysDictRefT);
		}
			return CommViewEnum.Default.toString();
	}
}
