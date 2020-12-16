package c.a.tools.jdbc;

import c.a.config.SysConfig;
import c.a.tools.primary_key.PkSimpleTool;
import c.a.tools.primary_key.PkTool;
import c.a.util.core.annotation.AnnotationTable;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.file.FileThreadLocal;
import c.a.util.core.file.FileUtil;
import c.a.util.core.jdbc.bean.nut.ColumnBean;
import c.a.util.core.jdbc.bean.nut.JdbcPrepareStatementDto;
import c.a.util.core.jdbc.bean.nut.PageCoreBean;
import c.a.util.core.jdbc.nut.IJdbcUtil;
import c.a.util.core.reflect.ReflectThreadLocal;
import c.a.util.core.reflect.ReflectUtil;
import c.a.util.core.request.RequestThreadLocal;
import c.a.util.core.string.StringUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class JdbcCoreTool implements IJdbcTool {
	protected Logger log = LogManager.getLogger(this.getClass());

	/**
	 * 1
	 *
	 * @Title: getJdbcUtil @Description: @return 参数说明 @return IJdbcUtil
	 * 返回类型 @throws
	 */
	@Override
	public abstract IJdbcUtil getJdbcUtil();

	/**
	 * 2分页(PageBean)返回PageBean;
	 *
	 * @Description: desc @Title: findPageBean @param classObj @param conn @param
	 * page @param sql @param parameterList @param sqlCount @param
	 * parameterListCount @param sqlPage @param
	 * parameterListPage @return return @throws Exception
	 * 参数说明 @return PageCoreBean<Map<String,Object>> 返回类型 @throws
	 */
	@Override
	public abstract PageCoreBean<Map<String, Object>> findPageBean(Class classObj, Connection conn,
																   PageCoreBean<Map<String, Object>> page, String sql, List<Object> parameterList, String sqlCount,
																   List<Object> parameterListCount, String sqlPage, List<Object> parameterListPage) throws Exception;

	/**
	 * 3分页(PageBean)返回List;
	 *
	 * @Description: desc @Title: findObjectList @param classObj @param conn @param
	 * page @param sql @param parameterList @param sqlCount @param
	 * parameterListCount @param sqlPage @param
	 * parameterListPage @return return @throws Exception
	 * 参数说明 @return List 返回类型 @throws
	 */
	@Override
	public abstract List findObjectList(Class classObj, Connection conn, PageCoreBean<Map<String, Object>> page,
										String sql, List<Object> parameterList, String sqlCount, List<Object> parameterListCount, String sqlPage,
										List<Object> parameterListPage) throws Exception;

	/**
	 * 4分页(long pageNo, long pageSize)返回PageBean;
	 *
	 * @Description: desc @Title: findPageBean @param classObj @param conn @param
	 * pageNo @param pageSize @param sql @param
	 * parameterList @param sqlCount @param
	 * parameterListCount @param sqlPage @param
	 * parameterListPage @return return @throws Exception
	 * 参数说明 @return PageCoreBean<Map<String,Object>> 返回类型 @throws
	 */
	@Override
	public abstract PageCoreBean<Map<String, Object>> findPageBean(Class classObj, Connection conn, int pageNo,
																   int pageSize, String sql, List<Object> parameterList, String sqlCount, List<Object> parameterListCount,
																   String sqlPage, List<Object> parameterListPage) throws Exception;

	/**
	 * 5分页(long pageNo, long pageSize)返回List;
	 *
	 * @Description: desc @Title: findObjectList @param classObj @param conn @param
	 * pageNo @param pageSize @param sql @param
	 * parameterList @param sqlCount @param
	 * parameterListCount @param sqlPage @param
	 * parameterListPage @return return @throws Exception
	 * 参数说明 @return List 返回类型 @throws
	 */
	@Override
	public abstract List findObjectList(Class classObj, Connection conn, int pageNo, int pageSize, String sql,
										List<Object> parameterList, String sqlCount, List<Object> parameterListCount, String sqlPage,
										List<Object> parameterListPage) throws Exception;

	/**
	 * 6 sql返回PageBean;
	 *
	 * @Description: desc @Title: findPageBean @param classObj @param conn @param
	 * sql @param parameterList @param sqlCount @param
	 * parameterListCount @param sqlPage @param
	 * parameterListPage @return return @throws Exception
	 * 参数说明 @return PageCoreBean<Map<String,Object>> 返回类型 @throws
	 */
	@Override
	public abstract PageCoreBean<Map<String, Object>> findPageBean(Class classObj, Connection conn, String sql,
																   List<Object> parameterList, String sqlCount, List<Object> parameterListCount, String sqlPage,
																   List<Object> parameterListPage) throws Exception;

	/**
	 * 7通过sql将查询数据库结果转化为List<Object>
	 *
	 * @Description: desc @Title: findObjectList @param classObj @param conn @param
	 * sql @param parameterList @return return @throws
	 * Exception 参数说明 @return List 返回类型 @throws
	 */
	@Override
	public <T> List<T> findObjectList(Class<T> classObj, Connection conn, String sql, List<Object> parameterList)
			throws Exception {
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		IJdbcUtil jdbcUtil = this.getJdbcUtil();
		try {
			JdbcPrepareStatementDto bean = jdbcUtil.findResultSet(conn, sql, parameterList);
			resultSet = bean.getResultSet();
			preparedStatement = bean.getPreparedStatement();
			return doResultSet2ListObject(classObj, resultSet);
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		}
	}

	/**
	 * 8获取结果
	 *
	 * @param classObj
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> T findObject(Class<T> classObj, Connection conn, String sql, List<Object> parameterList) throws Exception {
		List<T> list = this.findObjectList(classObj, conn, sql, parameterList);
		if (list == null) {
			return null;
		}
		int size = list.size();
		if (size == 0) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * 9通过id查找实体
	 *
	 * @param conn     数据库连接
	 * @param classObj 实体Clazz
	 * @return 实体
	 */
	@Override
	public Object findObject(Connection conn, Class classObj, String id) throws Exception {
		// 得到表名
		AnnotationTable t = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
		String tableName = t.name();
		// 得到主键的域值
		Field[] fieldArray = classObj.getDeclaredFields();
		String fieldId = null;
		for (int i = 0; i < fieldArray.length; i++) {
			boolean otherFlag = fieldArray[i].isAnnotationPresent(Id.class);
			if (otherFlag) {
				fieldId = fieldArray[i].getName();
				break;
			}
		}
		// 通过id查找实体
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(tableName);
		sb.append(" where ");
		sb.append(fieldId);
		sb.append("=?");
		String sql = sb.toString();
		return findObject(classObj, conn, sql, parameterList);
	}

	/**
	 * 10获取唯一结果
	 *
	 * @param classObj
	 * @param conn
	 * @param sql
	 * @param parameterList
	 * @return
	 * @throws Exception
	 */
	@Override
	public <T> T findObjectUnique(Class<T> classObj, Connection conn, String sql, List<Object> parameterList)
			throws Exception {
		List<T> list = this.findObjectList(classObj, conn, sql, parameterList);
		if (list == null) {
			return null;
		}
		int size = list.size();
		if (size == 0) {
			return null;
		}
		if (size > 1) {
			throw new RuntimeException("取值不唯一");
		}
		if (size == 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 11通过id查找唯一实体
	 *
	 * @param conn     jdbc连接
	 * @param classObj 实体类对象
	 * @return 实体
	 */
	@Override
	public <T> T findObjectUnique(Connection conn, Class<T> classObj, String id) throws Exception {
		// 得到表名
		AnnotationTable table = classObj.getAnnotation(AnnotationTable.class);
		String tableName = table.name();
		// 得到主键的域值
		Field[] fields = classObj.getDeclaredFields();
		String pk_key = null;
		for (int i = 0; i < fields.length; i++) {
			boolean otherFlag = fields[i].isAnnotationPresent(Id.class);
			if (otherFlag) {
				pk_key = fields[i].getAnnotation(Column.class).name();
				break;
			}
		}
		// 通过id查找实体
		ArrayList<Object> parameterList = new ArrayList<Object>();
		parameterList.add(id);
		StringBuilder sb = new StringBuilder();
		sb.append("select * from ");
		sb.append(tableName);
		sb.append(" where ");
		sb.append(pk_key);
		sb.append("=?");
		String sql = sb.toString();
		return findObjectUnique(classObj, conn, sql, parameterList);
	}

	/**
	 * 12需要简单主键;
	 * <p>
	 * 支持大对象;
	 * <p>
	 * 对象转为sql(insert);
	 *
	 * @param conn
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public String insertObjectSimplePK(Connection conn, Object object) throws Exception {
		// 得到表名
		AnnotationTable table = (AnnotationTable) object.getClass().getAnnotation(AnnotationTable.class);
		String tableName = table.name();
		// 主键
		String pk = PkSimpleTool.findInstance().findPk(tableName);
		Long idx = PkTool.findInstance().findPk(RequestThreadLocal.findThreadLocal().get().findMAC(), tableName);
		// 保存
		return this.insertObject(conn, tableName, object, pk, idx);
	}

	/**
	 * 13 不需要主键;
	 * <p>
	 * 支持大对象;
	 * <p>
	 * 对象转为sql(insert);
	 *
	 * @Description: desc @Title: insertObjectNotPK @param conn @param
	 * object @return return @throws Exception 参数说明 @return
	 * String 返回类型 @throws
	 */
	@Override
	public String insertObjectPkNot(Connection conn, Object object) throws Exception {
		// 得到表名
		AnnotationTable table = (AnnotationTable) object.getClass().getAnnotation(AnnotationTable.class);
		String tableName = table.name();
		// 保存
		return this.insertObjectNotPK(conn, tableName, object);
	}

	/**
	 * 14不需要主键;
	 * <p>
	 * 支持大对象;
	 * <p>
	 * 对象转为sql(insert);
	 *
	 * @Description: desc @Title: insertObjectNotPK @param conn @param
	 * tableName @param object @return return @throws
	 * Exception 参数说明 @return String 返回类型 @throws
	 */
	@Override
	public String insertObjectNotPK(Connection conn, String tableName, Object object) throws Exception {
		// 先构造key与value
		StringBuilder keySb = new StringBuilder();
		ArrayList<Object> parameterList = new ArrayList<Object>();
		StringBuilder valueSb = new StringBuilder();
		Class classObj = object.getClass();
		ReflectUtil reflectUtil = new ReflectUtil();
		Field[] fields = classObj.getDeclaredFields();
		for (Field field : fields) {
			/*
			 * 实体类的域是不是主键
			 */
			// boolean flagPk = field.isAnnotationPresent(Id.class);
			// if(flagPk){
			// continue;
			// }
			/*
			 * 实体类的域是不是对应数据库的列
			 */
			boolean flagColumn = field.isAnnotationPresent(Column.class);
			if (flagColumn) {
				String fieldName = field.getAnnotation(Column.class).name();
				// 追加key
				// sb_key.append(field_name).append(",");
				// oracle大写
				keySb.append(fieldName.toUpperCase()).append(",");
				Object type = field.getType().getName();
				Method getter = reflectUtil.findGetterMethodByField(classObj, field.getName());
				// 执行方法
				if (getter == null) {
					log.trace("getter为空，出错的类名=" + classObj.getName());
					log.trace("getter为空，出错的方法名=" + field.getName());
				}
				Object value = getter.invoke(object);
				// 追加value
				valueSb.append("?").append(",");
				parameterList.add(value);
			}
		}
		// 删除最后的逗号
		keySb.deleteCharAt(keySb.length() - 1);
		// 删除最后的逗号
		valueSb.deleteCharAt(valueSb.length() - 1);
		// 再构造sql
		IJdbcUtil jdbcUtil = this.getJdbcUtil();
		return jdbcUtil.insertObject(conn, tableName, keySb, valueSb, parameterList);
	}

	/**
	 * 15需要主键; 支持大对象;
	 * <p>
	 * 对象转为sql(insert);
	 *
	 * @param conn
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@Override
	public String insertObject(Connection conn, Object object) throws Exception {
		// 机器key
		String machine_key = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalMachine), "");
		if (StringUtil.isNotBlank(machine_key)) {
			// 得到表名
			AnnotationTable table = (AnnotationTable) object.getClass().getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			// 主键
			String pk = PkSimpleTool.findInstance().findPk(machine_key, tableName);
			// [master]2018-06-01 10:09:47,154[ERROR][{conn-10003, pstmt-20020}
			// execute error. UPDATE SYS_ID set
			// TABLE_NAME_=?,STEP_=?,NEXT_ID_=?,NEXT_LONG_=?,START_LONG_=?,
			// CREATE_TIME_=?, CREATE_TIME_LONG_=?,MACHINE_KEY_=? ,IP_=?,MAC_=?
			// where
			// IDX_=?][http-nio-8080-exec-2][com.alibaba.druid.filter.logging.Log4jFilter.statementLogError(Log4jFilter.java:152)][console_root_trace]
			// com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException:
			// Lock wait timeout exceeded; try restarting transaction
			// at sun.reflect.NativeConstructorAccessorImpl.newInstance0(Native
			// Method)
			if (false) {
				// Lock wait timeout exceeded; try restarting transaction
				// Long idx = PkTools.findInstance().findPk(machine_key,
				// tableName);
			}

			Long idx = 0l;
			// 保存
			return this.insertObject(conn, tableName, object, pk, idx);
		} else {
			return null;
		}
	}

	/**
	 * 16需要主键;
	 * <p>
	 * 支持大对象;
	 * <p>
	 * 对象转为sql(insert);
	 *
	 * @param conn
	 * @param tableName
	 * @param object
	 * @throws Exception
	 * @throws Exception
	 */
	@Override
	public String insertObject(Connection conn, String tableName, Object object, String pkInput, Long idxValue)
			throws Exception {
		// 先构造key与value
		ArrayList<Object> parameterList = new ArrayList<Object>();
		StringBuilder keySb = new StringBuilder();
		StringBuilder valueSb = new StringBuilder();
		Class classObj = object.getClass();
		ReflectUtil reflectUtil = ReflectThreadLocal.findThreadLocal().get();
		Field[] fieldArray = classObj.getDeclaredFields();
		for (Field field : fieldArray) {
			/*
			 * 实体类的域是不是主键
			 */
			boolean flagPK = field.isAnnotationPresent(Id.class);
			/*
			 * 实体类的域是不是对应数据库的列
			 */
			boolean columnFlag = field.isAnnotationPresent(Column.class);
			if (columnFlag) {
				String columnName = field.getAnnotation(Column.class).name();
				// 追加key
				// sb_key.append(field_name).append(",");
				// oracle大写
				if (flagPK) {
					//如果状态为自增的话，则不初始化该值
					if (GenerationType.IDENTITY.equals(field.getAnnotation(GeneratedValue.class).strategy())) {
						continue;
					}
					keySb.append(columnName.toUpperCase()).append(",");
					valueSb.append("?").append(",");
					Method getter = reflectUtil.findGetterMethodByField(classObj, field.getName());
					// 执行方法
					Object value = getter.invoke(object);
					pkInput = StringUtil.isBlank(value) ? pkInput : value.toString();
					parameterList.add(pkInput);
				} else {
					keySb.append(columnName.toUpperCase()).append(",");
					Method getter = reflectUtil.findGetterMethodByField(classObj, field.getName());
					// BaseLog.trace("field.getName()=" + field.getName());
					// 执行方法
					Object value = getter.invoke(object);
					// 索引
					if (columnName.toUpperCase().equals("IDX_")) {
						if (StringUtil.isBlank(value)) {
							value = idxValue;
						}
					}
					// 追加value
					valueSb.append("?").append(",");
					parameterList.add(value);
				}
			}
		}
		// 删除最后的逗号
		keySb.deleteCharAt(keySb.length() - 1);
		// 删除最后的逗号
		valueSb.deleteCharAt(valueSb.length() - 1);
		// 再构造sql
		IJdbcUtil jdbcUtil = this.getJdbcUtil();
		String pkReturn = jdbcUtil.insertObject(conn, tableName, keySb, valueSb, parameterList);
		if (StringUtil.isBlank(pkReturn)) {
			return pkInput;
		} else {
			return pkReturn;
		}
	}

	/**
	 * 17 支持大对象;
	 * <p>
	 * 对象转为sql(update);
	 *
	 * @param conn
	 * @param primaryKey
	 * @param pkValue
	 * @param tableName
	 * @param obj
	 * @throws Exception
	 * @throws Exception
	 */
	@Override
	public Integer updateObject(Connection conn, String primaryKey, String pkValue, String tableName, Object obj)
			throws Exception {
		// 先构造key与value
		StringBuilder keyAndValueSb = new StringBuilder();
		ArrayList<Object> listObject = new ArrayList<Object>();
		Class classObj = obj.getClass();
		ReflectUtil reflectUtil = new ReflectUtil();
		Field[] fieldArray = classObj.getDeclaredFields();
		for (Field field : fieldArray) {
			/**
			 *
			 * 实体类的域是不是对应数据库的列
			 */
			boolean columnFlag = field.isAnnotationPresent(Column.class);
			if (columnFlag) {
				Column column = (Column) field.getAnnotation(Column.class);
				String columnName = column.name();
				String fieldName = field.getName();
				// log.trace("columnName=" +columnName);
				// 追加key=value
				keyAndValueSb.append(columnName).append("=").append("?").append(",");
				Object type = field.getType().getName();
				Method methodSetter = reflectUtil.findGetterMethodByColumn(classObj, columnName);
				// 执行方法
				Object value = methodSetter.invoke(obj);
				listObject.add(value);
			}
		}
		// 删除逗号
		keyAndValueSb.deleteCharAt(keyAndValueSb.length() - 1);
		// 更新
		// 再构造sql
		IJdbcUtil jdbcUtil = this.getJdbcUtil();
		return jdbcUtil.updateObject(conn, primaryKey, pkValue, tableName, keyAndValueSb, listObject);
	}

	/**
	 * 18jpa更新
	 */
	@Override
	public Integer updateObject(Connection conn, Object object) throws Exception {
		Class classObj = object.getClass();
		// 得到表名
		AnnotationTable table = (AnnotationTable) object.getClass().getAnnotation(AnnotationTable.class);
		String tableName = table.name();
		// 得到主键的域值
		Field[] fieldArray = classObj.getDeclaredFields();
		String pkColumn = null;
		String pkField = null;
		for (int i = 0; i < fieldArray.length; i++) {
			boolean primaryKeyFlag = fieldArray[i].isAnnotationPresent(Id.class);
			if (primaryKeyFlag) {
				Column column = (Column) fieldArray[i].getAnnotation(Column.class);
				pkColumn = column.name();
				pkField = fieldArray[i].getName();
				break;
			}
		}
		// 得到主键的值
		ReflectUtil reflectUtil = ReflectThreadLocal.findThreadLocal().get();
		String pkValue = null;
		Method primaryKeyMethod = reflectUtil.findGetterMethodByField(classObj, pkField);
		if (primaryKeyMethod == null) {
			// AssertUtil.notNull(null, "找不到方法");
		}
		// 执行方法
		Object pkValueObject = primaryKeyMethod.invoke(object, new Object[]{});
		String pkClassName = pkValueObject.getClass().getName();
		// log.trace("class_name=" + class_name);
		if (pkClassName.equals("java.lang.Integer")) {
			Integer valueInteger = (Integer) pkValueObject;
			pkValue = valueInteger.toString();
		}
		if (pkClassName.equals("java.lang.Long")) {
			Long valueLong = (Long) pkValueObject;
			pkValue = valueLong.toString();
		}
		if (pkClassName.equals("java.lang.String")) {
			String valueStr = (String) pkValueObject;
			pkValue = valueStr.toString();
		}
		// 更新
		return this.updateObject(conn, pkColumn, pkValue, tableName, object);
	}

	/**
	 * 19将查询数据库结果转化为List<Object>
	 */
	@Override
	public <T> List<T> doResultSet2ListObject(Class<T> classObj, ResultSet resultSet) throws Exception {
		IJdbcUtil jdbcUtil = this.getJdbcUtil();
		// 声明
		// 第1步，所有的列名
		List<ColumnBean> columnBeanList = jdbcUtil.findColumnBeanListByApi(resultSet);
		// 第2步
		List<T> listObject = new ArrayList<>();
		if (resultSet == null) {
			return listObject;
		}
		while (resultSet.next()) {
			listObject.add(this.doResultSet2Object(columnBeanList, classObj, resultSet));
		}
		return listObject;
	}

	/**
	 * 20将查询数据库结果转化为类
	 *
	 * @param columnBeanList 列实例列表
	 * @param classObj       实例类对象
	 * @return 实例对象
	 */
	@Override
	public <T> T doResultSet2Object(List<ColumnBean> columnBeanList, Class<T> classObj, ResultSet resultSet)
			throws Exception {
		ReflectUtil reflectUtil = ReflectThreadLocal.findThreadLocal().get();
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
		T entityObject = classObj.newInstance();
		// 取出所有的列，列名，类型，根据列名找出方法,执行方法
		for (ColumnBean columnBean : columnBeanList) {
			Object value = resultSet.getObject(columnBean.getColumnLabel());
			if (value == null) {
				continue;
			}
			// 小写
			// Field field = reflectClass.findDeclaredField(classObj, info
			// .getColumnLabel().toLowerCase());
			Field field = reflectUtil.findDeclaredField(classObj, columnBean.getFieldName());
			if (field == null) {
				continue;
			}
			int columnType = columnBean.getSqlTypeInt();
			switch (columnType) {
				// mysql
				// case 1:
				case java.sql.Types.CHAR:
				case java.sql.Types.VARCHAR:
				case java.sql.Types.LONGVARCHAR:
					value = resultSet.getString(columnBean.getColumnLabel());
					if (value != null) {
						value = ((String) value).trim();
					}
					break;
				case java.sql.Types.BIT:
					value = this.BIT_ResultSet2EntityField(field, value, columnBean, resultSet);
					break;
				case java.sql.Types.TINYINT:
				case java.sql.Types.SMALLINT:
				case java.sql.Types.INTEGER:
					value = this.INTEGER_ResultSet2EntityField(field, value, columnBean, resultSet);
					break;
				case java.sql.Types.BIGINT:
					value = this.LONG_ResultSet2EntityField(field, value, columnBean, resultSet);
					break;
				case java.sql.Types.NUMERIC:
				case java.sql.Types.DECIMAL:
					value = this.NUMERIresultSet2EntityField(field, value, columnBean, resultSet);
					break;
				case 101:
				case java.sql.Types.DOUBLE:
					value = resultSet.getDouble(columnBean.getColumnLabel());
					break;
				case java.sql.Types.BLOB:
				case java.sql.Types.LONGVARBINARY:
					value = this.BLOB_ResultSet2EntityField(field, value, columnBean, resultSet);
					break;
				case java.sql.Types.DATE:
					value = resultSet.getDate(columnBean.getColumnLabel());
					break;
				case java.sql.Types.TIME:
					value = resultSet.getTime(columnBean.getColumnLabel());
					break;
				case java.sql.Types.TIMESTAMP:
					value = resultSet.getTimestamp(columnBean.getColumnLabel());
					break;
				default:
					log.trace("错误的列名=" + columnBean.getColumnLabel());
					throw new RuntimeException("错误的列的类型columnType=" + columnType);
			}
			Method setter = reflectUtil.findSetterMethodByColumn(classObj, columnBean.getColumnLabel());
			if (setter == null) {
				throw new RuntimeException("找不到方法set,所对应的FieldName=" + columnBean.getFieldName());
			}
			// 执行方法
			try {
				setter.invoke(entityObject, value);
			} catch (Exception e) {
				log.trace("columnType=" + columnType);
				log.trace("entityObject=" + entityObject);
				log.trace("columnBean.getFieldName()=" + columnBean.getFieldName());
				log.trace("value=" + value);
				e.printStackTrace();
				throw e;
			}
		}
		return entityObject;
	}

	/**
	 * 21结果集转成实体类的Field(变量,域值);
	 *
	 * @param field
	 * @param value
	 * @param columnBean
	 * @param resultSet
	 * @return
	 */
	@Override
	public Object BLOB_ResultSet2EntityField(Field field, Object value, ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException, IOException {
		FileUtil fileUtil = FileThreadLocal.findThreadLocal().get();
		String fieldName = columnBean.getFieldName();
		String typeName = field.getType().getName();
		String typeSimpleName = field.getType().getSimpleName();
		if (typeSimpleName.equals("byte[]")) {
			if (true) {
				Blob blob_value = resultSet.getBlob(columnBean.getColumnLabel());
				value = fileUtil.doBlob2byte(blob_value);
				return value;
			}
		}
		if (true) {
			throw new RuntimeException("数据库跟实体类参数类型不匹配,byte[]!=" + typeName);
		}
		return null;
	}

	/**
	 * 22结果集转成实体类的Field(变量,域值);
	 *
	 * @param field
	 * @param value
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 */
	@Override
	public Object NUMERIresultSet2EntityField(Field field, Object value, ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException {
		String fieldName = columnBean.getFieldName();
		// 实体类中域的类型
		String typeName = field.getType().getName();
		if (typeName.equals("java.math.BigDecimal")) {
			value = resultSet.getBigDecimal(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("int")) {
			/**
			 *
			 * oracle
			 */
			// java.sql.SQLException: 数字溢出
			// 数字太大，一般不转成int类型
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("java.lang.Integer")) {
			/**
			 *
			 * oracle
			 */
			// java.sql.SQLException: 数字溢出
			// 数字太大，一般不转成int类型
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("long")) {
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("java.lang.Long")) {
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("double")) {
			value = resultSet.getDouble(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("java.lang.Double")) {
			value = resultSet.getDouble(columnBean.getColumnLabel());
			return value;
		}
		/**
		 *
		 * 数字转成String
		 */
		if (typeName.equals("java.lang.String")) {
			value = resultSet.getString(columnBean.getColumnLabel());
			return value;
		}
		if (true) {
			throw new RuntimeException("数据库跟实体类参数类型不匹配,NUMERIC!=" + typeName);
		}
		return null;
	}

	/**
	 * 23结果集转成实体类的Field(变量,域值);
	 *
	 * @param field
	 * @param value
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 */
	@Override
	public Object BIT_ResultSet2EntityField(Field field, Object value, ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException {
		String fieldName = columnBean.getFieldName();
		// 实体类中域的类型
		String typeName = field.getType().getName();
		// log.trace("数据库是BIT 实体类变量fieldName=" + fieldName);
		// log.trace("数据库是BIT 实体类变量其它类型,typeName=" + typeName);
		if (typeName.equals("java.lang.Boolean")) {
			value = resultSet.getBoolean(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("int")) {
			// mysql
			// 有可能抛出异常 is outside valid range for the datatype INTEGER.
			// long类型一般不转成int类型
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("java.lang.Integer")) {
			// mysql
			// 有可能抛出异常 is outside valid range for the datatype INTEGER.
			// long类型一般不转成int类型
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("long")) {
			// log.trace("b 实体类变量fieldName=" + fieldName);
			// log.trace("b 实体类变量其它类型,typeName=" + typeName);
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("java.lang.Long")) {
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (true) {
			throw new RuntimeException("数据库跟实体类参数类型不匹配,BIT!=" + typeName);
		}
		return null;
	}

	/**
	 * 24结果集转成实体类的Field(变量,域值);
	 *
	 * @param field
	 * @param value
	 * @param columnBean
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 */
	@Override
	public Object INTEGER_ResultSet2EntityField(Field field, Object value, ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException {
		String fieldName = columnBean.getFieldName();
		// 实体类中域的类型
		String typeName = field.getType().getName();
		if (typeName.equals("int")) {
			value = resultSet.getInt(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("long")) {
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("java.lang.Short")) {
			value = resultSet.getShort(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("java.lang.Integer")) {
			value = resultSet.getInt(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("java.lang.Long")) {
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("java.math.BigDecimal")) {
			value = resultSet.getBigDecimal(columnBean.getColumnLabel());
			return value;
		}
		// sqlserver
		// {
		if (typeName.equals("java.lang.Byte")) {
			value = resultSet.getByte(columnBean.getColumnLabel());
			return value;
		}
		// }
		// sqlserver
		if (true) {
			throw new RuntimeException("数据库跟实体类参数类型不匹配,INTEGER!=" + typeName);
		}
		return null;
	}

	/**
	 * 25结果集转成实体类的Field(变量,域值);
	 *
	 * @param field
	 * @param value
	 * @param columnBean
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws SQLException
	 */
	@Override
	public Object LONG_ResultSet2EntityField(Field field, Object value, ColumnBean columnBean, ResultSet resultSet)
			throws SecurityException, NoSuchFieldException, SQLException {
		String fieldName = columnBean.getFieldName();
		// 实体类中域的类型
		String typeName = field.getType().getName();
		// log.trace("数据库是long 实体类变量fieldName=" + fieldName);
		// log.trace("数据库是long 实体类变量其它类型,typeName=" + typeName);
		if (typeName.equals("int")) {
			/**
			 * mysql
			 */
			// 有可能抛出异常 is outside valid range for the datatype INTEGER.
			// long类型一般不转成int类型
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("java.lang.Integer")) {
			// mysql
			// 有可能抛出异常 is outside valid range for the datatype INTEGER.
			// long类型一般不转成int类型
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("long")) {
			// log.trace("b 实体类变量fieldName=" + fieldName);
			// log.trace("b 实体类变量其它类型,typeName=" + typeName);
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("java.lang.Long")) {
			value = resultSet.getLong(columnBean.getColumnLabel());
			return value;
		}
		if (typeName.equals("java.math.BigDecimal")) {
			value = resultSet.getBigDecimal(columnBean.getColumnLabel());
			return value;
		}
		if (true) {
			throw new RuntimeException("数据库跟实体类参数类型不匹配,Long!=" + typeName);
		}
		return null;
	}
}
