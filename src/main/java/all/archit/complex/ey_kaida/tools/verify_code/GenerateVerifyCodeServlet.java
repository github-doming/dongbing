// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GenerateVerifyCodeServlet.java
package all.archit.complex.ey_kaida.tools.verify_code;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import c.a.util.core.verify_code.VerifyCodeUtil;
import c.a.util.redis.RedisUtil;
import redis.clients.jedis.Jedis;
public class GenerateVerifyCodeServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "image/jpeg";
	private List canUsedColors;
	public GenerateVerifyCodeServlet() {
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			RedisUtil redisUtil=RedisUtil.findInstance();
			Jedis jedis = redisUtil.findJedis();
			String code = VerifyCodeUtil.findVerifyCode();
			request.getSession().setAttribute("verifyCode", code);
			//redisUtil.saveSysSessionVerifyCode(jedis, sessionId, code);
			response.setContentType("image/jpeg");
			int iWidth = 60;
			int iHeight = 18;
			java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(
					iWidth, iHeight, 1);
			java.awt.Graphics g = image.getGraphics();
			g.setColor(java.awt.Color.white);
			g.fillRect(0, 0, iWidth, iHeight);
			g.setColor(java.awt.Color.BLACK);
			g.drawRect(0, 0, iWidth - 1, iHeight - 1);
			Random random = new Random();
			for (int iIndex = 0; iIndex < 20; iIndex++) {
				int x = random.nextInt(iWidth);
				int y = random.nextInt(iHeight);
				g.setColor(getRandColor(100, 200));
				g.drawLine(x, y, x, y);
			}
			for (int iIndex = 0; iIndex < 20; iIndex++) {
				int x = random.nextInt(iWidth);
				int y = random.nextInt(iHeight);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.setColor(getRandColor(100, 200));
				g.drawLine(x, y, x + xl, y + yl);
			}
			g.setColor(getRandColor(0, 100));
			g.setFont(new java.awt.Font("Arial", 3, 18));
			for (int i = 1; i <= 4; i++) {
				String singleRand = code.substring(i - 1, i);
				int currentColor[] = (int[]) canUsedColors.get(random
						.nextInt(canUsedColors.size()));
				g.setColor(new java.awt.Color(currentColor[0], currentColor[1],
						currentColor[2]));
				g.drawString(singleRand, 13 * (i - 1) + 5, 16);
			}
			g.dispose();
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	java.awt.Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new java.awt.Color(r, g, b);
	}
	public void init() throws ServletException {
		canUsedColors = new ArrayList();
		canUsedColors.add(new int[]{255, 128, 128});
		int ai[] = new int[3];
		ai[0] = 255;
		canUsedColors.add(ai);
		canUsedColors.add(new int[]{128, 64, 64});
		int ai1[] = new int[3];
		ai1[0] = 128;
		canUsedColors.add(ai1);
		int ai2[] = new int[3];
		ai2[0] = 64;
		canUsedColors.add(ai2);
		canUsedColors.add(new int[3]);
		canUsedColors.add(new int[]{255, 128, 64});
		int ai3[] = new int[3];
		ai3[0] = 255;
		ai3[1] = 128;
		canUsedColors.add(ai3);
		int ai4[] = new int[3];
		ai4[0] = 128;
		ai4[1] = 64;
		canUsedColors.add(ai4);
		int ai5[] = new int[3];
		ai5[0] = 128;
		ai5[1] = 128;
		canUsedColors.add(ai5);
		int ai6[] = new int[3];
		ai6[1] = 255;
		canUsedColors.add(ai6);
		int ai7[] = new int[3];
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
	}
	public void destroy() {
	}
}
