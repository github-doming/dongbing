package c.a.util.mongodb;

/**
 */
public class MongoDBUtil extends MongoDBImgUtil{
	
	// 单例
	private volatile static MongoDBUtil instance = null;
	private final static Object key = new Object();

	// 构造函数
	private MongoDBUtil() {
	}
	public static MongoDBUtil findInstance() throws Exception {
		if (instance == null) {
			synchronized (key) {
				if (instance == null) {
					instance = new MongoDBUtil();
					// 初始化
					init();
				}
			}
		}
		return instance;
	}
	
}
