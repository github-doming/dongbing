package all.gen.test_concurrent.t.action;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import all.gen.test_concurrent.t.service.TestConcurrentTService;
import c.a.util.core.enums.bean.CommViewEnum;
import c.x.platform.root.common.action.BaseAction;
public class TestConcurrentTDelAction extends BaseAction {
	protected Logger log = LogManager.getLogger(this.getClass());
	@Override
	public String execute() throws Exception {
		String id = request.getParameter("id");
		TestConcurrentTService service = new TestConcurrentTService();
		service.del(id);
		List<String> msgList = new ArrayList<String>();
		msgList.add("信息");
		msgList.add("删除成功");
		request.setAttribute("msg", msgList);
		return CommViewEnum.Default.toString();
	}
}
