package c.x.platform.admin.feng.fas.business.family_account_bill_info.action;
import c.x.platform.admin.feng.fas.business.family_account_bill_info.entity.FamilyAccountBillInfo;
import c.x.platform.admin.feng.fas.business.family_account_bill_info.service.FamilyAccountBillInfoService;
import c.x.platform.admin.feng.fas.business.family_account_bill_info.vo.FamilyAccountBillInfoVo;
import c.x.platform.root.common.action.BaseAction;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
public class FamilyAccountBillInfoSaveAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("family_account_bill_info.id");

		FamilyAccountBillInfo s = (FamilyAccountBillInfo) RequestThreadLocal.findThreadLocal().get()
				.doRequest2Entity(FamilyAccountBillInfoVo.class,
						FamilyAccountBillInfo.class, request);
		FamilyAccountBillInfoService service = new FamilyAccountBillInfoService();
		if (StringUtil.isBlank(id)) {
			service.insert(s);
		} else {
			service.update(s);
		}
		return "index";
	}
}
