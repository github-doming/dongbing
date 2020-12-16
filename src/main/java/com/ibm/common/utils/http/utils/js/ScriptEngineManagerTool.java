package com.ibm.common.utils.http.utils.js;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * @Description: 实例化ScriptEngine, 执行JS脚本
 * @Author: wwj
 * @Date: 2020/6/12 15:18
 * @Version: v1.0
 */
public class ScriptEngineManagerTool {

	private static ScriptEngineManagerTool instance = null;
	private static JsMethods methods = null;

	public static ScriptEngineManagerTool findInstance() {
		if (instance == null) {
			synchronized (ScriptEngineManagerTool.class) {
				if (instance == null) {
					ScriptEngineManagerTool instance = new ScriptEngineManagerTool();
					// 初始化
					instance.init();
					ScriptEngineManagerTool.instance = instance;
				}
			}

		}
		return instance;
	}

	private void init() {
		//创建一个ScriptEngineManager对象
		ScriptEngineManager manager = new ScriptEngineManager();
		//通过ScriptEngineManager获得ScriptEngine对象
		ScriptEngine engine = manager.getEngineByName("js");
		try {
			//用ScriptEngine的eval方法执行脚本
			engine.eval(JsMethods.SECURTY_JS);

			Invocable invocable = (Invocable) engine;
			methods = invocable.getInterface(JsMethods.class);
			System.out.println("初始化！！！！");
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行加密
	 *
	 * @param publicKey 公钥
	 * @param password  密码
	 * @return 加密后的密码
	 */
	public String getRSAPassword(String publicKey, String password) {
		return methods.execute(publicKey, password);
	}


}
