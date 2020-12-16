package all.gen.cms_board_user.t.action;

import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_board_user.t.entity.CmsBoardUserT;
import all.gen.cms_board_user.t.service.CmsBoardUserTService;
import all.gen.cms_board_user.t.vo.CmsBoardUserTVo;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoardUserTSaveJsonAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsBoardUserTService service = new CmsBoardUserTService();
		CmsBoardUserT entity = null;
		String id = request.getParameter("id");
		entity = (CmsBoardUserT) RequestThreadLocal.doRequest2EntityByJson(CmsBoardUserTVo.class, CmsBoardUserT.class, request);
		if (StringUtil.isBlank(id)) {
				service.save(entity);
		} else {
				service.update(entity);
		}
		return this.returnJson(true, "保存成功");
		
	}
}
