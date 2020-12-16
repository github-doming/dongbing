package c.x.platform.root.compo.tab.servlet;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
public class Servlet_dy extends HttpServlet {
	protected Logger log = LogManager.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	public Servlet_dy() {
		super();
	}
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		log.trace("d");
		log.trace("end");
		String returnPage_relative = "/pages/example/c/x/platform/all/feng/compo/tab/tools/tab/d.html";
		RequestDispatcher requestDispatcher = request
				.getRequestDispatcher(returnPage_relative);
		requestDispatcher.forward(request, response);
	}
}
