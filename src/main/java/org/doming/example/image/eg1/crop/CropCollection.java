package org.doming.example.image.eg1.crop;
import org.doming.core.tools.ImageTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
/**
 * @Description: 裁剪收款码
 * @Author: Dongming
 * @Date: 2019-04-22 09:35
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CropCollection {

	public static void main1(String[] args) throws IOException {
		long time1 = System.currentTimeMillis();
		BufferedImage aliPay = ImageIO.read(new File("E:\\alipay.jpg"));
		int height = aliPay.getHeight();
		int width = aliPay.getWidth();
		int reWidth = 1080;
		int reHeight = reWidth * height / width;
		BufferedImage outImage = new BufferedImage(reWidth, reHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = outImage.createGraphics();
		graphics.drawImage(aliPay.getScaledInstance(reWidth, reHeight, Image.SCALE_REPLICATE), 0, 0, null);
		graphics.dispose();
		outImage = outImage.getSubimage(165, 525, 750, 750);
		File outFile = new File("E:\\alipay2.jpg");
		ImageIO.write(outImage, "jpg", outFile);

		System.out.println("消耗时间=" + (System.currentTimeMillis() - time1));

	}

	public static void main(String[] args) throws IOException {
		long time1 = System.currentTimeMillis();
		BufferedImage tencentPay = ImageTool.getImage("E:\\tencent.png");
		tencentPay = ImageTool.scaledImage(tencentPay,1080);
		tencentPay = ImageTool.cropImage(tencentPay,235,255,610);
		ImageTool.saveImage(tencentPay,"E:\\tencent2.png");
	}
}
