package c.x.all.simple.javascript_dynamic_insert;

import c.a.tools.crud.action.TransactionAction;
import c.a.util.core.json.JsonTcpBean;

/**
 * 
 * 
 * 
 * url=http://localhost:8080/a/pages/example/c/x/all/simple/
 * javascript_dynamic_insert/form.jsp
 * 
 * 
 * 
 * 
 */
public class JavascriptDynamicInsertAction extends TransactionAction {
	@Override
	public  JsonTcpBean executeTransaction() throws Exception {
		return null;
	}
	@Override
	public String execute() throws Exception {
		String[] strArray_txtName = request.getParameterValues("txtName_1");
		if (strArray_txtName != null) {
			for (String str_txtName : strArray_txtName) {
				System.out.println("txtName=" + str_txtName);
			}
			System.out.println("valueAll="
					+ request.getParameter("name_input_valueAll"));
			String valueAll = request.getParameter("name_input_valueAll");
			String[] strArray_valueAll = valueAll.split("_dwb_");
			System.out.println("个数length=" + strArray_valueAll.length);
			for (String str_value : strArray_valueAll) {
				System.out.println("value=" + str_value);
			}
			request.setAttribute("value_all", valueAll);
		}
		System.out.println("end");
		return "index";
	}
}
