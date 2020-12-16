package org.doming.core.common.servlet;
import java.awt.image.BufferedImage;
/**
 * @Description: mvc返回类
 * @Author: Dongming
 * @Date: 2019-07-29 14:23
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class MvcResult {
	private Type type;
	private String content;
	private BufferedImage bufferedImage;

	public Type getType() {
		return type;
	}
	public String getContent() {
		return content;
	}
	public BufferedImage getBufferedImage() {
		return bufferedImage;
	}

	public MvcResult(Type type, String content) {
		this.type = type;
		this.content = content;
	}
	public MvcResult(Type type, BufferedImage bufferedImage) {
		this.type = type;
		this.bufferedImage = bufferedImage;
	}

	public static MvcResult fileResult(String fileName) {
		return new MvcResult(Type.FILE, fileName);
	}

	public static MvcResult imageResult(BufferedImage bufferedImage) {
		return new MvcResult(Type.IMAGE, bufferedImage);
	}

	public enum Type {
		/**
		 * 文件地址
		 */
		FILE("文件"),
		/**
		 * IO流
		 */
		IMAGE("图片流");
		String name;
		Type(String name) {
			this.name = name;

		}
	}
}
