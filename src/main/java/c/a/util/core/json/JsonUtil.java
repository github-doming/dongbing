package c.a.util.core.json;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import c.a.util.core.bean.BeanThreadLocal;
import c.a.util.core.reflect.ReflectUtil;
import c.a.util.core.string.StringUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
/**
 * JSON工具
 * 
 * @author cxy
 * @Email:  使用范围：
 * 
 */
public class JsonUtil {
	protected Logger log = LogManager.getLogger(this.getClass());
	public String pk = "_pk";
	/**
	 * 1是否为空
	 * 
	 */
	public Boolean isNull(Object jsonObject) throws UnsupportedEncodingException {
		if (jsonObject == null || jsonObject instanceof net.sf.json.JSONNull) {
			return true;
		}
		return false;
	}
	/**
	 * 1是否为空
	 * 
	 */
	public Boolean isNull(JSONObject jsonObject) throws UnsupportedEncodingException {
		if (jsonObject == null || jsonObject.isNullObject()) {
			return  true;
		}
		return false;
	}
	/**
	 * 2设置JSON-LIB让其过滤掉引起循环的字段
	 * 
	 * @param excludeArray
	 * @param datePattern
	 * @return
	 */
	public JsonConfig configJson(String[] datePatternArray, String[] excludeArray) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludeArray);
		jsonConfig.setIgnoreDefaultExcludes(false);
		// 设置JSON-LIB的setCycleDetectionStrategy属性让其自己处理循环，
		// 省事但是数据过于复杂的话会引起数据溢出或者效率低下
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		// 设置JSON-LIB的setCycleDetectionStrategy属性
		for (String datePattern : datePatternArray) {
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl(datePattern));
			jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl(datePattern));
			jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonValueProcessorImpl(datePattern));
			jsonConfig.registerJsonValueProcessor(java.sql.Time.class, new JsonValueProcessorImpl(datePattern));
		}
		return jsonConfig;
	}
	/**
	 * 3设置JSON-LIB让其过滤掉引起循环的字段
	 * 
	 * @param excludeArray
	 * @return
	 */
	public JsonConfig configJsonExclude(String[] excludeArray) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(excludeArray);
		jsonConfig.setIgnoreDefaultExcludes(false);
		// 设置JSON-LIB的setCycleDetectionStrategy属性让其自己处理循环，
		// 省事但是数据过于复杂的话会引起数据溢出或者效率低下
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		return jsonConfig;
	}
	/**
	 * 4 JSON 时间解析器具
	 * 
	 * @param datePattern
	 * @return
	 */
	public JsonConfig configJsonDate(String[] datePatternArray) {
		JsonConfig jsonConfig = new JsonConfig();
		for (String datePattern : datePatternArray) {
			jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonValueProcessorImpl(datePattern));
			jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new JsonValueProcessorImpl(datePattern));
			jsonConfig.registerJsonValueProcessor(Timestamp.class, new JsonValueProcessorImpl(datePattern));
			jsonConfig.registerJsonValueProcessor(java.sql.Time.class, new JsonValueProcessorImpl(datePattern));
		}
		return jsonConfig;
	}
	/**
	 * 5json转换成listMap
	 * 
	 * @param jsonStr
	 * @return
	 */
	public List<Map<String, Object>> json2listMap(String jsonStr) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Collection collection = json2Collection(jsonStr, Map.class);
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			if (object == null) {
				continue;
			} else {
				Map<String, Object> map = (Map<String, Object>) object;
				listMap.add(map);
			}
		}
		return listMap;
	}
	/**
	 * 6json转换成listMap
	 * 
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<LinkedHashMap> json2listLinkedHashMap(String jsonStr) {
		List<LinkedHashMap> listMap = new ArrayList<LinkedHashMap>();
		Collection collection = json2Collection(jsonStr, LinkedHashMap.class);
		Iterator iterator = collection.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			if (object == null) {
				continue;
			} else {
				LinkedHashMap map = (LinkedHashMap) object;
				listMap.add(map);
			}
		}
		return listMap;
	}
	/**
	 * 7 从json中解析出List<String>
	 * 
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<String> json2listString(String jsonStr) {
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		List<String> list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			list.add(jsonArray.getString(i));
		}
		return list;
	}
	/**
	 * 8 把json的yyyy-MM-dd的转换为Bean中的util.Date类型;
	 * 
	 * 从json中得到一个java对象列表;
	 * 
	 * 从json中解析出List<Bean>;
	 * 
	 * @param jsonStr
	 * @param pojoClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List json2listBean(String jsonStr, Class classObject, Map classMap) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(classObject);
		jsonConfig.setClassMap(classMap);
		JSONArray jsonArray = JSONArray.fromObject(jsonStr, jsonConfig);
		String[] dateFormats = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss"};
		DateMorpher dateMorpher = new DateMorpher(dateFormats);
		dateMorpher.setUseDefault(true);
		dateMorpher.setDefaultValue(new java.util.Date());
		JSONUtils.getMorpherRegistry().registerMorpher(dateMorpher);
		List list = (List) JSONArray.toCollection(jsonArray, jsonConfig);
		return list;
	}
	/**
	 * 9 把json的yyyy-MM-dd的转换为Bean中的util.Date类型;
	 * 
	 * 从json中得到一个java对象列表;
	 * 
	 * 从json中解析出List<Bean>;
	 * 
	 * @param jsonStr
	 * @param pojoClass
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List json2listBean(String jsonStr, Class classObject) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(classObject);
		// jsonConfig.setClassMap(classMap);
		JSONArray jsonArray = JSONArray.fromObject(jsonStr, jsonConfig);
		String[] dateFormats = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss"};
		DateMorpher dateMorpher = new DateMorpher(dateFormats);
		dateMorpher.setUseDefault(true);
		dateMorpher.setDefaultValue(new java.util.Date());
		JSONUtils.getMorpherRegistry().registerMorpher(dateMorpher);
		List list = (List) JSONArray.toCollection(jsonArray, jsonConfig);
		return list;
	}
	/**
	 * 10 把json的yyyy-MM-dd的转换为Bean中的util.Date类型;
	 * 
	 * 从json中得到一个java对象列表;
	 * 
	 * 从json中解析出List<Bean>;
	 * 
	 * @param jsonStr
	 * @param classObject
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Collection json2Collection(String jsonStr, Class classObject) {
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		String[] dateFormats = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss"};
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));
		Collection collection = JSONArray.toCollection(jsonArray, classObject);
		return collection;
	}
	/**
	 * 11从json中解析出java字符串数组
	 * 
	 * @param jsonStr
	 * @return
	 */
	public String[] json2strArray(String jsonStr) {
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		String[] stringArray = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			stringArray[i] = jsonArray.getString(i);
		}
		return stringArray;
	}
	/**
	 * 12从json数组中解析出javaLong型对象数组
	 * 
	 * @param jsonStr
	 * @return
	 */
	public Long[] json2longArray(String jsonStr) {
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		Long[] longArray = new Long[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			longArray[i] = jsonArray.getLong(i);
		}
		return longArray;
	}
	/**
	 * 13从json数组中解析出java Integer型对象数组
	 * 
	 * @param jsonStr
	 * @return
	 */
	public Integer[] json2arrayInteger(String jsonStr) {
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		Integer[] integerArray = new Integer[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			integerArray[i] = jsonArray.getInt(i);
		}
		return integerArray;
	}
	/**
	 * 14从json数组中解析出java Double型对象数组
	 * 
	 * @param jsonStr
	 * @return
	 */
	public Double[] json2arrayDouble(String jsonStr) {
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		Double[] doubleArray = new Double[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			doubleArray[i] = jsonArray.getDouble(i);
		}
		return doubleArray;
	}
	/**
	 * 15从json中得到相应java数组
	 * 
	 * @param jsonStr
	 * @return
	 */
	public Object[] json2arrayObject(String jsonStr) {
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		return jsonArray.toArray();
	}
	/**
	 * 
	 * 16 json转成bean
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public Object json2Bean(String jsonStr, Class classObj, Map classMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		String[] dateFormats = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss"};
		DateMorpher dateMorpher = new DateMorpher(dateFormats);
		dateMorpher.setUseDefault(true);
		dateMorpher.setDefaultValue(new java.util.Date());
		JSONUtils.getMorpherRegistry().registerMorpher(dateMorpher);
		Object resultObj = JSONObject.toBean(jsonObject, classObj, classMap);
		return resultObj;
	}
	/**
	 * 
	 * 17json转成bean
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public Object json2Bean(String jsonStr, Class classObj) {
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		String[] dateFormats = new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss"};
		DateMorpher dateMorpher = new DateMorpher(dateFormats);
		dateMorpher.setUseDefault(true);
		dateMorpher.setDefaultValue(new java.util.Date());
		JSONUtils.getMorpherRegistry().registerMorpher(dateMorpher);
		//Map<String, Class> classMap = new HashMap<String, Class>();
		//classMap.put("data", Map.class);
		//Object resultObj = JSONObject.toBean(jsonObject, classObj, classMap );
		Object resultObj = JSONObject.toBean(jsonObject, classObj);
		return resultObj;
	}
	/**
	 * 18从一个JSON 对象字符格式中得到一个map对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Object> json2map(String jsonStr) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Iterator keyIterator = jsonObject.keys();
		String key = null;
		Object value = null;
		while (keyIterator.hasNext()) {
			key = (String) keyIterator.next();
			value = jsonObject.get(key);
			returnMap.put(key, value);
		}
		return returnMap;
	}
	/**
	 * 18从一个JSON 对象字符格式中得到一个map对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, String> json2mapStr(String jsonStr) {
		Map<String, String> returnMap = new HashMap<String, String>();
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		Iterator keyIterator = jsonObject.keys();
		String key = null;
		String value = null;
		while (keyIterator.hasNext()) {
			key = (String) keyIterator.next();
			value = jsonObject.get(key).toString();
			returnMap.put(key, value);
		}
		return returnMap;
	}
	/**
	 * 19从一个JSON 对象字符格式中得到一个map对象(jquery表单保存的json)
	 * 
	 * 
	 */
	public Map<String, Object> json2mapByJquery(String jsonStr) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<Map<String, Object>> jsonMapList = json2listMap(jsonStr);
		for (Map<String, Object> jsonMap : jsonMapList) {
			String jsonName = (String) jsonMap.get("name");
			Object jsonValueObject = jsonMap.get("value");
			if (jsonValueObject instanceof List) {
				List gridList = (List) jsonValueObject;
				String gridJson = list2json(gridList);
				returnMap.put(jsonName, gridJson);
			} else {
				// 值不能为空
				if (StringUtil.isNotBlank(jsonValueObject)) {
					// 是否一个参数有多个值
					if (returnMap.get(jsonName) != null) {
						String jsonValueOld = (String) returnMap.get(jsonName);
						jsonValueOld = jsonValueOld + ","
								+ BeanThreadLocal.findThreadLocal().get().find(jsonValueObject.toString(), " ");
						returnMap.put(jsonName, jsonValueOld);
					} else {
						returnMap.put(jsonName,
								BeanThreadLocal.findThreadLocal().get().find(jsonValueObject.toString(), " "));
					}
				}
			}
		}
		return returnMap;
	}
	/**
	 * 20json转成bean(jquery表单保存的json)
	 * 
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public Object json2BeanByJquery(String jsonStr, Class classObj)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Map<String, Object> map = json2mapByJquery(jsonStr);
		ReflectUtil reflectUti = new ReflectUtil();
		Object resultObj = reflectUti.doMap2Object(classObj, map);
		return resultObj;
	}
	/**
	 * 21将java对象转换成BasicDBObject
	 * 
	 * @Title: bean2BasicDBObject
	 * @Description:
	 *
	 * 				参数说明
	 * @param object
	 * @return 返回类型 BasicDBObject
	 */
	public BasicDBObject bean2BasicDBObject(Object object) {
		String json = bean2json(object);
		BasicDBObject basicDBObject = BasicDBObject.parse(json);
		return basicDBObject;
	}
	/**
	 * 21将java对象转换成DBObject
	 * 
	 * @deprecated
	 * @Title: bean2DBObject
	 * @Description:
	 *
	 * 				参数说明
	 * @param object
	 * @return 返回类型 DBObject
	 */
	public DBObject bean2DBObject(Object object) {
		String[] dateArray = new String[]{"yyyy-MM-dd HH:mm:ss"};
		JsonConfig jsonConfig = configJsonDate(dateArray);
		JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
		DBObject dbObject = (DBObject) com.mongodb.util.JSON.parse(jsonObject.toString());
		return dbObject;
	}
	/**
	 * 21将java对象转换成json字符串
	 * 
	 * @param object
	 * @return
	 */
	public String bean2json(Object object) {
		String[] dateArray = new String[]{"yyyy-MM-dd HH:mm:ss"};
		JsonConfig jsonConfig = configJsonDate(dateArray);
		JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
		return jsonObject.toString();
	}
	/**
	 * 22将java对象转换成json字符串,并设定日期格式
	 * 
	 * @param datePattern
	 * @param object
	 * @return
	 */
	public String bean2jsonDate(Object object, String[] datePatternArray) {
		JsonConfig jsonConfig = configJsonDate(datePatternArray);
		JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
		return jsonObject.toString();
	}
	/**
	 * 23将java对象转换成json字符串,并设定过滤字段
	 * 
	 * @Title: bean2jsonExclude
	 * @Description:
	 *
	 * 				参数说明
	 * @param object
	 * @param excludeArray
	 * @return 返回类型 String
	 */
	public String bean2jsonExclude(Object object, String[] excludeArray) {
		String[] dateArray = new String[]{"yyyy-MM-dd HH:mm:ss"};
		JsonConfig jsonConfig = configJson(dateArray, excludeArray);
		JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
		return jsonObject.toString();
	}
	/**
	 * 24 将java对象转换成json字符串,并设定过滤字段和日期格式
	 * 
	 * @Title: bean2json
	 * @Description:
	 *
	 * 				参数说明
	 * @param object
	 * @param excludeArray
	 * @return 返回类型 String
	 */
	public String bean2json(Object object, String[] excludeArray) {
		String[] datePatternArray = new String[]{"yyyy-MM-dd HH:mm:ss"};
		JsonConfig jsonConfig = configJson(datePatternArray, excludeArray);
		JSONObject jsonObject = JSONObject.fromObject(object, jsonConfig);
		return jsonObject.toString();
	}
	/**
	 * 25map转换成JSON 对象
	 * 
	 * @param map
	 * @return
	 */
	public String mapStr2json(Map<String, String> map) {
		// String[] dateArray = new String[]{"yyyy-MM-dd HH:mm:ss",
		// "yyyy-MM-dd",
		// "HH:mm:ss"};
		String[] dateArray = new String[]{"yyyy-MM-dd HH:mm:ss"};
		JsonConfig jsonConfig = configJsonDate(dateArray);
		JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
		return jsonObject.toString();
	}
	/**
	 * 25map转换成JSON 对象
	 * 
	 * @param map
	 * @return
	 */
	public String map2json(Map<String, Object> map) {
		// String[] dateArray = new String[]{"yyyy-MM-dd HH:mm:ss",
		// "yyyy-MM-dd",
		// "HH:mm:ss"};
		String[] dateArray = new String[]{"yyyy-MM-dd HH:mm:ss"};
		JsonConfig jsonConfig = configJsonDate(dateArray);
		JSONObject jsonObject = JSONObject.fromObject(map, jsonConfig);
		return jsonObject.toString();
	}
	/**
	 * 26java数组转成json
	 * 
	 * @param arrayObject
	 * @return
	 */
	public String array2json(Object arrayObject) {
		String[] dateArray = new String[]{"yyyy-MM-dd HH:mm:ss"};
		JsonConfig jsonConfig = configJsonDate(dateArray);
		JSONArray jsonArray = JSONArray.fromObject(arrayObject, jsonConfig);
		return jsonArray.toString();
	}
	/**
	 * 27list转成json
	 * 
	 * @param jsonConfig
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String list2json(JsonConfig jsonConfig, List list) {
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		return jsonArray.toString();
	}
	/**
	 * 28list转成json
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String list2json(List list) {
		return list2JSONArray(list).toString();
	}
	/**
	 * 29 list转成json
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public JSONArray list2JSONArray(List list) {
		String[] dateArray = new String[]{"yyyy-MM-dd HH:mm:ss"};
		JsonConfig jsonConfig = configJsonDate(dateArray);
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		return jsonArray;
	}
	/**
	 * 30返回字符串
	 * 
	 * @Description: desc @Title: findString @param jsonObject @param
	 *               key @return 参数说明 @return String 返回类型 @throws
	 */
	public String findString(JSONObject jsonObject, String key) {
		Object value = jsonObject.get(key);
		if (value == null) {
			return "";
		}
		if (value instanceof JSONNull) {
			return "";
		}
		if ("null".equals(value.toString())) {
			return "";
		}
		return value.toString();
	}
	/**
	 * 30返回字符串
	 * 
	 */
	public String findStringForMap(Map<String, Object> jsonMap, String key) {
		Object value = jsonMap.get(key);
		if (value == null) {
			return "";
		}
		if (value instanceof JSONNull) {
			return "";
		}
		if ("null".equals(value.toString())) {
			return "";
		}
		return value.toString();
	}
	/**
	 * 31返回字符串
	 * 
	 * @Title: findString
	 * @Description:
	 *
	 * 				参数说明
	 * @param jsonObject
	 * @param key
	 * @param defaultStr
	 * @return 返回类型 String
	 */
	public String findString(JSONObject jsonObject, String key, String defaultStr) {
		Object value = jsonObject.get(key);
		if (value == null) {
			return defaultStr;
		}
		if (value instanceof JSONNull) {
			return defaultStr;
		}
		if ("null".equals(value.toString())) {
			return defaultStr;
		}
		return value.toString();
	}
	/**
	 * 31返回字符串
	 * 
	 * @Title: findString
	 * @Description:
	 *
	 * 				参数说明
	 * @param jsonMap
	 * @param key
	 * @param defaultStr
	 * @return 返回类型 String
	 */
	public String findString(Map<String, Object> jsonMap, String key, String defaultStr) {
		Object value = jsonMap.get(key);
		if (value == null) {
			return defaultStr;
		}
		if (value instanceof JSONNull) {
			return defaultStr;
		}
		if ("null".equals(value.toString())) {
			return defaultStr;
		}
		return value.toString();
	}
	/**
	 * 32返回整型
	 * 
	 * @Title: findInt
	 * @Description:
	 *
	 * 				参数说明
	 * @param jsonObject
	 * @param key
	 * @return 返回类型 Integer
	 */
	public Integer findInt(JSONObject jsonObject, String key) {
		Object value = jsonObject.get(key);
		if (value == null) {
			return 0;
		}
		if (value instanceof JSONNull) {
			return 0;
		}
		if ("null".equals(value.toString())) {
			return 0;
		}
		//return new Integer(value.toString());
		//Integer.valueOf的效率高。因为Integer.valueOf用到了缓存
		return Integer.valueOf(value.toString());
	}
	/**
	 * 33返回整型
	 * 
	 * @Title: findInt
	 * @Description:
	 *
	 * 				参数说明
	 * @param jsonObject
	 * @param key
	 * @param defaultValue
	 * @return 返回类型 Integer
	 */
	public Integer findInt(JSONObject jsonObject, String key, int defaultValue) {
		Object value = jsonObject.get(key);
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof JSONNull) {
			return defaultValue;
		}
		if ("null".equals(value.toString())) {
			return defaultValue;
		}
		//return new Integer(value.toString());
		//Integer.valueOf的效率高。因为Integer.valueOf用到了缓存
		return Integer.valueOf(value.toString());
	}
	/**
	 * 33返回整型
	 * 
	 * @Title: findInt
	 * @Description:
	 *
	 * 				参数说明
	 * @param jsonMap
	 * @param key
	 * @param defaultValue
	 * @return 返回类型 Integer
	 */
	public Integer findInt(Map<String, Object> jsonMap, String key, int defaultValue) {
		Object value = jsonMap.get(key);
		if (value == null) {
			return defaultValue;
		}
		if (value instanceof JSONNull) {
			return defaultValue;
		}
		if ("null".equals(value.toString())) {
			return defaultValue;
		}
		//return new Integer(value.toString());
		//Integer.valueOf的效率高。因为Integer.valueOf用到了缓存
		return Integer.valueOf(value.toString());
	}
	/**
	 * 转成json
	 * 
	 * @Title: toJson
	 * @Description:
	 *
	 * 				参数说明
	 * @param data
	 * @param success
	 * @param code
	 * @param message
	 * @param total
	 * @return 返回类型 String
	 */
	public String toJson(Object data, Boolean success, String code, String message, Integer total) {
		JsonTcpBean bean = new JsonTcpBean();
		bean.setSuccess(success);
		bean.setTotal(total);
		bean.setData(data);
		bean.setCodeSys(code);
		bean.setMsgSys(message);
		String json = JSONObject.fromObject(bean).toString();
		return json;
	}
	/**
	 * 转成json
	 * 
	 * @Title: toJson
	 * @Description:
	 *
	 * 				参数说明
	 * @param bean
	 * @return 返回类型 String
	 */
	public String toJson(Object bean) {
		String json = JSONObject.fromObject(bean).toString();
		return json;
	}
	/**
	 * 菜单json转成对象,菜单json包括数据库的列名
	 * 
	 * @Title: findObjectList
	 * @Description:
	 *
	 * 				参数说明
	 * @param jsonStr
	 * @param classObj
	 * @param objectList
	 * @param childrenKey
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 *             返回类型 List<Object>
	 */
	public List<Object> findObjectList(Object jsonStr, Class classObj, List<Object> objectList, String childrenKey)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		ReflectUtil reflectUtil = new ReflectUtil();
		JSONArray jsonArray = JSONArray.fromObject(jsonStr);
		for (int i = 0; i < jsonArray.size(); i++) {
			Object object = classObj.newInstance();
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			Set<String> keyList = jsonObj.keySet();
			for (String key : keyList) {
				Object value = jsonObj.get(key);
				if (childrenKey.equals(key)) {
					objectList = findObjectList(value, classObj, objectList, childrenKey);
				} else {
					// Method method =
					// reflectUtil.findSetterMethodByField(classObj, key);
					Method method = reflectUtil.findSetterMethodByColumn(classObj, key);
					if (method != null && StringUtil.isNotBlank(value)) {
						try {
							method.invoke(object, new Object[]{value});
						} catch (Exception e) {
							// log.trace("method="+method.getName());
							// log.trace("value="+value);
							// log.trace("value="+value.getClass().getName());
							e.printStackTrace();
						}
					}
				}
			}
			objectList.add(object);
		}
		return objectList;
	}
	/**
	 * 找出明细表的个数
	 * 
	 * @Title: findChildSize
	 * @Description:
	 *
	 * 				参数说明
	 * @param jsonString
	 * @return 返回类型 int
	 */
	public int findChildSize(String jsonString) {
		int size = 0;
		Map<String, Object> jsonMap = json2map(jsonString);
		Set<String> parentKeyList = jsonMap.keySet();
		for (String parentKey : parentKeyList) {
			Object parentValueObject = jsonMap.get(parentKey);
			if (parentValueObject instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray) parentValueObject;
				size = size + jsonArray.size();
			}
		}
		return size;
	}
	/**
	 * 从json找出值
	 * 
	 * @Title: findValue
	 * @Description:
	 *
	 * 				参数说明
	 * @param jsonString
	 * @param key
	 * @return 返回类型 List<JsonTableBean>
	 */
	public List<JsonTableBean> findValue(String jsonString, String key) {
		List<JsonTableBean> jsonTableBeanList = new ArrayList<JsonTableBean>();
		// Map<String, Class> classMap = new HashMap<String, Class>();
		Map<String, Object> jsonMap = json2map(jsonString);
		Set<String> parentKeyList = jsonMap.keySet();
		for (String parentKey : parentKeyList) {
			Object parentValueObject = jsonMap.get(parentKey);
			if (parentValueObject instanceof JSONArray) {
				List<Map<String, Object>> childListMap = json2listMap(parentValueObject.toString());
				for (Map<String, Object> childMap : childListMap) {
					Set<String> childKeyList = childMap.keySet();
					for (String childKey : childKeyList) {
						String childValue = childMap.get(childKey).toString();
						if (childKey.equals(key)) {
							JsonTableBean jsonTableBean = new JsonTableBean();
							jsonTableBean.setTableName(parentKey);
							jsonTableBean.setKey(childKey);
							jsonTableBean.setValue(childValue);
							jsonTableBeanList.add(jsonTableBean);
						}
					}
				}
			}
			if (parentValueObject instanceof String) {
				String parentValue = (String) parentValueObject;
				if (parentKey.equals(key)) {
					JsonTableBean jsonTableBean = new JsonTableBean();
					jsonTableBean.setTableName("");
					jsonTableBean.setKey(parentKey);
					jsonTableBean.setValue(parentValue);
					jsonTableBeanList.add(jsonTableBean);
				}
			}
		}
		return jsonTableBeanList;
	}
	/**
	 * 保存所有主键的值到json
	 * 
	 * @Title: saveAllPk
	 * @Description:
	 *
	 * 				参数说明
	 * @param jsonString
	 * @param pkList
	 * @return 返回类型 String
	 */
	public String saveAllPk(String jsonString, List<String> pkList) {
		// 主键集合下标值
		int pkListIndex = 0;
		Map<String, Object> jsonMap = json2map(jsonString);
		if (StringUtil.isBlank(jsonMap.get(pk))) {
			jsonMap.put(pk, pkList.get(pkListIndex));
			pkListIndex = pkListIndex + 1;
		}
		Set<String> parentKeyList = jsonMap.keySet();
		for (String parentKey : parentKeyList) {
			Object parentValueObject = jsonMap.get(parentKey);
			if (parentValueObject instanceof JSONArray) {
				List<Map<String, Object>> childListMap = json2listMap(parentValueObject.toString());
				for (Map<String, Object> childMap : childListMap) {
					if (StringUtil.isBlank(childMap.get(pk))) {
						childMap.put(pk, pkList.get(pkListIndex));
						pkListIndex = pkListIndex + 1;
					}
				}
				jsonMap.put(parentKey, list2JSONArray(childListMap));
			}
		}
		return map2json(jsonMap);
	}
}
