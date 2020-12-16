package c.a.util.mongodb;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.bson.Document;
import org.quartz.SchedulerException;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;

import c.a.config.SysConfig;
import c.a.tools.primary_key.PkSimpleTool;
import c.a.util.core.annotation.AnnotationTable;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.json.JsonThreadLocal;
import c.a.util.core.json.JsonUtil;
import c.a.util.core.reflect.ReflectThreadLocal;
import c.a.util.core.reflect.ReflectUtil;
import c.a.util.core.string.StringUtil;
/**
 * 
 * 多个MongoClient;
 * 
 * 没有数据库连接池;
 * @Description:
 * @date 2015年10月16日 下午7:05:10
 * @author cxy
 * @Email: 
 * @Copyright (c) 1995-2095 本源代码受软件著作法保护，请在授权允许范围内使用。
 *
 */
public class MongoDBAyUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	// 服务器IP
	private static String ip = null;
	// 端口号
	private static int port = 27017;
	// 访问密码
	private static String username = null;
	// 访问密码
	private static String password = null;
	private static String database = null;
	private static List<ServerAddress> serverAddressList = null;
	private static List<MongoCredential> credentialList = null;
	// 单例
	private static MongoDBAyUtil instance = null;
	private final static Object key = new Object();
	// json数据的前缀
	private String jsonPrefix = "json#";
	// 数据库数据的前缀
	private String dataPrefix = "data#";
	// 表的前缀
	private String tablePrefix = "table#";
	// 精确查询的前缀
	private String queryExactPrefix = "qE#";
	// 模糊查询的前缀
	private String queryFuzzyPrefix = "qF#";
	// 构造函数
	private MongoDBAyUtil() {
	}
	public static MongoDBAyUtil findInstance() throws Exception {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new MongoDBAyUtil();
					// 初始化
					init();
				}
			}
		}
		return instance;
	}
	/**
	 * 初始化
	 * 
	 * @Title: init
	 * @Description:
	 *
	 * 				参数说明
	 * @throws SchedulerException
	 * @throws Exception
	 *             返回类型 void
	 */
	private static void init() throws Exception {
		String mongodbLocalStart = SysConfig.findInstance().findMap().get("mongodb.local.start").toString();
		if ("true".equals(mongodbLocalStart)) {
			// 连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
			// ServerAddress()两个参数分别为 服务器地址 和 端口
			// ServerAddress serverAddress = new ServerAddress("39.108.124.247",
			// 27017);
			ip = SysConfig.findInstance().findMap().get("mongodb.local.ip").toString();
			port = BeanThreadLocal.findThreadLocal().get()
					.find(SysConfig.findInstance().findMap().get("mongodb.local.port").toString(), port);
			username = SysConfig.findInstance().findMap().get("mongodb.local.username").toString();
			password = SysConfig.findInstance().findMap().get("mongodb.local.password").toString();
			database = SysConfig.findInstance().findMap().get("mongodb.local.database").toString();
			ServerAddress serverAddress = new ServerAddress(ip, port);
			serverAddressList = new ArrayList<ServerAddress>();
			serverAddressList.add(serverAddress);
			// MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
			MongoCredential credential = MongoCredential.createScramSha1Credential(username, database,
					password.toCharArray());
			credentialList = new ArrayList<MongoCredential>();
			credentialList.add(credential);
		}
	}
	public static String getIp() {
		return ip;
	}
	public static void setIp(String ip) {
		MongoDBAyUtil.ip = ip;
	}
	public static int getPort() {
		return port;
	}
	public static void setPort(int port) {
		MongoDBAyUtil.port = port;
	}
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		MongoDBAyUtil.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		MongoDBAyUtil.password = password;
	}
	public static String getDatabase() {
		return database;
	}
	public static void setDatabase(String database) {
		MongoDBAyUtil.database = database;
	}
	public MongoClient findMongoClient() {
		// 通过连接认证获取MongoDB连接
		MongoClient mongoClient = new MongoClient(serverAddressList, credentialList);
		// System.out.println("mongoClient=" + mongoClient);
		// System.out.println("mongoClient hashCode=" + mongoClient.hashCode());
		return mongoClient;
	}
	public MongoDatabase findMongoDatabase(MongoClient mongoClient) {
		// 打开数据库
		// 连接到数据库
		MongoDatabase mongoDatabase = mongoClient.getDatabase(database);
		// System.out.println("mongoDatabase=" + mongoDatabase);
		// System.out.println("mongoDatabase hashCode=" +
		// mongoDatabase.hashCode());
		return mongoDatabase;
	}
	public void close(MongoClient mongoClient) {
		mongoClient.close();
	}
	/**
	 * 构造表名(保存json)
	 * 
	 * @Title: findKeyTableJson
	 * @Description:
	 *
	 * 				参数说明
	 * @param tableName
	 * @return 返回类型 String
	 */
	public String findKeyTableJson(String tableName) {
		return this.jsonPrefix + tableName;
	}
	/**
	 * 构造表名
	 * 
	 * @Description:
	 *
	 * 				参数说明
	 * @param tableName
	 * @return 返回类型 String
	 */
	public String findKeyTable(String tableName) {
		return this.tablePrefix + tableName;
	}
	/**
	 * 主键列名
	 * 
	 * @Title: findPkColumnName
	 * @Description:
	 *
	 * 				参数说明
	 * @param object
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findPkColumnName(Class classObj) throws Exception {
		String columnName = null;
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
				columnName = field.getAnnotation(Column.class).name();
				if (flagPK) {
					break;
				}
			}
		}
		return columnName.toUpperCase();
	}
	/**
	 * 主键的值
	 * 
	 * @Title: findPkValue
	 * @Description:
	 *
	 * 				参数说明
	 * @param object
	 * @return
	 * @throws Exception
	 *             返回类型 String
	 */
	public String findPkValue(Object object) throws Exception {
		Class classObj = object.getClass();
		// 得到主键的域值
		Field[] fieldArray = classObj.getDeclaredFields();
		String pkField = null;
		for (int i = 0; i < fieldArray.length; i++) {
			boolean primaryKeyFlag = fieldArray[i].isAnnotationPresent(Id.class);
			if (primaryKeyFlag) {
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
		return pkValue;
	}
	private String insertObject(MongoDatabase mongoDatabase, String tableName, Object object, String pkInput,
			Long idxValue) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		// 集合与表
		String keyTable = this.findKeyTable(tableName);
		MongoCollection<Document> collection = mongoDatabase.getCollection(keyTable);
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
				Object type = field.getType().getName();
				if (flagPK) {
					map.put(columnName.toUpperCase(), pkInput);
				} else {
					Method getter = reflectUtil.findGetterMethodByField(classObj, field.getName());
					// 执行方法
					Object value = getter.invoke(object);
					// 索引
					if (columnName.toUpperCase().equals("IDX_")) {
						if (StringUtil.isBlank(value)) {
							value = idxValue;
						}
					}
					map.put(columnName.toUpperCase(), value);
				}
			}
		}
		Document document = new Document();
		Set<Map.Entry<String, Object>> mapList = map.entrySet();
		for (Map.Entry<String, Object> entry : mapList) {
			String key = entry.getKey();
			Object value = entry.getValue();
			document.append(key, value);
		}
		List<Document> documentList = new ArrayList<Document>();
		documentList.add(document);
		collection.insertMany(documentList);
		return pkInput;
	}
	private String insertObject(MongoDatabase mongoDatabase, Object object) throws Exception {
		// 机器key
		String machine_key = BeanThreadLocal.findThreadLocal().get()
				.find(SysConfig.findInstance().findMap().get(SysConfig.commLocalMachine), "");
		if (StringUtil.isNotBlank(machine_key)) {
			// 得到表名
			AnnotationTable table = (AnnotationTable) object.getClass().getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			// 主键
			String pk = PkSimpleTool.findInstance().findPk(machine_key, tableName);
			Long idx = 0l;
			return this.insertObject(mongoDatabase, tableName, object, pk, idx);
		} else {
			return null;
		}
	}
	/**
	 * 保存(模拟数据库的表操作)
	 * 
	 * @Title: save
	 * @Description:
	 *
	 * 				参数说明
	 * @param object
	 * @throws Exception
	 *             返回类型 void
	 */
	public void save(MongoDatabase mongoDatabase, Object object) throws Exception {
		insertObject(mongoDatabase, object);
	}
	/**
	 * 删除数据(模拟数据库的表操作)
	 * 
	 * @Title: del
	 * @Description:
	 *
	 * 				参数说明
	 * @param classObj
	 * @param id
	 * @return
	 * @throws Exception
	 *             返回类型 DeleteResult
	 */
	public DeleteResult del(MongoDatabase mongoDatabase, Class classObj, String id) throws Exception {
		DeleteResult deleteResult = null;
		// 得到表名
		AnnotationTable table = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
		String tableName = table.name();
		// 集合与表
		String keyTable = this.findKeyTable(tableName);
		MongoCollection<Document> collection = mongoDatabase.getCollection(keyTable);
		String columnName = this.findPkColumnName(classObj);
		// 删除所有符合条件的文档
		// deleteResult =
		// collection.deleteMany(Filters.eq(columnName.toUpperCase(),
		// id));
		deleteResult = collection.deleteOne(Filters.eq(columnName.toUpperCase(), id));
		return deleteResult;
	}
	/**
	 * 更新(模拟数据库的表操作)
	 * 
	 * @Title: update
	 * @Description:
	 *
	 * 				参数说明
	 * @param object
	 * @throws Exception
	 *             返回类型 void
	 */
	public void update(MongoDatabase mongoDatabase, Object object) throws Exception {
		String pkValue = this.findPkValue(object);
		this.save(mongoDatabase, object);
		this.del(mongoDatabase, object.getClass(), pkValue);
	}
	/**
	 * 找出所有的记录(模拟数据库的表操作)
	 * 
	 * @Title: findListMap
	 * @Description:
	 *
	 * 				参数说明
	 * @param classObj
	 * @return
	 * @throws Exception
	 *             返回类型 List<Map<String,Object>>
	 */
	public List<Map<String, Object>> findListMap(MongoDatabase mongoDatabase, Class classObj) throws Exception {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		// 得到表名
		AnnotationTable table = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
		String tableName = table.name();
		// 集合与表
		String keyTable = this.findKeyTable(tableName);
		MongoCollection<Document> collection = mongoDatabase.getCollection(keyTable);
		// 检索所有文档
		/**
		 * 1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3.
		 * 通过游标遍历检索出的文档集合
		 */
		FindIterable<Document> findIterable = collection.find();
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext()) {
			// log.trace("文档=" + mongoCursor.next());
			Map<String, Object> map = new HashMap<String, Object>();
			map.putAll(mongoCursor.next());
			listMap.add(map);
		}
		return listMap;
	}
	/**
	 * 找出某行记录(模拟数据库的表操作)
	 * 
	 * @Title: findMap
	 * @Description:
	 *
	 * 				参数说明
	 * @param classObj
	 * @param id
	 * @return
	 * @throws Exception
	 *             返回类型 Map<String,Object>
	 */
	public Map<String, Object> findMap(MongoDatabase mongoDatabase, Class classObj, String id) throws Exception {
		if (mongoDatabase != null) {
			String columnName = this.findPkColumnName(classObj);
			// 得到表名
			AnnotationTable table = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			Map<String, Object> map = new HashMap<String, Object>();
			// 集合与表
			String keyTable = this.findKeyTable(tableName);
			MongoCollection<Document> collection = mongoDatabase.getCollection(keyTable);
			// 检索所有文档
			/**
			 * 1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3.
			 * 通过游标遍历检索出的文档集合
			 */
			FindIterable<Document> findIterable = collection.find(Filters.eq(columnName.toUpperCase(), id));
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				// log.trace("文档=" + mongoCursor.next());
				map.putAll(mongoCursor.next());
				break;
			}
			return map;
		}
		return null;
	}
	/**
	 * 找出某行记录(模拟数据库的表操作)
	 */
	public Map<String, Object> findMap(MongoDatabase mongoDatabase, Class classObj, String fieldName, String fieldValue)
			throws Exception {
		if (mongoDatabase != null) {
			// 得到表名
			AnnotationTable table = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			Map<String, Object> map = new HashMap<String, Object>();
			// 集合与表
			String keyTable = this.findKeyTable(tableName);
			MongoCollection<Document> collection = mongoDatabase.getCollection(keyTable);
			// 检索所有文档
			/**
			 * 1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3.
			 * 通过游标遍历检索出的文档集合
			 */
			FindIterable<Document> findIterable = collection.find(Filters.eq(fieldName, fieldValue));
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				// log.trace("文档=" + mongoCursor.next());
				map.putAll(mongoCursor.next());
				break;
			}
			return map;
		}
		return null;
	}
	/**
	 * 保存json
	 * 
	 * @Title: saveJson
	 * @Description:
	 *
	 * 				参数说明
	 * @param object
	 * @throws Exception
	 *             返回类型 void
	 */
	public void saveJson(MongoDatabase mongoDatabase, Object object) throws Exception {
		if (mongoDatabase != null) {
			JsonUtil jsonUtil = JsonThreadLocal.findThreadLocal().get();
			// 得到表名
			AnnotationTable table = (AnnotationTable) object.getClass().getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			// 集合与表
			String keyTable = this.findKeyTableJson(tableName);
			MongoCollection<DBObject> collection = mongoDatabase.getCollection(keyTable, DBObject.class);
			BasicDBObject bson = BasicDBObject.parse(jsonUtil.bean2json(object));
			collection.insertOne(bson);
		}
	}
	/**
	 * 找出json
	 */
	public Map<String, Object> findJsonMap(MongoDatabase mongoDatabase, Class classObj, String fieldName,
			String fieldValue) throws Exception {
		if (mongoDatabase != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			// 得到表名
			AnnotationTable table = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			// 集合与表
			String keyTable = this.findKeyTableJson(tableName);
			MongoCollection<Document> collection = mongoDatabase.getCollection(keyTable);
			// 检索所有文档
			/**
			 * 1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3.
			 * 通过游标遍历检索出的文档集合
			 */
			FindIterable<Document> findIterable = collection.find(Filters.eq(fieldName, fieldValue));
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				// log.trace("文档=" + mongoCursor.next());
				map.putAll(mongoCursor.next());
				break;
			}
			return map;
		}
		return null;
	}
	/**
	 * 找出json
	 */
	public String findJson(MongoDatabase mongoDatabase, Class classObj, String fieldName, String fieldValue)
			throws Exception {
		if (mongoDatabase != null) {
			String json = null;
			// 得到表名
			AnnotationTable table = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
			String tableName = table.name();
			// 集合与表
			String keyTable = this.findKeyTableJson(tableName);
			MongoCollection<Document> collection = mongoDatabase.getCollection(keyTable);
			// 检索所有文档
			/**
			 * 1. 获取迭代器FindIterable<Document> 2. 获取游标MongoCursor<Document> 3.
			 * 通过游标遍历检索出的文档集合
			 */
			FindIterable<Document> findIterable = collection.find(Filters.eq(fieldName, fieldValue));
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				json = mongoCursor.next().toJson();
				break;
			}
			return json;
		}
		return null;
	}
	/**
	 * 找出json(没有ObjectId)
	 */
	public String findJsonNotObjectId(MongoDatabase mongoDatabase, Class classObj, String fieldName, String fieldValue)
			throws Exception {
		if (mongoDatabase != null) {
			Map<String, Object> map = this.findJsonMap(mongoDatabase, classObj, fieldName, fieldValue);
			String json = JsonThreadLocal.findThreadLocal().get().map2json(map);
			return json;
		}
		return null;
	}
	/**
	 * 分页查询
	 *
	 * @param page
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> page(MongoDatabase mongoDatabase, Class classObj, int page, int pageSize)
			throws Exception {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		// 得到表名
		AnnotationTable table = (AnnotationTable) classObj.getAnnotation(AnnotationTable.class);
		String tableName = table.name();
		// 集合与表
		String keyTable = this.findKeyTable(tableName);
		MongoCollection<Document> userCollection = mongoDatabase.getCollection(keyTable);
		// －1表示倒序
		// 1表示正序
		FindIterable findIterable = userCollection.find().skip((page - 1) * pageSize)
				.sort(new BasicDBObject("UPDATE_TIME_LONG_", -1)).limit(pageSize);
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		while (mongoCursor.hasNext()) {
			// log.trace("文档=" + mongoCursor.next());
			Map<String, Object> map = new HashMap<String, Object>();
			map.putAll(mongoCursor.next());
			listMap.add(map);
		}
		return listMap;
	}

}
