package com.ibm.common.utils.game.tools;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_xync.entity.IbmRepDrawXync;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_xync.entity.IbmRepGrabXync;
import org.doming.core.tools.ContainerTool;
import org.doming.core.tools.IpTool;
import org.doming.core.tools.NumberTool;
import org.doming.core.tools.ReflectTool;

import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 幸运农场
 * @Author: Dongming
 * @Date: 2020-04-22 17:01
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class XYNCTool extends KlcTool {

	//region 开奖区

	private static volatile Map<String,String> lotteryMap=new HashMap<>(5);
	private static volatile Map<String,String> periodMap=new HashMap<>(5);

	public static boolean setLottery(String period, String lottery,String type) {
		if(!period.equals(periodMap.get(type))){
			synchronized (XYFTTool.class) {
				if(!period.equals(periodMap.get(type))){
					periodMap.put(type,period);
					lotteryMap.put(type,lottery);
					return true;
				}
			}
		}
		return false;
	}
	public static String getLottery(String period, String type) {
		if(period.equals(periodMap.get(type))){
			return lotteryMap.get(type);
		}
		return null;
	}
	//endregion

	//region 结果数据
	/**
	 * 获取结果数据
	 *
	 * @param grabId   爬取id
	 * @param grabXync 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawXync draw(String grabId, IbmRepGrabXync grabXync) throws SocketException {
		String[] drawNumberStrs = grabXync.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 8 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawXync drawXync = new IbmRepDrawXync();
		drawXync.setIbmRepDrawXyncId(grabId);
		drawXync.setHandicapCode(grabXync.getHandicapCode());
		drawXync.setGameId(grabXync.getGameId());
		drawXync.setType(grabXync.getType());
		drawXync.setPeriod(grabXync.getPeriod());
		drawXync.setDrawTime(grabXync.getDrawTime());
		drawXync.setDrawTimeLong(grabXync.getDrawTimeLong());
		drawXync.setDrawNumber(grabXync.getDrawNumber());
		for (int i = 0; i < 8; i++) {
			ReflectTool.set(drawXync, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawXync, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "Size", size(drawNumbers[i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "SizeEn", sizeEn(drawNumbers[i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "Single", single(drawNumbers[i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "SingleEn", singleEn(drawNumbers[i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "TailSize", tailSize(drawNumbers[i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "TailSizeEn", tailSizeEn(drawNumbers[i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "SumSingle", sumSingle(drawNumbers[i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "SumSingleEn", sumSingleEn(drawNumbers[i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "Msw", msw(drawNumbers[i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "MswEn", mswEn(drawNumbers[i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "Position", position(drawNumbers[i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "PositionEn", positionEn(drawNumbers[i]));
		}
		for (int i = 0; i < 4; i++) {
			ReflectTool.set(drawXync, "setP" + (i + 1) + "Dragon", dragon(drawNumbers[i], drawNumbers[7 - i]));
			ReflectTool.set(drawXync, "setP" + (i + 1) + "DragonEn", dragonEn(drawNumbers[i], drawNumbers[7 - i]));
		}
		int total = 0;
		for (int i = 0; i < 8; i++) {
			total += drawNumbers[i];
		}
		drawXync.setTotal(total);
		drawXync.setTotalEn(NumberTool.getEn(total));
		drawXync.setTotalSize(sizeTotal(total));
		drawXync.setTotalSizeEn(sizeTotalEn(total));
		drawXync.setTotalSingle(singleTotal(total));
		drawXync.setTotalSingleEn(singleTotalEn(total));
		drawXync.setTotalTailSize(tailTotalSize(total));
		drawXync.setTotalTailSizeEn(tailTotalSizeEn(total));
		drawXync.setTotalDragon(dragon(drawNumbers[0], drawNumbers[7]));
		drawXync.setTotalDragonEn(dragonEn(drawNumbers[0], drawNumbers[7]));
		drawXync.setCreateTime(new Date());
		drawXync.setCreateTimeLong(drawXync.getCreateTime().getTime());
		drawXync.setUpdateTime(new Date());
		drawXync.setUpdateTimeLong(drawXync.getUpdateTime().getTime());
		drawXync.setState(IbmStateEnum.OPEN.name());
		drawXync.setDesc(IpTool.getLocalIpList().toString());
		return drawXync;
	}

	//endregion



}
