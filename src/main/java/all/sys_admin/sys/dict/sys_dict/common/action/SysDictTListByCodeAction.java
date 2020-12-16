package all.sys_admin.sys.dict.sys_dict.common.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.util.StringUtil;
import all.app.common.action.AppAction;
import all.sys_admin.sys.dict.sys_dict.service.SysDictTService;
/**
 * 
 * http://localhost:8080/a/all/sys_admin/sys/dict/sys_dict/list_by_code.do?code=sex
 * @Description: 
 * @ClassName: SysDictTListByCodeAction 
 * @date 2019年1月16日 下午7:29:40 
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class SysDictTListByCodeAction extends AppAction {
	@Override
	public String run() throws Exception {
		String code = (String) request.getParameter("code");
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		if(StringUtil.isNotBlank(code)){
			SysDictTService service = new SysDictTService();
			listMap = service.findListByCode(code);
		}
		return this.returnJson(listMap);
	}
}
