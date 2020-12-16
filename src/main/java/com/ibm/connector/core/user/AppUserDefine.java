package com.ibm.connector.core.user;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * @Description: 用户工具类
 * @Author: Dongming
 * @Date: 2019-08-28 16:28
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class AppUserDefine {


	/**
	 * 获取验证码输出流
	 *
	 * @param verifyCode 验证码
	 * @param width      验证码宽
	 * @param height     验证码高
	 * @return 验证码输出流
	 */
	public static BufferedImage getImageBuffer(String verifyCode, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, 1);
		Graphics graphics = image.getGraphics();

		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, width, height);
		graphics.setColor(Color.BLACK);
		graphics.drawRect(0, 0, width - 1, height - 1);
		Random random = new Random();
		for (int iIndex = 0; iIndex < 20; iIndex++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			graphics.setColor(getRandColor(100, 200));
			graphics.drawLine(x, y, x, y);
		}
		for (int iIndex = 0; iIndex < 20; iIndex++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			graphics.setColor(getRandColor(100, 200));
			graphics.drawLine(x, y, x + xl, y + yl);
		}
		graphics.setColor(getRandColor(0, 100));
		graphics.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, height));

		int size = width /4 -1;

		List<int[]> canUsedColors = AppUserDefine.init();
		for (int i = 1; i <= verifyCode.length(); i++) {
			String singleRand = verifyCode.substring(i - 1, i);
			int[] currentColor = canUsedColors.get(random.nextInt(canUsedColors.size()));
			graphics.setColor(new Color(currentColor[0], currentColor[1], currentColor[2]));
			graphics.drawString(singleRand, size * (i - 1) + 1, height*9/10);
		}
		graphics.dispose();
		return image;
	}

	/**
	 * 初始化验证码图片背景
	 * @return 验证码图片背景
	 */
	private static List<int[]> init() {
		List<int[]> canUsedColors = new ArrayList<>(13);
		canUsedColors.add(new int[]{255, 128, 128});
		int[] ai = new int[3];
		ai[0] = 255;
		canUsedColors.add(ai);
		canUsedColors.add(new int[]{128, 64, 64});
		int[] ai1 = new int[3];
		ai1[0] = 128;
		canUsedColors.add(ai1);
		int[] ai2 = new int[3];
		ai2[0] = 64;
		canUsedColors.add(ai2);
		canUsedColors.add(new int[3]);
		canUsedColors.add(new int[]{255, 128, 64});
		int[] ai3 = new int[3];
		ai3[0] = 255;
		ai3[1] = 128;
		canUsedColors.add(ai3);
		int[] ai4 = new int[3];
		ai4[0] = 128;
		ai4[1] = 64;
		canUsedColors.add(ai4);
		int[] ai5 = new int[3];
		ai5[0] = 128;
		ai5[1] = 128;
		canUsedColors.add(ai5);
		int[] ai6 = new int[3];
		ai6[1] = 255;
		canUsedColors.add(ai6);
		int[] ai7 = new int[3];
		ai7[1] = 128;
		canUsedColors.add(ai7);
		canUsedColors.add(new int[]{0, 128, 128});
		canUsedColors.add(new int[]{0, 128, 64});
		canUsedColors.add(new int[]{0, 64, 64});
		canUsedColors.add(new int[]{0, 64, 128});
		canUsedColors.add(new int[]{0, 128, 255});
		canUsedColors.add(new int[]{0, 128, 192});
		canUsedColors.add(new int[]{128, 128, 255});
		canUsedColors.add(new int[]{255, 128, 255});
		canUsedColors.add(new int[]{255, 0, 255});
		canUsedColors.add(new int[]{255, 0, 128});
		canUsedColors.add(new int[]{128, 0, 255});
		canUsedColors.add(new int[]{64, 0, 128});
		return canUsedColors;
	}

	/**
	 * 获取随机颜色
	 * @param fc 最低颜色域
	 * @param bc 最高颜色域
	 * @return 随机颜色
	 */
	private static Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new java.awt.Color(r, g, b);
	}

}
