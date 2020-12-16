package all.sys.ay.sys_log_request.action;
import java.sql.Connection;
import java.util.Properties;

import all.gen.sys_log_request.t.entity.SysLogRequestT;
import all.gen.sys_log_request.t.service.SysLogRequestTService;
import c.a.config.SysConfig;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.tools.primary_key.PkSimpleTool;
import c.a.util.core.enums.ReturnCodeEnum;
import c.a.util.core.enums.bean.TaskStateEnum;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.random.RandomUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.x.platform.root.common.action.CommAction;
public class SysLogRequestTSaveAction extends CommAction {
//public class SysLogRequestTSaveAction extends TransactionAction {
	@Override
	public String execute() throws Exception {
		JsonTcpBean jrb = new JsonTcpBean();
		SysLogRequestTService service = new SysLogRequestTService();
		String ip = RequestThreadLocal.getThreadLocal().get().findIP(request);
		String mac = RequestThreadLocal.getThreadLocal().get().findMAC();
		String servletPath = request.getServletPath();
		// PkTool pkTool = PkTool.findInstance();
		// Long idx = pkTool.findPk("sys_request");
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		SysLogRequestT entity = new SysLogRequestT();
		entity.setName(RandomUtil.findRandomInteger(1000000).toString());
		entity.setThread(Thread.currentThread().getName());
		entity.setIp(ip);
		entity.setMac(mac);
		entity.setRequestUrl(request.getRequestURL().toString());		
		// entity.setNumber(idx);
		entity.setNumber(PkSimpleTool.findInstance().findPk());
		entity.setConnectionId(conn.toString());
		// 系统属性
		Properties properties = System.getProperties();
		String userDir= properties.getProperty("user.dir");
		entity.setTomcatBin(userDir);
		String servletContextPath =request.getServletContext().getRealPath("/");
		entity.setServletContextPath(servletContextPath);
		entity.setState(TaskStateEnum.OPEN.getCode());
		service.save(entity);
		//return CommViewEnum.Default.toString();
		jrb.setCodeSys(ReturnCodeEnum.code200.toString());
		jrb.setMsgSys(ReturnCodeEnum.code200.getMsgCn());
		jrb.setSuccess(true);
		return this.returnJson(jrb);
	}
	public  JsonTcpBean executeTransaction() throws Exception {
		return this.returnJsonTcpBean(SysConfig.configValueTrue);
	}
}
