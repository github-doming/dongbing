package c.a.tools.primary_key.cy;

import java.util.UUID;

public class PkSimpleToolCy {

	private static PkSimpleToolCy instance = null;
	private final static Object key = new Object();

	/**
	 * 私有的默认构造子
	 */
	private PkSimpleToolCy() {
	}

	public static PkSimpleToolCy findInstance() {
		synchronized (key) {
			if (instance == null) {
				instance = new PkSimpleToolCy();
			}
		}
		return instance;
	}

	public String findPk(String table_name) throws Exception {

		UUID uuid = java.util.UUID.randomUUID();
		return uuid.toString();
	}

	public String findPk(String machine_key, String table_name) throws Exception {

		UUID uuid = java.util.UUID.randomUUID();
		return uuid.toString();
	}

}
