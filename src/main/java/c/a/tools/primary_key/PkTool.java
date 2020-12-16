package c.a.tools.primary_key;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
import c.a.tools.jdbc.transaction.TransactionBase;
import c.a.util.core.annotation.AnnotationTable;
import c.a.util.core.asserts.AssertUtil;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.uuid.Uuid;
public class PkTool extends TransactionBase {
	protected Logger log = LogManager.getLogger(PkTool.class);
	private static PkTool instance = null;
	private final static Object key = new Object();
	// 初始值
	// private Long startLongInit = 1000l;
	private Long startLongInit = 1l;
	/**
	 * 私有的默认构造子
	 */
	private PkTool() {
	}
	public static PkTool findInstance() {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new PkTool();
				}
			}
		}
		return instance;
	}
	public Long findPk(Class classInput) throws Exception {
		// 得到表名
		AnnotationTable table = (AnnotationTable) classInput.getAnnotation(AnnotationTable.class);
		String tableName = table.name();
		return this.findPk("", tableName);
	}
	public Long findPk(String tableName) throws Exception {
		return this.findPk("", tableName);
	}
	//public synchronized Long findPk(String machineKey, String tableName) throws Exception {
	public  Long findPk(String machineKey, String tableName) throws Exception {
		AssertUtil.isBlank(tableName, " 找不到表名");
		// 返回的值
		Long nextId = null;
		Long nextLong = null;
		Long startLong = null;
		Integer step = null;
		Date date = new Date();
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		String sql = "SELECT * FROM SYS_PK WHERE MACHINE_KEY_=? AND TABLE_NAME_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(machineKey);
		parameterList.add(tableName);
		Map<String, Object> resultMap = jdbcUtil.findMap(conn, sql, parameterList);
		// 如果列表没有记录，表示该表未创建【表】主键，则新增【表】的一行记录
		if (resultMap != null) {
			// 得到表的id
			String sysPkId = (String) resultMap.get("SYS_PK_ID_");
			// 得到
			nextLong = (Long) resultMap.get("NEXT_LONG_");
			// 得到
			step = (Integer) resultMap.get("STEP_");
			if (step == null || step == 0) {
				step = 1;
			}
			// 加1
			nextLong = nextLong + step;
			nextId = nextLong;
			// 赋值
			// 更新
			parameterList = new ArrayList<Object>();
			sql = "UPDATE SYS_PK  set " + " TABLE_NAME_=?,STEP_=?,NEXT_ID_=?,NEXT_LONG_=?,START_LONG_=?,"
					+ " CREATE_TIME_=?, CREATE_TIME_LONG_=?,MACHINE_KEY_=? ,IP_=?,MAC_=? " + " where SYS_PK_ID_=?";
			parameterList.add(tableName);
			parameterList.add(step);
			parameterList.add(nextId);
			parameterList.add(nextLong);
			parameterList.add(startLong);
			parameterList.add(date);
			parameterList.add(date.getTime());
			parameterList.add(machineKey);
			parameterList.add(RequestThreadLocal.findThreadLocal().get().findIPLocal());
			parameterList.add(RequestThreadLocal.findThreadLocal().get().findMAC());
			parameterList.add(sysPkId);
			jdbcUtil.execute(conn, sql, parameterList);
		} else {
			// 设置值为1
			nextLong = startLongInit;
			startLong = startLongInit;
			step = 1;
			nextId = nextLong;
			// 赋值
			// 新增
			parameterList = new ArrayList<Object>();
			sql = "INSERT INTO SYS_PK (SYS_PK_ID_,STEP_,TABLE_NAME_,NEXT_ID_,NEXT_LONG_,START_LONG_,"
					+ " CREATE_TIME_, CREATE_TIME_LONG_,MACHINE_KEY_ ,IP_,MAC_) " + "values(?,?,?,?,?,?,?,?,?,?,?)";
			parameterList.add(Uuid.findInstance().toString());
			parameterList.add(step);
			parameterList.add(tableName);
			parameterList.add(nextId);
			parameterList.add(nextLong);
			parameterList.add(startLong);
			parameterList.add(date);
			parameterList.add(date.getTime());
			parameterList.add(machineKey);
			parameterList.add(RequestThreadLocal.findThreadLocal().get().findIPLocal());
			parameterList.add(RequestThreadLocal.findThreadLocal().get().findMAC());
			jdbcUtil.execute(conn, sql, parameterList);
		}
		/**
		 * 重要,需要提交事务
		 */
		this.doTransactionPost();
		StringBuilder sb = new StringBuilder();
		sb.append("表名【" + tableName + "】");
		sb.append("主键业务ID=【" + nextId + "】");
		log.trace(sb.toString());
		return nextId;
	}
}
