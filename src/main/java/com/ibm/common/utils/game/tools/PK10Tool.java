package com.ibm.common.utils.game.tools;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_pk10.entity.IbmRepDrawPk10;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_pk10.entity.IbmRepGrabPk10;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ReflectTool;

import java.net.SocketException;
import java.util.Date;
/**
 * @Description: 北京赛车工具类
 * @Author: Dongming
 * @Date: 2019-10-10 14:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class PK10Tool extends BallTool {
	// TODO: 开奖区

	// 开奖区
	/**
	 *  开奖期数- 开奖结果
	 */
	private static volatile Integer period;
	private static volatile String lottery;
	public static String getLottery(Integer period) {
		if (period.equals(PK10Tool.period)) {
			return lottery;
		}
		return null;
	}
	public static boolean setLottery(Integer period, String lottery) {
		if (!period.equals(PK10Tool.period)) {
			synchronized (PK10Tool.class) {
				if (!period.equals(PK10Tool.period)) {
					PK10Tool.period = period;
					PK10Tool.lottery = lottery;
					return true;
				}
			}
		}
		return false;
	}
	// 开奖区


	//TODO  结算数据区
	/**
	 * 获取结果数据
	 *
	 * @param grabId   爬取id
	 * @param grabPk10 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawPk10 draw(String grabId, IbmRepGrabPk10 grabPk10) throws SocketException {
		String[] drawNumberStrs = grabPk10.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 10 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawPk10 drawPk10 = new IbmRepDrawPk10();
		drawPk10.setRepGrabPk10Id(grabId);
        drawPk10.setHandicapCode(grabPk10.getHandicapCode());
        drawPk10.setType(grabPk10.getType());
		drawPk10.setGameId(grabPk10.getGameId());
		drawPk10.setPeriod(grabPk10.getPeriod());
		drawPk10.setDrawTime(grabPk10.getDrawTime());
		drawPk10.setDrawTimeLong(grabPk10.getDrawTimeLong());
		drawPk10.setDrawNumber(grabPk10.getDrawNumber());
		for (int i = 0; i < 10; i++) {
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "Size", PK10Tool.size(drawNumbers[i]));
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "SizeEn", PK10Tool.sizeEn(drawNumbers[i]));
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "Single", PK10Tool.single(drawNumbers[i]));
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "SingleEn", PK10Tool.singleEn(drawNumbers[i]));
		}
		for (int i = 0; i < 5; i++) {
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "Dragon", PK10Tool.dragon(drawNumbers[i], drawNumbers[9 - i]));
			ReflectTool.set(drawPk10, "setP" + (i + 1) + "DragonEn",
					PK10Tool.dragonEn(drawNumbers[i], drawNumbers[9 - i]));
		}
		int championSum = drawNumbers[0] + drawNumbers[1];
		drawPk10.setChampionSumNunmber(championSum);
		drawPk10.setChampionSumNunmberEn(NumberTool.getEn(championSum));
		drawPk10.setChampionSumSize(PK10Tool.sizeChampionSum(championSum));
		drawPk10.setChampionSumSizeEn(PK10Tool.sizeChampionSumEn(championSum));
		drawPk10.setChampionSumSingle(PK10Tool.singleChampionSum(championSum));
		drawPk10.setChampionSumSingleEn(PK10Tool.singleChampionSumEn(championSum));
		drawPk10.setCreateTime(new Date());
		drawPk10.setCreateTimeLong(drawPk10.getCreateTime().getTime());
		drawPk10.setUpdateTime(new Date());
		drawPk10.setUpdateTimeLong(drawPk10.getUpdateTime().getTime());
		drawPk10.setState(IbmStateEnum.OPEN.name());
		drawPk10.setDesc(IpTool.getLocalIpList().toString());
		return drawPk10;
	}
	//  结算数据区
}
