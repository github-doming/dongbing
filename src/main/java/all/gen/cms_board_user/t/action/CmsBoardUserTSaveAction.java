package all.gen.cms_board_user.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_board_user.t.entity.CmsBoardUserT;
import all.gen.cms_board_user.t.service.CmsBoardUserTService;
import all.gen.cms_board_user.t.vo.CmsBoardUserTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoardUserTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsBoardUserTService service = new CmsBoardUserTService();
		CmsBoardUserT entity = null;
		String id = request.getParameter("cms_board_user.cmsBoardUserId");
		entity = (CmsBoardUserT) RequestThreadLocal.doRequest2Entity(CmsBoardUserTVo.class,CmsBoardUserT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
