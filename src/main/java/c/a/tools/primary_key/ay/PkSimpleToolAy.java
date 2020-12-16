package c.a.tools.primary_key.ay;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import all.gen.sys_pk.t.entity.SysPkT;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.uuid.Uuid;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
public class PkSimpleToolAy {
	private static String machine_key = "cx";
	private static PkSimpleToolAy instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造子
	 */
	private PkSimpleToolAy() {
	}
	public static PkSimpleToolAy findInstance() {
		synchronized (key) {
			if (instance == null) {
				instance = new PkSimpleToolAy();
			}
		}
		return instance;
	}
	public String findPk(String table_name) throws Exception {
		return this.findSysPkInfo(this.machine_key, table_name).getNextId();
	}
	public String findPk(String machine_key, String table_name) throws Exception {
		return this.findSysPkInfo(machine_key, table_name).getNextId();
	}
	public synchronized SysPkT findSysPkInfo(String machine_key, String table_name) throws Exception {
		//String createTimeStr = DateThreadLocal.findThreadLocal().get().findNow2String17();
		Date date=new Date();
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		String sql = "select * FROM SYS_PK where machine_key_=? and table_name_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(machine_key);
		parameterList.add(table_name);
		SysPkT entity =(SysPkT)  jdbcTool.findObject(SysPkT.class, conn, sql, parameterList);
		if (entity == null) {
			entity = new SysPkT();
			entity.setCreateTime(date);
			entity.setCreateTimeLong(date.getTime());
			entity.setStartLong(1l);
			entity.setNextLong(1l);
			entity.setMachineKey(machine_key);
			entity.setTableName(table_name);
			entity.setIp(RequestThreadLocal.findThreadLocal().get().findIPLocal());
			entity.setMac(RequestThreadLocal.findThreadLocal().get().findMAC());
			entity.setNextId(entity.getNextLong().toString());
			String sysPkId = Uuid.create().toString(entity.getNextLong(),String.valueOf(date.getTime()), entity.getMac());
			entity.setSysPkId(sysPkId);
			jdbcTool.insertObjectPkNot(jdbcUtil.getConnection(), entity);
		} else {
			sql = "delete FROM SYS_PK where machine_key_=? and table_name_=?";
			parameterList = new ArrayList<Object>();
			parameterList.add(machine_key);
			parameterList.add(table_name);
			jdbcUtil.execute(conn, sql, parameterList);
			entity.setCreateTime(date);
			entity.setCreateTimeLong(date.getTime());
			entity.setNextLong(entity.getNextLong() + 1);
			entity.setMachineKey(machine_key);
			entity.setTableName(table_name);
			entity.setIp(RequestThreadLocal.findThreadLocal().get().findIPLocal());
			entity.setMac(RequestThreadLocal.findThreadLocal().get().findMAC());
			entity.setNextId(entity.getNextLong().toString());
			String sysPkId = Uuid.create().toString(entity.getNextLong(), String.valueOf(date.getTime()), entity.getMac());
			entity.setSysPkId(sysPkId);
			jdbcTool.insertObjectPkNot(jdbcUtil.getConnection(), entity);
		}
		return entity;
	}
}
