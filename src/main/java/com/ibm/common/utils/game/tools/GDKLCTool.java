package com.ibm.common.utils.game.tools;
import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_gdklc.entity.IbmRepDrawGdklc;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_gdklc.entity.IbmRepGrabGdklc;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ReflectTool;

import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 广东快乐彩
 *
 * @Author: Dongming
 * @Date: 2020-04-28 16:37
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class GDKLCTool extends KlcTool {
	//region 开奖区

	private static volatile Map<String, String> lotteryMap = new HashMap<>(5);
	private static volatile Map<String, String> periodMap = new HashMap<>(5);

	public static boolean setLottery(String period, String lottery, String type) {
		if (!period.equals(periodMap.get(type))) {
			synchronized (XYFTTool.class) {
				if (!period.equals(periodMap.get(type))) {
					periodMap.put(type, period);
					lotteryMap.put(type, lottery);
					return true;
				}
			}
		}
		return false;
	}
	public static String getLottery(String period, String type) {
		if (period.equals(periodMap.get(type))) {
			return lotteryMap.get(type);
		}
		return null;
	}
	//endregion

	//region 结果数据
	/**
	 * 获取结果数据
	 *
	 * @param grabId    爬取id
	 * @param grabGdklc 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawGdklc draw(String grabId, IbmRepGrabGdklc grabGdklc) throws SocketException {
		String[] drawNumberStrs = grabGdklc.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 8 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawGdklc drawGdklc = new IbmRepDrawGdklc();
		drawGdklc.setRepGrabGdklcId(grabId);
		drawGdklc.setHandicapCode(grabGdklc.getHandicapCode());
		drawGdklc.setGameId(grabGdklc.getGameId());
		drawGdklc.setType(grabGdklc.getType());
		drawGdklc.setPeriod(grabGdklc.getPeriod());
		drawGdklc.setDrawTime(grabGdklc.getDrawTime());
		drawGdklc.setDrawTimeLong(grabGdklc.getDrawTimeLong());
		drawGdklc.setDrawNumber(grabGdklc.getDrawNumber());
		for (int i = 0; i < 8; i++) {
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "Size", size(drawNumbers[i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "SizeEn", sizeEn(drawNumbers[i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "Single", single(drawNumbers[i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "SingleEn", singleEn(drawNumbers[i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "TailSize", tailSize(drawNumbers[i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "TailSizeEn", tailSizeEn(drawNumbers[i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "SumSingle", sumSingle(drawNumbers[i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "SumSingleEn", sumSingleEn(drawNumbers[i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "Msw", msw(drawNumbers[i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "MswEn", mswEn(drawNumbers[i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "Position", position(drawNumbers[i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "PositionEn", positionEn(drawNumbers[i]));
		}
		for (int i = 0; i < 4; i++) {
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "Dragon", dragon(drawNumbers[i], drawNumbers[7 - i]));
			ReflectTool.set(drawGdklc, "setP" + (i + 1) + "DragonEn", dragonEn(drawNumbers[i], drawNumbers[7 - i]));
		}
		int total = 0;
		for (int i = 0; i < 8; i++) {
			total += drawNumbers[i];
		}
		drawGdklc.setTotal(total);
		drawGdklc.setTotalEn(NumberTool.getEn(total));
		drawGdklc.setTotalSize(sizeTotal(total));
		drawGdklc.setTotalSizeEn(sizeTotalEn(total));
		drawGdklc.setTotalSingle(singleTotal(total));
		drawGdklc.setTotalSingleEn(singleTotalEn(total));
		drawGdklc.setTotalTailSize(tailTotalSize(total));
		drawGdklc.setTotalTailSizeEn(tailTotalSizeEn(total));
		drawGdklc.setTotalDragon(dragon(drawNumbers[0], drawNumbers[7]));
		drawGdklc.setTotalDragonEn(dragonEn(drawNumbers[0], drawNumbers[7]));
		drawGdklc.setCreateTime(new Date());
		drawGdklc.setCreateTimeLong(drawGdklc.getCreateTime().getTime());
		drawGdklc.setUpdateTime(new Date());
		drawGdklc.setUpdateTimeLong(drawGdklc.getUpdateTime().getTime());
		drawGdklc.setState(IbmStateEnum.OPEN.name());
		drawGdklc.setDesc(IpTool.getLocalIpList().toString());
		return drawGdklc;
	}
	//endregion
}
