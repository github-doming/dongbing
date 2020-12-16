package com.ibm.common.utils.game.tools;

import com.ibm.common.enums.IbmStateEnum;
import com.ibm.follow.servlet.cloud.ibm_rep_draw_xyft.entity.IbmRepDrawXyft;
import com.ibm.follow.servlet.cloud.ibm_rep_grab_xyft.entity.IbmRepGrabXyft;
import org.doming.core.tools.*;

import java.net.SocketException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 幸运飞艇工具类
 * @Author: Dongming
 * @Date: 2019-10-10 14:33
 * @Email: job.dongming@foxmail.com
 * @Version: v1.0
 */
public class XYFTTool extends BallTool {

	//region 开奖区
	/**
	 *  开奖期数- 开奖结果
	 */
	private static volatile Map<String,String> lotteryMap=new HashMap<>(10);
    private static volatile Map<String,String> periodMap=new HashMap<>(10);
	public static String getLottery(String period,String type) {
	    if(period.equals(periodMap.get(type))){
            return lotteryMap.get(type);
        }
		return null;
	}
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
	//endregion

	/**
	 * 获取结果数据
	 *
	 * @param grabId   爬取id
	 * @param grabXyft 爬取内容
	 * @return 结果数据
	 */
	public static IbmRepDrawXyft draw(String grabId, IbmRepGrabXyft grabXyft) throws SocketException {
		String[] drawNumberStrs = grabXyft.getDrawNumber().split(",");
		Integer[] drawNumbers = NumberTool.intValue(drawNumberStrs);
		if (ContainerTool.isEmpty(drawNumbers) || 10 != drawNumbers.length) {
			return null;
		}
		IbmRepDrawXyft drawXyft = new IbmRepDrawXyft();
		drawXyft.setRepGrabXyftId(grabId);
        drawXyft.setHandicapCode(grabXyft.getHandicapCode());
		drawXyft.setGameId(grabXyft.getGameId());
        drawXyft.setType(grabXyft.getType());
		drawXyft.setPeriod(grabXyft.getPeriod());
		drawXyft.setDrawTime(grabXyft.getDrawTime());
		drawXyft.setDrawTimeLong(grabXyft.getDrawTimeLong());
		drawXyft.setDrawNumber(grabXyft.getDrawNumber());
		for (int i = 0; i < 10; i++) {
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "Number", drawNumberStrs[i]);
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "NumberEn", NumberTool.getEn(drawNumberStrs[i]));
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "Size", XYFTTool.size(drawNumbers[i]));
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "SizeEn", XYFTTool.sizeEn(drawNumbers[i]));
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "Single", XYFTTool.single(drawNumbers[i]));
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "SingleEn", XYFTTool.singleEn(drawNumbers[i]));
		}
		for (int i = 0; i < 5; i++) {
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "Dragon", XYFTTool.dragon(drawNumbers[i], drawNumbers[9 - i]));
			ReflectTool.set(drawXyft, "setP" + (i + 1) + "DragonEn",
					XYFTTool.dragonEn(drawNumbers[i], drawNumbers[9 - i]));
		}
		int championSum = drawNumbers[0] + drawNumbers[1];
		drawXyft.setChampionSumNunmber(championSum);
		drawXyft.setChampionSumNunmberEn(NumberTool.getEn(championSum));
		drawXyft.setChampionSumSize(XYFTTool.sizeChampionSum(championSum));
		drawXyft.setChampionSumSizeEn(XYFTTool.sizeChampionSumEn(championSum));
		drawXyft.setChampionSumSingle(XYFTTool.singleChampionSum(championSum));
		drawXyft.setChampionSumSingleEn(XYFTTool.singleChampionSumEn(championSum));
		drawXyft.setCreateTime(new Date());
		drawXyft.setCreateTimeLong(drawXyft.getCreateTime().getTime());
		drawXyft.setUpdateTime(new Date());
		drawXyft.setUpdateTimeLong(drawXyft.getUpdateTime().getTime());
		drawXyft.setState(IbmStateEnum.OPEN.name());
		drawXyft.setDesc(IpTool.getLocalIpList().toString());
		return drawXyft;
	}
	//endregion
	//  结算数据区
}
