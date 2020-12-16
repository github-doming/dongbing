package all.gen.cms_board.t.action;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import all.gen.cms_board.t.entity.CmsBoardT;
import all.gen.cms_board.t.service.CmsBoardTService;
import all.gen.cms_board.t.vo.CmsBoardTVo;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class CmsBoardTSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		CmsBoardTService service = new CmsBoardTService();
		CmsBoardT entity = null;
		String id = request.getParameter("cms_board.cmsBoardId");
		entity = (CmsBoardT) RequestThreadLocal.doRequest2Entity(CmsBoardTVo.class,CmsBoardT.class, request);
		if (StringUtil.isBlank(id)) {
			service.save(entity);
		} else {
			service.update(entity);
		}
		return CommViewEnum.Default.toString();
	}
}
