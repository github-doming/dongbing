package org.doming.core.tools;
import org.apache.commons.codec.binary.Base64;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
/**
 * @Description: 图片工具类
 * @Author: Dongming
 * @Date: 2019-04-22 10:32
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class ImageTool {

	/**
	 * 获取图片
	 *
	 * @param fileName 图片文件地址
	 * @return 图片缓存类
	 */
	public static BufferedImage getImage(String fileName) throws IOException {
		if (StringTool.isEmpty(fileName)) {
			throw new FileNotFoundException("未找到文件，文件路径为：" + fileName);
		}
		return getImage(new File(fileName));
	}

	/**
	 * 获取图片
	 *
	 * @param file 图片文件
	 * @return 图片缓存类
	 */
	public static BufferedImage getImage(File file) throws IOException {
		return ImageIO.read(file);
	}

	/**
	 * 获取图片
	 *
	 * @param url 图片url地址
	 * @return 图片缓存类
	 */
	public static BufferedImage getImage(URL url) throws IOException {
		return ImageIO.read(url);
	}

	/**
	 * 获取图片
	 *
	 * @param input 输入流
	 * @return 图片缓存类
	 */
	public static BufferedImage getImage(InputStream input) throws IOException {
		return ImageIO.read(input);
	}

	/**
	 * 缩放图片
	 *
	 * @param image 图片缓存类
	 * @param scale 缩放比例
	 * @return 缩放后图片
	 */
	public static BufferedImage scaledImage(BufferedImage image, double scale) {
		if (scale - 1 == 0) {
			return image;
		}
		return scaledImage(image, (int) (image.getWidth() * scale));
	}

	/**
	 * 缩放图片
	 *
	 * @param image      图片缓存类
	 * @param scaleWidth 缩放后的宽度
	 * @return 缩放后图片
	 */
	public static BufferedImage scaledImage(BufferedImage image, int scaleWidth) {
		int width = image.getWidth();
		if (width - scaleWidth == 0) {
			return image;
		}
		int height = image.getHeight();
		int scaleHeight = scaleWidth * height / width;
		BufferedImage outImage = new BufferedImage(scaleWidth, scaleHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = outImage.createGraphics();
		graphics.drawImage(image.getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_REPLICATE), 0, 0, null);
		graphics.dispose();
		return outImage;
	}

	/**
	 * 裁剪图片
	 *
	 * @param image 图片缓存类
	 * @param x     裁剪x点
	 * @param y     裁剪y点
	 * @param size  裁剪大小
	 * @return 裁剪后的图片
	 */
	public static BufferedImage cropImage(BufferedImage image, int x, int y, int size) {
		return cropImage(image, x, y, size, size);
	}

	/**
	 * 裁剪图片
	 *
	 * @param image  图片缓存类
	 * @param x      裁剪x点
	 * @param y      裁剪y点
	 * @param width  裁剪宽度
	 * @param height 裁剪高度
	 * @return 裁剪后的图片
	 */
	public static BufferedImage cropImage(BufferedImage image, int x, int y, int width, int height) {
		return image.getSubimage(x, y, width, height);
	}

	/**
	 * 保存图片
	 *
	 * @param image    图片缓存类
	 * @param fileName 文件名称
	 */
	public static void saveImage(BufferedImage image, String fileName) throws IOException {
		int index = fileName.lastIndexOf(".");
		//获取被缩放的图片的格式
		String formatName = fileName.substring(index + 1);
		File outFile = new File(fileName);
		ImageIO.write(image, formatName, outFile);
	}

	/**
	 * 把图片的base64编码转成图片
	 *
	 * @param base64Code 图片的base64编码
	 * @param path       保存的路径
	 * @return boolean
	 */
	public static boolean base64ToImage(String base64Code, String path) {
		if (null == base64Code) {
			return false;
		}
		try (OutputStream out = new FileOutputStream(path)) {
			byte[] base64Byte = Base64.decodeBase64(base64Code);
			out.write(base64Byte);
			out.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 把图片转成 base64编码
	 *
	 * @param imgFile 图片路径
	 * @return 图片的base64编码
	 */
	public static String image2Base64(String imgFile) throws IOException {
		try (InputStream inputStream = new FileInputStream(imgFile)) {
			byte[] data = new byte[inputStream.available()];
			inputStream.read(data);
			return Base64.encodeBase64String(data);
		}
	}
}
