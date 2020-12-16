package com.cloud.user.app_verify_code.service;

import c.a.util.core.enums.bean.ChannelTypeEnum;
import com.cloud.user.app_verify_code.entity.AppVerifyCode;
import org.doming.core.common.jdbc.BaseServiceProxy;
import org.doming.core.enums.StateEnum;
import org.doming.core.tools.RandomTool;
import org.doming.core.tools.StringTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * APP认证_验证码 服务类
 *
 * @author Robot
 */
public class AppVerifyCodeService extends BaseServiceProxy {

	/**
	 * 保存APP认证_验证码 对象数据
	 *
	 * @param entity AppVerifyCode对象数据
	 */
	public String save(AppVerifyCode entity) throws Exception {
		return dao.save(entity);
	}

	/**
	 * 逻辑删除
	 *
	 * @param id 要删除app_verify_code 的 APP_SESSION_ID_主键id
	 */
	public void del(String id) throws Exception {
		String sql = "update app_verify_code set state_='DEL' where APP_SESSION_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 逻辑删除APP_SESSION_ID_主键id数组的数据
	 *
	 * @param idArray 要删除 app_verify_code 的 APP_SESSION_ID_数组
	 */
	public void delAll(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "update app_verify_code set state_='DEL' where APP_SESSION_ID_ in(" + stringBuilder.toString()
					+ ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 物理删除
	 *
	 * @param id 要删除 app_verify_code  的 APP_SESSION_ID_
	 */
	public void delPhysical(String id) throws Exception {
		String sql = "delete from app_verify_code where APP_SESSION_ID_=?";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(id);
		dao.execute(sql, parameterList);
	}

	/**
	 * 物理删除APP_SESSION_ID_主键id数组的数据
	 *
	 * @param idArray 要删除app_verify_code 的 APP_SESSION_ID_数组
	 */
	public void delAllPhysical(String[] idArray) throws Exception {
		if (idArray != null) {
			StringBuilder stringBuilder = new StringBuilder();
			for (String id : idArray) {
				stringBuilder.append("'").append(id).append("'").append(",");
			}
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			String sql = "delete from app_verify_code where APP_SESSION_ID_ in(" + stringBuilder.toString() + ")";
			dao.execute(sql, null);
		}
	}

	/**
	 * 更新AppVerifyCode实体信息
	 *
	 * @param entity APP认证_验证码 实体
	 */
	public void update(AppVerifyCode entity) throws Exception {
		dao.update(entity);
	}

	/**
	 * 根据app_verify_code表主键查找 APP认证_验证码 实体
	 *
	 * @param id app_verify_code 主键
	 * @return APP认证_验证码 实体
	 */
	public AppVerifyCode find(String id) throws Exception {
		return dao.find(AppVerifyCode.class, id);
	}

	/**
	 * 创建一个新的验证码
	 *
	 * @param sessionId   会话主键
	 * @param channelType 通道类型
	 * @return 验证码
	 */
	public String newVerifyCode(String sessionId, ChannelTypeEnum channelType) throws Exception {
		String sql = "SELECT APP_VERIFY_CODE_ID_ FROM `app_verify_code` where APP_SESSION_ID_ = ? and STATE_ = ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(sessionId);
		parameterList.add(StateEnum.OPEN.name());
		String verifyCodeId = super.findString("APP_VERIFY_CODE_ID_", sql, parameterList);

		Date nowTime = new Date();
		String verifyCode = RandomTool.getVerifyCode();
		if (StringTool.notEmpty(verifyCodeId)) {
			parameterList.clear();
			sql = "UPDATE app_verify_code set CODE_ = ?,UPDATE_TIME_ = ?,UPDATE_TIME_LONG_ = ? where APP_VERIFY_CODE_ID_ = ?";
			parameterList.add(verifyCode);
			parameterList.add(nowTime);
			parameterList.add(System.currentTimeMillis());
			parameterList.add(verifyCodeId);
			super.execute(sql, parameterList);
		} else {
			AppVerifyCode appVerifyCode = new AppVerifyCode();
			appVerifyCode.setAppSessionId(sessionId);
			appVerifyCode.setCode(verifyCode);
			appVerifyCode.setType(channelType.name());
			appVerifyCode.setCreateTime(nowTime);
			appVerifyCode.setCreateTimeLong(System.currentTimeMillis());
			appVerifyCode.setUpdateTime(nowTime);
			appVerifyCode.setUpdateTimeLong(System.currentTimeMillis());
			appVerifyCode.setState(StateEnum.OPEN.name());
			save(appVerifyCode);
		}
		return verifyCode;
	}

	/**
	 * 获取验证编码
	 * @param session 会话主键
	 * @param channelType 会话通道
	 * @return 验证编码
	 */
	public String findVerifyCode(String session, ChannelTypeEnum channelType) throws SQLException {
		String sql =  "SELECT CODE_ FROM app_verify_code where APP_SESSION_ID_ = ? and TYPE_ = ? ";
		List<Object> parameterList = new ArrayList<>();
		parameterList.add(session);
		parameterList.add(channelType.name());
		return super.findString("CODE_",sql,parameterList);
	}


	//region 图片验证码
	/**
	 * 获取验证码输出流
	 *
	 * @param verifyCode 验证码
	 * @param width      验证码宽
	 * @param height     验证码高
	 * @return 验证码输出流
	 */
	public BufferedImage getImageBuffer(String verifyCode, int width, int height) {
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

		int size = width / 4 - 1;

		List<int[]> canUsedColors = init();
		for (int i = 1; i <= verifyCode.length(); i++) {
			String singleRand = verifyCode.substring(i - 1, i);
			int[] currentColor = canUsedColors.get(random.nextInt(canUsedColors.size()));
			graphics.setColor(new Color(currentColor[0], currentColor[1], currentColor[2]));
			graphics.drawString(singleRand, size * (i - 1) + 1, height * 9 / 10);
		}
		graphics.dispose();
		return image;
	}

	/**
	 * 获取随机颜色
	 *
	 * @param fc 最低颜色域
	 * @param bc 最高颜色域
	 * @return 随机颜色
	 */
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

	/**
	 * 初始化验证码图片背景
	 *
	 * @return 验证码图片背景
	 */
	private List<int[]> init() {
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
	//endregion
}
