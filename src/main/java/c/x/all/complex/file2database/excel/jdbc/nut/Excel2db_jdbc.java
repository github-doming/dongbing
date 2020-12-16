package c.x.all.complex.file2database.excel.jdbc.nut;

import c.a.util.core.date.DateThreadLocal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import c.x.all.simple.jdbc.single_tools.nut.JdbcTools_simple_ax;
import c.a.util.core.date.DateThreadLocal;

/**
 * 读取excel文件并写入数据库
 * 
 * 
 * 
 */
public class Excel2db_jdbc extends JdbcTools_simple_ax {
	protected org.apache.logging.log4j.Logger log_custom = org.apache.logging.log4j.LogManager.getLogger("log_custom");
	protected static boolean isPrint = true;
	private static String field_delimiter = "\\|\\@\\|\\%\\|";//

	/**
	 * 
	 * 批处理增加;
	 * 
	 * 没有回调;
	 * 
	 * 
	 * 有事务;
	 * 
	 * @param connection
	 * @throws Exception
	 */
	public Object doJdbcTransactionBatch(String filePathInput) throws Exception {
		conn = this.findConnection();
		if (conn == null) {
			// return;
			throw new NullPointerException("Connection is  null");
		}
		try {
			// 设置事务属性
			this.doJdbcTransactionStart(conn);
			// 业务开始
			Object cObject = null;
			this.doJdbcServiceBatch(filePathInput);
			// 业务结束
			// 提交，设置事务初始值
			this.doJdbcTransactionCommit(conn);
			// 成功，返回
			return cObject;
		} catch (Exception e) {
			this.doJdbcTransactionRollback(conn);
			String str = "jdbc error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} finally {
			this.doCloseConnectionJdbc(conn);
		}
	}

	/**
	 * 分层
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void doJdbcServiceBatch(String filePathInput) throws Exception {
		this.doBatchDaoJdbc(filePathInput);
	}

	/**
	 * 分层;
	 * 
	 * 查询;
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public void doBatchDaoJdbc(String filePathInput) throws Exception {
		this.doBuildJdbcByMapList(filePathInput);
	}

	/**
	 * 
	 * 这部分需要自己写代码
	 * 
	 * 解析文件
	 * 
	 * @param filePath
	 * @param jdbcTemplate
	 * @throws Exception
	 * @throws SQLException
	 */
	public void doBuildJdbcByMapList(String filePath) throws Exception {
		Workbook wwb = null;
		// String sql = "insert into fun_type_str_t(id,name) values (?,?)";
		String sql = "insert into fun_type_str_t(name) values (?)";
		if (filePath == null) {
			throw new NullPointerException("路径不能为空");
		}
		List<Map<String, Object>> listMap = null;
		BufferedReader bufferedReader = null;
		try {
			/**
			 * excel
			 */
			wwb = Workbook.getWorkbook(new File(filePath));
			Sheet[] sheets = wwb.getSheets();
			Sheet sheet = sheets[0];
			// 行
			int rows = sheet.getRows();
			// 列
			int columns = sheet.getColumns();
			/**
			 * excel
			 */
			// 计数
			int int_count = 1;
			listMap = new ArrayList<Map<String, Object>>();
			for (int i = 0 + 2; i < rows; i++) {
				HashMap<String, Object> list = new HashMap<String, Object>();
				if (true) {
					/**
					 * 
					 * 这部分需要自己写代码
					 */
					list.put("id", sheet.getCell(1, i).getContents());
					list.put("name", sheet.getCell(2, i).getContents());
				}
				listMap.add(list);
				if (int_count % batch_size == 0) {
					this.doBatchJdbcByMapList(sql, listMap);
					listMap = new ArrayList<Map<String, Object>>();
				}
				int_count = int_count + 1;
			}
			// 最后一批放进数据库
			this.doBatchJdbcByMapList(sql, listMap);
		} catch (FileNotFoundException e) {
			String str = "jdbc error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			String str = "error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			String str = "error";
			if (isPrint) {
				System.err.println(str);
			}
			log_custom.error(str, e);
			e.printStackTrace();
			throw e;
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
			if (wwb != null) {
				wwb.close();
			}
		}
	}

	/**
	 * 
	 * 这部分需要自己写代码
	 * 
	 * 批量增加
	 * 
	 * @param listMap
	 * @param connection
	 * @return
	 * @throws SQLException
	 * @throws SQLException
	 */
	public int doBatchJdbcByMapList(String sql,
			List<Map<String, Object>> listMap) throws SQLException {
		

		conn = this.findConnection();
		if (conn == null) {
			// return;
			throw new NullPointerException("Connection is  null");
		}
		if (listMap == null) {
			return -1;
		}
		if (listMap.size() < 1) {
			return -1;
		}
		if (conn == null) {
			// return -1;
			throw new NullPointerException("Connection is  null");
		}
		PreparedStatement preparedStatement = null;
		int countUpdateInt = 0;
		preparedStatement = conn.prepareStatement(sql,
				ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		for (Map<String, Object> map : listMap) {
			if (true) {
				/**
				 * 
				 * 这部分需要自己写代码
				 */
				if (true) {
					preparedStatement.setObject(1, map.get("name"));
				}
				if (false) {
					try {
						java.sql.Timestamp ts = (java.sql.Timestamp) map
								.get("create_time2");
						preparedStatement.setObject(1,
								DateThreadLocal.findThreadLocal().get().doString2Date(ts.toLocaleString())
										.getTime());
					} catch (Exception e) {
						e.printStackTrace();
					}
					preparedStatement.setObject(2, map.get("id"));
				}
			}
			preparedStatement.addBatch();
		}
		int[] countUpdateIntArray = preparedStatement.executeBatch();
		countUpdateInt = countUpdateIntArray.length;
		if (isPrint) {
			String str = "批理处理行数insert : " + countUpdateInt;
			System.out.println(str);
			log_custom.trace(str);
		}
		return countUpdateInt;
	}
}
