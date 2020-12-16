package c.x.platform.admin.feng.fas.business_all.family_account_bill_info.action;
import c.a.tools.crud.action.TransactionAction;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.admin.feng.fas.business_all.family_account_bill_info.service.FamilyAccountBillInfoService;
public class FamilyAccountBillInfoAction_del extends TransactionAction {
	public FamilyAccountBillInfoAction_del() {
		transaction = true;
	}
	@Override
	public  JsonTcpBean executeTransaction() throws Exception {
		return null;
	}
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		FamilyAccountBillInfoService s = new FamilyAccountBillInfoService();
		s.del(id);
		return "index";
	}
}
