package c.a.tools.primary_key.cy;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import all.gen.sys_pk.t.entity.SysPkT;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.uuid.UuidAy;
import c.a.tools.jdbc.IJdbcTool;
import c.a.tools.jdbc.threadlocal.JdbcThreadLocal;
public class PkToolCy {
	private static PkToolCy instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造子
	 */
	private PkToolCy() {
	}
	public static PkToolCy findInstance() {
		synchronized (key) {
			if (instance == null) {
				instance = new PkToolCy();
			}
		}
		return instance;
	}
//	public String findPk(String machineKey, String tableName) throws Exception {
//
//		return Uuid.create().toString();
//	}
	public String findPk(String machineKey, String tableName) throws Exception {
		return this.findSysPkInfo(machineKey, tableName).getNextId();
	}
	public synchronized SysPkT findSysPkInfo(String machineKey, String tableName) throws Exception {
		//String createTimeStr = DateThreadLocal.findThreadLocal().get().findNow2String17();
		Date date=new Date();
		IJdbcTool jdbcTool = JdbcThreadLocal.findJdbcToolThreadLocal().get();
		IJdbcUtil jdbcUtil = jdbcTool.getJdbcUtil();
		Connection conn = jdbcUtil.getConnection();
		String sql = "SELECT * FROM SYS_PK WHERE machine_key_=? AND table_name_=?";
		List<Object> parameterList = new ArrayList<Object>();
		parameterList.add(machineKey);
		parameterList.add(tableName);
		SysPkT entity =(SysPkT)  jdbcTool.findObject(SysPkT.class, conn, sql, parameterList);
		if (entity == null) {
			entity = new SysPkT();
			entity.setCreateTime(date);
			entity.setCreateTimeLong(date.getTime());
			entity.setStartLong(1l);
			entity.setNextLong(1l);
			entity.setMachineKey(machineKey);
			entity.setTableName(tableName);
			entity.setIp(RequestThreadLocal.findThreadLocal().get().findIPLocal());
			entity.setMac(RequestThreadLocal.findThreadLocal().get().findMAC());
			String sysPkId = UuidAy.create().toString(entity.getNextLong(), String.valueOf(date.getTime()), entity.getMac());
			entity.setSysPkId(sysPkId);
			String id = entity.getNextLong().toString();
			entity.setNextId(id);
			jdbcTool.insertObjectPkNot(jdbcUtil.getConnection(), entity);
		} else {
			sql = "DELETE FROM SYS_PK WHERE machine_key_=? AND table_name_=?";
			parameterList = new ArrayList<Object>();
			parameterList.add(machineKey);
			parameterList.add(tableName);
			jdbcUtil.execute(conn, sql, parameterList);
			entity.setCreateTime(date);
			entity.setCreateTimeLong(date.getTime());
			entity.setNextLong(entity.getNextLong() + 1);
			entity.setMachineKey(machineKey);
			entity.setTableName(tableName);
			entity.setIp(RequestThreadLocal.findThreadLocal().get().findIPLocal());
			entity.setMac(RequestThreadLocal.findThreadLocal().get().findMAC());
			String sysPkId = UuidAy.create().toString(entity.getNextLong(), String.valueOf(date.getTime()), entity.getMac());
			entity.setSysPkId(sysPkId);
			String id = entity.getNextLong().toString();
			entity.setNextId(id);
		
			jdbcTool.insertObjectPkNot(jdbcUtil.getConnection(), entity);
		}
		return entity;
	}
}
