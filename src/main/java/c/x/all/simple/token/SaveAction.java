package c.x.all.simple.token;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
public class SaveAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public SaveAction() {
		super();
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// for (int i = 0; i < 10; i++) {
		if (TokenProcessor.getInstance().isTokenValid(request)) {
			System.out.println("提交 ");
			TokenProcessor.getInstance().reset(request);
		} else {
			System.out.println("不要重复提交 ");
		}
		// }
		System.out.println(this.getClass().getName() + " end");
	}
}
