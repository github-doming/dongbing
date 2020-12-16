package c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.action;

import c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.entity.Gen2PssProductgroupInfo;
import c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.service.Gen2PssProductgroupInfoService;
import c.x.platform.admin.feng.pss.gen2.pss_productgroup_info.vo.Gen2PssProductgroupInfoVo;
import c.a.util.core.string.StringUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.x.platform.root.common.action.BaseAction;

public class Gen2PssProductgroupInfoSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String parent_id = request
				.getParameter("pss_productgroup_info.parent_id");
		String id = request.getParameter("pss_productgroup_info.id");

		Gen2PssProductgroupInfo entity = (Gen2PssProductgroupInfo) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(Gen2PssProductgroupInfoVo.class,
						Gen2PssProductgroupInfo.class, request);
		Gen2PssProductgroupInfoService service = new Gen2PssProductgroupInfoService();
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return "index";
	}
}
