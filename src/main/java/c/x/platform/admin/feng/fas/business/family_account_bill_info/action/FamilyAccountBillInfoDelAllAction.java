package c.x.platform.admin.feng.fas.business.family_account_bill_info.action;
import c.x.platform.admin.feng.fas.business.family_account_bill_info.service.FamilyAccountBillInfoService;
import c.x.platform.root.common.action.BaseAction;
public class FamilyAccountBillInfoDelAllAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		String[] ids = request.getParameterValues("name_checkbox_ids");
		FamilyAccountBillInfoService service = new FamilyAccountBillInfoService();
		service.delAll(ids);
		return "index";
	}
}
