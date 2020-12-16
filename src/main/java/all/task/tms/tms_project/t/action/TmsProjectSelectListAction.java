package all.task.tms.tms_project.t.action;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import all.app.common.action.AppAction;
import all.task.tms.tms_project.t.service.TmsProjectTService;
/**
 * 
 * 下拉框
 * @Description:
 * @ClassName: 
 * @date 2019年1月16日 下午7:29:40
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class TmsProjectSelectListAction extends AppAction {
	@Override
	public String run() throws Exception {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		TmsProjectTService service = new TmsProjectTService();
		listMap = service.findAll();
		return this.returnJson(listMap);
	}
}
