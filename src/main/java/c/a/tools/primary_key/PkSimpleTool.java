package c.a.tools.primary_key;
import c.a.util.core.annotation.AnnotationTable;
import c.a.util.core.uuid.Uuid;
public class PkSimpleTool {
	private static PkSimpleTool instance = null;
	private final static Object key = new Object();
	/**
	 * 私有的默认构造子
	 */
	private PkSimpleTool() {
	}
	public static PkSimpleTool findInstance() {
		synchronized (key) {
			if (instance == null) {
				instance = new PkSimpleTool();
			}
		}
		return instance;
	}
	public Long findPk() throws Exception {
		return Uuid.findInstance().toLong();
	}
	
	public String findPk(Class classInput) throws Exception {
		// 得到表名
		AnnotationTable table = (AnnotationTable) classInput.getAnnotation(AnnotationTable.class);
		String tableName = table.name();
		// 主键
		String pk =findPk(tableName);
		return pk;
	}
	public String findPk(String tableName) throws Exception {
		return Uuid.findInstance().toString();
	}
	public String findPk(String machine_key, String tableNamee) throws Exception {
		return Uuid.findInstance().toString();
	}
	
}
