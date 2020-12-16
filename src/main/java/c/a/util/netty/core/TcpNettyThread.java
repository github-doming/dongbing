package c.a.util.netty.core;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import c.a.config.SysConfig;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonTcpBean;
import c.a.util.core.thread.common.CommThread;
public class TcpNettyThread extends CommThread {
	protected Logger log = LogManager.getLogger("netty");
	private JsonTcpBean _JsonTcpBean = null;
	public JsonTcpBean getJsonTcpBean() {
		return _JsonTcpBean;
	}
	public void setJsonTcpBean(JsonTcpBean _JsonTcpBean) {
		this._JsonTcpBean = _JsonTcpBean;
	}
	public TcpNettyThread() {
		// 不能长久等待JDBC连接
		// 不需要数据库操作
		this.database = false;
		// 不需要事务操作
		transaction = false;
	}
	@Override
	public String execute() throws Exception {
		int port= BeanThreadLocal.find(SysConfig.findInstance().findMap().get("netty.local.start"),2000);
		new TcpNettyServerBoot(port).run();
		log.trace("netty class=" + this.getClass());
		return null;
	}
}
