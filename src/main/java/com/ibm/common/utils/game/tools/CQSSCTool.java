package com.ibm.common.utils.game.tools;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_cqssc.entity.IbmRepDrawCqssc;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_cqssc.entity.IbmRepGrabCqssc;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ReflectTool;

import java.net.SocketException;
import java.util.Date;
/**
 * @Description: 重庆时时彩工具类
 * @Author: Dongming
 * @Date: 2019-10-10 14:23
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class CQSSCTool extends SSCTool{
	// TODO: 开奖区

	// 开奖区
	/**
	 *  开奖期数- 开奖结果
	 */
	private static volatile String period;
	private static volatile String lottery;
	public static String getLottery(String period) {
		if (period.equals(CQSSCTool.period)) {
			return lottery;
		}
		return null;
	}
	public static boolean setLottery(String period, String lottery) {
		if (!period.equals(CQSSCTool.period)) {
			synchronized (CQSSCTool.class) {
				if (!period.equals(CQSSCTool.period)) {
					CQSSCTool.period = period;
					CQSSCTool.lottery = lottery;
					return true;
				}
			}
		}
		return false;
	}
	// 开奖区

	// TODO 结算数据区

	/**
	 * 获取结果数据
	 *
	 * @param grabId    爬取id
	 * @param grabCqssc 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawCqssc draw(String grabId, IbmRepGrabCqssc grabCqssc) throws SocketException {
		String[] drawNumberStrs = grabCqssc.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 5 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawCqssc drawCqssc = new IbmRepDrawCqssc();
		drawCqssc.setRepGrabCqsscId(grabId);
        drawCqssc.setHandicapCode(grabCqssc.getHandicapCode());
		drawCqssc.setGameId(grabCqssc.getGameId());
		drawCqssc.setType(grabCqssc.getType());
		drawCqssc.setPeriod(grabCqssc.getPeriod());
		drawCqssc.setDrawTime(grabCqssc.getDrawTime());
		drawCqssc.setDrawTimeLong(grabCqssc.getDrawTimeLong());
		drawCqssc.setDrawNumber(grabCqssc.getDrawNumber());
		for (int i = 0; i < 5; i++) {
			ReflectTool.set(drawCqssc, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawCqssc, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawCqssc, "setP" + (i + 1) + "Size", CQSSCTool.size(drawNumbers[i]));
			ReflectTool.set(drawCqssc, "setP" + (i + 1) + "SizeEn", CQSSCTool.sizeEn(drawNumbers[i]));
			ReflectTool.set(drawCqssc, "setP" + (i + 1) + "Single", CQSSCTool.single(drawNumbers[i]));
			ReflectTool.set(drawCqssc, "setP" + (i + 1) + "SingleEn", CQSSCTool.singleEn(drawNumbers[i]));
		}
		drawCqssc.setDragonTiger(CQSSCTool.dragon(drawNumbers[0], drawNumbers[4]));
		drawCqssc.setDragonTigerEn(CQSSCTool.dragonEn(drawNumbers[0], drawNumbers[4]));

		int total = 0;
		for (int i = 0; i < 5; i++) {
			total += drawNumbers[i];
		}
		drawCqssc.setTotal(total);
		drawCqssc.setTotalEn(NumberTool.getEn(total));
		drawCqssc.setTotalSingle(CQSSCTool.single(total));
		drawCqssc.setTotalSingleEn(CQSSCTool.singleEn(total));
		drawCqssc.setTotalSize(CQSSCTool.sizeTotal(total));
		drawCqssc.setTotalSizeEn(CQSSCTool.sizeTotalEn(total));
		drawCqssc.setTop(CQSSCTool.threeBalls(drawNumbers[0], drawNumbers[1], drawNumbers[2]));
		drawCqssc.setTopEn(CQSSCTool.threeBallsEn(drawCqssc.getTop()));
		drawCqssc.setCentre(CQSSCTool.threeBalls(drawNumbers[1], drawNumbers[2], drawNumbers[3]));
		drawCqssc.setCentreEn(CQSSCTool.threeBallsEn(drawCqssc.getCentre()));
		drawCqssc.setLater(CQSSCTool.threeBalls(drawNumbers[2], drawNumbers[3], drawNumbers[4]));
		drawCqssc.setLaterEn(CQSSCTool.threeBallsEn(drawCqssc.getLater()));
		drawCqssc.setCreateTime(new Date());
		drawCqssc.setCreateTimeLong(drawCqssc.getCreateTime().getTime());
		drawCqssc.setUpdateTime(new Date());
		drawCqssc.setUpdateTimeLong(drawCqssc.getUpdateTime().getTime());
		drawCqssc.setState(IbmStateEnum.OPEN.name());
		drawCqssc.setDesc(IpTool.getLocalIpList().toString());
		return drawCqssc;
	}
	//  结算数据区

}
