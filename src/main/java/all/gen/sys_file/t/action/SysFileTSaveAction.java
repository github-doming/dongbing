package all.gen.sys_file.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.sys_file.t.entity.SysFileT;
import all.gen.sys_file.t.service.SysFileTService;
import all.gen.sys_file.t.vo.SysFileTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class SysFileTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		SysFileTService service = new SysFileTService();
		SysFileT entity = null;
		String id = request.getParameter("sys_file.sysFileId");
		entity = (SysFileT) RequestThreadLocal.doRequest2Entity(SysFileTVo.class,SysFileT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
