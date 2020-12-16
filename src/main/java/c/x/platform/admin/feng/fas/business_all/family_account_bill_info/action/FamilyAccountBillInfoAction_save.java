package c.x.platform.admin.feng.fas.business_all.family_account_bill_info.action;
import c.a.tools.crud.action.TransactionAction;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.admin.feng.fas.business_all.family_account_bill_info.service.FamilyAccountBillInfoService;
public class FamilyAccountBillInfoAction_save extends TransactionAction {
	public FamilyAccountBillInfoAction_save() {
		transaction = true;
	}
	@Override
	public  JsonTcpBean executeTransaction() throws Exception {
		return null;
	}
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("sys_type_info.id");
		log.trace("id=" + request.getParameter("sys_type_info.id"));
		if (id != null && !id.trim().equals("")) {
			FamilyAccountBillInfoService s = new FamilyAccountBillInfoService();
			s.update(request);
		} else {
			FamilyAccountBillInfoService s = new FamilyAccountBillInfoService();
			s.insert(request);
		}
		return "index";
	}
}
