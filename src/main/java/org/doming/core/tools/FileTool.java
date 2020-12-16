package org.doming.core.tools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 文件工具类
 * @Author: Dongming
 * @Date: 2018-08-02 11:49
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 * @Copyright © 2018-2019 本源代码受软件著作法保护，请在授权允许范围内使用
 */
public class FileTool {

	/**
	 * 将文本保存到文件
	 *
	 * @param str      文本
	 * @param filePath 文件路径
	 */
	public static void saveStr2File(String filePath, String str) {
		FileOutputStream fop = null;
		File file;
		try {
			file = new File(filePath);
			fop = new FileOutputStream(file);
			if (!file.exists()) {
				if (file.createNewFile()) {
					throw new Exception("创建文件失败，文件路径：" + filePath);
				}
			}
			byte[] contentInBytes = str.getBytes(StandardCharsets.UTF_8);
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * 以行的形式读取文件
	 *
	 * @param fileName 文件名
	 */
	public static List<String> readFileByLines(String fileName) {
		List<String> list = new ArrayList<>();
		File file = new File(fileName);
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
			String tempString;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				list.add(tempString);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 写文件行
	 *
	 * @param fileName 文件名称
	 * @param array    内容
	 */
	public static void writerFileByLines(String fileName, List<String> array) {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));) {
			for (String str : array) {
				writer.write(str);
				writer.write("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 写文件行
	 *
	 * @param fileName 文件名称
	 * @param lines    内容
	 */
	public static void writerFileByLines(String fileName, String[] lines) {
		try (BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8))) {
			for (String str : lines) {
				writer.write(str);
				writer.write("\r\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isExist(String fileName) {
		return new File(fileName).exists();
	}

	/**
	 * 写文件字节
	 *
	 * @param fileName 文件名称
	 * @param bytes    字节内容
	 */
	public static void writerFileByBytes(String fileName, byte[] bytes) {
		try (FileOutputStream write = new FileOutputStream(new File(fileName))) {
			write.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读文件
	 *
	 * @param fileName 文件名称
	 */
	public static byte[] readFile(String fileName) throws IOException {

		File file = new File(fileName);
		if (!file.exists()) {
			throw new FileNotFoundException(fileName);
		}
		try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
				ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length())) {
			int bufSize = 1024;
			byte[] buffer = new byte[bufSize];
			int len;
			while (-1 != (len = in.read(buffer, 0, bufSize))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * 向文件中加入行
	 *
	 * @param fileName 文件名称
	 * @param lines    内容
	 */
	public static void addLines2File(String fileName, String[] lines) throws IOException {
		File file = new File(fileName);
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, file.exists()))) {
			for (String str : lines) {
				writer.write(str);
				writer.write("\r\n");
			}
		}
	}
}
