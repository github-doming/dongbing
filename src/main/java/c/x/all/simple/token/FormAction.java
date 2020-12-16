package c.x.all.simple.token;
import java.io.IOException;
import java.util.Date;
import javax.servlet.RequestDispatcher;
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
/**
 * http://localhost:8080/a/form.htm
 * 
 * 
 */
public class FormAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public FormAction() {
		super();
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			TokenProcessor.getInstance().createToken(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 相对地址
		String returnPage_relative = "/pages/example/c/x/all/simple/token/form.jsp";
		/**
		 * 调用RequestDispatcher.forward 方法的请求转发过程结束后，
		 * 
		 * 浏览器地址栏保持初始的URL地址不变;
		 */
		// {
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher(returnPage_relative);
		requestDispatcher.forward(request, response);
		// }
		System.out.println(this.getClass().getName() + " end");
	}
}
