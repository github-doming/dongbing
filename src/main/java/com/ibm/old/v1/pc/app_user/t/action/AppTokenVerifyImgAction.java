package com.ibm.old.v1.pc.app_user.t.action;
import c.a.util.core.enums.ReturnCodeEnum;
import com.ibm.old.v1.common.doming.core.BaseAppAction;
import com.ibm.old.v1.common.doming.core.JsonResultBeanPlus;
import com.ibm.old.v1.pc.app_user.t.controller.AppTokenController;
import net.sf.json.JSONObject;
import org.doming.core.tools.StringTool;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * @Description: 验证码图片
 * @Author: zjj
 * @Date: 2019-05-05 10:43
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class AppTokenVerifyImgAction extends BaseAppAction {

	private List<int[]> canUsedColors;

	@Override public String run() throws Exception {
		JsonResultBeanPlus bean = new JsonResultBeanPlus();
		super.findJson();
		if (StringTool.isEmpty(json)) {
			return400Session(bean);
			return this.returnJson(bean);
		}
		JSONObject jObj = JSONObject.fromObject(json);
		if (StringTool.isEmpty(jObj.get("session"))) {
			return400Session(bean);
			return this.returnJson(bean);
		}
		AppTokenController controller = new AppTokenController();
		String code = controller.verifyCode(jObj.getString("session"));

		// 初始化
		this.init();
		// 返回图片
		response.setContentType("image/jpeg");
		int iWidth = 60;
		int iHeight = 18;
		BufferedImage image = new BufferedImage(iWidth, iHeight, 1);
		Graphics g = image.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, iWidth, iHeight);
		g.setColor(Color.BLACK);
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
		g.setFont(new Font("Arial", Font.BOLD | Font.ITALIC, 18));
		for (int i = 1; i <= 4; i++) {
			String singleRand = code.substring(i - 1, i);
			int[] currentColor = canUsedColors.get(random.nextInt(canUsedColors.size()));
			g.setColor(new java.awt.Color(currentColor[0], currentColor[1], currentColor[2]));
			g.drawString(singleRand, 13 * (i - 1) + 5, 16);
		}
		g.dispose();
		ImageIO.write(image, "JPEG", response.getOutputStream());
		return null;
	}

	private Color getRandColor(int fc, int bc) {
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

	public void init() throws ServletException {
		canUsedColors = new ArrayList<>(13);
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
	}

	private void return400Session(JsonResultBeanPlus bean) {
		bean.setCode(ReturnCodeEnum.app400Session.toString());
		bean.setMsg(ReturnCodeEnum.app400Session.getMsgCn());
		bean.setCodeSys(ReturnCodeEnum.code400.toString());
		bean.setMessageSys(ReturnCodeEnum.code400.getMsgCn());
	}
}
