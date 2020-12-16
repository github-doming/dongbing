package com.ibm.common.test.doming.moa;

import com.ibm.common.test.wwj.MOA.Methods;
import org.junit.Test;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @Author: Dongming
 * @Date: 2020-06-10 16:49
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class HandicapTest {

	//region JS引擎

	@Test
	public void testJsEngine() throws FileNotFoundException, ScriptException {
		long startTime = System.currentTimeMillis(), endTime;
		String publicKey = "a196909454a05661bcc03ebdc6fb6ce728446f6e741d6153f85caf00a9429f02368b3dfdd11a03c27a87a25474518ff202c514bda28e8a7ab4507de3896f7f330fa4f899a43ecc35c2c7e91463e17fd50f62dd1bd6b82f4872221bde5daa0f77ba72ecd29a3c43ac50f9e40b40ff7b38612f0d1c6ff23c0ba253165df20a2d31";
		String password = "10001";

		ScriptEngineManager manager = new ScriptEngineManager();
		String path = getClass().getResource(".").getPath().concat("sss.js");
		// 获取一个指定的名称的脚本引擎
		ScriptEngine engine = manager.getEngineByName("js");
		engine.eval(new FileReader(path));

		Invocable invocable = (Invocable) engine;
		Methods methods = invocable.getInterface(Methods.class);

		endTime = System.currentTimeMillis();
		System.out.println("loadTime:"+(endTime - startTime));


		startTime = System.currentTimeMillis();
		for(int i = 0; i < 1000; i++) {
			methods.execute(publicKey, password);
		}
		endTime = System.currentTimeMillis();
		System.out.println("aysnTime:"+(endTime - startTime));

	}

	public static void main(String[] args) {
		new Thread(() -> {
			try {
				new HandicapTest().run1();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(() -> {
			try {
				new HandicapTest().run2();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ScriptException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public void run1() throws FileNotFoundException, ScriptException {
		long startTime = System.currentTimeMillis(), endTime;
		for (int i = 0; i < 10; i++) {

			String publicKey = "a196909454a05661bcc03ebdc6fb6ce728446f6e741d6153f85caf00a9429f02368b3dfdd11a03c27a87a25474518ff202c514bda28e8a7ab4507de3896f7f330fa4f899a43ecc35c2c7e91463e17fd50f62dd1bd6b82f4872221bde5daa0f77ba72ecd29a3c43ac50f9e40b40ff7b38612f0d1c6ff23c0ba253165df20a2d31";
			String password = "10001" + i;

			ScriptEngineManager manager = new ScriptEngineManager();
			String path = getClass().getResource(".").getPath().concat("sss.js");
			// 获取一个指定的名称的脚本引擎
			ScriptEngine engine = manager.getEngineByName("js");
			engine.eval(new FileReader(path));

			Invocable invocable = (Invocable) engine;
			Methods methods = invocable.getInterface(Methods.class);


			String rsaPwd = methods.execute(publicKey, password);

		}
		endTime = System.currentTimeMillis();
		System.out.println("run1:"+(endTime - startTime));
	}

	public void run2() throws FileNotFoundException, ScriptException {
		long startTime = System.currentTimeMillis(), endTime;
		String publicKey = "a196909454a05661bcc03ebdc6fb6ce728446f6e741d6153f85caf00a9429f02368b3dfdd11a03c27a87a25474518ff202c514bda28e8a7ab4507de3896f7f330fa4f899a43ecc35c2c7e91463e17fd50f62dd1bd6b82f4872221bde5daa0f77ba72ecd29a3c43ac50f9e40b40ff7b38612f0d1c6ff23c0ba253165df20a2d31";

		ScriptEngineManager manager = new ScriptEngineManager();
		String path = getClass().getResource(".").getPath().concat("sss.js");
		// 获取一个指定的名称的脚本引擎
		ScriptEngine engine = manager.getEngineByName("js");
		engine.eval(new FileReader(path));

		Invocable invocable = (Invocable) engine;
		Methods methods = invocable.getInterface(Methods.class);
		for (int i = 0; i < 10; i++) {
			String password = "10001" + i;
			String rsaPwd = methods.execute(publicKey, password);
		}
		endTime = System.currentTimeMillis();
		System.out.println("run2:"+(endTime - startTime));
	}
	//endregion




}
