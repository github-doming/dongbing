package c.x.platform.admin.feng.fas.business_all.family_account_bill_info.action;

import c.a.tools.crud.action.TransactionAction;
import c.a.util.core.jdbc.bean.data_row.JdbcRowDto;
import c.a.util.core.json.JsonTcpBean;
import c.x.platform.admin.feng.fas.business_all.family_account_bill_info.service.FamilyAccountBillInfoService;

public class FamilyAccountBillInfoAction_form extends TransactionAction {
	public FamilyAccountBillInfoAction_form() {
		transaction = true;
	}

	@Override
	public  JsonTcpBean executeTransaction() throws Exception {
		return null;
	}
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		if (id != null) {
			FamilyAccountBillInfoService s = new FamilyAccountBillInfoService();
			JdbcRowDto cJdbcRow = s.query(id);
			request.setAttribute("row", cJdbcRow);
			request.setAttribute("id", cJdbcRow.getMap().get("id"));
		}
		return "index";
	}
}
