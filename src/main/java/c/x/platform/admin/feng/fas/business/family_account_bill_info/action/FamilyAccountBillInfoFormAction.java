package c.x.platform.admin.feng.fas.business.family_account_bill_info.action;
import c.a.util.core.date.DateThreadLocal;

import c.x.platform.admin.feng.fas.business.family_account_bill_info.entity.FamilyAccountBillInfo;
import c.x.platform.admin.feng.fas.business.family_account_bill_info.service.FamilyAccountBillInfoService;
import c.x.platform.admin.feng.fas.business.family_account_bill_info.vo.FamilyAccountBillInfoVo;

import c.x.platform.root.common.action.BaseAction;
public class FamilyAccountBillInfoFormAction extends BaseAction {
	@Override
	public String execute() throws Exception {
		
		String id = (String) request.getParameter("id");
		if (id != null) {
			FamilyAccountBillInfoService service = new FamilyAccountBillInfoService();
			FamilyAccountBillInfo s = service.find(id);
			request.setAttribute("id", id);
			request.setAttribute("s", s);
			System.out
					.println("edit user_create_time=" + s.getCreate_time_dt());
		} else {
			FamilyAccountBillInfoVo s = new FamilyAccountBillInfoVo();
			s.setCreate_time_dt(DateThreadLocal.findThreadLocal().get().findNow2String());
			request.setAttribute("s", s);
			log.trace("new user_create_time=" + s.getCreate_time_dt());
		}
		return "index";
	}
}
